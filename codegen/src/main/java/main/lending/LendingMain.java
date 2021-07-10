package main.lending;

import com.sun.codemodel.JCodeModel;
import generator.Generator;
import generator.JavaEntityGenerator;
import generator.JavaServiceGenerator;
import generator.mybatis.MyBatisMapperXMLGenerator;
import generator.mybatis.MyBatisMapprGenerator;
import generator.sql.MySQLTableGenerator;
import main.Entities;
import mate.Entity;
import mate.Module;
import mate.Project;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.PrintStream;

public class LendingMain {
    private static JCodeModel cm = new JCodeModel();

    private static Generator[] generators = new Generator[]{
            new JavaEntityGenerator(cm, true),
            new MyBatisMapprGenerator(cm, true),
            new MyBatisMapperXMLGenerator(),
            new JavaServiceGenerator(cm, true),
            new MySQLTableGenerator(false),
    };

    public static void main(String[] args) throws Exception {

        Project.getInstance().setXiaoMeName("com.youruan.lending.core");
        Project.getInstance().setQueryObjectPackageName("query");

        Entities[] entities = { new UserEntities(), new PlatformEntities(), new BorrowEntities(), new InvestmentEntities(), new MessageEntities() };
        for (Entities entitiesItem : entities) {
            Module module = new Module(entitiesItem.getModuleName());
            for (Entity entity : entitiesItem) {
                entity.setModule(module);
            }
        }

        File baseDir = new File(Project.getInstance().getBaseDir());
        if (baseDir.exists() && baseDir.isDirectory()) {
            System.out.println("delete directory:" + baseDir.getAbsolutePath());
            FileUtils.deleteDirectory(baseDir);
        }
        if (!baseDir.mkdir()) {
            throw new RuntimeException("文件夹创建失败");
        }

        for (Entities entitiesItem : entities) {
            for (Entity entity : entitiesItem) {
                for (Generator gen : generators) {
                    System.out.println("Generator：-> " + entity.getName() + " - " + gen.getName() + "...");
                    gen.generate(entity);
                }

                System.out.println("Generator：-> 完成 !");
            }
        }

        cm.build(baseDir, (PrintStream) null);
    }

}
