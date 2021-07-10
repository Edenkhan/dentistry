package generator.total.service;

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

public class ServiceBeansXMLGenerator implements ModuleGenerator {

	private boolean genTotalBeans;
	private boolean genModuleBeans;

	public boolean isGenTotalBeans() {
		return genTotalBeans;
	}

	public boolean isGenModuleBeans() {
		return genModuleBeans;
	}

	public ServiceBeansXMLGenerator(boolean genTotalBeans, boolean genModuleBeans) {
		this.genTotalBeans = genTotalBeans;
		this.genModuleBeans = genModuleBeans;
	}

	public ServiceBeansXMLGenerator() {
		this(false, false);
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

		File xmlFile = Project.getInstance().getSpringServiceFile(entities.getModuleName());
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
		Element moduleBean = null;
		if(genModuleBeans) {
			moduleBean = beans.addElement("bean", "http://www.springframework.org/schema/beans")
					.addAttribute("id", entities.getModuleName() + "ModuleService")
					.addAttribute("class", Project.getInstance().getServiceImplementClassName(entities.getModuleName(), StringUtils.capitalize(entities.getModuleName() + "Module")));
		}
		for (Entity entity : entities) {
			String serviceName = Project.getInstance().getServiceInterfaceClassName(entity);
			serviceName = serviceName.substring(serviceName.lastIndexOf(".") + 1);
			serviceName = StringUtils.uncapitalize(serviceName);
			String totalServiceName = "total" + StringUtils.capitalize(serviceName);

			String mapperName = Project.getInstance().getMapperInterfaceClassName(entity);
			mapperName = mapperName.substring(mapperName.lastIndexOf(".") + 1);
			mapperName = StringUtils.uncapitalize(mapperName);
			String totalMapperName = "total" + StringUtils.capitalize(mapperName);

			beans.addElement("bean", "http://www.springframework.org/schema/beans")
					.addAttribute("id", serviceName)
					.addAttribute("class", Project.getInstance().getServiceImplementClassName(entity))
					.addElement("property", "http://www.springframework.org/schema/beans")
						.addAttribute("name", mapperName)
						.addAttribute("ref", mapperName);

			if(genTotalBeans) {
				beans.addElement("bean", "http://www.springframework.org/schema/beans")
						.addAttribute("id", totalServiceName)
						.addAttribute("class", Project.getInstance().getTotalServiceImplementClassName(entity))
						.addElement("property", "http://www.springframework.org/schema/beans")
						.addAttribute("name", totalMapperName)
						.addAttribute("ref", totalMapperName);
			}

			if (moduleBean != null) {
				moduleBean.addElement("property", "http://www.springframework.org/schema/beans")
						.addAttribute("name", serviceName)
						.addAttribute("ref", serviceName);
			}
			if(moduleBean != null && genTotalBeans) {
				moduleBean.addElement("property", "http://www.springframework.org/schema/beans")
						.addAttribute("name", totalServiceName)
						.addAttribute("ref", totalServiceName);
			}
		}


		List<ThreeJoin> threeJoins = entities.getThreeJoins();
		for(ThreeJoin threeJoin : threeJoins){
			Entity middle = threeJoin.getMiddle();
			Join leftJoin = threeJoin.getLeftJoin();
			Join rightJoin = threeJoin.getRightJoin();

			String serviceImplementName = Project.getInstance().getThreeJoinServiceImplementClassName(leftJoin, middle, rightJoin);

			String mapperName = Project.getInstance().getThreeJoinMapperClassName(middle, leftJoin, rightJoin);
			mapperName = StringUtils.uncapitalize(mapperName.substring(mapperName.lastIndexOf(".") + 1));

			String serviceName = Project.getInstance().getThreeJoinServiceInterfaceClassName(leftJoin, middle, rightJoin);
			serviceName = StringUtils.uncapitalize(serviceName.substring(serviceName.lastIndexOf(".") + 1));

			beans.addElement("bean", "http://www.springframework.org/schema/beans")
					.addAttribute("id", serviceName)
					.addAttribute("class", serviceImplementName)
					.addElement("property", "http://www.springframework.org/schema/beans")
						.addAttribute("name", mapperName)
						.addAttribute("ref", mapperName);

			if (moduleBean != null) {
				moduleBean.addElement("property", "http://www.springframework.org/schema/beans")
						.addAttribute("name", serviceName)
						.addAttribute("ref", serviceName);
			}
		}

		OutputFormat format = OutputFormat.createPrettyPrint();
		XMLWriter writer = new XMLWriter(new FileWriter(xmlFile), format);
		writer.write(doc);
		writer.close();
	}

	@Override
	public String getName() {
		return "Service Beans XML";
	}

}
