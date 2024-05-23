package com.example.spmall.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

/**
 * Result
 *
 * @author toy aa@nfda
 * @version 2023/10/12 1:06 AM
 * @since JDK11
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private Integer code; //200 성공, 201 실패
    private String message;
    private Object data;

    // 추가 삭제 성공시
    public  static Result success() {
        return new Result(200, "success", null);
    }

    //리서치 성공시
    public static Result success(Object data) {
        return new Result(200, "success", data);
    }

    //실패시
    public static Result error(String msg) {
        return new Result(0, msg, null);
    }
}
