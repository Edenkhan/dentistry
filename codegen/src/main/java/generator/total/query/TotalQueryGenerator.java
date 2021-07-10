package generator.total.query;

import generator.Generator;
import generator.total.JCodeModelUtils;
import generator.total.TotalUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mate.ConstGroup;
import mate.Entity;
import mate.Project;
import mate.Property;

import org.apache.commons.lang3.StringUtils;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JConditional;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;

public class TotalQueryGenerator implements Generator{

	private JCodeModel cm;

	public TotalQueryGenerator(JCodeModel cm) {
		this.cm = cm;
	}

	@Override
	public void generate(Entity entity) {
		try {
			_generate(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void _generate(Entity entity) throws Exception{
		ConstGroup[] constGroups = entity.getConstGroups();
		List<Property> sums = TotalUtils.getSums(entity);
		List<Property> groupBys = TotalUtils.getGroupBys(entity);
		List<Property> joinProps = new ArrayList<>();
		for(Property property : entity.getProperties()){
			if(property.getJoinEntity() == null)
				continue;
			joinProps.add(property);
		}

		JDefinedClass clazz = cm._class(Project.getInstance().getTotalQueryConditionClassName(entity));
		clazz._extends(cm.ref(Project.getInstance().getQueryConditionClassName()));
		JClass groupBysClass = cm.ref(Project.getInstance().getGroupBysClassName());
		JCodeModelUtils.generateField(clazz, groupBysClass, "groupBys", JExpr._new(cm.ref(Project.getInstance().getGroupBysClassName())));
		JCodeModelUtils.generateGetter(clazz, groupBysClass, "groupBys");
		JCodeModelUtils.generateProperty(cm, clazz, "Long[]", "ids");
		JCodeModelUtils.generateProperty(cm, clazz, "Integer", "minTotalCountRows");
		JCodeModelUtils.generateProperty(cm, clazz, "Integer", "maxTotalCountRows");
		JCodeModelUtils.generateProperty(cm, clazz, "Boolean", "totalCountRows");
		JCodeModelUtils.generateProperty(clazz, cm.ref(Date.class), TotalUtils.getStartDatePropertyName("createdDate"));
		JCodeModelUtils.generateProperty(clazz, cm.ref(Date.class), TotalUtils.getEndDatePropertyName("createdDate"));
		generateOrderByMethod(clazz, "createdDate");

		for(Property property: entity.getProperties()){
			String propertyType = property.getType();

			if(property.isLikeSearchable()){
				if(propertyType.equals(BigDecimal.class.getName()) || propertyType.equals(Integer.class.getSimpleName())){
					JCodeModelUtils.generateProperty(clazz, cm.ref(propertyType), TotalUtils.getMinPropertyName(property));
					JCodeModelUtils.generateProperty(clazz, cm.ref(propertyType), TotalUtils.getMaxPropertyName(property));
				}else if(propertyType.equals(Date.class.getName())){
					JCodeModelUtils.generateProperty(clazz, cm.ref(propertyType), TotalUtils.getStartDatePropertyName(property));
					JCodeModelUtils.generateProperty(clazz, cm.ref(propertyType), TotalUtils.getEndDatePropertyName(property));
				}else if(propertyType.equals("String")){
					JCodeModelUtils.generateProperty(clazz, cm.ref(propertyType), TotalUtils.getLikeSearchPropertyName(property));
				}else if(propertyType.equals("Long")){
					JCodeModelUtils.generateProperty(clazz, cm.ref("Long[]"), property.getName() + "s");
				}
			}

			if(property.isSearchable())
				JCodeModelUtils.generateProperty(clazz, cm.ref(propertyType), property.getName());

			if(property.isNullSearchale())
				JCodeModelUtils.generateProperty(clazz, cm.ref("Boolean"), "null" + StringUtils.capitalize(property.getName()));
			
			if(property.isSortable())
				generateOrderByMethod(clazz, property.getName());
		}

		for(Property property : sums){
			JCodeModelUtils.generateProperty(clazz, cm.ref("Boolean"), TotalUtils.getTotalSumPropertyName(property));
			JCodeModelUtils.generateProperty(clazz, cm.ref("Boolean"), TotalUtils.getTotalMaxPropertyName(property));
			JCodeModelUtils.generateProperty(clazz, cm.ref("Boolean"), TotalUtils.getTotalMinPropertyName(property));
			JCodeModelUtils.generateProperty(clazz, cm.ref("Boolean"), TotalUtils.getTotalAvgPropertyName(property));

			JCodeModelUtils.generateProperty(cm, clazz, property.getType(), TotalUtils.getMaxSumPropertyName(property));
			JCodeModelUtils.generateProperty(cm, clazz, property.getType(), TotalUtils.getMinSumPropertyName(property));
			JCodeModelUtils.generateProperty(cm, clazz, property.getType(), TotalUtils.getMinMinPropertyName(property));
			JCodeModelUtils.generateProperty(cm, clazz, property.getType(), TotalUtils.getMaxMinPropertyName(property));
			JCodeModelUtils.generateProperty(cm, clazz, property.getType(), TotalUtils.getMinMaxPropertyName(property));
			JCodeModelUtils.generateProperty(cm, clazz, property.getType(), TotalUtils.getMaxMaxPropertyName(property));
			JCodeModelUtils.generateProperty(cm, clazz, property.getType(), TotalUtils.getMinAvgPropertyName(property));
			JCodeModelUtils.generateProperty(cm, clazz, property.getType(), TotalUtils.getMaxAvgPropertyName(property));
		}

		for(Property property : groupBys){
			boolean dateType = property.getType().equals(Date.class.getName());
			
			JMethod method = clazz.method(JMod.PUBLIC, clazz, "set" + StringUtils.capitalize(TotalUtils.getGroupByPropertyName(property)));
			if(dateType){
				method.param(cm.ref(Project.getInstance().getDateGroupByClassName()), "dateGroupBy");
			}else{
				method.param(cm.ref("Boolean"), "groupBy");
			}
			JBlock methodBody = method.body();
			if(dateType){
				methodBody.invoke(JExpr.ref("groupBys"), "addDateGroupBy").arg(JExpr.lit(property.getName())).arg(JExpr.ref("dateGroupBy"));
			}else{
				JConditional methodBodyIf = methodBody._if(JExpr.ref("groupBy"));
				methodBodyIf._then().invoke(JExpr.ref("groupBys"), "addGroupBy").arg(JExpr.lit(property.getName()));
				methodBodyIf._else().invoke(JExpr.ref("groupBys"), "removeGroupBy").arg(JExpr.lit(property.getName()));
			}
			methodBody._return(JExpr._this());
		}

		for(ConstGroup cg : constGroups){
			if(cg.getMethod() == ConstGroup.METHOD_TYPE){

				if(cg.isInclude()) JCodeModelUtils.generateProperty(clazz, cm.ref("Integer[]"), TotalUtils.getIncludeConstGroupName(cg));
				if(cg.isExclude()) JCodeModelUtils.generateProperty(clazz, cm.ref("Integer[]"), TotalUtils.getExcludeConstGroupName(cg));

				JMethod method = clazz.method(JMod.PUBLIC, clazz, "set" + StringUtils.capitalize(TotalUtils.getGroupByPropertyName(cg)));
				method.param(cm.ref("Boolean"), "groupBy");
				JBlock methodBody = method.body();
				JConditional methodBodyIf = methodBody._if(JExpr.ref("groupBy"));
				methodBodyIf._then().invoke(JExpr.ref("groupBys"), "addGroupBy").arg(JExpr.lit(cg.getName()));
				methodBodyIf._else().invoke(JExpr.ref("groupBys"), "removeGroupBy").arg(JExpr.lit(cg.getName()));
				methodBody._return(JExpr._this());
			}else if(cg.getMethod() == ConstGroup.METHOD_MOD){

				if(cg.isInclude()) JCodeModelUtils.generateProperty(clazz, cm.ref("Long[]"), TotalUtils.getIncludeConstGroupName(cg));
				if(cg.isExclude()) JCodeModelUtils.generateProperty(clazz, cm.ref("Long[]"), TotalUtils.getExcludeConstGroupName(cg));

				JMethod method = clazz.method(JMod.PUBLIC, clazz, "set" + StringUtils.capitalize(TotalUtils.getGroupByPropertyName(cg)));
				method.param(cm.ref(Long.class), "mods");
				JBlock methodBody = method.body();
				JConditional methodBodyIf = methodBody._if(JExpr.ref("mods").ne(JExpr._null()));
				methodBodyIf._then().invoke(JExpr.ref("groupBys"), "addModsGroupBy").arg(JExpr.lit(cg.getName())).arg(JExpr.ref("mods"));
				methodBodyIf._else().invoke(JExpr.ref("groupBys"), "removeGroupBy").arg(JExpr.lit(cg.getName()));
				methodBody._return(JExpr._this());
			}
		}

		for (Property property : joinProps) {
			String substringName = property.getName().substring(0, property.getName().length() - 2);
			JCodeModelUtils.generateProperty(clazz, cm.ref(Project.getInstance().getJoinTypeClassName()), "join" + StringUtils.capitalize(substringName));

			JClass joinQoType = cm.ref(Project.getInstance().getQueryConditionClassName(property.getJoinEntity()));
			String joinQoName = substringName + "Query";
			JCodeModelUtils.generateProperty(clazz, joinQoType, joinQoName);

			String setJoinMethodName = "setJoin" + StringUtils.capitalize(substringName);
			JMethod setMethod = clazz.method(JMod.PUBLIC, clazz, setJoinMethodName);
			JBlock body = setMethod.body();
			body.invoke(JExpr._this(), setJoinMethodName).arg(cm.ref(Project.getInstance().getJoinTypeClassName()).staticRef("LEFT"));
			body._return(JExpr._this());
		}
	}

	@Override
	public String getName() {
		return "Total Query";
	}

	private void generateOrderByMethod(JDefinedClass clazz, String propertyName) throws Exception{
		JMethod orderByMethod = clazz.method(JMod.PUBLIC, cm.VOID, "setOrderBy" + StringUtils.capitalize(propertyName));
		orderByMethod.param(cm.INT, "keyword");
		orderByMethod.body().invoke("setOrderBy").arg(JExpr.lit(propertyName)).arg(JExpr.ref("keyword"));

		orderByMethod = clazz.method(JMod.PUBLIC, cm.INT, "getOrderBy" + StringUtils.capitalize(propertyName));
		orderByMethod.body()._return(JExpr.invoke("getOrderByKeyword").arg(JExpr.lit(propertyName)));
	}
}
