package com.example.spmall.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * EmailProperties
 *
 * @author toy aa@nfda
 * @version 2024/04/03 1:54 PM
 * @since JDK11
 **/
@Data
public class EmailProperties {
    // userName
    public String user="sdfsf@22.com";

    //邮箱授权码 需要取得
    public String code="fdssdfsdfs";

    // 邮箱域名
    public String host = "smtp.qq.com";

    //身份开关
    private boolean auth = true;
}
