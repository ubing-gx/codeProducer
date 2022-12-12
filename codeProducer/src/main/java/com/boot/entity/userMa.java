package com.boot.entity;

import com.boot.annotation.TabField;
import com.boot.annotation.TabName;

import java.io.Serializable;
/**
 * @author ubing
 * @date 2022-12-04 22:38:17:594
 */
@TabName("userMa")
public class userMa implements Serializable {

    private static final long serialVersionUID = 1L;
    

    @TabField("user_name")
    private String userName;

    @TabField("password")
    private String password;
}