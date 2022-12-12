package com.boot.entity;

        import com.boot.annotation.TabField;
        import com.boot.annotation.TabName;
        import lombok.Data;

        import java.io.Serializable;
/**
 * @author ubing
 * @date 2022-12-04 11:36:19
 */
@Data
public class user implements Serializable {

    private static final long serialVersionUID = 1L;



    private String userName;


    private String password;
    private void say(){
        System.out.println("傻逼");
    }
}