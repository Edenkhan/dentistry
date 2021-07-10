package generator.total;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mate.ConstGroup;
import mate.Entity;
import mate.Property;

import org.apache.commons.lang3.StringUtils;

public class TotalUtils {
	
	public static List<Property> getSums(Entity entity){
		List<Property> sums = new ArrayList<Property>();			
		for (Property property : entity.getProperties()) {
			String typeName = property.getType();
			if(typeName.equals(BigDecimal.class.getName()) || typeName.equals("Integer")){
				sums.add(property);
			}
		}
		return sums;
	}
	
	public static List<Property> getGroupBys(Entity entity){
		
		List<Property> groupBys = new ArrayList<Property>();		
		groupBys.add(new Property(Date.class, "createdDate", "创建时间"));
		
		for (Property property : entity.getProperties()) {
			if(property.isGroupable()){
				groupBys.add(property);
				continue;
			}
			String typeName = property.getType();
			if(typeName.equals(Date.class.getName()))
				groupBys.add(property);
		}
		
		return groupBys;
	}
	
	
	public static String getMinPropertyName(Property property){
		return "min" + StringUtils.capitalize(property.getName());
	}
	
	public static String getMaxPropertyName(Property property){
		return "max" + StringUtils.capitalize(property.getName());
	}
	
	public static String getSumPropertyName(Property property){
		return "sum" + StringUtils.capitalize(property.getName());
	}
	
	public static String getAvgPropertyName(Property property){
		return "avg" + StringUtils.capitalize(property.getName());
	}
	
	public  static String getMinSumPropertyName(Property property){
		return "minTotalSum" + StringUtils.capitalize(property.getName());
	}
	
	public static String getMaxSumPropertyName(Property property){
		return "maxTotalSum" + StringUtils.capitalize(property.getName());
	}
	
	public  static String getMinMaxPropertyName(Property property){
		return "minTotalMax" + StringUtils.capitalize(property.getName());
	}
	
	public static String getMaxMaxPropertyName(Property property){
		return "maxTotalMax" + StringUtils.capitalize(property.getName());
	}
	
	public  static String getMinMinPropertyName(Property property){
		return "minTotalMin" + StringUtils.capitalize(property.getName());
	}
	
	public static String getMaxMinPropertyName(Property property){
		return "maxTotalMin" + StringUtils.capitalize(property.getName());
	}
	
	public  static String getMinAvgPropertyName(Property property){
		return "minTotalAvg" + StringUtils.capitalize(property.getName());
	}
	
	public static String getMaxAvgPropertyName(Property property){
		return "maxTotalAvg" + StringUtils.capitalize(property.getName());
	}
	
	public static String getGroupByPropertyName(Property property){
		return "groupBy" + StringUtils.capitalize(property.getName());
	}
	
	public static String getGroupByPropertyName(ConstGroup constGroup){
		return "groupBy" + StringUtils.capitalize(constGroup.getName());
	}

	public static String getStartDatePropertyName(Property property){
		return "start" + StringUtils.capitalize(property.getName());
	}
	
	public static String getEndDatePropertyName(Property property){
		return "end" + StringUtils.capitalize(property.getName());
	}
	
	public static String getEndDatePropertyName(String name){
		return "end" + StringUtils.capitalize(name);
	}
	public static String getStartDatePropertyName(String name){
		return "start" + StringUtils.capitalize(name);
	}
	
	public static String getTotalSumPropertyName(Property property){
		return "totalSum" + StringUtils.capitalize(property.getName());
	}
	
	public static String getTotalMinPropertyName(Property property){
		return "totalMin" + StringUtils.capitalize(property.getName());
	}
	
	public static String getTotalMaxPropertyName(Property property){
		return "totalMax" + StringUtils.capitalize(property.getName());
	}
	
	public static String getTotalAvgPropertyName(Property property){
		return "totalAvg" + StringUtils.capitalize(property.getName());
	}
	
	
	public static String getIncludeConstGroupName(ConstGroup cg){
		String name = cg.getName();
		return "include" + StringUtils.capitalize(name) + (name.endsWith("s") ? "" : "s");
	}
	
	public static String getExcludeConstGroupName(ConstGroup cg){
		String name = cg.getName();
		return "exclude" + StringUtils.capitalize(name) + (name.endsWith("s") ? "" : "s");
	}

	public static String getLikeSearchPropertyName(Property property) {
		return "like" + StringUtils.capitalize(property.getName());
	}
}
