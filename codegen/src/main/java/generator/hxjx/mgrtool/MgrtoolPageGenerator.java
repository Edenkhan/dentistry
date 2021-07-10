package generator.hxjx.mgrtool;

import generator.AbstractGenerator;
import mate.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MgrtoolPageGenerator extends AbstractGenerator {

    @Override
    public void generate(Entity entity) {
        TemplateEngine templateEngine = new TemplateEngine();

        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();
        context.setVariable("title", entity.getDescription());
        context.setVariable("breadcrumbs", new String[]{"一级菜单", entity.getDescription()});
        context.setVariable("actionName", StringUtils.uncapitalize(entity.getName()) + ".htm");
        context.setVariable("actionCmdName", "init");

        List<SortItem> sortItems = new ArrayList<>();
        sortItems.add(new SortItem("id", "创建时间", SortItemType.DATE));
        for (Property property : entity.getProperties()) {
            if (!property.isSortable()) {
                continue;
            }
            sortItems.add(new SortItem("id", property.getDescription(), SortItemType.DATE));
        }
        context.setVariable("sortItems", sortItems);

        List<FilterItem> filterItems = new ArrayList<>();
        filterItems.add(new FilterItem("createdDate", "创建时间", FilterItemType.DATE));
        for (Property property : entity.getProperties()) {
            if (property.isSearchable()) {
                filterItems.add(new FilterItem(property.getName(), property.getDescription(), FilterItemType.TEXT));
            }
            if (property.isLikeSearchable()) {
                if (property.getType().equals(Date.class.getName())) {
                    filterItems.add(new FilterItem(property.getName(), property.getDescription(), FilterItemType.DATE));
                }
            }
        }
        for (ConstGroup constGroup : entity.getConstGroups()) {
            if (constGroup.getMethod() == ConstGroup.METHOD_MOD) {
                continue;
            }
            filterItems.add(new ConstGroupFilterItem(constGroup.getName(), constGroup.getDescription(), FilterItemType.CONST_GROUP, constGroup.getConsts()));
        }
        context.setVariable("filterItems", filterItems);

        List<TableRow> tableRows = new ArrayList<>();
        for (Property property : entity.getProperties()) {
            TableRowType type = null;
            if (property.getType().equals(Date.class.getName())) {
                type = TableRowType.DATE;
            } else if (property.getType().equals(BigDecimal.class.getName())) {
                type = TableRowType.DECIMAL;
            } else if (property.getJoinEntity() != null) {
                String joinEntityName = property.getJoinEntity().getName();
                type = joinEntityName.equals("LoginInfo") ? TableRowType.USER
                        : joinEntityName.equals("BidRequest") ? TableRowType.BID_REQUEST
                        : TableRowType.TEXT;
            } else {
                type = TableRowType.TEXT;
            }
            tableRows.add(new TableRow(property.getName(), property.getDescription(), type, property));
        }
        for (ConstGroup constGroup : entity.getConstGroups()) {
            if (constGroup.getMethod() == ConstGroup.METHOD_MOD) {
                continue;
            }
            tableRows.add(new TableRow(constGroup.getName(), constGroup.getDescription(), TableRowType.CONST_GROUP, constGroup.getConsts()));
        }
        context.setVariable("tableRows", tableRows);

        String result = templateEngine.process("generator/hxjx/mgrtool/mgrtool-page-template.html", context);
        try {
            FileUtils.writeStringToFile(new File(new File(Project.getInstance().getBaseDir(), "mgrtool/pages"), entity.getName() + ".html"), result, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getName() {
        return "后台页面生成";
    }

    public enum SortItemType {
        DATE,
        DECIMAL
    }

    public enum FilterItemType {
        DATE,
        DECIMAL,
        TEXT,
        CONST_GROUP;
    }

    public enum TableRowType {
        DATE,
        TEXT,
        USER,
        BID_REQUEST,
        DECIMAL,
        CONST_GROUP;
    }

    public class FilterItem {

        private String propertyName;
        private String propertyDesc;
        private FilterItemType type;

        public FilterItem(String propertyName, String propertyDesc, FilterItemType type) {
            this.propertyName = propertyName;
            this.propertyDesc = propertyDesc;
            this.type = type;
        }

        public String getPropertyName() {
            return propertyName;
        }

        public String getPropertyDesc() {
            return propertyDesc;
        }

        public FilterItemType getType() {
            return type;
        }
    }

    public class ConstGroupFilterItem extends FilterItem {
        private Const[] constants;

        public ConstGroupFilterItem(String propertyName, String propertyDesc, FilterItemType type, Const[] constants) {
            super(propertyName, propertyDesc, type);
            this.constants = constants;
        }

        public Const[] getConstants() {
            return constants;
        }
    }

    public class SortItem {

        private String propertyName;
        private String propertyDesc;
        private SortItemType type;

        public SortItem(String propertyName, String propertyDesc, SortItemType type) {
            this.propertyName = propertyName;
            this.type = type;
            this.propertyDesc = propertyDesc;
        }

        public String getPropertyDesc() {
            return propertyDesc;
        }

        public SortItemType getType() {
            return type;
        }

        public String getPropertyName() {
            return propertyName;
        }
    }

    public class TableRow {
        private Object data;
        private String propertyName;
        private String propertyDesc;
        private TableRowType type;

        public TableRow(String propertyName, String propertyDesc, TableRowType type, Object data) {
            this.propertyName = propertyName;
            this.type = type;
            this.propertyDesc = propertyDesc;
            this.data = data;
        }

        public String getPropertyDesc() {
            return propertyDesc;
        }

        public TableRowType getType() {
            return type;
        }

        public String getPropertyName() {
            return propertyName;
        }

        public Object getData() {
            return data;
        }
    }
}
