package com.boot.JDBC;

import com.boot.annotation.TabField;
import com.boot.annotation.TabName;
import com.sun.org.apache.xpath.internal.SourceTree;
import lombok.SneakyThrows;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ClassName : SingleMySql
 * @Description : SingleMySql
 * @Author : ubing
 * @Date: 2022-12-04 17:04
 */
public class SingleMySql {
    //volatile 原子化，防止cpu加载重排序
    private static volatile SingleMySql singleMySql;

    static final String JDBC_DRIVER="com.mysql.cj.jdbc.Driver";

    static String DB_URL = null;
    static String DB_BASE=null;
    static String USER = null;
    static String PASS = null;
    public static Connection conn =null;
    public static Statement stmt = null;
    private SingleMySql(String url,String user,String pass) {
        DB_URL=url;
        USER=user;
        PASS=pass;
        try {
            Class.forName(JDBC_DRIVER);
            System.out.println("加载数据库驱动成功");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(url, user, pass);
            System.out.println("连接数据库驱动成功");
            stmt = conn.createStatement();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /***
    * @Title: getInstance
    * @Param: [url, user, pass]
    * @description:  实例化单例模式sql链接
    * @author: ubing
    * @date:  17:24
    * @return: com.boot.JDBC.SingleMySql
    * @throws:
    */
    public static SingleMySql getInstance(String url,String user,String pass){
        if (singleMySql == null) {
            synchronized (SingleMySql.class) {
                if (singleMySql == null) {
                    singleMySql = new SingleMySql(url,user,pass);
                }
            }
        }
        return singleMySql;
    }
    /***
    * @Title: initCode
    * @Param: [tabName, path]
    * @description: 通过数据库表创建对象CLASS
    * @author: ubing
    * @date:  17:17
    * @return: void
    * @throws:
    */
    public void initCode(String tabName,String path) throws SQLException {

        final String regex = "(.*\\d/)|(\\?.+)";
        final String string = DB_URL;
        final String subst = "";
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(string);
        String daBase = matcher.replaceAll(subst);
        DB_BASE = daBase;
        String  sql="SELECT COLUMN_NAME FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = '"+DB_BASE+"' AND TABLE_NAME = '"+tabName+"'";
        ResultSet rs = stmt.executeQuery(sql);
        //填充数据
        try {
            String tab = lineToHump(tabName);
            String code = singleMySql.readTmpl(rs);
            String[] split = path.split("/");
            String pakage = "";
            boolean flag = false;
            for (String s : split) {
                if(flag){
                    pakage+=s+".";
                }
                if(s.equals("java")){
                    flag =true;
                }
            }
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
            String now = df.format(System.currentTimeMillis());
            String codeProduce = code.replaceAll("#TabName", tab).replaceAll("#path", pakage).replaceAll("\\.;#end",";").replaceAll("#date",now);
            //向固定path输出java文件
            FileWriter writer;
            writer = new FileWriter(path+"/"+tab+".java"); // 如果已存在，以覆盖的方式写文件
            // writer = new FileWriter("testFileWriter.txt", true); // 如果已存在，以追加的方式写文件
            writer.write(""); //清空原文件内容
            writer.write(codeProduce);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 完成后关闭
        rs.close();
    }
    /***
    * @Title: createTab
    * @Param: [obj]
    * @description: 通过对象CLASS创建数据库表
    * @author: ubing
    * @date:  17:18
    * @return: void
    * @throws:
    */
    public void createTab(Object obj) throws SQLException {
        //填充数据
        String TAB_NAME=null;
        TabName tabName = obj.getClass().getAnnotation(TabName.class);
        if(tabName!=null){
            TAB_NAME=tabName.value();
        }else {
            TAB_NAME = humpToLine(obj.getClass().getSimpleName());
        }
        Field[] fields = obj.getClass().getDeclaredFields();
        String createBody="";
        for (Field field : fields) {
            field.setAccessible(true);
            TabField tabField = field.getAnnotation(TabField.class);
            if(tabField!=null){
                createBody += tabField.value()+" varchar(255),\n";
            }else if(field.getName()!="serialVersionUID"){
                createBody += humpToLine(field.getName())+" varchar(255),\n";
            }
        }
        String createHeader = "create table "+TAB_NAME+"(\n";
        String createRear="REAR";
        String sql = (createHeader+createBody+createRear).replaceAll(",\nREAR","\n)");
        boolean execute = stmt.execute(sql);
        if(!execute){
            System.out.println("table创建成功");
        }
    }

    //读取模板信息
    public static  String readTmpl(ResultSet rs) throws IOException, SQLException {
//        Path path = Paths.get("src/main/java/com/boot/entity/tmpl.txt");
//        Stream<String> lines = Files.lines(path);
        //lineSeparator()是Java中的内置方法,该方法返回system-dependent行分隔符字符串。它总是返回相同的值–系统属性line.separator的初始值。
//        String tmpl = Files.lines(Paths.get("src/main/java/com/boot/entity/tmpl.txt")).collect(Collectors.joining(System.lineSeparator()));
//        Path path_1 = Paths.get("src/main/java/com/boot/entity/TabField.txt");
//        Stream<String> lines_1 = Files.lines(path_1);
        //lineSeparator()是Java中的内置方法,该方法返回system-dependent行分隔符字符串。它总是返回相同的值–系统属性line.separator的初始值。
        String TabField = Files.lines(Paths.get("src/main/java/com/boot/entity/TabField.txt")).collect(Collectors.joining(System.lineSeparator()));
        String GetAndSet = Files.lines(Paths.get("src/main/java/com/boot/entity/GetAndSet.txt")).collect(Collectors.joining(System.lineSeparator()));
        String code = new String();
        String getterAndSetter = new String();
        String string;
        String camelCase;
        String getMethodName;
        while (rs.next()) {
            string = rs.getString(1);
            camelCase = lineToHump(string);
            getMethodName=getMethodName(camelCase);
            code += "\n\n"+TabField.replaceAll("#TabField", string).replaceAll("#field", camelCase);
            getterAndSetter+="\n"+GetAndSet.replaceAll("#field", camelCase).replaceAll("#Up_field",getMethodName);
        }
        return Files.lines(Paths.get("src/main/java/com/boot/entity/tmpl.txt")).collect(Collectors.joining(System.lineSeparator())).replaceAll("#value", code).replaceAll("#getAndset", getterAndSetter);
    }

    //驼峰式转下划线命名
    public static String humpToLine(String str) {
        Pattern humpPattern = Pattern.compile("[A-Z]");
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
    //下划线转驼峰式命名
    public static String lineToHump(String str) {
        str = str.toLowerCase();
        Pattern linePattern = Pattern.compile("_(\\w)");
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
    //首字母大写
    public static String getMethodName(String str) {
        char[] cs=str.toCharArray();
        cs[0]-=32;
        return String.valueOf(cs);
    }

}
