package com.example.spmall.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Orders
 *
 * @author toy aa@nfda
 * @version 2024/04/11 10:25 PM
 * @since JDK11
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders {
    private int id;
    private int userId;
    private String deliveryCode;
    private LocalDateTime orderDate;
}
