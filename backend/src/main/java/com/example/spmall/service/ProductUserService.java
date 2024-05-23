package com.example.spmall.service;

import com.example.spmall.pojo.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * ProductService
 *
 * @author toy aa@nfda
 * @version 2024/04/12 9:18 PM
 * @since JDK11
 **/
@Service
public interface ProductUserService {

    void insertUser(ProductUser productUser);

    boolean checkUserId(String userId);

    Result login(Map<String, Object> params);

    Result findUserByToken(String token);

    SkuInfo getSkuById(int skuId);
    void saveCart(Cart cart);

    List<Cart> getCartListByUserId(int userId);

    int getCartListTotalByUserId(Integer userId);

    void updateCart(Cart cart);

    List<OrderType> getOrderList(Map<String, Object> params);

    PageBean getSaleProductList(int pageNo, int pageSize, Map<String, Object> searchParam);

    void saveOrder(Map<String, Object> params);

    void deleteCartByIds(Integer userId, String ids);
}
