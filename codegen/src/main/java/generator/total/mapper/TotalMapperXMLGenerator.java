package generator.total.mapper;

import generator.Generator;
import generator.total.TotalUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mate.ConstGroup;
import mate.Entity;
import mate.Project;
import mate.Property;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.BaseElement;

public class TotalMapperXMLGenerator implements Generator {

	@Override
	public void generate(Entity entity) {
		List<Property> sums = TotalUtils.getSums(entity);
		List<Property> groupBys = TotalUtils.getGroupBys(entity);
		List<Property> properties = new ArrayList<Property>();
		properties.addAll(sums);
		properties.addAll(groupBys);
		
		Element root = createRootElement(entity);
		Element resultMap = createTotalResultMap(entity, sums, groupBys);
		Element queryWhere = createQueryWhere(entity);
		Element totalColumns = createTotalColumns(entity, sums, groupBys);
		Element queryGroupBy = createQueryGroupBy(entity);
		Element queryHaving = createQueryHaving(entity, sums);
		Element query = createQuery(entity);
		
		root.add(resultMap);
		root.add(queryWhere);
		root.add(totalColumns);
		root.add(queryGroupBy);
		root.add(queryHaving);
		root.add(query);
		
		String totalMapperXMLFileName = Project.getInstance().getTotalMapperXMLFileName(entity);
		OutputFormat format = OutputFormat.createPrettyPrint();
		try {
			XMLWriter writer = new XMLWriter(new FileWriter(totalMapperXMLFileName), format);
			writer.write(root.getDocument());
			writer.close();
		} catch (IOException e) {
			throw new RuntimeException("写入文件失败...");
		}
	}
	
	private Element createQueryWhere(Entity entity) {
		String basicMapperNamespace = Project.getInstance().getMapperInterfaceClassName(entity);
		
		Element element = new BaseElement("sql").addAttribute("id", "QUERY_WHERE");
		element.addElement("where").addElement("include").addAttribute("refid", basicMapperNamespace + ".QUERY_WHERE_SQL");
		return element;
	}

	private Element createQuery(Entity entity) {
		Element element = new BaseElement("select");
		element.addAttribute("id", "query")
			   .addAttribute("resultMap", "TOTAL_RESULT")
			   .addAttribute("parameterType", Project.getInstance().getTotalQueryConditionClassName(entity));
		
		String basicMapperNamespace = Project.getInstance().getMapperInterfaceClassName(entity);
		element.addText("SELECT ")
			   .addElement("include").addAttribute("refid", "TOTAL_COLUMNS")
			   .getParent().addText(String.format("FROM `%s` AS %s", entity.getName(), entity.getAlias()))
			   			   .addElement("include").addAttribute("refid", "QUERY_WHERE")
			   .getParent().addElement("include").addAttribute("refid", "QUERY_GROUP_BY")
			   .getParent().addElement("include").addAttribute("refid", "QUERY_HAVING")
			   .getParent().addElement("include").addAttribute("refid", basicMapperNamespace + ".QUERY_ORDER_BY")
			   .getParent().addElement("include").addAttribute("refid", basicMapperNamespace + ".QUERY_LIMIT");
		
		return element;
	}

	private Element createQueryHaving(Entity entity, List<Property> sums){
		Element element = new BaseElement("sql");
		element.addAttribute("id", "QUERY_HAVING");
		
		Element trimElement = element.addElement("trim")
				.addAttribute("prefixOverrides", "AND | OR")
				.addAttribute("prefix", "HAVING");
		trimElement.addElement("if")
					.addAttribute("test", "minTotalCountRows != null")
					.addText("AND COUNT(0) >= #{minTotalCountRows}");
		trimElement.addElement("if")
					.addAttribute("test", "maxTotalCountRows != null")
					.addText("AND COUNT(0) <= #{maxTotalCountRows}");
		
		for (Property property : sums) {
			String minSumPropertyName = TotalUtils.getMinSumPropertyName(property);
			String maxSumPropertyName = TotalUtils.getMaxSumPropertyName(property);
			String minMinPropertyName = TotalUtils.getMinMinPropertyName(property);
			String maxMinPropertyName = TotalUtils.getMaxMinPropertyName(property);
			String minMaxPropertyName = TotalUtils.getMinMaxPropertyName(property);
			String maxMaxPropertyName = TotalUtils.getMaxMaxPropertyName(property);
			String minAvgPropertyName = TotalUtils.getMinAvgPropertyName(property);
			String maxAvgPropertyName = TotalUtils.getMaxAvgPropertyName(property);
			
			trimElement.addElement("if").addAttribute("test", minSumPropertyName + " != null").addText(String.format("AND SUM(%s.%s) >= #{%s}", entity.getAlias(), property.getName(), minSumPropertyName));
			trimElement.addElement("if").addAttribute("test", maxSumPropertyName + " != null").addText(String.format("AND SUM(%s.%s) <= #{%s}", entity.getAlias(), property.getName(), maxSumPropertyName));
			trimElement.addElement("if").addAttribute("test", minMinPropertyName + " != null").addText(String.format("AND MIN(%s.%s) >= #{%s}", entity.getAlias(), property.getName(), minMinPropertyName));
			trimElement.addElement("if").addAttribute("test", maxMinPropertyName + " != null").addText(String.format("AND MIN(%s.%s) <= #{%s}", entity.getAlias(), property.getName(), maxMinPropertyName));
			trimElement.addElement("if").addAttribute("test", minMaxPropertyName + " != null").addText(String.format("AND MAX(%s.%s) >= #{%s}", entity.getAlias(), property.getName(), minMaxPropertyName));
			trimElement.addElement("if").addAttribute("test", maxMaxPropertyName + " != null").addText(String.format("AND MAX(%s.%s) <= #{%s}", entity.getAlias(), property.getName(), maxMaxPropertyName));
			trimElement.addElement("if").addAttribute("test", minAvgPropertyName + " != null").addText(String.format("AND AVG(%s.%s) >= #{%s}", entity.getAlias(), property.getName(), minAvgPropertyName));
			trimElement.addElement("if").addAttribute("test", maxAvgPropertyName + " != null").addText(String.format("AND AVG(%s.%s) <= #{%s}", entity.getAlias(), property.getName(), maxAvgPropertyName));
		}
		
		return element;
	}
	
	private Element createQueryGroupBy(Entity entity){
		Element element = new BaseElement("sql").addAttribute("id", "QUERY_GROUP_BY");
		element.addElement("if").addAttribute("test", "groupBys.size() > 0")
			   .addText("GROUP BY")
			   .addElement("foreach").addAttribute("collection", "groupBys.entrySet()").addAttribute("item", "entry").addAttribute("separator", ",")
			   		.addElement("choose").addElement("when").addAttribute("test", "entry.value != null")
			   			.addElement("choose")
			   				.addElement("when").addAttribute("test", "entry.value.startsWith('&')")
			   					.addText(String.format("%s.${entry.key} ${entry.value}", entity.getAlias()))
			   				.getParent().addElement("otherwise")
			   					.addText(String.format("${entry.value}(%s.${entry.key})", entity.getAlias()))
		   			.getParent().getParent().getParent().addElement("otherwise")
			   				.addText(String.format("%s.${entry.key}", entity.getAlias()));
			   					
		return element;
	}
	
	private Element createTotalColumns(Entity entity, List<Property> sums, List<Property> groupBys){
		
		Element element = new BaseElement("sql");
		element.addAttribute("id", "TOTAL_COLUMNS");
		
		Element trimElement = element.addElement("trim").addAttribute("suffixOverrides", ",");
		trimElement.addText("NULL, ");
		trimElement.addElement("if")
						.addAttribute("test", "totalCountRows")
						.addText("COUNT(0) AS rows, ");
		trimElement.addElement("if").addAttribute("test", "groupBys.size() > 0")
						.addElement("foreach").addAttribute("collection", "groupBys.keySet()").addAttribute("item", "key")
						.addText(String.format("%1$s.%2$s AS %2$s, ", entity.getAlias(), "${key}"));
		
		for(Property property : sums){
			String totalSumPropertyName = TotalUtils.getTotalSumPropertyName(property);
			String totalMinPropertyName = TotalUtils.getTotalMinPropertyName(property);
			String totalMaxPropertyName = TotalUtils.getTotalMaxPropertyName(property);
			String totalAvgPropertyName = TotalUtils.getTotalAvgPropertyName(property);
			
			trimElement.addElement("if").addAttribute("test", totalSumPropertyName).addText(String.format("SUM(%1$s.%2$s) AS %3$s, ", entity.getAlias(), property.getName(), totalSumPropertyName));
			trimElement.addElement("if").addAttribute("test", totalMinPropertyName).addText(String.format("MIN(%1$s.%2$s) AS %3$s, ", entity.getAlias(), property.getName(), totalMinPropertyName));
			trimElement.addElement("if").addAttribute("test", totalMaxPropertyName).addText(String.format("MAX(%1$s.%2$s) AS %3$s, ", entity.getAlias(), property.getName(), totalMaxPropertyName));
			trimElement.addElement("if").addAttribute("test", totalAvgPropertyName).addText(String.format("AVG(%1$s.%2$s) AS %3$s, ", entity.getAlias(), property.getName(), totalAvgPropertyName));
		}
		return element;
	}

	private Element createRootElement(Entity entity){
		Document doc = DocumentHelper.createDocument();
		doc.addDocType("mapper", "-//mybatis.org//DTD Mapper 3.0//EN", "http://mybatis.org/dtd/mybatis-3-mapper.dtd");
		Element root = doc.addElement("mapper");
		root.addAttribute("namespace", Project.getInstance().getTotalMapperInterfaceClassName(entity));
		return root;
	}
	
	private Element createTotalResultMap(Entity entity, List<Property> sums, List<Property> groupBys){
		Element element = new BaseElement("resultMap");
		element.addAttribute("id", "TOTAL_RESULT");
		element.addAttribute("type", Project.getInstance().getTotalVOClassName(entity));
		
		element.addElement("result")
					.addAttribute("column", "rows")
					.addAttribute("property", "rows");
		
		List<Property> properties = new ArrayList<Property>();
		properties.addAll(groupBys);
		for (Property property : properties) {
			element.addElement("result")
						.addAttribute("column", property.getName())
						.addAttribute("property", property.getName());
		}
		
		for(ConstGroup cg : entity.getConstGroups()){
			element.addElement("result")
						.addAttribute("column", cg.getName())
						.addAttribute("property", cg.getName());
		}
		
		return element;
	}
	

	@Override
	public String getName() {
		return "Total Mapper XML";
	}

}
