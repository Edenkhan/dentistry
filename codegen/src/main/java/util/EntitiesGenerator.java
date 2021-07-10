package util;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;

public class EntitiesGenerator {
	public static void main(String[] args) throws IOException {

		StringBuilder code = new StringBuilder(),
					  cc = new StringBuilder(),
					  ct = new StringBuilder();

		List<String> readLines = IOUtils.readLines(EntitiesGenerator.class.getResourceAsStream("file.txt"), "UTF-8");
		boolean M = false, T = false, C = false;
		String N = null;

		for (String line : readLines) {
			if(line.trim().isEmpty())
				continue;
			if(line.startsWith("-")){
				line = line.substring(1);
				String[] frags = line.split("\\s+");
				String type = frags[0],
						name = frags[1],
						desc = frags[2];
				N = name;

				if(type.equalsIgnoreCase("MOD")){
					M = true;
					T = false;
					C = false;

					if(ct.length() > 0 && ct.toString().endsWith(", %s")){
						ct.delete(ct.length() - ", %s".length(), ct.length());
						ct.append("), ");
					}

					ct.append(String.format("\n\t\tnew ConstGroup(ConstGroup.METHOD_MOD, \"%s\", \"%s\", ", name, desc));
					ct.append(" %s),");
				}else if(type.equalsIgnoreCase("TYPE")){
					T = true;
					M = false;
					C = false;

					if(ct.length() > 0 && ct.toString().endsWith(", %s")){
						ct.delete(ct.length() - ", %s".length(), ct.length());
						ct.append("), ");
					}

					ct.append(String.format("\n\t\tnew ConstGroup(ConstGroup.METHOD_TYPE, \"%s\", \"%s\", ", name, desc));
					ct.append(" %s");
				}else{
					C = true;
					T = false;
					M = false;
					code.append(String.format("public static Entity %1$s = get%1$sEntity();", type));
					code.append(String.format("\nprivate static Entity get%sEntity() {", type));
					code.append(String.format("\n\tEntity entity = new Entity(\"%s\", \"%s\", \"%s\");", type, name, desc));
				}
			}else{
				if(T || M){
					String[] frags = line.split("\\s+");
					String name = frags[0], desc = frags[1];
					ct = new StringBuilder(String.format(ct.toString(), String.format("\n\t\t\tnew Const(\"%s\", \"%s\", \"%s\")", getConstName(N, name), getConstAliasName(name), desc)));
					if(ct.toString().endsWith(")),"))
						ct.delete(ct.length() - 2, ct.length());
					ct.append(", %s");
				}else if(C){
					String[] frags = line.split("\\s+");
					String type = frags[0],
							name = frags[1],
							desc = frags[2];
					if(name.equals("Integer") || name.equals("Long") || name.equals("String")){
						name = "\"" + name + "\"";
					}else if(name.equals("BigDecimal") || name.equals("Date")){
						name = name + ".class";
					}else{
						name = "\"Long\"";
					}
					cc.append(String.format("\n\t\tnew Property(%s, \"%s\", \"%s\"),", name, type, desc));
				}
			}
		}

		if(ct.length() > 0 && ct.toString().endsWith(", %s")){
			ct.delete(ct.length() - ", %s".length(), ct.length());
			ct.append("))");
		}

		cc.deleteCharAt(cc.length() - 1);
		if(ct.length() > 1) ct.deleteCharAt(ct.length() - 1);
		code.append("\n\t" + String.format("entity.setProperties(%s);", cc));
		if(ct.length() > 1)
			code.append("\n\t" + String.format("entity.setConstGroups(%s);", ct));
		code.append("\n\treturn entity;");
		code.append("\n}");
		System.out.println(code);
	}

	private static String getConstName(String name, String constName){
		name = name.replaceAll("([A-Z])", "_$1").toUpperCase();
		return constName.replaceFirst(name + "_", "");
	}

	private static String getConstAliasName(String constName){
		constName = constName.toLowerCase();
		Matcher m = Pattern.compile("_[a-z]").matcher(constName);

	    StringBuilder sb = new StringBuilder();
	    int last = 0;
	    while (m.find()) {
	        sb.append(constName.substring(last, m.start()));
	        sb.append(m.group(0).toUpperCase().replace("_", ""));
	        last = m.end();
	    }
	    sb.append(constName.substring(last));
		return sb.toString();
	}
}
