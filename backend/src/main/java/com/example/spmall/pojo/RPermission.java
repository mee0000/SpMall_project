package com.example.spmall.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RPermission
 *
 * @author toy aa@nfda
 * @version 2024/03/07 10:29 PM
 * @since JDK11
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RPermission {
    private int id;
    private int r_id;
    private int p_id;
    private int type;
    private int level;
    private String p_name;
    private String code;
}
