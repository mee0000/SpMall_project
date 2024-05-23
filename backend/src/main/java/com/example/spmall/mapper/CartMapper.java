package com.example.spmall.mapper;

import com.example.spmall.pojo.Cart;
import com.example.spmall.pojo.OrderType;
import com.example.spmall.pojo.SkuInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * CartMapper
 *
 * @author toy aa@nfda
 * @version 2024/04/11 9:38 PM
 * @since JDK11
 **/
@Mapper
public interface CartMapper {
    List<Cart> getOrderListByCartIds(String[] ids) ;

    /**
     * @param cart -> userId, skuId, createDate, qty ==>
     */
   void insertCartAndUpdate(Cart cart);

    /**
     *
     * @param userId 에 의해 cartList 가져오기
     * @return
     */
   List<Cart> getCartListByUserId(Integer userId);

    /**
     * @param id
     */
   @Delete("delete from cart where id = #{id}")
   void deleteCartById(Integer id);

   @Select("select sum(qty) from cart where user_id = #{userId}")
   int getTotalByUserId(Integer userId);

    /**
     *
     * @param cart 수량만 업데이트
     */
    @Update("update cart set qty = #{qty} where id = #{id}")
    void updateCartQty(Cart cart);

    /**
     *
     * @param map userId, cartIds 에 의해 차트리스트 삭제하기
     */
    void deleteCartByIds(Map<String, Object> map);
}
