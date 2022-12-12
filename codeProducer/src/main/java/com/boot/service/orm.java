package com.boot.service;

import com.boot.JDBC.SingleMySql;

import java.sql.SQLException;
import java.util.List;

/**
 * @ClassName : orm
 * @Description : orm
 * @Author : Xxxxx
 * @Date: 2022-12-12 09:47
 */
public interface orm {
    boolean insert(SingleMySql singleMySql,Object o) throws IllegalAccessException, SQLException;

    public <T> List<T> selectList(SingleMySql singleMySql, Class<T> var);
}
