package com.example.spmall.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * BaseSaleAttr
 *
 * @author toy aa@nfda
 * @version 2023/12/22 3:20 AM
 * @since JDK11
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseSaleAttr {
    private int id;
    private String attrName;
}
