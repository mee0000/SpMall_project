package com.example.spmall.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Roles
 *
 * @author toy aa@nfda
 * @version 2024/01/27 12:10 AM
 * @since JDK11
 **/
@Data
public class Roles {
    private Integer id;
    private String name;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
