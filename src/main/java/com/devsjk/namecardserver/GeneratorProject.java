package com.devsjk.namecardserver;
import com.devsjk.namecardserver.model.Company;
import com.devsjk.namecardserver.model.Industry;
import com.devsjk.namecardserver.model.WxUser;
import com.devsjk.namecardserver.utils.CodeAutoGenerator;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: zjp
 * @Date: 2020/6/22 16:17
 * @Description:
 */
public class GeneratorProject {

    public static void main(String[] args) throws Exception{
        //generator();
        CodeAutoGenerator.generate(WxUser.class,"WxUser",false,"com.devsjk.namecardserver.admin");
    }


    private static void generator() throws Exception{
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        //指定逆向工程配置文件
        File configFile = new File("./src/main/resources/mybatis/generator/generatorConfig.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }
}
