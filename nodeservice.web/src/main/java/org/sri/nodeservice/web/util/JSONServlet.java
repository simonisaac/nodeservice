package org.sri.nodeservice.web.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * Servlet implementation class JSONServlet
 */
public class JSONServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	public static Log log = LogFactory.getLog(JSONServlet.class.getName());
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JSONServlet() {
        super();
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try {
			initSystemProperties(config);
		} catch (Throwable e) {
			e.printStackTrace();
			throw new ServletException(
					"Not able to initialise the system properties.\n Please add the SYS_PROPERTIES_FILES parameter in web.xml \n");
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("doGet");
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("doPost");
		HttpUtils.traceParameters(request);
		
		String jsonUrl = System.getProperty("json.node.delegate.url");
		
		if(request.getContentType() != null && request.getContentType().contains("multipart/form-data")){
			HttpUtils.postMultipart(request, response, jsonUrl);
		}else{
			HttpUtils.postJson(request, response, jsonUrl);
		}
	}
	
	private void initSystemProperties(ServletConfig config) throws Throwable {
		String propsFileName = config.getInitParameter("SYS_PROPERTIES_FILES");
		Resource resource = new ClassPathResource(propsFileName);

		Properties properties = new Properties();
		properties.load(new FileInputStream(resource.getURL().getFile()));

		for (Iterator iter = properties.keySet().iterator(); iter.hasNext();) {
			String propName = (String) iter.next();
			log("Setting System Property: key -> " + propName + " value -> " + properties.getProperty(propName), null);
			System.setProperty(propName, properties.getProperty(propName));
		}
	}

}
