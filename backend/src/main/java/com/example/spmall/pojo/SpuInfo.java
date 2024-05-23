package com.example.spmall.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * SpuInfo
 *
 * @author toy aa@nfda
 * @version 2023/10/11 10:21 PM
 * @since JDK11
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpuInfo {
    private int id;
    private String spuName;
    private String description;
    private int price;
    private int category2Id;
    private List<SkuInfo> skuInfoList;
    private List<SpuSaleAttr> spuSaleAttrList;
    private List<SpuImage> spuImageList;

}
