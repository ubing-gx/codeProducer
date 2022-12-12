//package com.boot.JDBC;
//
//import com.boot.annotation.TabField;
//import com.boot.annotation.TabName;
//import com.boot.entity.user;
//
//import java.io.*;
//import java.lang.reflect.Field;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.sql.*;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
///**
// * @ClassName : MySql
// * @Description : MySql
// * @Author : ubing
// * @Date: 2022-12-02 09:59
// */
//public class MySql {
//    static final String JDBC_DRIVER="com.mysql.cj.jdbc.Driver";
//    static Connection conn =null;
//    static Statement stmt = null;
//    static final String DB_URL = "jdbc:mysql://localhost:3306/gorm?charset=utf8mb4&parseTime=True&loc=Local&serverTimezone=UTC";
//    static String DB_BASE=null;
//    static final String USER = "root";
//    static final String PASS = "123456";
//
//    public MySql() {
//        try {
//            Class.forName(JDBC_DRIVER);
//            System.out.println("加载数据库驱动成功");
//        } catch (ClassNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        try {
//            conn = DriverManager.getConnection(DB_URL, USER, PASS);
//            System.out.println("连接数据库驱动成功");
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
//
//    public void query() {
//        try{
//            stmt = conn.createStatement();
//            String sql;
//            sql = "SELECT id, name, url FROM websites";
//            ResultSet rs = stmt.executeQuery(sql);
//
//            // 展开结果集数据库
//            while(rs.next()){
//                // 通过字段检索
//                int id  = rs.getInt("id");
//                String name = rs.getString("name");
//                String url = rs.getString("url");
//
//                // 输出数据
//                System.out.print("ID: " + id);
//                System.out.print(", 站点名称: " + name);
//                System.out.print(", 站点 URL: " + url);
//                System.out.print("\n");
//            }
//            // 完成后关闭
//            rs.close();
//            stmt.close();
//            conn.close();
//        }catch(SQLException se){
//            // 处理 JDBC 错误
//            se.printStackTrace();
//        }catch(Exception e){
//            // 处理 Class.forName 错误
//            e.printStackTrace();
//        }finally{
//            // 关闭资源
//            try{
//                if(stmt!=null) stmt.close();
//            }catch(SQLException se2){
//            }// 什么都不做
//            try{
//                if(conn!=null) conn.close();
//            }catch(SQLException se){
//                se.printStackTrace();
//            }
//        }
//        System.out.println("");
//    }
//    public void insert() {
//
//        String sql1 = "insert into websites(id,name,url,alexa,country) values(6,'java','www.baidu',6,'hmoe')";
//        PreparedStatement ps = null;
//
//        try {
//            ps = conn.prepareStatement(sql1);
//            ps.executeUpdate();
//            System.out.println("插入成功！");
//
//            System.out.println("插入结束！");
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//    }
//    //查询
//    public void like(){
//
//        String sql = "select * from hot where name like '%zhang%'";
//        PreparedStatement ps = null;
//        ResultSet res = null;
//        try {
//            ps = (PreparedStatement) conn.prepareStatement(sql);
//            res = ps.executeQuery();
//        } catch (SQLException e1) {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//        }
//        try {
//
//            while(res.next()){
//
//                int num = res.getInt(1);
//                String name = res.getString(2);
//                String author = res.getString(3);
//                String style = res.getString(4);
//                String form = res.getString(5);
//
//
//                System.out.println("num: " + num + " ,name: " + name +" ,author: " + author + " ,style: " + style + " ,form: " + form);
//                System.out.println("模糊查询成功");
//            }
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//
//    }
//    //更新
//    public int update(){
//        String sql = "update hot set name = '张军' where num=7";
//        PreparedStatement ps = null;
//
//        try {
//            ps = (PreparedStatement) conn.prepareStatement(sql);
//            ps.executeUpdate();
//            System.out.println("更新成功！");
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return 0;
//
//    }
//    //删除
//    public void delete(){
//
//        String sql = "delete from hot where num = 1";
//        String sql2 = "delete from hot where num = 2";
//        String sql3 = "delete from hot where num = 3";
//        String sql4 = "delete from hot where num = 4";
//        String sql5 = "delete from hot where num = 5";
//
//        PreparedStatement ps = null;
//
//        try {
//            ps = (PreparedStatement) conn.prepareStatement(sql);
//            ps.executeUpdate();
//            System.out.println("删除成功！");
//            ps = (PreparedStatement) conn.prepareStatement(sql2);
//            ps.executeUpdate();
//            System.out.println("删除成功！");
//            ps = (PreparedStatement) conn.prepareStatement(sql3);
//            ps.executeUpdate();
//            System.out.println("删除成功！");
//            ps = (PreparedStatement) conn.prepareStatement(sql4);
//            ps.executeUpdate();
//            System.out.println("删除成功！");
//            ps = (PreparedStatement) conn.prepareStatement(sql5);
//            ps.executeUpdate();
//            System.out.println("删除成功！");
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//    }
//
//    /***
//    * @Title: initCode
//    * @Param: []
//    * @description:  数据库表生成对象
//    * @author: ubing
//    * @date:  14:06
//    * @return: void
//    * @throws:
//    */
//    public void initCode(String tabName,String path){
//        try {
//            String[] splitOne = DB_URL.split("/");
//            String[] split = splitOne[3].split("\\?");
//            DB_BASE = split[0];
//            System.out.println(DB_BASE);
//            stmt = conn.createStatement();
//            String  sql="SELECT COLUMN_NAME FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = '"+DB_BASE+"' AND TABLE_NAME = '"+tabName+"'";
//            ResultSet rs = stmt.executeQuery(sql);
//            //填充数据
//            try {
//                String tab = lineToHump(tabName);
//                String code = MySql.readTmpl(rs);
//                String codeProduce = code.replaceAll("#TabName", tab);
//                //向固定path输出java文件
//                FileWriter writer;
//                writer = new FileWriter(path+"/"+tab+".java"); // 如果已存在，以覆盖的方式写文件
//                // writer = new FileWriter("testFileWriter.txt", true); // 如果已存在，以追加的方式写文件
//                writer.write(""); //清空原文件内容
//                writer.write(codeProduce);
//                writer.flush();
//                writer.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            // 完成后关闭
//            rs.close();
//            stmt.close();
//            conn.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }finally{
//            // 关闭资源
//            try{
//                if(stmt!=null) stmt.close();
//            }catch(SQLException se2){
//            }// 什么都不做
//            try{
//                if(conn!=null) conn.close();
//            }catch(SQLException se){
//                se.printStackTrace();
//            }
//        }
//
//    }
//
//
//    //驼峰式转下划线命名
//    private static String humpToLine(String str) {
//        Pattern humpPattern = Pattern.compile("[A-Z]");
//        Matcher matcher = humpPattern.matcher(str);
//        StringBuffer sb = new StringBuffer();
//        while (matcher.find()) {
//            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
//        }
//        matcher.appendTail(sb);
//        return sb.toString();
//    }
//
//    //下划线转驼峰式命名
//    public static String lineToHump(String str) {
//        str = str.toLowerCase();
//        Pattern linePattern = Pattern.compile("_(\\w)");
//        Matcher matcher = linePattern.matcher(str);
//        StringBuffer sb = new StringBuffer();
//        while (matcher.find()) {
//            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
//        }
//        matcher.appendTail(sb);
//        return sb.toString();
//    }
//
//    public void createTab(Object obj){
//        try {
//            stmt = conn.createStatement();
//            //填充数据
//            String tabName = obj.getClass().getAnnotation(TabName.class).value();
//            Field[] fields = obj.getClass().getDeclaredFields();
//            String createBody="";
//            for (Field field : fields) {
//                field.setAccessible(true);
//                TabField tabField = field.getAnnotation(TabField.class);
//                if(tabField!=null){
//                    createBody+= tabField.value()+" varchar(255),\n";
//                }
//            }
//            String createHeader = "create table "+tabName+"(\n";
//            String createRear="REAR";
//            String sql = (createHeader+createBody+createRear).replaceAll(",\nREAR","\n)");
//            // 完成后关闭
//            boolean execute = stmt.execute(sql);
//            if(execute){
//                System.out.println("table创建成功");
//            }
//            stmt.close();
//            conn.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }finally{
//            // 关闭资源
//            try{
//                if(stmt!=null) stmt.close();
//            }catch(SQLException se2){
//            }// 什么都不做
//            try{
//                if(conn!=null) conn.close();
//            }catch(SQLException se){
//                se.printStackTrace();
//            }
//        }
//
//    }
//}
