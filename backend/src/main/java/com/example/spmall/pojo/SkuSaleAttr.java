package com.example.spmall.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SkuSaleAttr
 *
 * @author toy aa@nfda
 * @version 2024/01/15 7:50 PM
 * @since JDK11
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkuSaleAttr {
    private int id;
    private int saleAttrId;
    private int saleValueId;
    private int skuId;
    private String saleAttrName;
    private String saleAttrValueName;
}
