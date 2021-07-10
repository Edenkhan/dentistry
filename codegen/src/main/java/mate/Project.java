package mate;

import java.io.File;

import mate.ThreeJoin.Join;

import org.apache.commons.lang3.StringUtils;


public class Project {
    public static final Project INSTANCE = new Project();

    private String baseDir = "./gen/";

    private String name = "com.lanyotech.p2p.core";
    private String entityPackageName = "domain";
    private String mapperPackageName = "mapper";
    private String servicePackageName = "service";
    private String queryObjectPackageName = "qo";
    private String valueObjectPackageName = "vo";
    private String serviceImplementPackageName = "service.impl";

    private String basicEntityClassName = name + ".base.domain.BasicDomain";
    private String queryConditionClassName = name + ".base.query.QueryCondition";
    private String optimismLockingExceptionClassName = name + ".base.ex.OptimismLockingException";
    private String paginationClassName = name + ".base.query.Pagination";
    private String modClassName = name + ".base.domain.Mod";
    private String joinTypeClassName = name + ".base.query.JoinType";
    private String groupBysClassName = name + ".base.query.GroupBys";
    private String dateGroupByClassName = name + ".base.query.DateGroupBy";

    private String springServiceDir = "./spring";
    private String springMapperDir = "./spring";
    private String myBatisDir = "./";


    public void setName(String name) {
        this.name = name;
        basicEntityClassName = name + ".base.domain.BasicDomain";
        queryConditionClassName = name + ".base.query.QueryCondition";
        optimismLockingExceptionClassName = name + ".base.ex.OptimismLockingException";
        paginationClassName = name + ".base.query.Pagination";
        modClassName = name + ".department.Mod";
        joinTypeClassName = name + ".department.qo.JoinType";
        groupBysClassName = name + ".base.query.GroupBys";
        dateGroupByClassName = name + ".base.query.DateGroupBy";;
    }
    public void setXiaoMeName(String name) {
        this.name = name;
        basicEntityClassName = name + ".base.domain.BasicDomain";
        queryConditionClassName = name + ".base.query.QueryCondition";
        optimismLockingExceptionClassName = name + ".base.exception.OptimismLockingException";
        paginationClassName = name + ".base.query.Pagination";
        modClassName = name + ".base.domain.Mod";
        joinTypeClassName = name + ".base.query.JoinType";
        groupBysClassName = name + ".base.query.GroupBys";
        dateGroupByClassName = name + ".base.query.DateGroupBy";
    }

    public static Project getInstance() {
        return INSTANCE;
    }

    public File getSpringMapperFile(String moduleName) {
        File parent = new File(baseDir, springMapperDir);
        if (!parent.exists() || !parent.isDirectory())
            parent.mkdirs();

        File file = new File(parent, "mapper-" + moduleName + ".xml");
        return file;
    }

    public File getSpringServiceFile(String moduleName) {
        File parent = new File(baseDir, springServiceDir);
        if (!parent.exists() || !parent.isDirectory())
            parent.mkdirs();

        File file = new File(parent, "service-" + moduleName + ".xml");
        return file;
    }

    public File getMyBatisFile() {
        File parent = new File(baseDir, myBatisDir);
        if (!parent.exists() || !parent.isDirectory())
            parent.mkdirs();

        File file = new File(parent, "mybatis-config.xml");
        return file;
    }

    public String getBaseDir() {
        return baseDir;
    }


    public String getQueryConditionClassName(Entity entity) {
        return getClassName(queryObjectPackageName, entity, "", "Query");
    }

    public String getValueObjectClassName(Entity entity) {
        return getClassName(valueObjectPackageName, entity, "Extended", "");
    }

    public String getServiceInterfaceClassName(Entity entity) {
        return getClassName(servicePackageName, entity, "", "Service");
    }

    public String getServiceImplementClassName(Entity entity) {
        return getClassName(serviceImplementPackageName, entity, "Basic", "Service");
    }

    public String getThreeJoinServiceImplementClassName(Join leftJoin, Entity middle, Join rightJoin) {
        return getThreeJoinClassName(servicePackageName, middle, leftJoin, rightJoin, "Basic", "Service");
    }

    public String getThreeJoinServiceInterfaceClassName(Join leftJoin, Entity middle, Join rightJoin) {
        return getThreeJoinClassName(servicePackageName, middle, leftJoin, rightJoin, "", "Service");
    }

    public String getServiceTotalImplementClassName(Entity entity) {
        return getClassName(serviceImplementPackageName, entity, "BasicTotal", "Service");
    }

    public String getMapperInterfaceClassName(Entity entity) {
        return getClassName(mapperPackageName, entity, "", "Mapper");
    }

    public String getEntityClassName(Entity entity) {
        return getClassName(entityPackageName, entity);
    }

    public String getMapperXMLFileName(Entity entity) {
        String mapperInterfaceClassName = getMapperInterfaceClassName(entity).replace(".", "/");
        String filename = mapperInterfaceClassName.substring(mapperInterfaceClassName.lastIndexOf("/") + 1) + ".xml";

        File parent = new File(mapperInterfaceClassName.substring(0, mapperInterfaceClassName.lastIndexOf("/")));
        parent = new File(baseDir, parent.getPath());
        if (!parent.exists() || !parent.isDirectory())
            parent.mkdirs();

        return new File(parent, filename).getPath();
    }

    private String getClassName(String packageName, Entity entity, String classNamePrefix, String classNameSuffix) {
        return name + "." + entity.getModule().getName() + "." + packageName + "." + classNamePrefix + entity.getName() + classNameSuffix;
    }

    private String getClassName(String packageName, String moduleName, String entityName, String classNamePrefix, String classNameSuffix) {
        String className = name + "." + moduleName;
        if (!StringUtils.isBlank(packageName))
            className = className + "." + packageName;
        return className + "." + classNamePrefix + entityName + classNameSuffix;
    }

    private String getClassName(String packageName, Entity entity) {
        return getClassName(packageName, entity, "", "");
    }

    public String getEntityPackageName() {
        return entityPackageName;
    }

    public String getMapperPackageName() {
        return mapperPackageName;
    }

    public String getServicePackageName() {
        return servicePackageName;
    }

    public String getQueryObjectPackageName() {
        return queryObjectPackageName;
    }

    public String getValueObjectPackageName() {
        return valueObjectPackageName;
    }

    public String getServiceImplementClassName(String moduleName, String entityName) {
        return getClassName(serviceImplementPackageName, moduleName, entityName, "Basic", "Service");
    }

    public String getServiceImplementClassName(String moduleName, String entityName, String suffix) {
        return getClassName(serviceImplementPackageName, moduleName, entityName, "Basic", suffix);
    }

    public String getServiceInterfaceClassName(String moduleName, String entityName) {
        return getClassName(servicePackageName, moduleName, entityName, "", "Service");
    }

    public String getServiceInterfaceClassName(String moduleName, String entityName, String suffix) {
        return getClassName(servicePackageName, moduleName, entityName, "", suffix);
    }

    public String getServiceImplementPackageName() {
        return serviceImplementPackageName;
    }

    public String getBasicEntityClassName() {
        return basicEntityClassName;
    }

    public String getQueryConditionClassName() {
        return queryConditionClassName;
    }

    public String getOptimismLockingExceptionClassName() {
        return optimismLockingExceptionClassName;
    }

    public String getPaginationClassName() {
        return paginationClassName;
    }

    public String getName() {
        return name;
    }

    public String getModClassName() {
        return modClassName;
    }

    public String getTotalMapperInterfaceClassName(Entity entity) {
        return getClassName(mapperPackageName, entity, "Total", "Mapper");
    }

    public String getTotalVOClassName(Entity entity) {
        return getClassName(valueObjectPackageName, entity, "Total", "");
    }

    public String getTotalQueryConditionClassName(Entity entity) {
        return getClassName(queryObjectPackageName, entity, "Total", "Query");
    }

    public String getTotalMapperXMLFileName(Entity entity) {
        String mapperInterfaceClassName = getTotalMapperInterfaceClassName(entity).replace(".", "/");
        String filename = mapperInterfaceClassName.substring(mapperInterfaceClassName.lastIndexOf("/") + 1) + ".xml";

        File parent = new File(mapperInterfaceClassName.substring(0, mapperInterfaceClassName.lastIndexOf("/")));
        parent = new File(baseDir, parent.getPath());
        if (!parent.exists() || !parent.isDirectory())
            parent.mkdirs();

        return new File(parent, filename).getPath();
    }

    public String getTotalValueObjectClassName(Entity entity) {
        return getClassName(valueObjectPackageName, entity, "Total", "");
    }

    public String getTotalServiceInterfaceClassName(Entity entity) {
        return getClassName(servicePackageName, entity, "Total", "Service");
    }

    public String getTotalServiceImplementClassName(Entity entity) {
        return getClassName(serviceImplementPackageName, entity, "BasicTotal", "Service");
    }

    public String getModuleServiceInterfaceClassName(String moduleName) {
        return getServiceInterfaceClassName(moduleName, StringUtils.capitalize(moduleName) + "Module");
    }

    public String getThreeJoinVoClassName(Entity middle, Join leftJoin,
                                          Join rightJoin) {
        return getThreeJoinClassName(valueObjectPackageName, middle, leftJoin, rightJoin, "", "");
    }

    public String getThreeJoinMapperClassName(Entity middle, Join leftJoin, Join rightJoin) {
        return getThreeJoinClassName(mapperPackageName, middle, leftJoin, rightJoin, "", "Mapper");
    }

    public String getJoinMapperClassName(Join join) {
        return getClassName(mapperPackageName, join.getEntity().getModule().getName(), join.getEntity().getName(), "", "Mapper");
    }

    public String getThreeJoinQoClassName(Entity middle, Join leftJoin,
                                          Join rightJoin) {
        return getThreeJoinClassName(queryObjectPackageName, middle, leftJoin, rightJoin, "", "Query");
    }

    private String getThreeJoinClassName(String packageName, Entity middle, Join leftJoin, Join rightJoin, String classNamePrefix, String classNameSuffix) {
        String entityName = leftJoin.getEntity().getName() + middle.getName() + rightJoin.getEntity().getName();
        return getClassName(packageName, middle.getModule().getName(), entityName, classNamePrefix, classNameSuffix);
    }

    public String getThreeJoinMapperXMLFileName(Entity middle, Join leftJoin, Join rightJoin) {
        String mapperInterfaceClassName = getThreeJoinMapperClassName(middle, leftJoin, rightJoin).replace(".", "/");
        String filename = mapperInterfaceClassName.substring(mapperInterfaceClassName.lastIndexOf("/") + 1) + ".xml";

        File parent = new File(mapperInterfaceClassName.substring(0, mapperInterfaceClassName.lastIndexOf("/")));
        parent = new File(baseDir, parent.getPath());
        if (!parent.exists() || !parent.isDirectory())
            parent.mkdirs();

        return new File(parent, filename).getPath();
    }

    public String getEntityConstClassName(String moduleName) {
        return getClassName(null, moduleName, StringUtils.capitalize(moduleName) + "EntityConst", "", "");
    }

    public String getJoinTypeClassName() {
        return joinTypeClassName;
    }

    public String getGroupBysClassName() {
        return groupBysClassName;
    }

    public String getDateGroupByClassName() {
        return dateGroupByClassName;
    }

    public void setQueryObjectPackageName(String queryObjectPackageName) {
        this.queryObjectPackageName = queryObjectPackageName;
    }

    public void setOptimismLockingExceptionClassName(String optimismLockingExceptionClassName) {
        this.optimismLockingExceptionClassName = optimismLockingExceptionClassName;
    }
}

