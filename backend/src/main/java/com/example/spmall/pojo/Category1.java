package com.example.spmall.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Category1
 *
 * @author toy aa@nfda
 * @version 2023/10/11 9:59 PM
 * @since JDK11
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category1 {
    private int id;
    private String categoryName;
    private int level;
    private List<Category2> category2List;
}
