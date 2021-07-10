package generator.mybatis;

import com.sun.codemodel.*;
import generator.AbstractGenerator;

import java.util.List;

import mate.Entity;
import mate.Project;

import org.apache.commons.lang3.StringUtils;

public class MyBatisMapprGenerator extends AbstractGenerator{

	private final boolean useAnnotation;
	protected JCodeModel cm;

	public MyBatisMapprGenerator(JCodeModel cm, boolean useAnnotation) {
		this.cm = cm;
		this.useAnnotation = useAnnotation;
	}

	public MyBatisMapprGenerator(JCodeModel cm) {
		this(cm, false);
	}

	@Override
	public void generate(Entity entity) {
		try {
			_generate(entity);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	private void _generate(Entity entity) throws Exception{

		JDefinedClass clazz = cm._class(Project.getInstance().getMapperInterfaceClassName(entity), ClassType.INTERFACE);

		if(useAnnotation) {
			clazz.annotate(cm.ref("org.apache.ibatis.annotations.Mapper"));
			clazz.annotate(cm.ref("org.springframework.stereotype.Repository"));
		}

		JType entityType = cm.parseType(Project.getInstance().getEntityClassName(entity));
		JType qoType = cm.parseType(Project.getInstance().getQueryConditionClassName(entity));
		JType voType = cm.parseType(Project.getInstance().getValueObjectClassName(entity));

		JMethod getMethod = clazz.method(JMod.PUBLIC, entityType, "get");
		getMethod.param(cm.ref(Long.class), "id");

		JMethod updateMethod = clazz.method(JMod.PUBLIC, cm.INT, "update");
		updateMethod.param(entityType, StringUtils.uncapitalize(entity.getName()));

		JClass paramsAnnoate = cm.ref("org.apache.ibatis.annotations.Param");
		if(entity.isBatchUpdate()){
			JMethod batchUpdateMethod = clazz.method(JMod.PUBLIC, cm.INT, "batchUpdate");
			batchUpdateMethod.param(cm.ref(List.class).narrow(entityType), "values")
					.annotate(paramsAnnoate).param("value", "values");
		}

		JMethod addMethod = clazz.method(JMod.PUBLIC, cm.INT, "add");
		addMethod.param(entityType, StringUtils.uncapitalize(entity.getName()));

		if(entity.isBatchInsert()){
			JMethod batchUpdateMethod = clazz.method(JMod.PUBLIC, cm.INT, "batchAdd");
			batchUpdateMethod.param(cm.ref(List.class).narrow(entityType), "values")
					.annotate(paramsAnnoate).param("value", "values");
		}

		JMethod deleteMethod = clazz.method(JMod.PUBLIC, cm.INT, "delete");
		deleteMethod.param(cm.ref(Long.class), "id");

		JMethod countMethod = clazz.method(JMod.PUBLIC, cm.INT, "count");
		countMethod.param(qoType, "qo");

		JMethod queryMethod = clazz.method(JMod.PUBLIC, cm.ref(List.class).narrow(voType), "query");
		queryMethod.param(qoType, "qo");
	}

	@Override
	public String getName() {
		return "Mapper Interface";
	}
}
