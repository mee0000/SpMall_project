package com.example.spmall.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Delivery
 *
 * @author toy aa@nfda
 * @version 2024/04/11 10:52 PM
 * @since JDK11
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Delivery {
    private int id;
    private String city;
    private String street;
    private String delivery_dode;
    private String zipcode;
}
