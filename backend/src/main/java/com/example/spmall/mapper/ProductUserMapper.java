package com.example.spmall.mapper;

import com.example.spmall.pojo.ProductUser;
import com.example.spmall.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * ProductUserMapper
 *
 * @author toy aa@nfda
 * @version 2024/04/12 8:50 PM
 * @since JDK11
 **/
@Mapper
public interface ProductUserMapper {

    /**
     * 회원가입
     */
    void insertProductUser(ProductUser productUser);

    @Select("select id, user_name, password, last_name, address, phone_number, create_date, first_name, zip_code from product_user where user_name = #{userName}")
    ProductUser getProductUser(String userName);

    @Select("select * from product_user where user_name = #{userName}")
    ProductUser checkUserId(String userName);

    @Select("select id, user_name, password from product_user where user_name = #{userName} and password = SHA2(#{password}, 256)")
    ProductUser checkProductUser(String userName, String password);

    void updateProductUser(Map<String, Object> params);

}
