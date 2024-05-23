package com.example.spmall.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * PageBean
 *
 * @author toy aa@nfda
 * @version 2023/10/16 6:35 PM
 * @since JDK11
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageBean {
    private Long total;
    private List rows;
}
