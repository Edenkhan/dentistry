package generator.module;

import generator.ModuleGenerator;
import generator.total.JCodeModelUtils;
import generator.total.TotalUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import main.Entities;
import mate.ConstGroup;
import mate.Entity;
import mate.Project;
import mate.Property;

import org.apache.commons.lang3.StringUtils;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JType;

public class TotalWebModuleActionGenerator implements ModuleGenerator {

	private JCodeModel cm;

	private String basePackageName = "com.lanyotech.p2p.admin";
	private String actionAnnonationClassName = "com.easyjf.container.annonation.Action";
	private String baseActionClassName = "com.lanyotech.p2p.admin.base.BaseAction";
	private String pageClassName = "com.easyjf.web.Page";
	private String pageFactoryClassName = "com.lanyotech.p2p.admin.base.PageFactory";

	private JClass needPermissionClass;
	private JClass permissionGroupClass;
	private JClass pageFacotyClass;
	private JClass pageClass;
	private JClass returnClass;
	private JClass webFormUtilsClass;
	private JClass totalWebFormUtilsClass;
	private JClass webFormClass;

	public TotalWebModuleActionGenerator(JCodeModel cm) {
		this.cm = cm;
		pageFacotyClass = cm.ref(pageFactoryClassName);
		pageClass = cm.ref(pageClassName);
		permissionGroupClass = cm
				.ref("com.lanyotech.p2p.admin.base.annotation.PermissionGroup");
		needPermissionClass = cm
				.ref("com.lanyotech.p2p.admin.base.annotation.NeedPermission");
		webFormUtilsClass = cm.ref("com.lanyotech.p2p.admin.base.WebFormUtils");
		totalWebFormUtilsClass = cm
				.ref("com.lanyotech.p2p.admin.base.TotalWebFormUtils");
		returnClass = cm.ref("com.lanyotech.p2p.admin.base.ret.Return");
		webFormClass = cm.ref("com.easyjf.web.WebForm");
	}

	@Override
	public void generate(Entities entities) {
		try {
			_generate(entities);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void _generate(Entities entities) throws Exception {
		Project PROJECT = Project.getInstance();

		String moduleName = entities.getModuleName();
		String actionName = "Total" + StringUtils.capitalize(moduleName)
				+ "ModuleAction";
		String actionClassName = String.format("%s.%s.%s", basePackageName,
				moduleName, actionName);
		String moduleServiceName = PROJECT
				.getModuleServiceInterfaceClassName(moduleName);
		JType moduleServiceType = cm.ref(moduleServiceName);

		JDefinedClass clazz = cm._class(actionClassName);
		clazz._extends(cm.ref(baseActionClassName));
		clazz.annotate(cm.ref(actionAnnonationClassName)).param("alias",
				"total_" + moduleName);
		clazz.annotate(permissionGroupClass).param("value",
				moduleName + "模块汇总");

		JCodeModelUtils.generateField(clazz, moduleServiceType,
				StringUtils.uncapitalize(moduleServiceType.name()));
		JCodeModelUtils.generateSetterWithoutReturn(cm, clazz,
				moduleServiceType,
				StringUtils.uncapitalize(moduleServiceType.name()));

		for (Entity entity : entities) {
			generateDoMethod(clazz, entity, moduleName);
		}
	}

	private void generateDoMethod(JDefinedClass clazz, Entity entity,
			String moduleName) {
		generateDoDataMethod(clazz, entity, moduleName);
	}

	private void generateDoDataMethod(JDefinedClass clazz, Entity entity,
			String moduleName) {

		String methodName = getDoName(entity.getAlias() + "s");
		JMethod method = clazz.method(JMod.PUBLIC, pageClass, methodName);
		method.param(webFormClass, "form");
		method.annotate(needPermissionClass).param("value",
				"获取" + entity.getDescription() + "汇总数据");
		JBlock body = method.body();

		genFormGetLongArray(body, "ids");
		genFormOrderBy(body, "createdDate");
		genFormStartAndEndDate(body, "createdDate");

		for (Property property : entity.getProperties()) {
			String type = property.getType();
			String name = property.getName();
			if (property.isSearchable()) {
				if (type.equals("String"))
					genFormGetString(body, name);
				if (type.equals("Long"))
					genFormGetLong(body, name);
			}
			if (property.isLikeSearchable()) {
				if (type.equals("Integer"))
					genFormMinAndMaxInteger(body, name);
				if (type.equals(BigDecimal.class.getName()))
					genFormMinAndMaxBigDecimal(body, name);
				if (type.equals(Date.class.getName()))
					genFormStartAndEndDate(body, name);
				if (type.equals("String"))
					genFormLikeString(body, name);
			}
			if (property.isSortable())
				genFormOrderBy(body, name);
		}
		for (ConstGroup cg : entity.getConstGroups()) {
			if (cg.getMethod() == ConstGroup.METHOD_TYPE)
				genFormIncludeAndExcludeTypes(body, cg.getName());
			if (cg.getMethod() == ConstGroup.METHOD_MOD)
				genFormIncludeAndExcludeMods(body, cg.getName());
		}

		JClass qoClass = cm.ref(Project.getInstance()
				.getTotalQueryConditionClassName(entity));
		body.decl(qoClass, "qo", JExpr._new(qoClass));
		genQoGetLongArray(body, "ids");
		genQoOrderBy(body, "createdDate");
		genQoStartAndEndDate(body, "createdDate");
		for (Property property : entity.getProperties()) {
			String type = property.getType();
			String name = property.getName();

			if (property.isSearchable()) {
				if (type.equals("String"))
					genQoGetString(body, name);
				if (type.equals("Long"))
					genQoGetLong(body, name);
			}
			if (property.isLikeSearchable()) {
				if (type.equals("Integer"))
					genQoMinAndMaxInteger(body, name);
				if (type.equals(BigDecimal.class.getName()))
					genQoMinAndMaxBigDecimal(body, name);
				if (type.equals(Date.class.getName()))
					genQoStartAndEndDate(body, name);
				if (type.equals("String"))
					genQoLikeString(body, name);
			}
			if (property.isSortable())
				genQoOrderBy(body, name);
		}
		for (ConstGroup cg : entity.getConstGroups()) {
			genQoIncludeAndExclude(body, cg.getName());
		}

		// total
		body.staticInvoke(totalWebFormUtilsClass, "setGroupBys").arg(
				JExpr.ref("qo"));
		body.invoke(JExpr.ref("qo"), "setTotalCountRows").arg(
				totalWebFormUtilsClass.staticInvoke("getTotalCountRows"));

		for (Property p : TotalUtils.getSums(entity)) {
			if (p.getType().equals(Integer.class.getSimpleName()))
				continue;
			String name = p.getName();
			String capitalizedName = StringUtils.capitalize(name);
			JExpression lit = JExpr.lit(name);
			body.invoke(JExpr.ref("qo"), "setTotalSum" + capitalizedName)
					.arg(totalWebFormUtilsClass.staticInvoke("getTotalSum")
							.arg(lit));
			body.invoke(JExpr.ref("qo"), "setTotalAvg" + capitalizedName)
					.arg(totalWebFormUtilsClass.staticInvoke("getTotalAvg")
							.arg(lit));
			body.invoke(JExpr.ref("qo"), "setTotalMin" + capitalizedName)
					.arg(totalWebFormUtilsClass.staticInvoke("getTotalMin")
							.arg(lit));
			body.invoke(JExpr.ref("qo"), "setTotalMax" + capitalizedName)
					.arg(totalWebFormUtilsClass.staticInvoke("getTotalMax")
							.arg(lit));

			body.invoke(JExpr.ref("qo"), "setMinTotalSum" + capitalizedName)
					.arg(totalWebFormUtilsClass.staticInvoke("getMinTotalSum")
							.arg(lit));
			body.invoke(JExpr.ref("qo"), "setMaxTotalSum" + capitalizedName)
					.arg(totalWebFormUtilsClass.staticInvoke("getMaxTotalSum")
							.arg(lit));
			body.invoke(JExpr.ref("qo"), "setMinTotalMin" + capitalizedName)
					.arg(totalWebFormUtilsClass.staticInvoke("getMinTotalMin")
							.arg(lit));
			body.invoke(JExpr.ref("qo"), "setMaxTotalMin" + capitalizedName)
					.arg(totalWebFormUtilsClass.staticInvoke("getMaxTotalMin")
							.arg(lit));

			body.invoke(JExpr.ref("qo"), "setMinTotalMax" + capitalizedName)
					.arg(totalWebFormUtilsClass.staticInvoke("getMinTotalMax")
							.arg(lit));
			body.invoke(JExpr.ref("qo"), "setMaxTotalMax" + capitalizedName)
					.arg(totalWebFormUtilsClass.staticInvoke("getMaxTotalMax")
							.arg(lit));
			body.invoke(JExpr.ref("qo"), "setMinTotalAvg" + capitalizedName)
					.arg(totalWebFormUtilsClass.staticInvoke("getMinTotalAvg")
							.arg(lit));
			body.invoke(JExpr.ref("qo"), "setMaxTotalAvg" + capitalizedName)
					.arg(totalWebFormUtilsClass.staticInvoke("getMaxTotalAvg")
							.arg(lit));
		}

		String totalServiceInterfaceClassName = Project.getInstance()
				.getTotalServiceInterfaceClassName(entity);
		JClass serviceInterfaceClass = cm.ref(totalServiceInterfaceClassName);
		String moduleServiceName = Project.getInstance()
				.getModuleServiceInterfaceClassName(moduleName);
		JClass moduleServiceClass = cm.ref(moduleServiceName);

		body.decl(serviceInterfaceClass, StringUtils
				.uncapitalize(serviceInterfaceClass.name()),
				JExpr.ref(StringUtils.uncapitalize(moduleServiceClass.name()))
						.invoke("get" + serviceInterfaceClass.name()));
		body.decl(
				cm.ref(List.class).narrow(
						cm.ref(Project.getInstance()
								.getTotalValueObjectClassName(entity))),
				"totals").init(
				JExpr.ref(
						StringUtils.uncapitalize(serviceInterfaceClass.name()))
						.invoke("query").arg(JExpr.ref("qo")));
		body.decl(
				returnClass,
				"ret",
				JExpr.ref("returnFactory").invoke("createNormalReturn")
						.arg(JExpr.ref("totals")));
		body._return(pageFacotyClass.staticInvoke("createJsonPage").arg(
				JExpr.ref("ret")));
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

	private void genQo(JBlock body, String name, String varNamePrefix,
			String setMethodPrefix) {
		String varName = varNamePrefix != null ? varNamePrefix : "";
		varName += varNamePrefix != null ? StringUtils.capitalize(name) : name;
		body.invoke(JExpr.ref("qo"),
				setMethodPrefix + StringUtils.capitalize(name)).arg(
				JExpr.ref(varName));
	}

	private void genQoIncludeAndExclude(JBlock body, String name) {
		if (!name.endsWith("s"))
			name += "s";
		genQo(body, name, "include", "setInclude");
		genQo(body, name, "exclude", "setExclude");
	}

	private void genQoGetLong(JBlock body, String name) {
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

	private void genQoOrderBy(JBlock body, String name) {
		genQo(body, name, "orderBy", "setOrderBy");
	}

	private void genQoGetString(JBlock body, String name) {
		genQo(body, name, null, "set");
	}

	private void genQoStartAndEndDate(JBlock body, String name) {
		genQo(body, name, "start", "setStart");
		genQo(body, name, "end", "setEnd");
	}

	private void genFormLikeString(JBlock body, String name) {
		genForm(body, name, "like", cm.ref("String"), "getLikeString");
	}

	private void genFormIncludeAndExcludeTypes(JBlock body, String name) {
		genForm(body, name, "include", "s", cm.ref("Integer[]"),
				"getIncludeTypes");
		genForm(body, name, "exclude", "s", cm.ref("Integer[]"),
				"getExcludeTypes");
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

	private void genForm(JBlock body, String name, String varNamePrefix,
			String varNameSuffix, JClass returnType,
			String webFormUtilsMethodName) {
		boolean emptyVarNamePrefix = StringUtils.isNotEmpty(varNamePrefix);
		String varName = emptyVarNamePrefix ? varNamePrefix : "";
		varName += emptyVarNamePrefix ? StringUtils.capitalize(name) : name;
		varName += varNameSuffix;
		body.decl(returnType, varName,
				webFormUtilsClass.staticInvoke(webFormUtilsMethodName)
						.arg(name));
	}

	private void genForm(JBlock body, String name, String varNamePrefix,
			JClass returnType, String webFormUtilsMethodName) {
		genForm(body, name, varNamePrefix, "", returnType,
				webFormUtilsMethodName);
	}

	private void genFormOrderBy(JBlock body, String name) {
		genForm(body, name, "orderBy", cm.ref(Integer.class),
				"getOrderByKeyword");
	}

	private void genFormGetString(JBlock body, String name) {
		genForm(body, name, null, cm.ref(String.class), "getString");
	}

	private void genFormStartAndEndDate(JBlock body, String name) {
		genFormStartDate(body, name);
		genFormEndDate(body, name);
	}

	private void genFormStartDate(JBlock body, String name) {
		body.decl(
				cm.ref(Date.class),
				"start" + StringUtils.capitalize(name),
				webFormUtilsClass.staticInvoke("getStartDate").arg(
						JExpr.lit(name)));
	}

	private void genFormEndDate(JBlock body, String name) {
		body.decl(
				cm.ref(Date.class),
				"end" + StringUtils.capitalize(name),
				webFormUtilsClass.staticInvoke("getEndDate").arg(
						JExpr.lit(name)));
	}

	private String getDoName(String name) {
		return "do" + StringUtils.capitalize(name.replaceAll("_", ""));
	}

	@Override
	public String getName() {
		return "Total Module Action";
	}
}
