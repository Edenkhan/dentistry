package generator.mybatis;

import generator.AbstractGenerator;
import mate.*;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyBatisMapperXMLGenerator extends AbstractGenerator {

    @Override
    public void generate(Entity type) {
        try {
            _generate(type);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void _generate(Entity entity) throws Exception {

        List<Property> joinProps = new ArrayList<Property>();
        List<Property> largeTextProps = new ArrayList<Property>();
        Property[] properties = entity.getProperties();
        for (Property property : properties) {
            if (property.getJoinEntity() != null || !property.getJoins().isEmpty()) {
                joinProps.add(property);
            }
            if (property.isLarge() && property.getType().equals("String"))
                largeTextProps.add(property);
        }

        String tableAlias = entity.getAlias();
        String colPrefix = tableAlias + ".";

        String mapperClassName = Project.getInstance().getMapperInterfaceClassName(entity);
        String entityClassName = Project.getInstance().getEntityClassName(entity);
        String voClassName = Project.getInstance().getValueObjectClassName(entity);
        String qoClassName = Project.getInstance().getQueryConditionClassName(entity);

        // document/root
        Document doc = DocumentHelper.createDocument();
        doc.addDocType("mapper", "-//mybatis.org//DTD Mapper 3.0//EN", "http://mybatis.org/dtd/mybatis-3-mapper.dtd");
        Element root = doc.addElement("mapper");
        root.addAttribute("namespace", mapperClassName);

        // BASE_RESULT
        Element baseResult = root.addElement("resultMap");
        baseResult.addAttribute("id", "BASE_RESULT")
                .addAttribute("type", entityClassName);

        baseResult.addElement("id").addAttribute("property", "id")
                .addAttribute("column", "id");
        baseResult.addElement("result").addAttribute("property", "createdDate")
                .addAttribute("column", "createdDate");
        baseResult.addElement("result").addAttribute("property", "lastModifiedDate")
                .addAttribute("column", "lastModifiedDate");
        baseResult.addElement("result").addAttribute("property", "version")
                .addAttribute("column", "version");

        // BASE_COLUMNS
        Element baseColumns = root.addElement("sql").addAttribute("id", "BASE_COLUMNS");
        baseColumns.addText(colPrefix + "id, ");
        baseColumns.addText(colPrefix + "createdDate, ");
        baseColumns.addText(colPrefix + "lastModifiedDate, ");
        baseColumns.addText(colPrefix + "version");

        // QUERY_COLUMNS
        Element queryColumns = root.addElement("sql").addAttribute("id", "QUERY_COLUMNS");
        queryColumns.addText(colPrefix + "id, ");
        queryColumns.addText(colPrefix + "createdDate, ");
        queryColumns.addText(colPrefix + "lastModifiedDate, ");
        queryColumns.addText(colPrefix + "version");

        for (int i = 0, len = properties.length; i < len; i++) {
            Property property = properties[i];
            String propertyName = property.getName();
            baseResult.addElement("result").addAttribute("property", propertyName).addAttribute("column", propertyName);

            if (property.getEncryptionKey() != null) {
                baseColumns.addText(",CONVERT(AES_DECRYPT("
                        + colPrefix
                        + propertyName + ","
                        + property.getEncryptionKey()
                        + ") USING 'utf8') AS "
                        + propertyName);
            } else {
                baseColumns.addText("," + colPrefix + propertyName);
            }

            if (largeTextProps.contains(property)) {
                queryColumns.addElement("if").addAttribute("test", "select" + StringUtils.capitalize(propertyName))
                        .addText("," + colPrefix + propertyName);
            } else {
                if (property.getEncryptionKey() != null) {
                    queryColumns.addText(",CONVERT(AES_DECRYPT("
                            + colPrefix
                            + propertyName + ","
                            + property.getEncryptionKey()
                            + ") USING 'utf8') AS "
                            + propertyName);
                } else {
                    queryColumns.addText("," + colPrefix + propertyName);
                }
            }
        }
        ConstGroup[] constGroups = entity.getConstGroups();
        if (constGroups != null) {
            for (ConstGroup cg : constGroups) {
                String propertyName = cg.getName();
                baseResult.addElement("result").addAttribute("property", propertyName).addAttribute("column", propertyName);
                baseColumns.addText("," + colPrefix + propertyName);
                queryColumns.addText("," + colPrefix + propertyName);
            }
        }

        // VO_RESULT
        Element voResult = root.addElement("resultMap");
        voResult.addAttribute("id", "VO_RESULT")
                .addAttribute("type", voClassName)
                .addAttribute("extends", "BASE_RESULT");

        // JOIN_COLUMNS
        Element joinColumns = root.addElement("sql").addAttribute("id", "JOIN_COLUMNS");
        for (Property property : joinProps) {
            if (property.getJoinEntity() != null) {
                Entity joinEntity = property.getJoinEntity();
                String propertyName = property.getName();

                Element association = voResult.addElement("association");

                association.addAttribute("property", propertyName.replaceAll("Id$", ""))
                        .addAttribute("column", propertyName)
                        .addAttribute("columnPrefix", property.getName().replaceAll("Id$", "") + "_");

                if (property.getJoinEntity().isEntityLike() || "LoginInfo".equals(property.getJoinEntity().getName())) {
                    association.addAttribute("javaType", "java.util.HashMap");
                    for (String joinName : property.getJoinNames()) {
                        association.addElement("result")
                                .addAttribute("property", joinName)
                                .addAttribute("column", joinName);
                    }
                } else {
                    association.addAttribute("javaType", Project.getInstance().getEntityClassName(joinEntity))
                            .addAttribute("resultMap", Project.getInstance().getMapperInterfaceClassName(joinEntity) + "." + "BASE_RESULT");
                }

                List<String> joinColumnNames = new ArrayList<>();
                Element ifEle = joinColumns.addElement("if").addAttribute("test", "join" + StringUtils.capitalize(property.getName().replaceAll("Id$", "")) + " != null");
                for (String name : property.getJoinNames()) {
                    if (("LoginInfo".equals(property.getJoinEntity().getName()) && name.equals("userName"))
                            || ("FinancialProductInvestAccount".equals(property.getJoinEntity().getName()) && name.equals("investUserName"))) {
                        joinColumnNames.add(String.format("CONVERT(AES_DECRYPT(%1$s.%2$s, #{USER_NAME_KEY,javaType=UseNameEncryKey}) USING 'utf8') AS %1$s_%2$s", property.getName().replaceAll("Id$", ""), name));
                    } else {
                        joinColumnNames.add(String.format("%1$s.%2$s AS %1$s_%2$s", property.getName().replaceAll("Id$", ""), name));
                    }
                }
                ifEle.setText(", " + StringUtils.join(joinColumnNames, ", "));
            }

            for (Join join : property.getJoins()) {
                generateJoinColumns(voResult, joinColumns, join.getTargetName(), join);
            }
        }
        for (Join join : entity.getJoins()) {
            generateJoinColumns(voResult, joinColumns, null, join);
        }


        // getMethod
        Element getMethod = root.addElement("select")
                .addAttribute("id", "get")
                .addAttribute("resultMap", "BASE_RESULT")
                .addAttribute("parameterType", Long.class.getName());
        getMethod.addText(" SELECT ");
        getMethod.addElement("include").addAttribute("refid", "BASE_COLUMNS");
        getMethod.addText(" FROM " + entity.getName() + " " + entity.getAlias());
        getMethod.addText(" WHERE id = #{id }");

        // deleteMethod
        Element deleteMethod = root.addElement("delete")
                .addAttribute("id", "delete")
                .addAttribute("parameterType", Long.class.getName());
        deleteMethod.addText(String.format("DELETE FROM %s WHERE id = #{id}", entity.getName()));

        // addMethod
        Element addMethod = root.addElement("insert")
                .addAttribute("id", "add")
                .addAttribute("parameterType", entityClassName)
                .addAttribute("useGeneratedKeys", "true")
                .addAttribute("keyProperty", "id")
                .addAttribute("keyColumn", "id");
        StringBuilder addColumns = new StringBuilder("id, createdDate, version");
        StringBuilder addColumnValues = new StringBuilder("#{id }, #{createdDate}, 0");
        for (Property property : properties) {
            if (!property.isInsert())
                continue;

            addColumns.append(",").append(property.getName());

            String columnValue = "#{" + property.getName() + "}";
            if (property.getEncryptionKey() != null) {
                columnValue = "AES_ENCRYPT(" + columnValue + ", " + property.getEncryptionKey() + ")";
            }
            addColumnValues.append(", ").append(columnValue);
        }
        constGroups = entity.getConstGroups();
        if (constGroups != null) {
            for (ConstGroup cg : constGroups) {
                if (!cg.isInsert())
                    continue;

                addColumns.append(",").append(cg.getName());
                addColumnValues.append(", #{").append(cg.getName()).append("}");
            }
        }
        addMethod.addText(" INSERT INTO " + entity.getName() + "(" + addColumns + ")");
        addMethod.addText(" VALUES(" + addColumnValues + ")");

        // batchAddMethod
        if (entity.isBatchInsert()) {
            Element batchAddMethod = root.addElement("insert")
                    .addAttribute("id", "batchAdd")
                    .addAttribute("parameterType", List.class.getName());

            StringBuilder batchAddColumnValues = new StringBuilder("#{value.id}, #{value.createdDate}, 0");
            for (Property property : properties) {
                if (!property.isInsert())
                    continue;
                batchAddColumnValues.append(", #{value.")
                        .append(property.getName()).append("}");
            }
            constGroups = entity.getConstGroups();
            if (constGroups != null) {
                for (ConstGroup cg : constGroups) {
                    if (!cg.isInsert())
                        continue;
                    batchAddColumnValues.append(", #{value.")
                            .append(cg.getName())
                            .append("}");
                }
            }

            batchAddMethod.addText(" INSERT INTO " + entity.getName() + "(" + addColumns + ") VALUES ");
            batchAddMethod.addElement("foreach")
                    .addAttribute("collection", "values")
                    .addAttribute("item", "value")
                    .addAttribute("separator", ",")
                    .addText("(" + batchAddColumnValues + ")");
        }

        // update
        Element updateMethod = root.addElement("update")
                .addAttribute("id", "update")
                .addAttribute("parameterType", entityClassName);
        updateMethod.addText(" UPDATE " + entity.getName() + "");
        Element setEle = updateMethod.addElement("set");
        setEle.addText("lastModifiedDate = now(), version = version + 1");
        for (Property property : properties) {
            if (!property.isUpdate()) {
                continue;
            }
            String valueExpression = "#{" + property.getName() + "}";
            if (property.getEncryptionKey() != null) {
                valueExpression = "AES_ENCRYPT(" + valueExpression + ", " + property.getEncryptionKey() + ")";
            }
            setEle.addText(", " + property.getName() + " = " + valueExpression);
        }
        if (constGroups != null) {
            for (ConstGroup cg : constGroups) {
                if (!cg.isUpdate())
                    continue;

                setEle.addText(", " + cg.getName() + " = #{" + cg.getName() + "}");
            }
        }
        updateMethod.addElement("where").addText("id = #{id} AND version = #{version}");

        // batchUpdateMethod
        if (entity.isBatchUpdate()) {
            throw new UnsupportedOperationException("不支持");
            /*Element batchUpdateMethod = root.addElement("update")
                    .addAttribute("id", "batchUpdate")
                    .addAttribute("parameterType", List.class.getName());

            Element foreachEle = batchUpdateMethod.addElement("foreach")
                    .addAttribute("collection", "values")
                    .addAttribute("item", "value")
                    .addAttribute("separator", ";");
            foreachEle.addText(" UPDATE " + entity.getName() + "");
            Element batchSetEle = foreachEle.addElement("set");
            batchSetEle.addText("lastModifiedDate = now(), version = version + 1");
            for (Property property : properties) {
                if (!property.isUpdate())
                    continue;
                batchSetEle.addText(", " + property.getName() + " = #{value." + property.getName() + "}");
            }
            if (constGroups != null) {
                for (ConstGroup cg : constGroups) {
                    if (!cg.isUpdate())
                        continue;
                    batchSetEle.addText(", " + cg.getName() + " = #{value." + cg.getName() + "}");
                }
            }
            foreachEle.addElement("where").addText("id = #{value.id} AND version = #{value.version}");*/
        }

        // JOIN
        Element joinEl = root.addElement("sql").addAttribute("id", "QUERY_JOIN");
        for (Property property : joinProps) {
            if (property.getJoinEntity() != null) {
                Entity joinEntity = property.getJoinEntity();
                String name = property.getName().replaceAll("Id$", "");
                String joinPropertyName = "join" + StringUtils.capitalize(name);
                joinEl.addElement("if").addAttribute("test", joinPropertyName + " != null")
                        .addText(String.format("${" + joinPropertyName + ".name} %1$s %2$s ON %2$s.id = %3$s.%4$s", joinEntity.getName(), name, entity.getAlias(), property.getName()));
            }
            for (Join join : property.getJoins()) {
                generateQueryJoin(entity, joinEl, property.getName(), join);
            }
        }
        for (Join join : entity.getJoins()) {
            generateQueryJoin(entity, joinEl, "id", join);
        }

        Entity.MappingSearch mappingSearch = entity.getMappingSearch();
        if (mappingSearch != null) {
            Entity mappingEntity = mappingSearch.getEntity();
            String searchPropertyName = mappingSearch.getSearchPropertyName();
            String joinPropertyName = mappingSearch.getJoinPropertyName();
            joinEl.addElement("if").addAttribute("test", searchPropertyName + " != null")
                    .addText(String.format("JOIN %1$s %2$s ON %2$s.%3$s = %4$s.id AND %2$s.%5$s = #{%5$s}",
                            mappingEntity.getName(),
                            mappingEntity.getAlias(),
                            joinPropertyName,
                            entity.getAlias(),
                            searchPropertyName));
        }

        // QUERY WHERE_SQL
        Element queryWhereSQL = root.addElement("sql").addAttribute("id", "QUERY_WHERE_SQL");
        addQueryWhere(queryWhereSQL, entity, null, null);
        addJoinQueryWhere(queryWhereSQL, joinProps);
        for (Join join : entity.getJoins()) {
            generateJoinQueryWhere(queryWhereSQL, join);
        }

        // QUERY_WHERE
        Element queryWhere = root.addElement("sql").addAttribute("id", "QUERY_WHERE").addElement("where");
        queryWhere.addElement("include").addAttribute("refid", "QUERY_WHERE_SQL");

        // QUERY_ORDER_BY
        Element foreachElement = root.addElement("sql").addAttribute("id", "QUERY_ORDER_BY")
                .addElement("if")
                .addAttribute("test", "orderBys.size() > 0")
                .addText("ORDER BY")
                .addElement("foreach")
                .addAttribute("collection", "orderBys")
                .addAttribute("item", "value")
                .addAttribute("index", "key")
                .addAttribute("separator", ", ");
        if (entity.isRandomable()) {
            foreachElement
                    .addElement("if")
                    .addAttribute("test", "key != 'RAND()'")
                    .addText(colPrefix);
        } else {
            foreachElement.addText(colPrefix);
        }
        foreachElement.addText("${key} ${value}");
        root.addElement("sql").addAttribute("id", "QUERY_LIMIT")
                .addElement("if")
                .addAttribute("test", "pageSize > 0")
                .setText("LIMIT ${start}, ${pageSize}");

        // select - count
        Element countMethod = root.addElement("select")
                .addAttribute("id", "count")
                .addAttribute("parameterType", qoClassName)
                .addAttribute("resultType", Integer.class.getName());
        countMethod.addText(" SELECT COUNT(*) FROM " + entity.getName() + " AS " + tableAlias);
        countMethod.addElement("include").addAttribute("refid", "QUERY_JOIN");
        countMethod.addElement("include").addAttribute("refid", "QUERY_WHERE");

        // query
        Element queryMethod = root.addElement("select")
                .addAttribute("id", "query")
                .addAttribute("parameterType", qoClassName)
                .addAttribute("resultMap", "VO_RESULT");
        queryMethod.addText(" SELECT ");
        queryMethod.addElement("include").addAttribute("refid", "QUERY_COLUMNS");
        queryMethod.addElement("include").addAttribute("refid", "JOIN_COLUMNS");
        queryMethod.addText(" FROM " + entity.getName() + " AS " + tableAlias);
        queryMethod.addElement("include").addAttribute("refid", "QUERY_JOIN");
        queryMethod.addElement("include").addAttribute("refid", "QUERY_WHERE");
        queryMethod.addElement("include").addAttribute("refid", "QUERY_ORDER_BY");
        queryMethod.addElement("include").addAttribute("refid", "QUERY_LIMIT");

        OutputFormat format = OutputFormat.createPrettyPrint();
        XMLWriter writer = new XMLWriter(new FileWriter(Project.getInstance().getMapperXMLFileName(entity)), format);
        writer.write(doc);
        writer.close();
    }

    private void generateQueryJoin(Entity entity, Element joinEl, String propertyName, Join join) {
        Entity joinEntity = join.getEntity();
        String name = join.getPropertyName();
        String joinPropertyName = "join" + StringUtils.capitalize(name);
        joinEl.addElement("if").addAttribute("test", joinPropertyName + " != null")
                .addText(String.format("${" + joinPropertyName + ".name} %1$s %2$s ON %2$s.%5$s = %3$s.%4$s",
                        joinEntity.getName(),
                        name,
                        entity.getAlias(),
                        propertyName,
                        join.getTargetName()));
    }

    private void generateJoinColumns(Element voResult, Element joinColumns, String columnName, Join join) {
        Entity joinEntity = join.getEntity();
        String propertyName = join.getPropertyName();
        Element association = voResult.addElement("association");
        association.addAttribute("property", propertyName);
        if (columnName != null) {
            association.addAttribute("column", columnName);
        }
        association.addAttribute("columnPrefix", propertyName + "_");

        if (joinEntity.isEntityLike() || "LoginInfo".equals(joinEntity.getName())) {
            association.addAttribute("javaType", "java.util.HashMap");
            for (String joinName : join.getJoinPropertyNames()) {
                association.addElement("result")
                        .addAttribute("property", joinName)
                        .addAttribute("column", joinName);
            }
        } else {
            association.addAttribute("javaType", Project.getInstance().getEntityClassName(joinEntity))
                    .addAttribute("resultMap", Project.getInstance().getMapperInterfaceClassName(joinEntity) + "." + "BASE_RESULT");
        }

        List<String> joinColumnNames = new ArrayList<>();
        Element ifEle = joinColumns.addElement("if").addAttribute("test", "join" + StringUtils.capitalize(propertyName) + " != null");
        for (String name : join.getJoinPropertyNames()) {
            if (("LoginInfo".equals(joinEntity.getName()) && name.equals("userName"))
                    || ("FinancialProductInvestAccount".equals(joinEntity.getName()) && name.equals("investUserName"))) {
                joinColumnNames.add(String.format("CONVERT(AES_DECRYPT(%1$s.%2$s, #{USER_NAME_KEY,javaType=UseNameEncryKey}) USING 'utf8') AS %1$s_%2$s", propertyName, name));
            } else {
                joinColumnNames.add(String.format("%1$s.%2$s AS %1$s_%2$s", propertyName, name));
            }
        }
        ifEle.setText(", " + StringUtils.join(joinColumnNames, ", "));
    }

    private void addQueryWhere(Element queryWhereSQL, Entity entity, String propertyPrefix, String colPrefix) {
        if (colPrefix == null)
            colPrefix = entity.getAlias() + ".";
        if (propertyPrefix == null)
            propertyPrefix = "";

        queryWhereSQL.addElement("if")
                .addAttribute("test", propertyPrefix + "ids != null")
                .addText("AND " + colPrefix + "id IN")
                .addElement("foreach")
                .addAttribute("collection", propertyPrefix + "ids")
                .addAttribute("item", "id")
                .addAttribute("open", "(")
                .addAttribute("close", ")")
                .addAttribute("separator", ", ")
                .setText("#{id }");
        queryWhereSQL.addElement("if").addAttribute("test", propertyPrefix + "startCreatedDate != null").addText("AND " + colPrefix + "createdDate >= #{" + propertyPrefix + "startCreatedDate }");
        queryWhereSQL.addElement("if").addAttribute("test", propertyPrefix + "endCreatedDate != null").addText("AND  " + colPrefix + "createdDate <= #{" + propertyPrefix + "endCreatedDate }");

        for (Property property : entity.getProperties()) {
            String type = property.getType();
            String name = property.getName();
            String capitalizedName = StringUtils.capitalize(name);

            if (property.isSearchable()) {
                String fieldName = colPrefix + name;
                String fieldValue = "#{" + propertyPrefix + name + "}";
                if (property.getEncryptionKey() != null) {
                    fieldValue = "AES_ENCRYPT(" + fieldValue + ", " + property.getEncryptionKey() + ")";
                }
                queryWhereSQL
                        .addElement("if")
                        .addAttribute("test", propertyPrefix + name + " != null")
                        .addText("AND " + fieldName + " = " + fieldValue);
            }

            if (property.isNotEqualSearchable()) {
                String qoPropertyName = propertyPrefix + name + "NotEqualTo";
                String fieldName = colPrefix + name;
                String fieldValue = "#{" + propertyPrefix + qoPropertyName + "}";
                if (property.getEncryptionKey() != null) {
                    fieldValue = "AES_ENCRYPT(" + fieldValue + ", " + property.getEncryptionKey() + ")";
                }
                queryWhereSQL
                        .addElement("if")
                        .addAttribute("test", propertyPrefix + qoPropertyName + " != null")
                        .addText("AND " + fieldName + " <> " + fieldValue);
            }

            if (property.isNullOrEqualTo()) {
                String qoPropertyName = propertyPrefix + name + "IsNullOrEqualTo";
                String fieldName = colPrefix + name;
                String fieldValue = "#{" + propertyPrefix + qoPropertyName + "}";
                queryWhereSQL
                        .addElement("if")
                        .addAttribute("test", propertyPrefix + qoPropertyName + " != null")
                        .addText("AND (" + fieldName + " IS NULL OR " + fieldName + " = " + fieldValue + ")");
            }

            if (property.isNullSearchale()) {
                String qoPropertyName = propertyPrefix + "null" + StringUtils.capitalize(name);
                Element ifEle = queryWhereSQL.addElement("if").addAttribute("test", qoPropertyName + " != null");
                ifEle.addElement("choose")
                        .addElement("when").addAttribute("test", qoPropertyName)
                        .addText("AND " + colPrefix + name + " IS NULL")
                        .getParent()
                        .addElement("otherwise")
                        .addText("AND " + colPrefix + name + " IS NOT NULL");
            }

            if (!property.isLikeSearchable())
                continue;

            if (type.equals(BigDecimal.class.getName()) || type.equals(Integer.class.getSimpleName())
                    || type.equals(Short.class.getSimpleName())
                    || type.equals(Byte.class.getSimpleName())) {
                String minName = propertyPrefix + "min" + capitalizedName;
                String maxName = propertyPrefix + "max" + capitalizedName;
                queryWhereSQL.addElement("if").addAttribute("test", minName + " != null").addText("AND " + colPrefix + name + " >= #{" + minName + "}");
                queryWhereSQL.addElement("if").addAttribute("test", maxName + " != null").addText("AND " + colPrefix + name + " <= #{" + maxName + "}");
            } else if (type.equals(Date.class.getName())) {
                String startName = propertyPrefix + "start" + capitalizedName;
                String endName = propertyPrefix + "end" + capitalizedName;
                queryWhereSQL.addElement("if").addAttribute("test", startName + " != null").addText("AND " + colPrefix + name + " >= #{" + startName + "}");
                queryWhereSQL.addElement("if").addAttribute("test", endName + " != null").addText("AND " + colPrefix + name + " <= #{" + endName + "}");
            } else if (type.equals("String")) {
                String fieldName = colPrefix + name;
                if (property.getEncryptionKey() != null) {
                    fieldName = "CONVERT(AES_DECRYPT(" + fieldName + "," + property.getEncryptionKey() + ") USING 'utf8')";
                }
                String likeName = propertyPrefix + "like" + StringUtils.capitalize(name);
                queryWhereSQL.addElement("if").addAttribute("test", likeName + " != null").addText("AND " + fieldName + " LIKE CONCAT('%', #{" + likeName + "} ,'%')");
            } else if (type.equals(Long.class.getSimpleName())) {
                String collName = propertyPrefix + name + "s";
                queryWhereSQL.addElement("if").addAttribute("test", collName + " != null")
                        .addText("AND " + colPrefix + name + " IN")
                        .addElement("foreach")
                        .addAttribute("collection", collName)
                        .addAttribute("item", name)
                        .addAttribute("open", "(")
                        .addAttribute("close", ")")
                        .addAttribute("separator", ", ")
                        .setText("#{" + name + "}");
            }
        }

        for (ConstGroup cg : entity.getConstGroups()) {

            String capitalizedName = StringUtils.capitalize(cg.getName());
            String suffix = (cg.getMethod() == ConstGroup.METHOD_MOD) ? "" : "s";
            String excludeName = propertyPrefix + "exclude" + capitalizedName + suffix;
            String includeName = propertyPrefix + "include" + capitalizedName + suffix;

            if (cg.getMethod() == ConstGroup.METHOD_TYPE) {
                if (cg.isExclude()) {
                    queryWhereSQL.addElement("if").addAttribute("test", excludeName + " != null")
                            .addText("AND " + colPrefix + cg.getName() + " NOT IN")
                            .addElement("foreach")
                            .addAttribute("collection", excludeName)
                            .addAttribute("item", cg.getName())
                            .addAttribute("open", "(")
                            .addAttribute("close", ")")
                            .addAttribute("separator", ", ")
                            .setText("#{" + cg.getName() + "}");
                }

                if (cg.isInclude()) {
                    queryWhereSQL.addElement("if").addAttribute("test", includeName + " != null"
                            + (cg.isExclude() ? " && " + excludeName + " == null" : ""))
                            .addText("AND " + colPrefix + cg.getName() + " IN")
                            .addElement("foreach")
                            .addAttribute("collection", includeName)
                            .addAttribute("item", cg.getName())
                            .addAttribute("open", "(")
                            .addAttribute("close", ")")
                            .addAttribute("separator", ", ")
                            .setText("#{" + cg.getName() + "}");
                }
            } else if (cg.getMethod() == ConstGroup.METHOD_MOD) {
                if (cg.isExclude()) {
                    queryWhereSQL.addElement("if").addAttribute("test", excludeName + " != null")
                            .addText(" AND ")
                            .addElement("foreach")
                            .addAttribute("collection", excludeName)
                            .addAttribute("item", "mod")
                            .addAttribute("open", "(")
                            .addAttribute("close", ")")
                            .addAttribute("separator", " AND ")
                            .addText(colPrefix + cg.getName() + " & #{mod } = 0");
                }

                if (cg.isInclude()) {
                    queryWhereSQL.addElement("if").addAttribute("test", includeName + " != null")
                            .addText(" AND ")
                            .addElement("foreach")
                            .addAttribute("collection", includeName)
                            .addAttribute("item", "mod")
                            .addAttribute("open", "(")
                            .addAttribute("close", ")")
                            .addAttribute("separator", " OR ")
                            .setText(colPrefix + cg.getName() + " & #{mod } <> 0");
                }
            }
        }
    }

    // JOIN QUERY
    private void addJoinQueryWhere(Element queryWhereSQL, List<Property> joinProps) {
        for (Property joinProperty : joinProps) {
            if (joinProperty.getJoinEntity() != null) {
                if (joinProperty.getJoinEntity().isEntityLike() || joinProperty.getJoinEntity().getName().equals("LoginInfo")) {
                    continue;
                }

                String joinName = joinProperty.getName().substring(0, joinProperty.getName().length() - 2);
                String colPrefix = joinName + ".";
                String propertyPrefix = joinName + "Query.";

                Element joinIf = queryWhereSQL.addElement("if")
                        .addAttribute("test", joinName + "Query != null && join" + StringUtils.capitalize(joinName));
                addQueryWhere(joinIf, joinProperty.getJoinEntity(), propertyPrefix, colPrefix);
            }
            for (Join join : joinProperty.getJoins()) {
                generateJoinQueryWhere(queryWhereSQL, join);
            }
        }
    }

    private void generateJoinQueryWhere(Element queryWhereSQL, Join join) {
        if (join.getEntity().isEntityLike() || join.getEntity().getName().equals("LoginInfo")) {
            return;
        }

        String joinName = join.getPropertyName();
        String colPrefix = joinName + ".";
        String propertyPrefix = joinName + "Query.";

        Element joinIf = queryWhereSQL.addElement("if")
                .addAttribute("test", joinName + "Query != null && join" + StringUtils.capitalize(joinName));
        addQueryWhere(joinIf, join.getEntity(), propertyPrefix, colPrefix);
    }

    @Override
    public String getName() {
        return "Mapper XML";
    }
}
