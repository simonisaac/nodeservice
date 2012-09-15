package org.sri.nodeservice.core.nodemgr.util.transform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sri.nodeservice.core.nodedef.service.model.AttrDefMap;
import org.sri.nodeservice.core.nodedef.service.model.AttrDefTO;
import org.sri.nodeservice.core.nodedef.service.model.NodeDefTO;
import org.sri.nodeservice.core.nodedef.service.model.NodeSetDefMap;
import org.sri.nodeservice.core.nodedef.service.model.NodeSetDefTO;
import org.sri.nodeservice.core.nodedef.service.model.TypeConstraintDefTO;
import org.sri.nodeservice.core.nodedef.service.model.TypeConstraintParameterTO;
import org.sri.nodeservice.core.nodedef.service.model.TypeExDefTO;
import org.sri.nodeservice.core.nodemgr.nodedef.NodeDef;
import org.sri.nodeservice.core.nodemgr.nodedef.NodeSetDef;
import org.sri.nodeservice.core.nodemgr.nodedef.TypeDefMgr;
import org.sri.nodeservice.core.nodemgr.nodedef.attr.AttrDef;
import org.sri.nodeservice.core.nodemgr.nodedef.attr.constraint.TypeConstraintDef;
import org.sri.nodeservice.core.nodemgr.nodedef.attr.constraint.TypeConstraintDef.TypeConstraintParameterDef;
import org.sri.nodeservice.core.nodemgr.nodedef.attr.type.TypeDef;
import org.sri.nodeservice.core.nodemgr.nodedef.attr.type.TypeExDef;
/**
 * @author sisaac
 *
 */
public class NodeDefTOTransformer {

	private TypeDefMgr typeDefMgr;

	public NodeDefTOTransformer(TypeDefMgr typeDefMgr) {
		super();
		this.typeDefMgr = typeDefMgr;
	}

	public NodeDef transformNodeDefTO(NodeDefTO to) {
		NodeDef rNodeDef = new NodeDef(to.getId());
		List<AttrDefMap> attrDefMaps = to.getAttributes();
		for (AttrDefMap attrDefMap : attrDefMaps) {
			AttrDefTO attrDefTO = attrDefMap.getAttrDef();
			AttrDef attrDef = this.transformAttrDefTO(attrDefTO);
			rNodeDef.addAttrDef(attrDef);
		}

		List<NodeSetDefMap> childSetDefs = to.getChildSetDefs();
		for (NodeSetDefMap nodeSetDefMap : childSetDefs) {
			NodeSetDefTO nodeSetDefTO = nodeSetDefMap.getNodeSetDef();
			NodeSetDef nodeSetDef = new NodeSetDef(nodeSetDefTO.getId(), nodeSetDefTO.getNodeType());
			nodeSetDef.setMinOccurances(nodeSetDefTO.getMinOccurances());
			nodeSetDef.setMaxOccurances(nodeSetDefTO.getMaxOccurances());
			rNodeDef.addChildSetDef(nodeSetDef);
		}
		return rNodeDef;
	}

	public AttrDef transformAttrDefTO(AttrDefTO to) {

		TypeDef type = typeDefMgr.getTypeDef(to.getTypeId());
		if (type == null) {
			throw new RuntimeException("Type with id [" + to.getTypeId() + "] not supported.");
		}
		AttrDef attrDef = new AttrDef(to.getId(), type, to.getLabel(), to.getDefaultValue(), to.isMandatory(), to.isReadonly());

		for (TypeConstraintDefTO curConstraint : to.getConstraints()) {
			TypeConstraintDef cDef = this.transformConstraintDefTO(curConstraint);
			attrDef.addTypeContstraintDef(cDef);
		}
		
		return attrDef;
	}

	public List<NodeDef> transformNodeDefTO(List<NodeDefTO> nodeDefs) {
		List<NodeDef> rList = new ArrayList<NodeDef>();

		for (NodeDefTO nodeDefTO : nodeDefs) {
			rList.add(this.transformNodeDefTO(nodeDefTO));
		}

		return rList;
	}

	public List<TypeExDef> transformTypeExDefTOs(List<TypeExDefTO> typeDefs) {
		List<TypeExDef> rList = new ArrayList<TypeExDef>();
		for (TypeExDefTO typeExDefTO : typeDefs) {
			TypeExDef te = this.transformTypeDefTO(typeExDefTO);
			rList.add(te);
		}
		return rList;
	}

	public TypeExDef transformTypeDefTO(TypeExDefTO typeDefTO) {
		TypeExDef typeEx = new TypeExDef();
		typeEx.setId(typeDefTO.getId());
		typeEx.setParentId(typeDefTO.getParentId());
		
		List<TypeConstraintDefTO> constraints = typeDefTO.getConstraints();
		for (TypeConstraintDefTO typeConstraintDefTO : constraints) {
			TypeConstraintDef cd = this.transformConstraintDefTO(typeConstraintDefTO);
			typeEx.getConstraintDefs().add(cd);
		}
		return typeEx;
	}

	public TypeConstraintDef transformConstraintDefTO(TypeConstraintDefTO to) {
		TypeConstraintDef constraint = this.typeDefMgr.getNewConstraint(to.getId());
		Map<String, TypeConstraintParameterDef> cMap = new HashMap<String, TypeConstraintParameterDef>();
		
		List<TypeConstraintParameterTO> parameterList = to.getParameterList();
		for (TypeConstraintParameterTO pTO : parameterList) {
			TypeConstraintParameterDef newTC = new TypeConstraintParameterDef();
			newTC.setName(pTO.getName());
			newTC.setValue(pTO.getValue());
			cMap.put(newTC.getName(), newTC);
		}
		constraint.initFromParams(cMap);
		return constraint;
	}
}
