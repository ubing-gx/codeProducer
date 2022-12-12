package com.boot.entity;

import com.boot.annotation.TabField;
import com.boot.annotation.TabName;

import java.io.Serializable;
/**
 * @author ubing
 * @date 2022-12-05 21:05:44:941
 */
@TabName("users")
public class users implements Serializable {

    private static final long serialVersionUID = 1L;

    public users() {
    }

    @TabField("id")
    private String id;

    @Override
    public String toString() {
        return "users{" +
                "id='" + id + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", deletedAt='" + deletedAt + '\'' +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", birthday='" + birthday + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", memberNumber='" + memberNumber + '\'' +
                ", num='" + num + '\'' +
                ", address='" + address + '\'' +
                ", ignoreMe='" + ignoreMe + '\'' +
                '}';
    }

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

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }


    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }


    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }


    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public String getMemberNumber() {
        return memberNumber;
    }

    public void setMemberNumber(String memberNumber) {
        this.memberNumber = memberNumber;
    }


    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getIgnoreMe() {
        return ignoreMe;
    }

    public void setIgnoreMe(String ignoreMe) {
        this.ignoreMe = ignoreMe;
    }


}