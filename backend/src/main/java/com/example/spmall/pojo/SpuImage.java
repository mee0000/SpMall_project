package com.example.spmall.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SpuImage
 *
 * @author toy aa@nfda
 * @version 2023/10/11 10:25 PM
 * @since JDK11
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpuImage {
    private int id;
    private int spuId;
    private String imgUrl;
    private String imgName;
}
