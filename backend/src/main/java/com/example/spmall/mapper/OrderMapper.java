package com.example.spmall.mapper;

import com.example.spmall.pojo.Delivery;
import com.example.spmall.pojo.OrderItem;
import com.example.spmall.pojo.Orders;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * OrderMapper
 *
 * @author toy aa@nfda
 * @version 2024/04/11 11:00 PM
 * @since JDK11
 **/
@Mapper
public interface OrderMapper {
    /**
     * 첫번째 추가
     * 유저 주소등 등록
     * @param delivery ->city, street, zipcode
     */
    void insertDelivery(Delivery delivery);

    /**
     * 두번째 추가
     * @param orders
     */
    void insertOrder(Orders orders);

    /**
     * 마지막 추가
     * @param params skuId, orderId,qty,orderPrice
     */
    void insertOrderItems(List<Map<String, Object>> params);




}
