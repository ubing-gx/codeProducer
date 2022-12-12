package com.boot.service;

/**
 * @ClassName : utils
 * @Description : utils
 * @Author : ubing
 * @Date: 2022-12-12 11:56
 */
public class convertUtils {
    public static <T> T convert(Object obj, Class<T> var) {
        return (T) obj;
    }
}
