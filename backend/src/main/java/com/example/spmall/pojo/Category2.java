package com.example.spmall.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * category2
 *
 * @author toy aa@nfda
 * @version 2023/10/11 10:01 PM
 * @since JDK11
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category2 {
    private int id;
    private String categoryName;
    private int category1Id;
}
