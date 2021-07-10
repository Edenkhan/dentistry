package generator.total.mapper;

import generator.Generator;

import java.util.List;

import mate.Entity;
import mate.Project;

import com.sun.codemodel.ClassType;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JType;

public class TotalMapperInterfaceGenerator implements Generator{

	private JCodeModel cm;

	public TotalMapperInterfaceGenerator(JCodeModel cm) {
		this.cm = cm;
	}

	@Override
	public void generate(Entity entity) {
		try {
			_generate(entity);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void _generate(Entity entity) throws Exception{
		JDefinedClass clazz = cm._class(Project.getInstance().getTotalMapperInterfaceClassName(entity), ClassType.INTERFACE);
		JType totalVOClass = cm.ref(Project.getInstance().getTotalVOClassName(entity));
		JType totalQOClass = cm.ref(Project.getInstance().getTotalQueryConditionClassName(entity));
		clazz.method(JMod.PUBLIC, cm.ref(List.class).narrow(totalVOClass), "query").param(totalQOClass, "qo");
	}

	@Override
	public String getName() {
		return "Total Value Object";
	}

}
