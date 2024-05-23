package com.example.spmall.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * spu_sale_attr
 *
 * @author toy aa@nfda
 * @version 2023/10/28 1:36 AM
 * @since JDK11
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpuSaleAttr {
    private Integer id;
    private Integer spuId;
    private Integer baseAttrId;
    private String baseAttrName;
    private List<SpuSaleAttrValue> spuSaleAttrValueList;
}
