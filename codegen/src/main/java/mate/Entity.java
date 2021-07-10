package mate;

import javax.management.AttributeList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Entity extends Basic {

    protected String alias;
    protected ConstGroup[] constGroups;
    protected Property[] properties;
    protected List<ServiceMethod> serviceMethods;
    private Module module;
    protected boolean batchInsert;
    protected boolean batchDelete;
    private boolean batchUpdate;
    private boolean randomable;
    private boolean entityLike;
    private MappingSearch mappingSearch;
    private List<Join> joins = new ArrayList<>();

    public MappingSearch getMappingSearch() {
        return mappingSearch;
    }

    public void setMappingSearch(MappingSearch mappingSearch) {
        this.mappingSearch = mappingSearch;
    }

    public void setBatchDelete(boolean batchDelete) {
        this.batchDelete = batchDelete;
    }

    public boolean isBatchDelete() {
        return batchDelete;
    }

    public boolean isBatchInsert() {
        return batchInsert;
    }

    public void setBatchInsert(boolean batchInsert) {
        this.batchInsert = batchInsert;
    }

    public void setBatchInsert(Boolean batchInsert) {
        this.batchInsert = batchInsert;
    }


    public Module getModule() {
        return module;
    }

    public Entity(String name, String alias, String description) {
        super(name, description);
        setAlias(alias);
    }

    public Entity addServiceMethods(ServiceMethod... serviceMethods) {
        this.serviceMethods = Arrays.asList(serviceMethods);
        return this;
    }

    public List<ServiceMethod> getServiceMethods() {
        return serviceMethods;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public ConstGroup[] getConstGroups() {
        if (constGroups == null)
            constGroups = new ConstGroup[0];
        return constGroups;
    }

    public void setConstGroups(ConstGroup... constGroups) {
        this.constGroups = constGroups;
    }

    public Property[] getProperties() {
        return properties;
    }

    public void setProperties(Property... properties) {
        this.properties = properties;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Entity setModule(String module) {
        setModule(new Module(module));
        return this;
    }

    public void setBatchUpdate(boolean batchUpdate) {
        this.batchUpdate = batchUpdate;
    }

    public boolean isBatchUpdate() {
        return batchUpdate;
    }

    public void setRandomable(boolean randomable) {
        this.randomable = randomable;
    }

    public boolean isRandomable() {
        return randomable;
    }

    public boolean isEntityLike() {
        return entityLike;
    }

    public void setEntityLike(boolean entityLike) {
        this.entityLike = entityLike;
    }

    public void setMappingSearch(String searchPropertyName, Entity entity, String joinPropertyName) {
        this.mappingSearch = new MappingSearch(searchPropertyName, entity, joinPropertyName);
    }

    public void addJoin(Entity entity, String targetName, String propertyName, String[] joinPropertyNames) {
        joins.add(new Join(entity, targetName, propertyName, joinPropertyNames));
    }

    public static class MappingSearch {
        private final String joinPropertyName;
        private final Entity entity;
        private final String searchPropertyName;

        public MappingSearch(String searchPropertyName, Entity entity, String joinPropertyName) {
            this.entity = entity;
            this.searchPropertyName = searchPropertyName;
            this.joinPropertyName = joinPropertyName;
        }

        public Entity getEntity() {
            return entity;
        }

        public String getSearchPropertyName() {
            return searchPropertyName;
        }

        public String getJoinPropertyName() {
            return joinPropertyName;
        }
    }

    public List<Join> getJoins() {
        return joins;
    }
}
