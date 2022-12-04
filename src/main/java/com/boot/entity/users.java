package com.boot.entity;

import com.boot.annotation.TabField;
import com.boot.annotation.TabName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
/**
 * @author ubing
 * @date 2022-12-04 11:36:19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TabName("users")
public class users implements Serializable {

    private static final long serialVersionUID = 1L;
    

    @TabField("id")
    private String id;

    @TabField("created_at")
    private String createdAt;

    @TabField("updated_at")
    private String updatedAt;

    @TabField("deleted_at")
    private String deletedAt;

    @TabField("name")
    private String name;

    @TabField("age")
    private String age;

    @TabField("birthday")
    private String birthday;

    @TabField("email")
    private String email;

    @TabField("role")
    private String role;

    @TabField("member_number")
    private String memberNumber;

    @TabField("num")
    private String num;

    @TabField("address")
    private String address;

    @TabField("ignore_me")
    private String ignoreMe;
}