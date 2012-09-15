package org.sri.nodeservice.core.nodemgr.util.transform;

import java.util.List;

import org.sri.nodeservice.core.nodedef.service.model.LanguageDefTO;
import org.sri.nodeservice.core.nodemgr.nodedef.NodeDef;
import org.sri.nodeservice.core.nodemgr.nodedef.NodeDefMgr;
import org.sri.nodeservice.core.nodemgr.nodedef.attr.type.TypeDef;
import org.sri.nodeservice.core.nodemgr.nodedef.attr.type.TypeExDef;

/**
 * @author sisaac
 * 
 */
public class LanguageLoader {

	private NodeDefTOTransformer nodeDefTransformer;

	private NodeDefMgr nodeDefMgr;

	public LanguageLoader(NodeDefTOTransformer nodeDefTransformer,
			NodeDefMgr nodeDefMgr) {
		super();
		this.nodeDefTransformer = nodeDefTransformer;
		this.nodeDefMgr = nodeDefMgr;
	}

	public void loadLanguage(LanguageDefTO languageTO) throws RuntimeException{

		// first transform the types.
		List<TypeExDef> newTypes = nodeDefTransformer
				.transformTypeExDefTOs(languageTO.getTypeExDef());

		// now register the types. This must be done first as the node defs may
		// depend on the new types.
		// Fail the language load in case a Type is already register.
		boolean fail = false;
		String existingTypeExDef = "";
		for (TypeExDef typeExDef : newTypes) {
			TypeDef typeDef = null;
			try {
				typeDef = this.nodeDefMgr.getTypeDefMgr().getTypeDef(
						typeExDef.getId());
			} catch (Exception e) {
				// Do Nothing ..Swallow
			}

			if (typeDef != null) {
				fail = true;
				existingTypeExDef = typeExDef.getId();
				break;
			}
		}

		if (fail) {
			throw new RuntimeException(
					"Load language process is terminated. Type definition ["
							+ existingTypeExDef + "] already registered.");
		}

		this.nodeDefMgr.getTypeDefMgr().registerTypeExDefs(newTypes);

		// now the node defs
		List<NodeDef> newNodes = this.nodeDefTransformer
				.transformNodeDefTO(languageTO.getNodeDef());
		this.nodeDefMgr.addNodeDefinitions(newNodes);
	}
}
