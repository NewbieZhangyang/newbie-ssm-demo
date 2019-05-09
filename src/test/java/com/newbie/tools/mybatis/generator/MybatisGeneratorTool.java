package com.newbie.tools.mybatis.generator;

import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MybatisGeneratorTool {

    /**
     *  该方法用于自动生成MyBatis下的pojo、dao、mapper.xml使用的，在项目的业务功能中并不使用
     *  注意：realPath的值，可使用相对路径设置（目前作者使用相对路径没有成功，暂时使用的是绝对路径）
     *
     * @throws IOException
     * @throws XMLParserException
     * @throws InvalidConfigurationException
     * @throws SQLException
     * @throws InterruptedException
     */
    @Test
    public void test() throws IOException, XMLParserException, InvalidConfigurationException, SQLException, InterruptedException {
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
       // System.out.println(this.getClass().getResource("/usr"));
       // File configFile =new File(this.getClass().getResource("/mybatis-generator-tools/mybatis-generator.xml").getFile());
        String realPath = "/Users/zhangyangyang/IdeaProjects/newbie-ssm-demo/src/main/Resource/mybatis-generator-tools/generator.xml";
        File configFile = new File(realPath);
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }
}
