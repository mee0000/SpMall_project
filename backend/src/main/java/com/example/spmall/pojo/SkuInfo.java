package com.example.spmall.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

/**
 * SkuInfo
 *
 * @author toy aa@nfda
 * @version 2023/10/11 9:56 PM
 * @since JDK11
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkuInfo {
    private int id;
    private int spuId;
    private int quantity;
    private int price;
    private int goods_price_min;
    private String spuName;
    private String skuName;
    private int category2Id;
    private String skuDefaultImg;
    private String discount;
    private String decoration;
    private int status; // 1.정상 2.내림 3.삭제
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private List<SkuAttr> skuAttrValueList;
    private List<SkuSaleAttr> skuSaleAttrValueList;
}
