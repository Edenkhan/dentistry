package generator.total.vo;

import generator.Generator;
import generator.total.JCodeModelUtils;
import generator.total.TotalUtils;

import java.util.List;

import mate.ConstGroup;
import mate.Entity;
import mate.Project;
import mate.Property;

import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;

public class TotalVoGenerator implements Generator{

	private JCodeModel cm;
	
	public TotalVoGenerator(JCodeModel cm) {
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
		ConstGroup[] constGroups = entity.getConstGroups();
		List<Property> sums = TotalUtils.getSums(entity);
		List<Property> groupBys = TotalUtils.getGroupBys(entity);
		
		JDefinedClass clazz = cm._class(Project.getInstance().getTotalValueObjectClassName(entity));
		JCodeModelUtils.generateProperty(cm, clazz, "Integer", "rows");
		
		for(Property property : sums){
			JCodeModelUtils.generateProperty(cm, clazz, property.getType(), TotalUtils.getTotalSumPropertyName(property));
			JCodeModelUtils.generateProperty(cm, clazz, property.getType(), TotalUtils.getTotalAvgPropertyName(property));
			JCodeModelUtils.generateProperty(cm, clazz, property.getType(), TotalUtils.getTotalMaxPropertyName(property));
			JCodeModelUtils.generateProperty(cm, clazz, property.getType(), TotalUtils.getTotalMinPropertyName(property));
			
		}
		
		for(Property property : groupBys)
			JCodeModelUtils.generateProperty(cm, clazz, property.getType(), property.getName());
		
		for(ConstGroup cg : constGroups){
			String type = (cg.getMethod() == ConstGroup.METHOD_TYPE ? "Integer": "Long");
			JCodeModelUtils.generateProperty(cm, clazz, type, cg.getName());
		}
	}
	
	@Override
	public String getName() {
		return "Total Value Object";
	}

}
