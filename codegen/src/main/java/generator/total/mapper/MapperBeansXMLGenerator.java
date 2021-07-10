package generator.total.mapper;

import generator.ModuleGenerator;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import main.Entities;
import mate.Entity;
import mate.Project;
import mate.ThreeJoin;
import mate.ThreeJoin.Join;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.QName;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class MapperBeansXMLGenerator implements ModuleGenerator {

	private boolean genTotalBeans;

	public boolean isGenTotalBeans() {
		return genTotalBeans;
	}

	public MapperBeansXMLGenerator(boolean genTotalBeans) {
		this.genTotalBeans = genTotalBeans;
	}

	public MapperBeansXMLGenerator() {
		this(false);
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

		Document doc = null;

		File xmlFile = Project.getInstance().getSpringMapperFile(entities.getModuleName());
		if(!xmlFile.exists() || !xmlFile.isFile()){
			doc = DocumentHelper.createDocument();
			Element root = doc.addElement("beans");
			root.addNamespace("", "http://www.springframework.org/schema/beans");
			root.addAttribute(QName.get("schemaLocation", "xsi", "http://www.w3.org/2001/XMLSchema-instance"), "http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.2.xsd");
		}else{
			SAXReader reader = new SAXReader();
			doc = reader.read(xmlFile);
		}

		Element beans = doc.getRootElement();
		for(Entity entity : entities){
			String mapperName = Project.getInstance().getMapperInterfaceClassName(entity);
			String totalMapperName = Project.getInstance().getTotalMapperInterfaceClassName(entity);

			beans.addElement("bean", "http://www.springframework.org/schema/beans")
					.addAttribute("id", StringUtils.uncapitalize(mapperName.substring(mapperName.lastIndexOf(".") + 1)))
					.addAttribute("parent", "baseMapper")
					.addElement("property", "http://www.springframework.org/schema/beans")
					.addAttribute("name", "mapperInterface")
					.addAttribute("value", mapperName);

			if (genTotalBeans) {
				beans.addElement("bean", "http://www.springframework.org/schema/beans")
						.addAttribute("id", StringUtils.uncapitalize(totalMapperName.substring(totalMapperName.lastIndexOf(".") + 1)))
						.addAttribute("parent", "baseMapper")
						.addElement("property", "http://www.springframework.org/schema/beans")
						.addAttribute("name", "mapperInterface")
						.addAttribute("value", totalMapperName);
			}
		}

		List<ThreeJoin> threeJoins = entities.getThreeJoins();
		for(ThreeJoin threeJoin : threeJoins){
			Entity middle = threeJoin.getMiddle();
			Join leftJoin = threeJoin.getLeftJoin();
			Join rightJoin = threeJoin.getRightJoin();

			String mapperName = Project.getInstance().getThreeJoinMapperClassName(middle, leftJoin, rightJoin);
			beans.addElement("bean", "http://www.springframework.org/schema/beans")
				 	.addAttribute("id", StringUtils.uncapitalize(mapperName.substring(mapperName.lastIndexOf(".") + 1)))
				 	.addAttribute("parent", "baseMapper")
				 	.addElement("property", "http://www.springframework.org/schema/beans")
				 	.addAttribute("name", "mapperInterface")
				 	.addAttribute("value", mapperName);
		}

		OutputFormat format = OutputFormat.createPrettyPrint();
		XMLWriter writer = new XMLWriter(new FileWriter(xmlFile), format);
		writer.write(doc);
		writer.close();
	}

	@Override
	public String getName() {
		return "Mapper Beans XML";
	}

}

