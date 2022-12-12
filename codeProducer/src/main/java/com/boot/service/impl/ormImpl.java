package com.boot.service.impl;

import com.boot.JDBC.SingleMySql;
import com.boot.annotation.TabField;
import com.boot.annotation.TabName;
import com.boot.entity.user;
import com.boot.entity.users;
import com.boot.service.convertUtils;
import com.boot.service.orm;
import com.sun.xml.internal.fastinfoset.util.ValueArrayResourceException;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName : ormImpl
 * @Description : ormImpl
 * @Author : Xxxxx
 * @Date: 2022-12-12 09:47
 */
public class ormImpl implements orm {
    @Override
    public boolean insert(SingleMySql singleMySql,Object o) throws IllegalAccessException, SQLException {
        String sqlHeader = "INSERT INTO ";
        String sqlRear = " VALUES (";
        String left ="(";
        String right = ")";
        String S = "RE";
////        INSERT INTO table1 (column1, column2,...)
//VALUES
//    (value1, value2,...);
//        String sql="INSERT INTO jdbc_t (id,`name`) VALUES (?,?);";
        //2,创建preparestatement对象来存储我们的SQL语句并填充占位符
        TabName tabName = o.getClass().getAnnotation(TabName.class);
        sqlHeader= sqlHeader+tabName.value()+" (";
        Field[] fields = o.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            TabField tabField = field.getAnnotation(TabField.class);
            if(tabField!=null){
                if(tabField.value()!=null){
                    sqlHeader+="`"+tabField.value()+"`" +",";
                    if(field.get(o)==null){
                        sqlRear+=field.get(o)+",";
                    }else {
                        sqlRear+="'"+field.get(o)+"'"+",";
                    }
                }
            }else if(field.getName()!="serialVersionUID"){
            }
        }
        String sql = (sqlHeader+ right + sqlRear + right).replaceAll(",\\)", ")");
        System.out.println(sql);
        singleMySql.stmt.execute(sql);
        return false;
    }

    @Override
    public <T> List<T> selectList(SingleMySql singleMySql, Class<T> var) {
        List<T> lists = new ArrayList<T>();
        String sql = null;
        TabName tabName = var.getAnnotation(TabName.class);
        if(tabName!=null){
            sql="SELECT * FROM "+tabName.value();
        }else{
            sql="SELECT * FROM "+singleMySql.humpToLine(var.getSimpleName());
        }
        try {
            ResultSet resultSet = singleMySql.stmt.executeQuery(sql);
            Field[] fields = var.getDeclaredFields();
            while (resultSet.next()) {
                Object o = var.newInstance();
                for (Field field : fields) {
                    field.setAccessible(true);
                    TabField tabField = field.getAnnotation(TabField.class);
                    if(tabField!=null){
                        String string = resultSet.getString(tabField.value());
                        field.set(o,string);
                    }else if(field.getName()!="serialVersionUID"){
                    }
                }
                lists.add((T)o);
            }
            return lists;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

}
