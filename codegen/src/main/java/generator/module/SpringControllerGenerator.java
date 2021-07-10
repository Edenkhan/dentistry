package generator.module;

import com.sun.codemodel.*;
import generator.ModuleGenerator;
import generator.total.JCodeModelUtils;
import main.Entities;
import mate.ConstGroup;
import mate.Entity;
import mate.Project;
import mate.Property;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

public class SpringControllerGenerator implements ModuleGenerator {

    private JCodeModel cm;

    private String basePackageName = "com.yirejie.ems";

    private JClass controllerClass;
    private JClass autowiredClass;
    private JClass resourceGroupClass;
    private JClass resourceClass;
    private JClass responseBodyClass;
    private JClass requestMappingClass;
    private JClass httpServletRequestClass;
    private JClass dataBodyClass;
    private JClass paginationClass;

    public SpringControllerGenerator(JCodeModel cm) {
        this.cm = cm;
        controllerClass = cm.ref("org.springframework.stereotype.Controller");
        autowiredClass = cm.ref("org.springframework.beans.factory.annotation.Autowired");
        resourceGroupClass = cm.ref("com.yirejie.ems.base.annotation.ResourceGroup");
        resourceClass = cm.ref("com.yirejie.ems.base.annotation.Resource");
        responseBodyClass = cm.ref("org.springframework.web.bind.annotation.ResponseBody");
        requestMappingClass = cm.ref("org.springframework.web.bind.annotation.RequestMapping");
        httpServletRequestClass = cm.ref("javax.servlet.http.HttpServletRequest");
        dataBodyClass = cm.ref("com.yirejie.ems.base.DataBody");
        paginationClass = cm.ref("com.yirejie.ems.base.query.Pagination");
    }

    @Override
    public void generate(Entities entities) {
        try {
            _generate(entities);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void _generate(Entities entities) throws Exception {
        Project PROJECT = Project.getInstance();
        String moduleName = entities.getModuleName();

        for (Entity entity : entities) {

            String controllerName = StringUtils.capitalize(entity.getName()) + "Controller";
            String actionClassName = String.format("%s.%s.%s.%s", basePackageName, moduleName, "controller", controllerName);
            String serviceInterfaceClassName = PROJECT.getServiceInterfaceClassName(entity);
            JType serviceType = cm.ref(serviceInterfaceClassName);

            JDefinedClass clazz = cm._class(actionClassName);
            clazz.annotate(controllerClass).param("value", StringUtils.uncapitalize(controllerName));
            clazz.annotate(requestMappingClass).param("value", "/" + StringUtils.uncapitalize(entity.getName()));
            clazz.annotate(resourceGroupClass).param("value", moduleName);

//            JDocComment javadoc = clazz.javadoc();
//            String entityAlias = entity.getAlias().replaceAll("_", "");
//            javadoc.add(String.format("\n/%s.htm?cmd=%s\t\t\t\t获取%s数据", moduleName, entityAlias + "s", entity.getDescription()));
//            javadoc.add(String.format("\n/%s.htm?cmd=%sPage\t\t\t访问%s页面", moduleName, entityAlias + "s", entity.getDescription()));

            JCodeModelUtils.generateReturnField(clazz, serviceType, StringUtils.uncapitalize(serviceType.name())).annotate(autowiredClass);
//            JCodeModelUtils.generateSetterWithoutReturn(cm, clazz, serviceType, StringUtils.uncapitalize(serviceType.name()));

            generateDoDataMethod(clazz, entity, moduleName);
        }
    }

    private void generateDoDataMethod(JDefinedClass clazz, Entity entity, String moduleName) {

        String methodName = getListName(entity.getAlias() + "s");
        JMethod method = clazz.method(JMod.PUBLIC, dataBodyClass, methodName);
        method.param(httpServletRequestClass, "request");
        method.param(cm.ref(Project.getInstance().getQueryConditionClassName(entity)), "qo");

        method.annotate(resourceClass).param("value", "获取" + entity.getDescription() + "数据");
        method.annotate(responseBodyClass);
        method.annotate(requestMappingClass);

        JBlock body = method.body();

        String serviceInterfaceClassName = Project.getInstance().getServiceInterfaceClassName(entity);
        JClass serviceInterfaceClass = cm.ref(serviceInterfaceClassName);

        body.decl(paginationClass.narrow(cm.ref(Project.getInstance().getValueObjectClassName(entity))), "pagination")
                .init(JExpr.ref(StringUtils.uncapitalize(serviceInterfaceClass.name())).invoke("query" + entity.getName() + "s").arg(JExpr.ref("qo")));
        body._return(dataBodyClass.staticInvoke("create").arg(JExpr.ref("pagination")));
    }


    private String getListName(String name) {
        return "list" + StringUtils.capitalize(name.replaceAll("_", ""));
    }

    @Override
    public String getName() {
        return "Spring Controller";
    }
}
