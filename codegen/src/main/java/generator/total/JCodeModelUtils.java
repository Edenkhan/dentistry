package generator.total;

import com.sun.codemodel.*;
import org.apache.commons.lang3.StringUtils;

public class JCodeModelUtils {

	public static void generateProperty(JCodeModel cm, JDefinedClass clazz,
			String type, String name) {
		generateField(cm, clazz, type, name);
		generateGetter(cm, clazz, type, name);
		generateSetter(cm, clazz, type, name);
	}
	
	public static void generateProperty(JDefinedClass clazz, JType type, String name) {
		generateField(clazz, type, name);
		generateGetter(clazz, type, name);
		generateSetter(clazz, type, name);
	}

	public static void generateSetter(JCodeModel cm, JDefinedClass clazz, String type, String name) {
		generateSetter(clazz, cm.ref(type), name);
	}
	
	public static void generateGetter(JDefinedClass clazz, JType type, String name) {
		String methodName = "get" + StringUtils.capitalize(name);
		clazz.method(JMod.PUBLIC, type, methodName).body()._return(JExpr.refthis(name));
	}

	public static void generateGetter(JCodeModel cm, JDefinedClass clazz, String type, String name) {
		String methodName = "get" + StringUtils.capitalize(name);
		clazz.method(JMod.PUBLIC, cm.ref(type), methodName).body()._return(JExpr.refthis(name));
	}
	
	public static void generateSetterWithoutReturn(JCodeModel cm, JDefinedClass clazz, JType type, String name) {
		String methodName = "set" + StringUtils.capitalize(name);
		JMethod method = clazz.method(JMod.PUBLIC, cm.VOID, methodName);
		method.param(type, name);
		method.body().assign(JExpr.refthis(name), JExpr.ref(name));
	}

	public static void generateField(JCodeModel cm, JDefinedClass clazz,
			String type, String name) {
		generateField(clazz, cm.ref(type), name, null);
	}

	public static void generateField(JDefinedClass clazz, JType type, String name, JExpression init) {
		clazz.field(JMod.PROTECTED, type, name, init);
	}

	public static JFieldVar generateReturnField(JDefinedClass clazz, JType type, String name, JExpression init) {
		return clazz.field(JMod.PROTECTED, type, name, init);
	}

	public static void generateField(JDefinedClass clazz, JType type, String name) {
		generateField(clazz, type, name, null);
	}

	public static JFieldVar generateReturnField(JDefinedClass clazz, JType type, String name) {
		return generateReturnField(clazz, type, name, null);
	}

	public static void generateSetter(JDefinedClass clazz, JType type, String name) {
		String methodName = "set" + StringUtils.capitalize(name);
		JMethod method = clazz.method(JMod.PUBLIC, clazz, methodName);
		method.param(type, name);
		method.body().assign(JExpr.refthis(name), JExpr.ref(name))
				._return(JExpr._this());
	}

}
