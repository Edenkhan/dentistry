package generator.export;

import generator.ModuleGenerator;
import generator.total.JCodeModelUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import main.Entities;
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
import com.sun.codemodel.JDocComment;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JFieldRef;
import com.sun.codemodel.JInvocation;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JType;

public class ExportModuleActionGenerator implements ModuleGenerator{

	private JCodeModel cm;

	private String basePackageName = "com.lanyotech.p2p.admin";
	private String actionAnnonationClassName = "com.easyjf.container.annonation.Action";
	private String baseActionClassName = "com.lanyotech.p2p.admin.base.BaseAction";
	private String pageClassName = "com.easyjf.web.Page";
	private String pageFactoryClassName = "com.lanyotech.p2p.admin.base.PageFactory";
	private String excelExportorClassName = "com.lanyotech.p2p.core.base.export.excel.ExcelExportor";
	private String excelColumnClassName = "com.lanyotech.p2p.core.base.export.excel.ExcelColumn";
	private String amountCellRendererClassName = "com.lanyotech.p2p.core.base.export.excel.renderer.AmountCellRenderer";
	private String dateCellRendererClassName = "com.lanyotech.p2p.core.base.export.excel.renderer.DateCellRenderer";
	private String rateCellRendererClassName = "com.lanyotech.p2p.core.base.export.excel.renderer.RateCellRenderer";
	private String typeCellRendererClassName = "com.lanyotech.p2p.core.base.export.excel.renderer.TypeCellRenderer";
	private String modsCellRendererClassName = "com.lanyotech.p2p.core.base.export.excel.renderer.ModsCellRenderer";
	private String excelExportUtilsClassName = "com.lanyotech.p2p.core.base.export.excel.ExcelExportUtils";

	private JClass excelExportUtilsClass;
	private JClass modsCellRendererClass;
	private JClass typeCellRendererClass;
	private JClass rateCellRendererClass;
	private JClass dateCellRendererClass;
	private JClass amountCellRendererClass;
	private JClass excelColumnClass;
	private JClass excelExportorClass;
	private JClass needPermissionClass;
	private JClass permissionGroupClass;
	private JClass pageFacotyClass;
	private JClass pageClass;
	private JClass webFormUtilsClass;
	private JClass webFormClass;

	public ExportModuleActionGenerator(JCodeModel cm) {
		this.cm = cm;
		pageFacotyClass = cm.ref(pageFactoryClassName);
		pageClass = cm.ref(pageClassName);
		permissionGroupClass = cm.ref("com.lanyotech.p2p.admin.base.annotation.PermissionGroup");
		needPermissionClass = cm.ref("com.lanyotech.p2p.admin.base.annotation.NeedPermission");
		webFormUtilsClass = cm.ref("com.lanyotech.p2p.admin.base.WebFormUtils");
		webFormClass = cm.ref("com.easyjf.web.WebForm");
		excelExportorClass = cm.ref(excelExportorClassName);
		excelColumnClass = cm.ref(excelColumnClassName);
		amountCellRendererClass = cm.ref(amountCellRendererClassName);
		dateCellRendererClass = cm.ref(dateCellRendererClassName);
		rateCellRendererClass = cm.ref(rateCellRendererClassName);
		typeCellRendererClass = cm.ref(typeCellRendererClassName);
		modsCellRendererClass = cm.ref(modsCellRendererClassName);
		excelExportUtilsClass = cm.ref(excelExportUtilsClassName);
	}

	@Override
	public void generate(Entities entities) {
		try {
			_generate(entities);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void _generate(Entities entities) throws Exception{
		Project PROJECT = Project.getInstance();

		String moduleName = entities.getModuleName();
		String actionName = StringUtils.capitalize(moduleName) + "ExportModuleAction";
		String actionClassName = String.format("%s.%s.%s", basePackageName, moduleName, actionName);
		String moduleServiceName = PROJECT.getModuleServiceInterfaceClassName(moduleName);
		JType moduleServiceType = cm.ref(moduleServiceName);

		JDefinedClass clazz = cm._class(actionClassName);
		clazz._extends(cm.ref(baseActionClassName));
		clazz.annotate(cm.ref(actionAnnonationClassName)).param("alias", moduleName + "Export");
		clazz.annotate(permissionGroupClass).param("value", moduleName + "模块导出");
		for(Entity entity: entities){
			JDocComment javadoc = clazz.javadoc();
			String entityAlias = entity.getAlias().replaceAll("_", "");
			javadoc.add(String.format("\n/%s.htm?cmd=%s\t\t\t\t导出%s数据", moduleName + "Export" , entityAlias + "s", entity.getDescription()));
		}

		JCodeModelUtils.generateField(clazz, moduleServiceType, StringUtils.uncapitalize(moduleServiceType.name()));
		JCodeModelUtils.generateSetterWithoutReturn(cm, clazz, moduleServiceType, StringUtils.uncapitalize(moduleServiceType.name()));

		for(Entity entity: entities){
			generateDoMethod(clazz, entity, moduleName);
		}
	}

	private void generateDoMethod(JDefinedClass clazz, Entity entity, String moduleName){
		generateDoDataMethod(clazz, entity, moduleName);
	}

	private void generateDoDataMethod(JDefinedClass clazz, Entity entity, String moduleName){

		String methodName = getDoName(entity.getAlias() + "s");
		JMethod method = clazz.method(JMod.PUBLIC, pageClass, methodName);
		method.param(webFormClass, "form");
		method.annotate(needPermissionClass).param("value", "导出" + entity.getDescription() + "数据");
		JBlock body = method.body();

		genFormGetInteger(body, "page");
		genFormGetInteger(body, "pageSize");
		genFormGetLongArray(body, "ids");
		genFormOrderBy(body, "id");
		genFormOrderBy(body, "createdDate");
		genFormStartAndEndDate(body, "createdDate");

		for(Property property : entity.getProperties()){
			String type = property.getType();
			String name = property.getName();

			if(property.isSearchable()){
				if(type.equals("String")) genFormGetString(body, name);
				if(type.equals("Long")) genFormGetLong(body, name);
			}
			if(property.isLikeSearchable()){
				if(type.equals("Integer")) genFormMinAndMaxInteger(body, name);
				if(type.equals(BigDecimal.class.getName())) genFormMinAndMaxBigDecimal(body, name);
				if(type.equals(Date.class.getName())) genFormStartAndEndDate(body, name);
				if(type.equals("String")) genFormLikeString(body, name);
			}
			if(property.isSortable()) genFormOrderBy(body, name);
		}
		for(ConstGroup cg : entity.getConstGroups()){
			if(cg.getMethod() == ConstGroup.METHOD_TYPE)
				genFormIncludeAndExcludeTypes(body, cg.getName());
			if(cg.getMethod() == ConstGroup.METHOD_MOD)
				genFormIncludeAndExcludeMods(body, cg.getName());
		}

		JClass qoClass = cm.ref(Project.getInstance().getQueryConditionClassName(entity));
		body.decl(qoClass, "qo", JExpr._new(qoClass));
		genQoGetInteger(body, "page");
		genQoGetInteger(body, "pageSize");
		genQoGetLongArray(body, "ids");
		genQoOrderBy(body, "id");
		genQoOrderBy(body, "createdDate");
		genQoStartAndEndDate(body, "createdDate");
		for(Property property : entity.getProperties()){
			String type = property.getType();
			String name = property.getName();

			if(property.isSearchable()){
				if(type.equals("String")) genQoGetString(body, name);
				if(type.equals("Long")) genQoGetLong(body, name);
			}
			if(property.isLikeSearchable()){
				if(type.equals("Integer")) genQoMinAndMaxInteger(body, name);
				if(type.equals(BigDecimal.class.getName())) genQoMinAndMaxBigDecimal(body, name);
				if(type.equals(Date.class.getName())) genQoStartAndEndDate(body, name);
				if(type.equals("String")) genQoLikeString(body, name);
			}
			if(property.isSortable()) genQoOrderBy(body, name);
		}
		for(ConstGroup cg : entity.getConstGroups()){
			genQoIncludeAndExclude(body, cg.getName());
		}

		for(Property property : entity.getProperties()){
			if(property.getJoinEntity() == null)
				continue;
			genQoJoin(body, property.getName());
		}

		String serviceInterfaceClassName = Project.getInstance().getServiceInterfaceClassName(entity);
		JClass serviceInterfaceClass = cm.ref(serviceInterfaceClassName);
		String moduleServiceName = Project.getInstance().getModuleServiceInterfaceClassName(moduleName);
		JClass moduleServiceClass = cm.ref(moduleServiceName);

		body.decl(serviceInterfaceClass, StringUtils.uncapitalize(serviceInterfaceClass.name()), JExpr.ref(StringUtils.uncapitalize(moduleServiceClass.name())).invoke("get" + serviceInterfaceClass.name()));
		body.decl(cm.ref(Project.getInstance().getPaginationClassName()).narrow(cm.ref(Project.getInstance().getValueObjectClassName(entity))), "pagination")
			.init(JExpr.ref(StringUtils.uncapitalize(serviceInterfaceClass.name())).invoke("query" + entity.getName() + "s").arg(JExpr.ref("qo")));
		
		String exportFilename = "exportFilename";
		String exportProperties = "exportProperties";
		String excelExportor = "excelExportor";
		
		genFormGetStringAndTrim(body, exportFilename, new String[]{getCamelCaseName(exportFilename), entity.getDescription() + "列表"});
		genFormGetStringSet(body, exportProperties, new String[]{getCamelCaseName(exportProperties)});
		
		JClass refExtended = cm.ref(Project.getInstance().getValueObjectClassName(entity));
		body.decl(excelExportorClass.narrow(refExtended), excelExportor).init(JExpr._new(excelExportorClass.narrow(refExtended)));
		JFieldRef excelExportorRef = JExpr.ref(excelExportor);
		body.invoke(excelExportorRef, "setFilename").arg(JExpr.ref(exportFilename));
		
		body.invoke(excelExportorRef, "addColumn").arg(JExpr._new(excelColumnClass.narrow(Date.class)).arg("createdDate").arg("创建时间").arg(JExpr._new(dateCellRendererClass)));
		for(Property property : entity.getProperties()){
			if(property.isEncrypt())
				continue;
			
			String name = property.getName();
			String description = property.getDescription();
			
			if(property.getType().equals(BigDecimal.class.getName()) && !property.isHighPrecision()){
				JFieldRef exportPropertiesRef = JExpr.ref("exportProperties");
				JConditional jIf = body._if(exportPropertiesRef.invoke("contains").arg(name)); 
				jIf._then().invoke(excelExportorRef, "addColumn").arg(JExpr._new(excelColumnClass.narrow(BigDecimal.class)).arg(name).arg(description).arg(JExpr._new(amountCellRendererClass)));
			}else if(property.getType().equals(Date.class.getName())){
				JFieldRef exportPropertiesRef = JExpr.ref("exportProperties");
				JConditional jIf = body._if(exportPropertiesRef.invoke("contains").arg(name)); 
				jIf._then().invoke(excelExportorRef, "addColumn").arg(JExpr._new(excelColumnClass.narrow(Date.class)).arg(name).arg(description).arg(JExpr._new(dateCellRendererClass)));
			}else if(property.getType().equals(BigDecimal.class.getName()) && property.isHighPrecision()){
				JFieldRef exportPropertiesRef = JExpr.ref("exportProperties");
				JConditional jIf = body._if(exportPropertiesRef.invoke("contains").arg(name)); 
				jIf._then().invoke(excelExportorRef, "addColumn").arg(JExpr._new(excelColumnClass.narrow(BigDecimal.class)).arg(name).arg(description).arg(JExpr._new(rateCellRendererClass)));
			}else if(property.getJoinEntity() != null){
				String[] joinNames = property.getJoinNames();
				for (String string : joinNames) {
					if(!string.equals("id") && !string.endsWith("Id")){
						JFieldRef exportPropertiesRef = JExpr.ref("exportProperties");
						JConditional jIf  = body._if(exportPropertiesRef.invoke("contains").arg(name.replaceAll("Id$", ".") + string));
						jIf._then().invoke(excelExportorRef, "addColumn").arg(JExpr._new(excelColumnClass.narrow(String.class)).arg(name.replaceAll("Id$", ".") + string).arg(description));
						break;
					}
				}
			}else{
				JFieldRef exportPropertiesRef = JExpr.ref("exportProperties");
				JConditional jIf = body._if(exportPropertiesRef.invoke("contains").arg(name)); 
				jIf._then().invoke(excelExportorRef, "addColumn").arg(JExpr._new(excelColumnClass.narrow(String.class)).arg(name).arg(description));
			}
		}
		
		ConstGroup[] constGroups = entity.getConstGroups();
		for (ConstGroup constGroup : constGroups) {
			String name = constGroup.getName();
			String description = constGroup.getDescription();
			JFieldRef exportPropertiesRef = JExpr.ref("exportProperties");
			JConditional jIf = body._if(exportPropertiesRef.invoke("contains").arg(name)); 
			int _method = constGroup.getMethod();
			if(_method == ConstGroup.METHOD_TYPE){
				JFieldRef argConst = cm.ref(Project.getInstance().getEntityConstClassName(moduleName)).staticRef(StringUtils.capitalize(entity.getName())).ref(getUpperCamelCaseName(constGroup.getName()));
				jIf._then().invoke(excelExportorRef, "addColumn").arg(JExpr._new(excelColumnClass.narrow(Integer.class)).arg(name).arg(description).arg(JExpr._new(typeCellRendererClass).arg(argConst)));
			}else{
				JFieldRef argConst = cm.ref(Project.getInstance().getEntityConstClassName(moduleName)).staticRef(StringUtils.capitalize(entity.getName())).ref(getUpperCamelCaseName(constGroup.getName()));
				jIf._then().invoke(excelExportorRef, "addColumn").arg(JExpr._new(excelColumnClass.narrow(Long.class)).arg(name).arg(excelExportUtilsClass.staticInvoke("getModsEntityConstNames").arg(argConst)).arg(JExpr._new(modsCellRendererClass).arg(argConst)));
			}
		}
		
		
		body._return(pageFacotyClass.staticInvoke("createExportPage").arg(excelExportorRef).arg(JExpr.ref("pagination").invoke("getDatas")));
	}
	
	private String getCamelCaseName(String name){
		return StringUtils.lowerCase(name.replaceAll("([A-Z])", "_$1"));
	}
	
	private String getUpperCamelCaseName(String name){
		return StringUtils.upperCase(name.replaceAll("([A-Z])", "_$1"));
	}

	private void genQoLikeString(JBlock body, String name) {
		genQo(body, name, "like", "setLike");
	}

	private void genQoGetLongArray(JBlock body, String name) {
		genQo(body, name, null, "set");
	}

	private void genFormGetLongArray(JBlock body, String name) {
		genForm(body, name, null, cm.ref("Long[]"), "getLongArray");
	}

	private void genFormGetInteger(JBlock body, String name) {
		genForm(body, name, null, cm.ref("Integer"), "getInteger");
	}

	private void genQoJoin(JBlock body, String propertyName) {
		String joinName = StringUtils.capitalize(propertyName);
		if(joinName.endsWith("Id"))
			joinName = joinName.substring(0, joinName.length() - 2);
		body.invoke(JExpr.ref("qo"), "setJoin" + joinName);
	}

	private void genQo(JBlock body, String name, String varNamePrefix, String setMethodPrefix){
		String varName = varNamePrefix != null ? varNamePrefix : "";
		varName += varNamePrefix != null ? StringUtils.capitalize(name) : name;
		body.invoke(JExpr.ref("qo"), setMethodPrefix + StringUtils.capitalize(name)).arg(JExpr.ref(varName));
	}

	private void genQoIncludeAndExclude(JBlock body, String name) {
		if(!name.endsWith("s")) name += "s";
		genQo(body, name, "include", "setInclude");
		genQo(body, name, "exclude", "setExclude");
	}

	private void genQoGetLong(JBlock body, String name) {
		genQo(body, name, null, "set");
	}

	private void genQoGetInteger(JBlock body, String name) {
		genQo(body, name, null, "set");
	}

	private void genQoMinAndMaxInteger(JBlock body, String name) {
		genQo(body, name, "max", "setMax");
		genQo(body, name, "min", "setMin");
	}
	private void genQoMinAndMaxBigDecimal(JBlock body, String name) {
		genQo(body, name, "min", "setMin");
		genQo(body, name, "max", "setMax");
	}

	private void genQoOrderBy(JBlock body, String name){
		genQo(body, name, "orderBy", "setOrderBy");
	}
	private void genQoGetString(JBlock body, String name){
		genQo(body, name, null, "set");
	}
	private void genQoStartAndEndDate(JBlock body, String name){
		genQo(body, name, "start", "setStart");
		genQo(body, name, "end", "setEnd");
	}

	private void genFormLikeString(JBlock body, String name) {
		genForm(body, name, "like", cm.ref("String"), "getLikeString");
	}

	private void genFormIncludeAndExcludeTypes(JBlock body, String name) {
		genForm(body, name, "include", "s", cm.ref("Integer[]"), "getIncludeTypes");
		genForm(body, name, "exclude", "s", cm.ref("Integer[]"), "getExcludeTypes");
	}

	private void genFormIncludeAndExcludeMods(JBlock body, String name) {
		genForm(body, name, "include", cm.ref("Long[]"), "getIncludeMods");
		genForm(body, name, "exclude", cm.ref("Long[]"), "getExcludeMods");
	}

	private void genFormGetLong(JBlock body, String name) {
		genForm(body, name, null, cm.ref(Long.class), "getLong");
	}

	private void genFormMinAndMaxInteger(JBlock body, String name) {
		genFormMinInteger(body, name);
		genFormMaxInteger(body, name);
	}
	private void genFormMaxInteger(JBlock body, String name) {
		genForm(body, name, "max", cm.ref(Integer.class), "getMaxInteger");
	}
	private void genFormMinInteger(JBlock body, String name) {
		genForm(body, name, "min", cm.ref(Integer.class), "getMinInteger");
	}
	private void genFormMinAndMaxBigDecimal(JBlock body, String name) {
		genFormMinBigDecimal(body, name);
		genFormMaxBigDecimal(body, name);
	}
	private void genFormMaxBigDecimal(JBlock body, String name) {
		genForm(body, name, "max", cm.ref(BigDecimal.class), "getMaxBigDecimal");
	}

	private void genFormMinBigDecimal(JBlock body, String name) {
		genForm(body, name, "min", cm.ref(BigDecimal.class), "getMinBigDecimal");
	}
	private void genForm(JBlock body, String name, String varNamePrefix, String varNameSuffix, JClass returnType, String webFormUtilsMethodName){
		boolean emptyVarNamePrefix = StringUtils.isNotEmpty(varNamePrefix);
		String varName = emptyVarNamePrefix ? varNamePrefix : "";
		varName += emptyVarNamePrefix ? StringUtils.capitalize(name) : name;
		varName += varNameSuffix;
		body.decl(returnType, varName, webFormUtilsClass.staticInvoke(webFormUtilsMethodName).arg(name));
	}
	private void genForm(JBlock body, String name, String varNamePrefix, JClass returnType, String webFormUtilsMethodName){
		genForm(body, name, varNamePrefix, "", returnType, webFormUtilsMethodName);
	}
	private void genFormOrderBy(JBlock body, String name){
		genForm(body, name, "orderBy", cm.ref(Integer.class), "getOrderByKeyword");
	}
	private void genFormGetString(JBlock body, String name){
		genForm(body, name, null, cm.ref(String.class), "getString");
	}
	
	
	private void genForm2(JBlock body, String paramName, String[] argNames, JClass returnType, String webFormUtilsMethodName){
		JInvocation ji = webFormUtilsClass.staticInvoke(webFormUtilsMethodName);
		if(argNames != null & argNames.length > 0){
			for (String argName : argNames) {
				ji.arg(argName);
			}
		}
		body.decl(returnType, paramName, ji);
	}
	
	private void genFormGetStringAndTrim(JBlock body, String paramName, String[] argNames){
		genForm2(body, paramName, argNames, cm.ref(String.class), "getStringAndTrim");
	}
	
	private void genFormGetStringSet(JBlock body, String paramName, String[] argNames){
		genForm2(body, paramName, argNames, cm.ref(Set.class).narrow(String.class), "getStringSet");
	}
	
	private void genFormStartAndEndDate(JBlock body, String name){
		genFormStartDate(body, name);
		genFormEndDate(body, name);
	}
	private void genFormStartDate(JBlock body, String name){
		body.decl(cm.ref(Date.class), "start" + StringUtils.capitalize(name), webFormUtilsClass.staticInvoke("getStartDate").arg(JExpr.lit(name)));
	}
	private void genFormEndDate(JBlock body, String name){
		body.decl(cm.ref(Date.class), "end" + StringUtils.capitalize(name), webFormUtilsClass.staticInvoke("getEndDate").arg(JExpr.lit(name)));
	}

	private String getDoName(String name){
		return "do" + StringUtils.capitalize(name.replaceAll("_", ""));
	}
	@Override
	public String getName() {
		return "Export Module Action";
	}
}
