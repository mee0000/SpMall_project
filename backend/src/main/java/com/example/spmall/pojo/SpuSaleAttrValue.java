package com.example.spmall.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SpuSaleAttrValue
 *
 * @author toy aa@nfda
 * @version 2023/10/28 1:39 AM
 * @since JDK11
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpuSaleAttrValue {
    private Integer id;
    private Integer spuId;
    private Integer spuSaleAttrId;
    private String spuAttrValueName;
}
