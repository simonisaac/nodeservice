package org.sri.nodeservice.web.util;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.sri.nodeservice.web.security.NSAuthenticationSuccessHandler;

public class HttpUtils {

	public static Log log = LogFactory.getLog(HttpUtils.class.getName());

	public static void traceParameters(HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		Map<String, String[]> paramMap = request.getParameterMap();
		StringBuilder bill = new StringBuilder();
		for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
			for (int i = 0; i < entry.getValue().length; i++) {
				bill.append(entry.getKey());
				bill.append("=");
				bill.append(entry.getValue()[i]);
				bill.append("\n");
			}
		}
		log.debug(bill);
		System.out.println(bill);
	}

	public static void postJson(HttpServletRequest request, HttpServletResponse response, String jsonUrl) throws ClientProtocolException, IOException {

		response.setContentType("application/json");

		// separate the 'json' parameter from the parameters.
		String jsonString = null;
		Map<String, String[]> filteredMap = new HashMap<String, String[]>();

		HttpSession session = ((HttpServletRequest) request).getSession();
		
		String sessionToken = (String) session.getAttribute("sngSession");
		//TODO filteredMap.put("sessionToken", new String[] { sessionToken });
		filteredMap.put("sessionToken", new String[] { "jsid222" });

		@SuppressWarnings("unchecked")
		Map<String, String[]> paramMap = request.getParameterMap();
		//StringBuilder bill = new StringBuilder();
		for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
			if (entry.getKey().equals("json")) {
				jsonString = entry.getValue()[0];
			}
			else {
				filteredMap.put(entry.getKey(), entry.getValue());
			}
		}

		String urlString = getURLString(filteredMap, jsonUrl);
		
		// create request
		HttpPost httppost = new HttpPost(urlString);

		//Get Basic Auth details
		String basicAuth = (String)session.getAttribute(NSAuthenticationSuccessHandler.BASIC_AUTH_KEY);
		if(basicAuth != null){
			httppost.setHeader("Authorization", "Basic "+basicAuth);
		}

		if (jsonString != null) {
			StringEntity myEntity = new StringEntity(jsonString, "UTF-8");
			httppost.setEntity(myEntity);
		}

		// remote call
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse remoteResponse = httpClient.execute(httppost);

		HttpEntity rEntity = remoteResponse.getEntity();
		String jsonOutString = null;
		
		jsonOutString = EntityUtils.toString(rEntity);
		
		// write to the response.
		response.getOutputStream().print(jsonOutString);
		response.getOutputStream().flush();
	}
	
	public static void postMultipart(HttpServletRequest request,
			HttpServletResponse response, String jsonUrl)
			throws ClientProtocolException, IOException {

		DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
		/* Set the size threshold, above which content will be stored on disk. */
		fileItemFactory.setSizeThreshold(1 * 1024 * 1024); // 1 MB
		/* Set the temporary directory to store the uploaded files of size above threshold.*/
		fileItemFactory.setRepository(new File("."));
		ServletFileUpload uploadHandler = new ServletFileUpload(fileItemFactory);

		// Create proxy upload
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

		HttpPost post = new HttpPost(jsonUrl);
		HttpSession session = ((HttpServletRequest) request).getSession();
		// Get Basic Auth details
		String basicAuth = (String) session.getAttribute(NSAuthenticationSuccessHandler.BASIC_AUTH_KEY);
		if (basicAuth != null) {
			post.setHeader("Authorization", "Basic " + basicAuth);
		}

		MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
		try {
			/* Parse the request */
			@SuppressWarnings("rawtypes")
			List items = uploadHandler.parseRequest(request);
			@SuppressWarnings("rawtypes")
			Iterator itr = items.iterator();
			while (itr.hasNext()) {
				FileItem item = (FileItem) itr.next();
				/* Handle Form Fields. */
				if (item.isFormField()) {
					log.info("File Name = " + item.getFieldName()
							+ ", Value = " + item.getString());
					entity.addPart( item.getFieldName(), new StringBody( item.getString(), "text/plain", Charset.forName( "UTF-8" )));
				} else {
					// Handle Uploaded files.
					log.info("Field Name = " + item.getFieldName()
							+ ", File Name = " + item.getName()
							+ ", Content type = " + item.getContentType()
							+ ", File Size = " + item.getSize());
					entity.addPart(item.getFieldName(), new InputStreamBody(item.getInputStream(), item.getName()));
				}
			}

			post.setEntity(entity);
			// Here we go!
			String stringResponse = EntityUtils.toString(client.execute(post)
					.getEntity(), "UTF-8");
			client.getConnectionManager().shutdown();

			// write to the response.
			response.setContentType("text/html");
			response.getOutputStream().print(stringResponse);
			response.getOutputStream().flush();
		} catch (FileUploadException ex) {
			log.error("Error encountered while parsing the request", ex);
		} catch (Exception ex) {
			log.error("Error encountered while uploading file", ex);
		}

	}

	public static String getURLString(Map<String, String[]> paramMap, String path) {
		log.debug("---- Generate Redirect URL ----");

		StringBuilder queryStringBill = new StringBuilder();
		StringBuilder urlBill = new StringBuilder();

		urlBill.append(path);
		urlBill.append("?");

		String separator = "";
		for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
			for (int i = 0; i < entry.getValue().length; i++) {
				queryStringBill.append(separator);
				queryStringBill.append(entry.getKey());
				queryStringBill.append("=");

				String value = entry.getValue()[i];
				String newValue = value.replaceAll("&", "&amp;");
				queryStringBill.append(newValue);

				separator = "&";
			}
		}

		log.debug("Unencoded queryString : " + queryStringBill.toString());
		String encodedQueryString = URLEncoder.encode(queryStringBill.toString());
		log.debug("Encoded queryString   : " + encodedQueryString);

		urlBill.append(encodedQueryString);
		log.debug("Encoded urlString   : " + urlBill.toString());

		return urlBill.toString();
	}
}
