package org.sri.nodeservice.core.nodemgr.mongo.util;


import org.sri.nodeservice.core.nodedef.service.model.AttrDefMap;
import org.sri.nodeservice.core.nodedef.service.model.AttrDefTO;
import org.sri.nodeservice.core.nodedef.service.model.LanguageDefTO;
import org.sri.nodeservice.core.nodedef.service.model.NodeDefTO;
import org.sri.nodeservice.core.nodedef.service.model.NodeSetDefMap;
import org.sri.nodeservice.core.nodedef.service.model.NodeSetDefTO;
import org.sri.nodeservice.core.nodedef.service.model.TypeConstraintDefTO;
import org.sri.nodeservice.core.nodedef.service.model.TypeConstraintParameterTO;
import org.sri.nodeservice.core.nodedef.service.model.TypeExDefTO;
import org.sri.nodeservice.core.nodemgr.nodedef.attr.constraint.TypeConstraintMaxLength;
import org.sri.nodeservice.core.nodemgr.nodedef.attr.constraint.TypeConstraintMinLength;
import org.sri.nodeservice.core.nodemgr.nodedef.attr.type.BaseTypes;

public class TstLanguageDefReader {

	public static String SIMPLE_NODE_DEF = "simpleNodeDef";
	
	public static String SIMPLE_HIERACHY_DEF = "simpleHierachyDef";

	public static LanguageDefTO generateSimpleLanguage() {
		LanguageDefTO languageTO = new LanguageDefTO();

		// --------------------------------------
		// 1. Create language specific types. These are built using and specialising 
		// the types and constraints registered in the above step

		{
			TypeExDefTO lcaStringType = new TypeExDefTO();
			lcaStringType.setId("STRING_TYPE_MIN_5");
			lcaStringType.setParentId(BaseTypes.STRING);

			TypeConstraintDefTO minLengthConstraint = new TypeConstraintDefTO();
			minLengthConstraint.setId(TypeConstraintMinLength.ID);
			lcaStringType.getConstraints().add(minLengthConstraint);
			
			TypeConstraintParameterTO minLengthParam = new TypeConstraintParameterTO();
			minLengthParam.setName(TypeConstraintMinLength.PARAM_MIN_LENGTH);
			minLengthParam.setValue("5");
			
			minLengthConstraint.getParameterList().add(minLengthParam);
			languageTO.getTypeExDef().add(lcaStringType);
		}

		// --------------------------------------
		// 2. Create the node types

		// for the itms.
		NodeDefTO lcaItemNodeDefTO = new NodeDefTO();
		lcaItemNodeDefTO.setId(SIMPLE_NODE_DEF);
		
		languageTO.getNodeDef().add(lcaItemNodeDefTO);

		{
			AttrDefTO stringAttr = new AttrDefTO();
			stringAttr.setId("string");
			stringAttr.setTypeId(BaseTypes.STRING);
			stringAttr.setLabel("A String");
			stringAttr.setDefaultValue("A String Value");
			stringAttr.setMandatory(true);

			AttrDefMap map = new AttrDefMap();
			map.setId(stringAttr.getId());
			map.setAttrDef(stringAttr);
			lcaItemNodeDefTO.getAttributes().add(map);
		}

		{
			AttrDefTO stringAttrMinLength = new AttrDefTO();
			stringAttrMinLength.setId("stringMin10");
			stringAttrMinLength.setLabel("A String Min 10");
			stringAttrMinLength.setDefaultValue("0123456789");
			stringAttrMinLength.setTypeId(BaseTypes.STRING);

			TypeConstraintDefTO minLengthConstraint = new TypeConstraintDefTO();
			minLengthConstraint.setId(TypeConstraintMinLength.ID);
			stringAttrMinLength.getConstraints().add(minLengthConstraint);
			TypeConstraintParameterTO minLengthParam = new TypeConstraintParameterTO();
			minLengthParam.setName(TypeConstraintMinLength.PARAM_MIN_LENGTH);
			minLengthParam.setValue("10");
			minLengthConstraint.getParameterList().add(minLengthParam);
			
			AttrDefMap map = new AttrDefMap();
			map.setId(stringAttrMinLength.getId());
			map.setAttrDef(stringAttrMinLength);
			lcaItemNodeDefTO.getAttributes().add(map);
		}

		{
			AttrDefTO stringAttrMaxLength = new AttrDefTO();
			stringAttrMaxLength.setId("stringMax20");
			stringAttrMaxLength.setLabel("A String Max 20");
			stringAttrMaxLength.setDefaultValue("01234567890123456789");
			stringAttrMaxLength.setTypeId(BaseTypes.STRING);

			TypeConstraintDefTO maxLengthConstraint = new TypeConstraintDefTO();
			maxLengthConstraint.setId(TypeConstraintMaxLength.ID);
			stringAttrMaxLength.getConstraints().add(maxLengthConstraint);
			
			TypeConstraintParameterTO maxLengthParam = new TypeConstraintParameterTO();
			maxLengthParam.setName(TypeConstraintMaxLength.PARAM_MAX_LENGTH);
			maxLengthParam.setValue("20");
			maxLengthConstraint.getParameterList().add(maxLengthParam);

			AttrDefMap map = new AttrDefMap();
			map.setId(stringAttrMaxLength.getId());
			map.setAttrDef(stringAttrMaxLength);
			lcaItemNodeDefTO.getAttributes().add(map);
		}

		{
			AttrDefTO stringAttrMaxLength = new AttrDefTO();
			stringAttrMaxLength.setId("stringExMin5Max10");
			stringAttrMaxLength.setTypeId("STRING_TYPE_MIN_5");
			stringAttrMaxLength.setLabel("A String Min5 Max10");
			stringAttrMaxLength.setDefaultValue("012345678");

			TypeConstraintDefTO maxLengthConstraint = new TypeConstraintDefTO();
			maxLengthConstraint.setId(TypeConstraintMaxLength.ID);
			stringAttrMaxLength.getConstraints().add(maxLengthConstraint);
			TypeConstraintParameterTO maxLengthParam = new TypeConstraintParameterTO();
			maxLengthParam.setName(TypeConstraintMaxLength.PARAM_MAX_LENGTH);
			maxLengthParam.setValue("10");
			maxLengthConstraint.getParameterList().add(maxLengthParam);

			AttrDefMap map = new AttrDefMap();
			map.setId(stringAttrMaxLength.getId());
			map.setAttrDef(stringAttrMaxLength);
			lcaItemNodeDefTO.getAttributes().add(map);
		}

		{
			AttrDefTO longAttr = new AttrDefTO();
			longAttr.setId("long");
			longAttr.setLabel("A Long");
			longAttr.setDefaultValue("1");
			longAttr.setTypeId(BaseTypes.LONG);
			
			AttrDefMap map = new AttrDefMap();
			map.setId(longAttr.getId());
			map.setAttrDef(longAttr);
			lcaItemNodeDefTO.getAttributes().add(map);
		}

		return languageTO;
	}

	public static LanguageDefTO generateTwoLevelLanguage() {

		// -------------------------------------------------------------------------
		//
		// Build the language definition.  This is the portion of definition that 
		// can be built by configuration and does not required a recompilations.
		//
		// This part is built using TOs that could come from an XML document, a 
		// service call (JSON or XML) etc.

		LanguageDefTO languageTO = new LanguageDefTO();

		// --------------------------------------
		// 1. Create language specific types. These are built using and specialising 
		// the types and constraints registered in the above step

		// --------------------------------------
		// 2. Create the node types

		// child node
		{
			NodeDefTO lcaBatchNodeDefTO = new NodeDefTO();
			lcaBatchNodeDefTO.setId("childnode");
			languageTO.getNodeDef().add(lcaBatchNodeDefTO);

			{
				AttrDefTO stringAttr = new AttrDefTO();
				stringAttr.setId("string");
				stringAttr.setTypeId(BaseTypes.STRING);
				stringAttr.setLabel("A String");
				stringAttr.setDefaultValue("Another String Value");

				AttrDefMap map = new AttrDefMap();
				map.setId(stringAttr.getId());
				map.setAttrDef(stringAttr);
				lcaBatchNodeDefTO.getAttributes().add(map);
			}
		}
		
		// top level node
		{
			NodeDefTO lcaItemNodeDefTO = new NodeDefTO();
			lcaItemNodeDefTO.setId(SIMPLE_HIERACHY_DEF);
			languageTO.getNodeDef().add(lcaItemNodeDefTO);

			{
				AttrDefTO stringAttr = new AttrDefTO();
				stringAttr.setId("string");
				stringAttr.setTypeId(BaseTypes.STRING);
				stringAttr.setLabel("A String");
				stringAttr.setDefaultValue("A String Value");

				AttrDefMap map = new AttrDefMap();
				map.setId(stringAttr.getId());
				map.setAttrDef(stringAttr);
				lcaItemNodeDefTO.getAttributes().add(map);
			}
			
			// add a collection of childnode nodes called childnodes
			NodeSetDefTO childSet = new NodeSetDefTO();
			childSet.setId("childnodes");
			childSet.setNodeType("childnode");
			childSet.setMaxOccurances(5);
			childSet.setMinOccurances(3);
			
			NodeSetDefMap nodeSetMap = new NodeSetDefMap();
			nodeSetMap.setId(childSet.getId());
			nodeSetMap.setNodeSetDef(childSet);
			lcaItemNodeDefTO.getChildSetDefs().add(nodeSetMap);
		}
		
		return languageTO;
	}
}
