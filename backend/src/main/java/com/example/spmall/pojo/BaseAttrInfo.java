package com.example.spmall.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * BaseAttrInfo
 *
 * @author toy aa@nfda
 * @version 2023/10/16 6:53 PM
 * @since JDK11
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseAttrInfo {
    private Integer id;
    private String attrName;
    private Integer categoryId;
    private Integer categoryLevel;
    private List<BaseAttrValue> attrValueList;
}
