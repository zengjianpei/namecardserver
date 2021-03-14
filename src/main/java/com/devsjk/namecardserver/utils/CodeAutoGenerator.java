package com.devsjk.namecardserver.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Xxw on 2016/11/30.
 * Modify by zjp on 20200622
 */
public class CodeAutoGenerator {

    private static final Logger logger = LoggerFactory.getLogger(CodeAutoGenerator.class);

    public static void generate(Class clazz, String tableName, boolean realDelete,String platformPath) throws IOException, ClassNotFoundException {
        generateMybatisFile(clazz, tableName, realDelete);
        generateDao(clazz);
        generateService(clazz,platformPath);
        generateServiceImpl(clazz,platformPath);
        generateController(clazz,platformPath);
    }

    public static void generateMybatisFile(Class clazz, String tableName, boolean realDelete) throws IOException {
        String modelName = clazz.getSimpleName();
        String modelPackageName = clazz.getName();
        String packagePrefix = modelPackageName.substring(0, modelPackageName.indexOf("model"));
        String daoPackageName = packagePrefix + "dao." + modelName + "Dao";
        Boolean hasId = Boolean.FALSE;
        for (int i=0; i<clazz.getDeclaredFields().length; ++i) {
            if (!clazz.getDeclaredFields()[i].getName().equals("id")){
                hasId = Boolean.TRUE;
                break;
            }
        }

        String xmlHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\"\n" +
                "        \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">";

        String mybatisHeader = "\n\n<mapper namespace=\"" + daoPackageName + "\">";

        String insertSql = "\n\n\t<insert id=\"insert" + modelName + "\" parameterType=\"" + modelName + "\"";
        if (hasId) insertSql += " useGeneratedKeys=\"true\" keyProperty=\"id\"";
        insertSql +=">\n\t\tINSERT INTO " + tableName + "(";
        for (int i=0; i<clazz.getDeclaredFields().length; ++i) {
            if (clazz.getDeclaredFields()[i].getName().equals("id")) continue;
            insertSql += clazz.getDeclaredFields()[i].getName() + ",";
        }
        insertSql += "creatorId,creator)\n\t\tVALUES (";
        for (int i=0; i<clazz.getDeclaredFields().length; ++i) {
            if (clazz.getDeclaredFields()[i].getName().equals("id")) continue;
            insertSql += "#{" + clazz.getDeclaredFields()[i].getName() + "},";
        }
        insertSql += "#{creatorId},#{creator})\n\t</insert>";

        String deleteSql = "";
        if (realDelete) {
            deleteSql = "\n\n\t<delete id=\"delete" + modelName + "\" parameterType=\"" + modelName +
                    "\">\n\t\tDELETE FROM " + tableName + " WHERE id=#{id}\n\t</delete>";
        }
        else {
            deleteSql = "\n\n\t<update id=\"delete" + modelName + "\" parameterType=\"" + modelName +
                    "\">\n\t\tUPDATE " + tableName + " SET del=1 WHERE id=#{id}\n\t</update>";
        }

        String selectSql = "\n\n\t<select id=\"find" + modelName + "\" parameterType=\"" + modelName +
                "\" resultType=\"" + modelName + "\">\n\t\tSELECT * FROM " + tableName + " WHERE id=#{id}\n\t</select>";

        String updateSql = "\n\n\t<update id=\"update" + modelName + "\" parameterType=\"" + modelName +
                "\">\n\t\tUPDATE " + tableName + "\n\t\t<trim prefix=\"SET\" prefixOverrides=\",\">";
        for (int i=0; i<clazz.getDeclaredFields().length; ++i) {
            if (!clazz.getDeclaredFields()[i].getName().equals("id")) {
                if (clazz.getDeclaredFields()[i].getType().getTypeName().equals("java.lang.String")) {
                    updateSql += "\n\t\t\t<if test=\"" + clazz.getDeclaredFields()[i].getName() + "!=null and " +
                            clazz.getDeclaredFields()[i].getName() + "!=''\">";
                }
                else {
                    updateSql += "\n\t\t\t<if test=\"" + clazz.getDeclaredFields()[i].getName() + "!=null\">";
                }
                updateSql += "\n\t\t\t\t," + clazz.getDeclaredFields()[i].getName() + "=#{" + clazz.getDeclaredFields()[i].getName() + "}\n\t\t\t</if>";
            }
        }
        updateSql += "\n\t\t\t<if test=\"updatorId!=null and updatorId!=''\">\n\t\t\t\t,updatorId=#{updatorId}\n\t\t\t</if>";
        updateSql += "\n\t\t\t<if test=\"updator!=null and updator!=''\">\n\t\t\t\t,updator=#{updator}\n\t\t\t</if>";
        updateSql += "\n\t\t\t,updateTime=now()\n\t\t</trim>\n\t\tWHERE id=#{id}\n\t</update>";

        String listCondition = "\n\n\t<sql id=\"listCondition\">\n\t\t<trim prefix=\"WHERE\" prefixOverrides=\"AND|OR\">";
        for (int i=0; i<clazz.getDeclaredFields().length; ++i) {
            if (clazz.getDeclaredFields()[i].getType().getTypeName().equals("java.lang.String")) {
                listCondition += "\n\t\t\t<if test=\"" + clazz.getDeclaredFields()[i].getName() + "!=null and " +
                        clazz.getDeclaredFields()[i].getName() + "!=''\">";
                listCondition += "\n\t\t\t\tAND " + clazz.getDeclaredFields()[i].getName() + " LIKE " +
                        "CONCAT('%',#{" + clazz.getDeclaredFields()[i].getName() + "},'%')\n\t\t\t</if>";
            }else if(clazz.getDeclaredFields()[i].getType().getTypeName().equals("java.time.LocalDate")){
                listCondition += "\n\t\t\t<if test=\"" + clazz.getDeclaredFields()[i].getName() + "!=null\">";
                listCondition += "\n\t\t\t\tAND " + clazz.getDeclaredFields()[i].getName() + " between #{" +
                        clazz.getDeclaredFields()[i].getName() + "} and DATE_ADD(#{"+ clazz.getDeclaredFields()[i].getName() +"},INTERVAL 1 DAY)\n\t\t\t</if>";
            }
            else {
                listCondition += "\n\t\t\t<if test=\"" + clazz.getDeclaredFields()[i].getName() + "!=null\">";
                listCondition += "\n\t\t\t\tAND " + clazz.getDeclaredFields()[i].getName() + "=#{" +
                        clazz.getDeclaredFields()[i].getName() + "}\n\t\t\t</if>";
            }

        }
        listCondition += "\n\t\t\tAND del=0\n\t\t</trim>\n\t</sql>";

        String listSql = "\n\n\t<select id=\"list" + modelName + "\" parameterType=\"" + modelName +
                "\" resultType=\"" + modelName + "\">\n\t\t<include refid=\"BaseMapper.pageStart\" />";
        listSql += "\n\t\t\tSELECT * FROM " + tableName + "\n\t\t\t<include refid=\"listCondition\" />" +
                "\n\t\t\t<include refid=\"BaseMapper.sort\" />" +
                "\n\t\t<include refid=\"BaseMapper.pageEnd\" />\n\t</select>";

        String listCountSql = "\n\n\t<select id=\"list" + modelName + "Count\" parameterType=\"" + modelName +
                "\" resultType=\"java.lang.Long\">\n\t\tSELECT COUNT(*) FROM " + tableName +
                "\n\t\t<include refid=\"listCondition\" />\n\t</select>";

        String mapperFooter = "\n</mapper>";

        String str = xmlHeader + mybatisHeader + insertSql + deleteSql + selectSql +updateSql + listCondition +
                listSql + listCountSql + mapperFooter;

        String filePosition = System.getProperty("user.dir") + "\\src\\main\\resources\\";
        String[] packagePositions = packagePrefix.substring(0, packagePrefix.length() - 1).split("\\.");
        /*for (int i=0; i<packagePositions.length; ++i) {
            filePosition += packagePositions[i] + "\\";
        }
        String fileDir = filePosition + "persistence";*/
        String fileDir = filePosition + "mappers\\dao";
        File file = new File(fileDir);
        if (!file.exists()) file.mkdirs();
        filePosition = fileDir + "\\" + modelName + "Dao.xml";

        FileOutputStream fileOutputStream = new FileOutputStream(new File(filePosition));
        fileOutputStream.write(str.getBytes());
        fileOutputStream.close();

        logger.info("generate " + modelName + " mybatis File success");
    }

    public static void generateDao(Class clazz) throws IOException {
        String modelName = clazz.getSimpleName();
        String modelPackageName = clazz.getName();
        String packagePrefix = modelPackageName.substring(0, modelPackageName.indexOf("model"));

        String packageHeader = "package " + packagePrefix + "dao;";

        String importStr = "\n\nimport " + modelPackageName + ";\nimport org.apache.ibatis.annotations.Mapper;\nimport org.springframework.stereotype.Repository;\n\nimport java.util.List;";

        String interfaceStr = "\n\n@Mapper\n@Repository\npublic interface " + modelName + "Dao {";
        interfaceStr += "\n\n\tint insert" + modelName + "(" + modelName + " " + StringUtils.lowerCase(modelName) + ");";
        interfaceStr += "\n\n\tint delete" + modelName + "(" + modelName + " " + StringUtils.lowerCase(modelName) + ");";
        interfaceStr += "\n\n\t" + modelName + " find" + modelName + "(" + modelName + " " + StringUtils.lowerCase(modelName) + ");";
        interfaceStr += "\n\n\tint update" + modelName + "(" + modelName + " " + StringUtils.lowerCase(modelName) + ");";
        interfaceStr += "\n\n\tList<" + modelName + "> list" + modelName + "(" + modelName + " " + StringUtils.lowerCase(modelName) + ");";
        interfaceStr += "\n\n\tLong list" + modelName + "Count(" + modelName + " " + StringUtils.lowerCase(modelName) + ");\n}";

        String str = packageHeader + importStr + interfaceStr;

        String filePosition = System.getProperty("user.dir") + "\\src\\main\\java\\";
        String[] packagePositions = packagePrefix.substring(0, packagePrefix.length() - 1).split("\\.");
        for (int i=0; i<packagePositions.length; ++i) {
            filePosition += packagePositions[i] + "\\";
        }
        String fileDir = filePosition + "dao";
        File file = new File(fileDir);
        if (!file.exists()) file.mkdirs();
        filePosition = fileDir + "\\" + modelName + "Dao.java";

        FileOutputStream fileOutputStream = new FileOutputStream(new File(filePosition));
        fileOutputStream.write(str.getBytes());
        fileOutputStream.close();

        logger.info("generate " + modelName + " dao success");
    }

    public static void generateService(Class clazz,String platformPath) throws IOException {
        String modelName = clazz.getSimpleName();
        String modelPackageName = clazz.getName();
        String packagePrefix="";
        if(StringUtils.isBlank(platformPath)){
            packagePrefix = modelPackageName.substring(0, modelPackageName.indexOf("model"));
        }else{
            packagePrefix=platformPath+".";
        }

        String packageHeader = "package " + packagePrefix + "service;";

        String importStr = "\n\nimport " + modelPackageName + ";\nimport java.util.List;";

        String serviceStr = "\n\npublic interface " + modelName + "Service {";
        serviceStr += "\n\n\tvoid insert" + modelName + "(" + modelName + " " + StringUtils.lowerCase(modelName) + ");";
        serviceStr += "\n\n\tvoid delete" + modelName + "(" + modelName + " " + StringUtils.lowerCase(modelName) + ");";
        serviceStr += "\n\n\t" + modelName + " find" + modelName + "(" + modelName + " " + StringUtils.lowerCase(modelName) + ");";
        serviceStr += "\n\n\tvoid update" + modelName + "(" + modelName + " " + StringUtils.lowerCase(modelName) + ");";
        serviceStr += "\n\n\tList<" + modelName + "> list" + modelName + "(" + modelName + " " + StringUtils.lowerCase(modelName) + ");";
        serviceStr += "\n\n\tLong list" + modelName + "Count(" + modelName + " " + StringUtils.lowerCase(modelName) + ");\n}";

        String str = packageHeader + importStr + serviceStr;

        String filePosition = System.getProperty("user.dir") + "\\src\\main\\java\\";
        String[] packagePositions = packagePrefix.substring(0, packagePrefix.length() - 1).split("\\.");
        for (int i=0; i<packagePositions.length; ++i) {
            filePosition += packagePositions[i] + "\\";
        }
        String fileDir = filePosition + "service";
        File file = new File(fileDir);
        if (!file.exists()) file.mkdirs();
        filePosition = fileDir + "\\" + modelName + "Service.java";

        FileOutputStream fileOutputStream = new FileOutputStream(new File(filePosition));
        fileOutputStream.write(str.getBytes());
        fileOutputStream.close();

        logger.info("generate " + modelName + " service success");
    }

    public static void generateServiceImpl(Class clazz,String platformPath) throws IOException {
        String modelName = clazz.getSimpleName();
        String modelPackageName = clazz.getName();
        //String packagePrefix = modelPackageName.substring(0, modelPackageName.indexOf("model"));
        String packagePrefix="";
        if(StringUtils.isBlank(platformPath)){
            packagePrefix = modelPackageName.substring(0, modelPackageName.indexOf("model"));
        }else{
            packagePrefix=platformPath+".";
        }

        String packageHeader = "package " + packagePrefix + "service.impl;";

        String importStr = "\n\nimport " + modelPackageName.substring(0, modelPackageName.indexOf("model")) + "dao." + modelName + "Dao;" +
                "\nimport " + modelPackageName + ";" +
                "\nimport " + packagePrefix + "service." + modelName + "Service;" +
                "\nimport org.springframework.beans.factory.annotation.Autowired;" +
                "\nimport org.springframework.stereotype.Service;" +
                "\nimport java.util.List;";

        String serviceStr = "\n\n@Service\npublic class " + modelName + "ServiceImpl implements " + modelName + "Service {";
        serviceStr += "\n\n\t@Autowired\n\tprivate " + modelName + "Dao " + StringUtils.lowerCase(modelName) + "Dao;";

        serviceStr += "\n\n\t@Override\n\tpublic void insert" + modelName + "(" + modelName + " " + StringUtils.lowerCase(modelName) + ") {" +
                "\n\t\t" + StringUtils.lowerCase(modelName) + "Dao.insert" + modelName + "(" + StringUtils.lowerCase(modelName) + ");\n\t}";

        serviceStr += "\n\n\t@Override\n\tpublic void delete" + modelName + "(" + modelName + " " + StringUtils.lowerCase(modelName) + ") {" +
                "\n\t\t" + StringUtils.lowerCase(modelName) + "Dao.delete" + modelName + "(" + StringUtils.lowerCase(modelName) + ");\n\t}";

        serviceStr += "\n\n\t@Override\n\tpublic " + modelName + " find" + modelName + "(" + modelName + " " + StringUtils.lowerCase(modelName) + ") {" +
                "\n\t\treturn " + StringUtils.lowerCase(modelName) + "Dao.find" + modelName + "(" + StringUtils.lowerCase(modelName) + ");\n\t}";

        serviceStr += "\n\n\t@Override\n\tpublic void update" + modelName + "(" + modelName + " " + StringUtils.lowerCase(modelName) + ") {" +
                "\n\t\t" + StringUtils.lowerCase(modelName) + "Dao.update" + modelName + "(" + StringUtils.lowerCase(modelName) + ");\n\t}";

        serviceStr += "\n\n\t@Override\n\tpublic List<" + modelName + "> list" + modelName + "(" + modelName + " " + StringUtils.lowerCase(modelName) + ") {" +
                "\n\t\treturn " + StringUtils.lowerCase(modelName) + "Dao.list" + modelName + "(" + StringUtils.lowerCase(modelName) + ");\n\t}";

        serviceStr += "\n\n\t@Override\n\tpublic Long list" + modelName + "Count(" + modelName + " " + StringUtils.lowerCase(modelName) + ") {" +
                "\n\t\treturn " + StringUtils.lowerCase(modelName) + "Dao.list" + modelName + "Count(" + StringUtils.lowerCase(modelName) + ");\n\t}\n}";

        String str = packageHeader + importStr + serviceStr;

        String filePosition = System.getProperty("user.dir") + "\\src\\main\\java\\";
        String[] packagePositions = packagePrefix.substring(0, packagePrefix.length() - 1).split("\\.");
        for (int i=0; i<packagePositions.length; ++i) {
            filePosition += packagePositions[i] + "\\";
        }
        String fileDir = filePosition + "service\\impl";
        File file = new File(fileDir);
        if (!file.exists()) file.mkdirs();
        filePosition = fileDir + "\\" + modelName + "ServiceImpl.java";

        FileOutputStream fileOutputStream = new FileOutputStream(new File(filePosition));
        fileOutputStream.write(str.getBytes());
        fileOutputStream.close();

        logger.info("generate " + modelName + " serviceImpl success");
    }

    public static void generateController(Class clazz,String controllerPath) throws IOException {
        String modelName = clazz.getSimpleName();
        String modelPackageName = clazz.getName();
        String packagePrefix="";
        if(StringUtils.isBlank(controllerPath)){
            packagePrefix = modelPackageName.substring(0, modelPackageName.indexOf("model"));
        }else{
            packagePrefix=controllerPath+".";
        }


        String packageHeader = "package " + packagePrefix + "controller;";

        String importStr = "\n\nimport " + modelPackageName + ";" +
                "\nimport " + packagePrefix + "service." + modelName + "Service;" +
                "\nimport com.leshang.framework3.annotation.AutoReturn;" +
                "\nimport com.leshang.framework3.annotation.LeshangController;" +
                "\nimport org.springframework.beans.factory.annotation.Autowired;" +
                "\nimport com.leshang.framework3.controller.BaseController;" +
                "\nimport org.springframework.web.bind.annotation.PostMapping;";

        String controllerStr = "\n\n@LeshangController\npublic class " + modelName + "Controller extends BaseController {";
        controllerStr += "\n\n\t@Autowired\n\tprivate " + modelName + "Service " + StringUtils.lowerCase(modelName) + "Service;";

        controllerStr += "\n\n\t@PostMapping(value = \"/admin/v1/" + StringUtils.lowerCase(modelName)  + "/insert\")" +
                "\n\t@AutoReturn\n\tpublic void insert" + modelName + "(" + modelName + " " + StringUtils.lowerCase(modelName) + ") {" +
                "\n\t\t" + StringUtils.lowerCase(modelName) + "Service.insert" + modelName + "(" + StringUtils.lowerCase(modelName) + ");" +
                 "\n\t}";

        controllerStr += "\n\n\t@PostMapping(value = \"/admin/v1/" + StringUtils.lowerCase(modelName)  + "/delete\")" +
                "\n\t@AutoReturn\n\tpublic void delete" + modelName + "(" + modelName + " " + StringUtils.lowerCase(modelName) + ") {" +
                "\n\t\t" + StringUtils.lowerCase(modelName) + "Service.delete" + modelName + "(" + StringUtils.lowerCase(modelName) + ");" +
                "\n\t}";

        controllerStr += "\n\n\t@PostMapping(value = \"/admin/v1/" + StringUtils.lowerCase(modelName)  + "/find\")" +
                "\n\t@AutoReturn\n\tpublic void find" + modelName + "(" + modelName + " " + StringUtils.lowerCase(modelName) + ") {" +
                "\n\t\tthis.setResult(" + StringUtils.lowerCase(modelName) + "Service.find" + modelName + "(" + StringUtils.lowerCase(modelName) + "));" +
                "\n\t}";

        controllerStr += "\n\n\t@PostMapping(value = \"/admin/v1/" + StringUtils.lowerCase(modelName)  + "/update\")" +
                "\n\t@AutoReturn\n\tpublic void update" + modelName + "(" + modelName + " " + StringUtils.lowerCase(modelName) + ") {" +
                "\n\t\t" + StringUtils.lowerCase(modelName) + "Service.update" + modelName + "(" + StringUtils.lowerCase(modelName) + ");" +
                "\n\t}";

        controllerStr += "\n\n\t@PostMapping(value = \"/admin/v1/" + StringUtils.lowerCase(modelName)  + "/list\")" +
                "\n\t@AutoReturn\n\tpublic void list" + modelName + "(" + modelName + " " + StringUtils.lowerCase(modelName) + ") {" +
                "\n\t\tthis.setResult(" + StringUtils.lowerCase(modelName) + "Service.list" + modelName + "(" + StringUtils.lowerCase(modelName) + "));" +
                "\n\t\tthis.setTotal(" + StringUtils.lowerCase(modelName) + "Service.list" + modelName + "Count(" + StringUtils.lowerCase(modelName) + "));" +
                "\n\t}\n}";

        String str = packageHeader + importStr + controllerStr;

        String filePosition = System.getProperty("user.dir") + "\\src\\main\\java\\";
        String[] packagePositions = packagePrefix.substring(0, packagePrefix.length() - 1).split("\\.");
        for (int i=0; i<packagePositions.length; ++i) {
            filePosition += packagePositions[i] + "\\";
        }
        String fileDir = filePosition + "controller";
        File file = new File(fileDir);
        if (!file.exists()) file.mkdirs();
        filePosition = fileDir + "\\" + modelName + "Controller.java";

        FileOutputStream fileOutputStream = new FileOutputStream(new File(filePosition));
        fileOutputStream.write(str.getBytes());
        fileOutputStream.close();

        logger.info("generate " + modelName + " controller success");
    }
}
