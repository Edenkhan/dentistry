package generator.export;

import generator.ModuleGenerator;

import java.util.HashMap;
import java.util.Map;

import main.Entities;
import mate.Const;
import mate.ConstGroup;
import mate.Entity;
import mate.Project;

import org.apache.commons.lang3.StringUtils;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JPrimitiveType;


public class ExportEntityConstGenerator implements ModuleGenerator {

	private JCodeModel cm;

	private int constMods = JMod.PUBLIC + JMod.FINAL + JMod.STATIC;
	private String entityConstClassName = "com.lanyotech.p2p.core.base.EntityConst";
	private String entityConstBrandClassName = "com.lanyotech.p2p.core.base.EntityConstBrand";
	
	private JClass entityConstClass; 
	private JClass entityConstBrandClass; 
	
	private static final Map<String, String> brandMap;
	
	static{
		brandMap = new HashMap<String, String>();
		brandMap.put("muted", "MUTED");
		brandMap.put("success", "SUCCESS");
		brandMap.put("primary", "PRIMARY");
		brandMap.put("danger", "DANGER");
		brandMap.put("warning", "WARNING");
		brandMap.put("info", "INFO");
	}
	
	public ExportEntityConstGenerator(JCodeModel cm) {
		this.cm = cm;
		entityConstClass = cm.ref(entityConstClassName);
		entityConstBrandClass = cm.ref(entityConstBrandClassName);
	}
	
	@Override
	public String getName() {
		return "Export Entity Const";
	}

	@Override
	public void generate(Entities entities) {
		JDefinedClass constClass;
		try {
			constClass = cm._class(Project.getInstance().getEntityConstClassName(entities.getModuleName()));
		} catch (JClassAlreadyExistsException e) {
			throw new RuntimeException(e);
		}
		
		for (Entity entity : entities) {
			genEntityConst(constClass, entity);
		}
	}

	private void genEntityConst(JDefinedClass constClass, Entity entity) {
		
		ConstGroup[] constGroups = entity.getConstGroups();
		if(constGroups.length == 0)
			return;
		
		JDefinedClass clazz;
		try {
			clazz = constClass._class(constMods, entity.getName());
		} catch (JClassAlreadyExistsException e) {
			throw new RuntimeException(e);
		}
		
		for (ConstGroup cg : constGroups) {
			JPrimitiveType keyType = cm.INT;
			if(cg.getMethod() == ConstGroup.METHOD_MOD)
				keyType = cm.LONG;
			
			String cgName = StringUtils.upperCase(cg.getName().replaceAll("([A-Z])", "_$1"));
			clazz.field(constMods, cm.ref(Map.class).narrow(keyType).narrow(entityConstClass), cgName).init(
					JExpr._new(cm.ref(HashMap.class).narrow(keyType).narrow(entityConstClass)));
			JBlock initBlock = clazz.init();
			
			Const[] consts = cg.getConsts();
			for (Const cont : consts) {
				String brandName = brandMap.get(cont.getStyle());
				if(brandName == null) 
					brandName = Const.DEFAULT_STYLE.toUpperCase();
				initBlock.invoke(JExpr.ref(cgName), "put").arg(cm.ref(Project.getInstance().getEntityClassName(entity)).staticRef(StringUtils.upperCase(cg.getName().replaceAll("([A-Z])", "_$1")) + "_" + cont.getName()))
														  .arg(JExpr._new(entityConstClass).arg(cont.getDescription()).arg(entityConstBrandClass.staticRef(brandName)));
			}
		}
		
	}
}
