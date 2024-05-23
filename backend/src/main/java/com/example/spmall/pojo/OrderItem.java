package com.example.spmall.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * OrderItem
 *
 * @author toy aa@nfda
 * @version 2024/04/11 10:50 PM
 * @since JDK11
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    private int id;
    private int skuId;
    private int orderId;
    private int orderQty;
    private String orderPrice;
}
