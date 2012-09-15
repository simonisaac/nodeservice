package org.sri.nodeservice.core.nodemgr.util.transform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.sri.nodeservice.core.nodedef.service.model.AttrDefMap;
import org.sri.nodeservice.core.nodedef.service.model.AttrDefTO;
import org.sri.nodeservice.core.nodedef.service.model.NodeDefTO;
import org.sri.nodeservice.core.nodedef.service.model.NodeSetDefMap;
import org.sri.nodeservice.core.nodedef.service.model.NodeSetDefTO;
import org.sri.nodeservice.core.nodedef.service.model.TypeConstraintDefTO;
import org.sri.nodeservice.core.nodedef.service.model.TypeConstraintParameterTO;
import org.sri.nodeservice.core.nodemgr.nodedef.NodeDef;
import org.sri.nodeservice.core.nodemgr.nodedef.NodeSetDef;
import org.sri.nodeservice.core.nodemgr.nodedef.attr.AttrDef;
import org.sri.nodeservice.core.nodemgr.nodedef.attr.constraint.TypeConstraintDef;
import org.sri.nodeservice.core.nodemgr.nodedef.attr.constraint.TypeConstraintDef.TypeConstraintParameterDef;
/**
 * @author sisaac
 *
 */
public class NodeDefTransformer {

	//private TypeDefMgr typeDefMgr;

	public NodeDefTransformer(/*TypeDefMgr typeDefMgr*/) {
		super();
		//this.typeDefMgr = typeDefMgr;
	}

	public NodeDefTO transformNodeDef(NodeDef nodeDef) {
		NodeDefTO rNodeDefTO = new NodeDefTO();
		rNodeDefTO.setId(nodeDef.getNodeDefId());

		for (AttrDef attrDef : nodeDef.getAttrDefList()) {
			AttrDefTO attrDefTO = this.transformAttrDef(attrDef);
			//rNodeDefTO.addAttribute(attrDefTO);
			AttrDefMap map = new AttrDefMap();
			map.setAttrDef(attrDefTO);
			map.setId(attrDefTO.getId());
			rNodeDefTO.getAttributes().add(map);
		}

		for (NodeSetDef nodeSetDef : nodeDef.getChildSetCollection()) {
			NodeSetDefTO nodeSetDefTO = new NodeSetDefTO();
			nodeSetDefTO.setId(nodeSetDef.getId());
			nodeSetDefTO.setNodeType(nodeSetDef.getNodeType());
			nodeSetDefTO.setMinOccurances(nodeSetDef.getMinOccurances());
			nodeSetDefTO.setMaxOccurances(nodeSetDef.getMaxOccurances());
			//rNodeDefTO.addChildSetDef(nodeSetDefTO);
			NodeSetDefMap map = new NodeSetDefMap();
			map.setId(nodeSetDefTO.getId());
			map.setNodeSetDef(nodeSetDefTO);
			rNodeDefTO.getChildSetDefs().add(map);
		}

		return rNodeDefTO;
	}

	public AttrDefTO transformAttrDef(AttrDef attrDef) {

		AttrDefTO rTO = new AttrDefTO();
		rTO.setId(attrDef.getId());
		rTO.setTypeId(attrDef.getType().getId());
		rTO.setLabel(attrDef.getLabel());
		rTO.setDefaultValue(attrDef.getDefaultValue());
		rTO.setMandatory(attrDef.isMandatory());
		rTO.setReadonly(attrDef.isReadonly());
		
		for (TypeConstraintDef curConstraint : attrDef.getTypeConstraintList()) {
			rTO.getConstraints().add(this.transformConstraintDef(curConstraint));			
		}
		
		return rTO;
	}

	public TypeConstraintDefTO transformConstraintDef(TypeConstraintDef constraintDef) {
		TypeConstraintDefTO rTO = new TypeConstraintDefTO();
		rTO.setId(constraintDef.getId());
		rTO.getApplicableTypes().clear();
		rTO.getApplicableTypes().addAll(constraintDef.getApplicableTypes());
		
		List<TypeConstraintParameterTO> paramTOList = new ArrayList<TypeConstraintParameterTO>();
		Collection<TypeConstraintParameterDef> params = constraintDef.serialiseToParams().values();
		for (TypeConstraintParameterDef typeConstraintParameterDef : params) {
			TypeConstraintParameterTO paramDef = this.transformConstraintDef(typeConstraintParameterDef);
			paramTOList.add(paramDef);
		}
		rTO.getParameterList().clear();
		rTO.getParameterList().addAll(paramTOList);
		
		return rTO;
	}

	private TypeConstraintParameterTO transformConstraintDef(TypeConstraintParameterDef param) {
		TypeConstraintParameterTO rTO = new TypeConstraintParameterTO();
		rTO.setName(param.getName());
		rTO.setValue(param.getValue());
		return rTO;
	}
}
