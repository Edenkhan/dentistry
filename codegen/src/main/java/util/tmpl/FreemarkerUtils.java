package util.tmpl;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreemarkerUtils {
	
	private static Configuration cfg;
	
	static{
		cfg = new Configuration();
		try {
			cfg.setDirectoryForTemplateLoading(new File(FreemarkerUtils.class.getResource("./").getFile()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}  
	}
	
		
	public static String render(String template, Map<String, Object> context){
		try {
			Template tmpl = cfg.getTemplate(template, "UTF-8");
			StringWriter stringWriter = new StringWriter();
			tmpl.process(context, stringWriter);
			return stringWriter.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args) {
		String template = "export.js.ftl";
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("permission", "com.lanyotech.p2p.admin.financial.FinancialExportModuleAction:doFinbs");
		context.put("url", "/financialExport.html");
		context.put("filename", "理财买入");
		
		List<Cell> cells = new ArrayList<Cell>();
		cells.add(new Cell("amount", "金额"));
		cells.add(new Cell("rate", "利率"));
		
		context.put("cells", cells);
		System.out.println(render(template, context));
	}
	
	
	public static class Cell{
		public String name;
		public String displayName;
		
		public Cell(String name, String displayName) {
			super();
			this.name = name;
			this.displayName = displayName;
		}
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDisplayName() {
			return displayName;
		}
		public void setDisplayName(String displayName) {
			this.displayName = displayName;
		}
		
		
	}
}
