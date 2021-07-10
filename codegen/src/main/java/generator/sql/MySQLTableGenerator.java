package generator.sql;

import generator.Generator;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

import org.apache.commons.io.FileUtils;

import mate.ConstGroup;
import mate.Entity;
import mate.Project;
import mate.Property;
import org.apache.commons.lang3.StringUtils;
import util.MySQLUtil;

public class MySQLTableGenerator implements Generator{

	private final static Map<String, String> typeMapping;
	private boolean exec;

	public MySQLTableGenerator(boolean exec) {
		this.exec = exec;
	}

	public MySQLTableGenerator() {

	}

	static{
		typeMapping = new HashMap<String, String>();

		typeMapping.put("TYPE", "TINYINT");
		typeMapping.put("MOD", "BIGINT");

		typeMapping.put("byte", "INT");
		typeMapping.put("short", "INT");
		typeMapping.put("int", "INT");
		typeMapping.put("long", "BIGINT");
		typeMapping.put("boolean", "BIT");

		typeMapping.put("large_String", "LONGTEXT");
		typeMapping.put("encryption_String", "TINYBLOB");
		typeMapping.put("String", "VARCHAR(255)");
		typeMapping.put("Byte", "INT");
		typeMapping.put("Short", "INT");
		typeMapping.put("Integer", "INT");
		typeMapping.put("Long", "BIGINT");
		typeMapping.put("Boolean", "BIT");

		typeMapping.put(BigDecimal.class.getName(), "DECIMAL(18, 2)");
		typeMapping.put("high_" + BigDecimal.class.getName(), "DECIMAL(18, 4)");
		typeMapping.put(Date.class.getName(), "DATETIME");
	}

	@Override
	public void generate(Entity entity) {

		StringBuilder dropSQL = new StringBuilder();
		dropSQL.append("DROP TABLE IF EXISTS ").append(entity.getName()).append(";");

//		if(exec){
//			try {
//				MySQLUtil.exec(dropSQL.toString());
//			} catch (SQLException e) {
//				throw new RuntimeException(e);
//			}
//		}

		StringBuilder createSQL = new StringBuilder();
		createSQL.append("CREATE TABLE ").append(entity.getName()).append("(");
		createSQL.append("\n  id BIGINT PRIMARY KEY AUTO_INCREMENT, ");
		createSQL.append("\n  createdDate DATETIME, ");
		createSQL.append("\n  lastModifiedDate DATETIME, ");
		createSQL.append("\n  version INT");
		Property[] properties = entity.getProperties();
		if(properties != null){
			for(Property property : properties){
				String key = "";
				if(property.isLarge()){
					key = key + "large_";
				}else if(property.isHighPrecision()){
					key = key + "high_";
				} else if(property.getEncryptionKey() != null){
					key = key + "encryption_";
				}

				key = key + property.getType();
				createSQL.append(",\n  ").append(property.getName()).append(" ").append(
						property.getDatabaseDataType() != null ? property.getDatabaseDataType() : typeMapping.get(key));
			}
		}
		ConstGroup[] constGroups = entity.getConstGroups();
		if(constGroups != null){
			for(ConstGroup cg : constGroups){
				String dbType = typeMapping.get(cg.getMethod() == ConstGroup.METHOD_MOD ? "MOD" : "TYPE");
				if(cg.getConsts().length > 127){
					dbType = "Integer";
				}
				createSQL.append(", ").append(cg.getName()).append(" ").append(dbType);
			}
		}
		createSQL.append("\n) ENGINE = InnoDB DEFAULT CHARSET = utf8;");

		for (Property property : entity.getProperties()) {
			if (property.isUniqueIndex()) {
				String[] uniqueIndexWith = property.getUniqueIndexWith();
				if (uniqueIndexWith == null || uniqueIndexWith.length == 0) {
					createSQL.append(String.format("\nCREATE UNIQUE INDEX %2$s_uindex ON %1$s (%2$s);", entity.getName(), property.getName()));
				} else {
					createSQL.append(String.format("\nCREATE UNIQUE INDEX %2$s_uindex ON %1$s (%3$s);", entity.getName(),
							property.getName() + "_" + StringUtils.join(uniqueIndexWith, "_"),
							property.getName() + ", " + StringUtils.join(uniqueIndexWith, ", ")));
				}

			} else if (property.isIndex()) {
				String[] indexWith = property.getIndexWith();
				if (indexWith == null || indexWith.length == 0) {
					createSQL.append(String.format("\nCREATE INDEX %2$s_index ON %1$s (%2$s);", entity.getName(), property.getName()));
				} else {
					createSQL.append(String.format("\nCREATE INDEX %2$s_index ON %1$s (%3$s);", entity.getName(),
							property.getName() + "_" + StringUtils.join(indexWith, "_"),
							property.getName() + ", " + StringUtils.join(indexWith, ", ")));
				}

			}
		}

		if(constGroups != null){
			for(ConstGroup cg : constGroups){
				if (cg.isIndex()) {
					createSQL.append(String.format("\nCREATE INDEX %2$s_index ON %1$s (%2$s);", entity.getName(), cg.getName()));
				}
			}
		}

		try {
			File sqlFile = new File(Project.getInstance().getBaseDir() + "/"
					+ entity.getModule().getName() + ".sql");
			FileUtils.writeStringToFile(sqlFile, dropSQL + "\r\n" + createSQL.toString() + "\r\n", true);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if(exec){
			try {
				MySQLUtil.exec(createSQL.toString());
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public String getName() {
		return "DB Table";
	}
}
