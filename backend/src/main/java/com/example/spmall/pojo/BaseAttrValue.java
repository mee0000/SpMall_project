package com.example.spmall.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * BaseAttrValue
 *
 * @author toy aa@nfda
 * @version 2023/10/22 11:53 PM
 * @since JDK11
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseAttrValue {
    private Integer id;
    private Integer attrId;
    private String valueName;
}
