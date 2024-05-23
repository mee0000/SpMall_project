package com.example.spmall.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Permission
 *
 * @author toy aa@nfda
 * @version 2024/03/02 10:09 PM
 * @since JDK11
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Permission {
    private Integer id;
    private Integer pid;
    private String name;
    private String code;
    private String toCode;
    private Integer type;
    private Integer status;
    private Integer level;
    private String select;
    private String path;
    private String checked;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private List<Permission> children;
}
