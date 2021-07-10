package generator;

import com.sun.codemodel.*;
import mate.Entity;
import mate.Project;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JavaServiceGenerator extends AbstractGenerator {

    protected JCodeModel cm;
    protected boolean useAnnotation;

    JClass serviceAnnotation;
    JClass autowireAnnotation;

    public JavaServiceGenerator(JCodeModel cm) {
        this(cm, false);
    }

    public JavaServiceGenerator(JCodeModel cm, boolean useAnnoation) {
        this.cm = cm;
        this.useAnnotation = useAnnoation;
        serviceAnnotation = cm.ref("org.springframework.stereotype.Service");
        autowireAnnotation = cm.ref("org.springframework.beans.factory.annotation.Autowired");
    }

    @Override
    public void generate(Entity entity) {
        try {
            _generate(entity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void _generate(Entity entity) throws Exception {

        JType entityType = cm.parseType(Project.getInstance().getEntityClassName(entity));
        JType qoType = cm.parseType(Project.getInstance().getQueryConditionClassName(entity));
        JType voType = cm.parseType(Project.getInstance().getValueObjectClassName(entity));
        JType mapperType = cm.parseType(Project.getInstance().getMapperInterfaceClassName(entity));
        JClass pageListType = cm.ref(Project.getInstance().getPaginationClassName()).narrow(voType);

        String getMethodName = "get";
        String addMethodName = "add";
        String updateMethodName = "update";
        String listMethodName = "list";
        String queryMethodName = "query";
        String countMethodName = "count";

        String mapperTypeVarName = StringUtils.uncapitalize(mapperType.name());
        String entityTypeVarName = StringUtils.uncapitalize(entityType.name());

        JFieldRef mapperFieldRef = JExpr.ref(mapperTypeVarName);
        JFieldRef entityFieldRef = JExpr.ref(entityTypeVarName);
        JFieldRef mapperFieldThisRef = JExpr.refthis(mapperTypeVarName);

        JDefinedClass serviceClazz = cm._class(Project.getInstance().getServiceInterfaceClassName(entity), ClassType.INTERFACE);
        serviceClazz.method(JMod.PUBLIC, entityType, getMethodName).param(cm.ref(Long.class), "id");
        serviceClazz.method(JMod.PUBLIC, voType, "queryOne").param(qoType, "qo");
        serviceClazz.method(JMod.PUBLIC, cm.ref(List.class).narrow(voType), listMethodName).param(qoType, "qo");
        serviceClazz.method(JMod.PUBLIC, pageListType, queryMethodName).param(qoType, "qo");
        serviceClazz.method(JMod.PUBLIC, cm.INT, countMethodName).param(qoType, "qo");

        JDefinedClass serviceImplClazz = cm._class(Project.getInstance().getServiceImplementClassName(entity));
        serviceImplClazz._implements(serviceClazz);
        if (this.useAnnotation) {
            serviceImplClazz.annotate(serviceAnnotation).param("value", StringUtils.uncapitalize(serviceClazz.name()));
        }
        serviceImplClazz.field(JMod.PRIVATE | JMod.FINAL, mapperType, mapperTypeVarName);
        JMethod constructor = serviceImplClazz.constructor(JMod.PUBLIC);
        constructor.param(mapperType, mapperTypeVarName);
        constructor.body().assign(mapperFieldThisRef, mapperFieldRef);

        JMethod getMethod = serviceImplClazz.method(JMod.PUBLIC, entityType, getMethodName);
        getMethod.annotate(Override.class);
        getMethod.param(cm.ref(Long.class), "id");
        getMethod.body()._return(JExpr.invoke(mapperFieldRef, "get").arg(JExpr.ref("id")));

        JMethod updateMethod = serviceImplClazz.method(JMod.PROTECTED, cm.VOID, updateMethodName);
        updateMethod.param(entityType, entityTypeVarName);
        JBlock updateMethodBody = updateMethod.body();
        updateMethodBody.decl(cm.INT, "affected", JExpr.invoke(mapperFieldRef, "update").arg(entityFieldRef));
        updateMethodBody._if(JExpr.ref("affected").eq(JExpr.lit(0)))
                ._then()
                ._throw(JExpr._new(cm.parseType(Project.getInstance().getOptimismLockingExceptionClassName()))
                        .arg(JExpr.lit("version!!")));
        updateMethodBody.invoke(entityFieldRef, "setVersion").arg(entityFieldRef.invoke("getVersion").plus(JExpr.lit(1)));

        JMethod addMethod = serviceImplClazz.method(JMod.PROTECTED, entityType, addMethodName);
        addMethod.param(entityType, entityTypeVarName);
        JBlock addMethodBody = addMethod.body();
        addMethodBody.add(JExpr.ref(entityTypeVarName).invoke("setCreatedDate").arg(JExpr._new(cm.ref(Date.class))));
        addMethodBody.add(JExpr.invoke(mapperFieldRef, "add").arg(entityFieldRef));
        addMethodBody._return(entityFieldRef);

        JMethod listMethod = serviceImplClazz.method(JMod.PUBLIC, cm.ref(List.class).narrow(voType), listMethodName);
        listMethod.annotate(Override.class);
        listMethod.param(qoType, "qo");
        JBlock listMethodBody = listMethod.body();
        listMethodBody._return(JExpr.invoke(mapperFieldRef, "query").arg(JExpr.ref("qo")));

        JMethod queryOneMethod = serviceImplClazz.method(JMod.PUBLIC, voType, "queryOne");
        queryOneMethod.annotate(Override.class);
        queryOneMethod.param(qoType, "qo");
        JBlock queryOneMethodBody = queryOneMethod.body();
        queryOneMethodBody.invoke(JExpr.ref("qo"), "setPageSize").arg(JExpr.lit(1));
        queryOneMethodBody.decl(cm.ref(List.class).narrow(voType), "data", JExpr.invoke(mapperFieldRef, "query").arg(JExpr.ref("qo")));
        queryOneMethodBody._return(JOp.cond(JExpr.ref("data").eq(JExpr._null()).cor(JExpr.invoke(JExpr.ref("data"), "isEmpty")), JExpr._null(), JExpr.ref("data").invoke("get").arg(JExpr.lit(0))));

        JMethod queryMethod = serviceImplClazz.method(JMod.PUBLIC, pageListType, queryMethodName);
        queryMethod.annotate(Override.class);
        queryMethod.param(qoType, "qo");
        JBlock queryMethodBody = queryMethod.body();
        queryMethodBody.decl(cm.INT, "rows", JExpr.invoke(mapperFieldRef, "count").arg(JExpr.ref("qo")));
        queryMethodBody.decl(cm.ref(List.class).narrow(voType), "data", JOp.cond(JExpr.ref("rows").eq(JExpr.lit(0)),
                JExpr._new(cm.ref(ArrayList.class).narrow(voType)), JExpr.invoke(mapperFieldRef, "query").arg(JExpr.ref("qo"))));
        queryMethodBody._return(JExpr._new(pageListType)
                .arg(JExpr.ref("rows"))
                .arg(JExpr.ref("data")));

        JMethod countMethod = serviceImplClazz.method(JMod.PUBLIC, cm.INT, countMethodName);
        countMethod.annotate(Override.class);
        countMethod.param(qoType, "qo");
        countMethod.body()._return(JExpr.invoke(mapperFieldRef, "count").arg(JExpr.ref("qo")));
    }

    @Override
    public String getName() {
        return "Service Interface And Service Implements";
    }
}
