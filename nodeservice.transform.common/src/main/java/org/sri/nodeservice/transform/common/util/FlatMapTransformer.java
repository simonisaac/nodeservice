package org.sri.nodeservice.transform.common.util;

import java.util.Map;

import flexjson.JSONContext;
import flexjson.Path;
import flexjson.TypeContext;
import flexjson.transformer.AbstractTransformer;
import flexjson.transformer.TransformerWrapper;

/**
 * 
 * @author sisaac
 *
 */
public class FlatMapTransformer extends AbstractTransformer {

	public void transform(Object object) {
		JSONContext context = getContext();
		Path path = context.getPath();
		// path.pop();
		Map map = (Map) object;

		TypeContext typeContext = getContext().peekTypeContext();
		for (Object key : map.keySet()) {

			path.enqueue((String) key);

			if (context.isIncluded((String) key, map.get(key))) {

				TransformerWrapper transformer = (TransformerWrapper) context.getTransformer(map.get(key));

				if (!transformer.isInline()) {
					if (!typeContext.isFirst()) {
						getContext().writeComma();
					}
					typeContext.setFirst(false);
					getContext().writeName(key.toString());
				}

				typeContext.setPropertyName(key.toString());

				transformer.transform(map.get(key));

			}

			path.pop();

		}
		// getContext().writeCloseObject();
	}

	@Override
	public Boolean isInline() {
		return Boolean.TRUE;
	}
}
