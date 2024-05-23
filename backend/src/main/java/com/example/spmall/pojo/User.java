package com.example.spmall.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Delete;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * User
 *
 * @author toy aa@nfda
 * @version 2023/10/14 12:24 PM
 * @since JDK11
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private String username;
    private String password;
    private String desc;
    private String avatar;
    private String nickName;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private List<UserRole> roles;
    private List<String> buttons;
    private List<String> routes;
}
