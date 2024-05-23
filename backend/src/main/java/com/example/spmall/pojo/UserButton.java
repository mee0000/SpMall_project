package com.example.spmall.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserRoute
 *
 * @author toy aa@nfda
 * @version 2023/11/18 10:23 PM
 * @since JDK11
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserButton {
    private int id;
    private int userId;
    private int rId;
    private String btnName;
    private String code;
}
