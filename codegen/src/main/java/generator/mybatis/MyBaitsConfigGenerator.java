package generator.mybatis;

import generator.Generator;

import java.io.File;
import java.io.FileWriter;

import mate.Entity;
import mate.Project;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class MyBaitsConfigGenerator implements Generator {

	@Override
	public void generate(Entity entity) {
		try {
			_generate(entity);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	private void _generate(Entity entity) throws Exception{
		Document doc = null;

		File myBatisFile = Project.getInstance().getMyBatisFile();
		if(!myBatisFile.exists() || !myBatisFile.isFile()){
			doc = DocumentHelper.createDocument();
			doc.addDocType("configuration", "-//mybatis.org//DTD Config 3.0//EN", "http://mybatis.org/dtd/mybatis-3-config.dtd");
			doc.addElement("configuration")
				.addElement("settings")
					.addElement("setting")
						.addAttribute("name", "lazyLoadingEnabled")
						.addAttribute("value", "true")
					.getParent().addElement("setting")
						.addAttribute("name", "aggressiveLazyLoading")
						.addAttribute("value", "false")
				.getParent().getParent().addElement("mappers");

		}else{
			SAXReader reader = new SAXReader();
			doc = reader.read(myBatisFile);
		}

		doc.getRootElement().element("mappers").addElement("mapper").addAttribute("resource",
				Project.getInstance().getMapperInterfaceClassName(entity).replace(".", "/") + ".xml");

		OutputFormat format = OutputFormat.createPrettyPrint();
		XMLWriter writer = new XMLWriter(new FileWriter(myBatisFile), format);
		writer.write(doc);
		writer.close();
	}

	@Override
	public String getName() {
		return "MyBatis Config";
	}
}
