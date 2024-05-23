package com.example.spmall.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SkuAttrVal
 *
 * @author toy aa@nfda
 * @version 2023/11/03 9:31 PM
 * @since JDK11
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkuAttr {
  private int id;
  private int attrId;
  private String attrName;
  private String valueName;
  private int valueId;
  private int skuId;
}
