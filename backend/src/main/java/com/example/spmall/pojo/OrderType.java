package com.example.spmall.pojo;

import lombok.Data;

/**
 * OrderType
 *
 * @author toy aa@nfda
 * @version 2024/04/21 10:43 PM
 * @since JDK11
 **/
@Data
public class OrderType {
    private int qty;
    private SkuInfo skuInfo;
}
