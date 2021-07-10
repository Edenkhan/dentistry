package generator.total.service;

import generator.Generator;
import generator.total.JCodeModelUtils;

import java.util.List;

import mate.Entity;
import mate.Project;

import org.apache.commons.lang3.StringUtils;

import com.sun.codemodel.ClassType;
import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JOp;

public class TotalServiceGenerator implements Generator {
	
	private JCodeModel cm;

	public TotalServiceGenerator(JCodeModel cm) {
		this.cm = cm;
	}

	public void _generate(Entity entity) throws Exception {
		generateServiceInterface(entity);
		generateServiceImplementClass(entity);
	}
	
	private void generateServiceImplementClass(Entity entity) throws Exception {
		
		String totalServiceImplementClassName = Project.getInstance().getTotalServiceImplementClassName(entity);
		JClass totalValueObjectClass = cm.ref(Project.getInstance().getTotalValueObjectClassName(entity));
		JClass totalMapperInterfaceClass = cm.ref(Project.getInstance().getTotalMapperInterfaceClassName(entity));
		String totalMapperInterfaceFieldName = StringUtils.uncapitalize(totalMapperInterfaceClass.name());
		JClass totalQueryConditionClass = cm.ref(Project.getInstance().getTotalQueryConditionClassName(entity));
		JClass totalServiceInterfaceClass = cm.ref(Project.getInstance().getTotalServiceInterfaceClassName(entity));
		
		JDefinedClass clazz = cm._class(totalServiceImplementClassName)._implements(totalServiceInterfaceClass);
		
		// fields
		JCodeModelUtils.generateField(clazz, totalMapperInterfaceClass, totalMapperInterfaceFieldName);
		JCodeModelUtils.generateSetter(clazz, totalMapperInterfaceClass, totalMapperInterfaceFieldName);
		
		// methods
		JMethod queryMethod = clazz.method(JMod.PUBLIC, cm.ref(List.class).narrow(totalValueObjectClass), "query");
		queryMethod.param(totalQueryConditionClass, "qo");
		queryMethod.body()._return(JExpr.refthis(totalMapperInterfaceFieldName).invoke("query").arg(JExpr.ref("qo")));
		
		JMethod queryOneMethod = clazz.method(JMod.PUBLIC, totalValueObjectClass, "queryOne");
		queryOneMethod.param(totalQueryConditionClass, "qo");
		JBlock queryOneMethodBody = queryOneMethod.body();
		queryOneMethodBody.invoke(JExpr.ref("qo"), "setPageSize").arg(JExpr.lit(1));
		queryOneMethodBody.decl(cm.ref(List.class).narrow(totalValueObjectClass), "datas", JExpr.refthis(totalMapperInterfaceFieldName).invoke("query").arg(JExpr.ref("qo")));
		queryOneMethodBody._return(JOp.cond(JExpr.ref("datas").eq(JExpr._null()).cor(JExpr.invoke(JExpr.ref("datas"), "isEmpty")), JExpr._null(), JExpr.ref("datas").invoke("get").arg(JExpr.lit(0))));
	}

	private void generateServiceInterface(Entity entity) throws Exception{
		JClass totalQueryConditionClass = cm.ref(Project.getInstance().getTotalQueryConditionClassName(entity));
		JClass totalValueObjectClassName = cm.ref(Project.getInstance().getTotalValueObjectClassName(entity));
		String totalServiceInterfaceClassName = Project.getInstance().getTotalServiceInterfaceClassName(entity);
		
		JDefinedClass clazz = cm._class(totalServiceInterfaceClassName, ClassType.INTERFACE);
		clazz.method(JMod.PUBLIC, cm.ref(List.class).narrow(totalValueObjectClassName), "query").param(totalQueryConditionClass, "qo");
		clazz.method(JMod.PUBLIC, totalValueObjectClassName, "queryOne").param(totalQueryConditionClass, "qo");
		
	}

	@Override
	public void generate(Entity entity) {
		try {
			_generate(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getName() {
		return "Total Service";
	}

}
