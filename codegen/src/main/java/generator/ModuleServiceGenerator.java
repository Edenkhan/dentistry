package generator;

import generator.total.JCodeModelUtils;

import java.util.Iterator;
import java.util.List;

import main.Entities;
import mate.Entity;
import mate.Project;
import mate.ThreeJoin;
import mate.ThreeJoin.Join;

import org.apache.commons.lang3.StringUtils;

import com.sun.codemodel.ClassType;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JType;

public class ModuleServiceGenerator implements ModuleGenerator{

	protected JCodeModel cm;

	public ModuleServiceGenerator(JCodeModel cm) {
		this.cm = cm;
	}

	@Override
	public void generate(Entities entities) {
		try {
			_generate(entities);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void _generate(Entities entities) throws Exception {

		JDefinedClass interfaceClass = cm._class(Project.getInstance().getServiceInterfaceClassName(entities.getModuleName(), StringUtils.capitalize(entities.getModuleName() + "Module")), ClassType.INTERFACE);
		JDefinedClass implementsClass = cm._class(Project.getInstance().getServiceImplementClassName(entities.getModuleName(), StringUtils.capitalize(entities.getModuleName() + "Module")));
		implementsClass._implements(interfaceClass);

		Iterator<Entity> iterator = entities.iterator();
		while(iterator.hasNext()){
			Entity entity = iterator.next();

			JType serviceInterfaceClass = cm.ref(Project.getInstance().getServiceInterfaceClassName(entity));
			JType totalServiceInterfaceClass = cm.ref(Project.getInstance().getTotalServiceInterfaceClassName(entity));

			interfaceClass.method(JMod.PUBLIC, serviceInterfaceClass, "get" + serviceInterfaceClass.name());
			interfaceClass.method(JMod.PUBLIC, totalServiceInterfaceClass, "get" + totalServiceInterfaceClass.name());

			JCodeModelUtils.generateProperty(implementsClass, serviceInterfaceClass, StringUtils.uncapitalize(serviceInterfaceClass.name()));
			JCodeModelUtils.generateProperty(implementsClass, totalServiceInterfaceClass, StringUtils.uncapitalize(totalServiceInterfaceClass.name()));
		}

		List<ThreeJoin> threeJoins = entities.getThreeJoins();
		for(ThreeJoin threeJoin : threeJoins){
			Entity middle = threeJoin.getMiddle();
			Join leftJoin = threeJoin.getLeftJoin();
			Join rightJoin = threeJoin.getRightJoin();

			String entityName = leftJoin.getEntity().getName() + middle.getName() + rightJoin.getEntity().getName();
			JType serviceInterfaceClass = cm.ref(Project.getInstance().getServiceInterfaceClassName(middle.getModule().getName(), entityName));
			interfaceClass.method(JMod.PUBLIC, serviceInterfaceClass, "get" + serviceInterfaceClass.name());
			JCodeModelUtils.generateProperty(implementsClass, serviceInterfaceClass, StringUtils.uncapitalize(serviceInterfaceClass.name()));

		}
	}

	@Override
	public String getName() {
		return "Spring 模块 Service";
	}

}
