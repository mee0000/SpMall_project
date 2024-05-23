package com.example.spmall.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * productUser
 *
 * @author toy aa@nfda
 * @version 2024/04/12 8:43 PM
 * @since JDK11
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductUser {
    private int id;
    private String userName;
    private String firstName;
    private String lastName;
    private String password;
    private String address;
    private String zipCode;
    private String phoneNumber;
    private LocalDateTime createDate;
}
