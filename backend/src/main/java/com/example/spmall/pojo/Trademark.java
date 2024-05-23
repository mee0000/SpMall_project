package com.example.spmall.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Trademark
 *
 * @author toy aa@nfda
 * @version 2023/12/15 7:36 PM
 * @since JDK11
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Trademark {
    private Integer id;
    private String tmName;
    private String logoUrl;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
