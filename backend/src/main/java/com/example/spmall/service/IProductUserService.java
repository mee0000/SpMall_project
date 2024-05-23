package com.example.spmall.service;

import com.example.spmall.mapper.CartMapper;
import com.example.spmall.mapper.OrderMapper;
import com.example.spmall.mapper.ProductUserMapper;
import com.example.spmall.mapper.SkuInfoMapper;
import com.example.spmall.pojo.*;
import com.example.spmall.utils.JwtUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * IproductUserService
 *
 * @author toy aa@nfda
 * @version 2024/04/12 9:20 PM
 * @since JDK11
 **/
@Service
@Slf4j
public class IProductUserService implements ProductUserService{

    @Autowired
    private ProductUserMapper productUserMapper;

    @Autowired
    private SkuInfoMapper skuInfoMapper;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Result login(Map<String, Object> params) {

        // 1. 받은 값이 유효한지 확인
        String userName = (String) params.get("userName");
        String password = (String)params.get("password");
        if(!StringUtils.hasText(userName) || !StringUtils.hasText(password)) {
            return Result.error("매개변수가 정확하지않습니다");
        }

        ProductUser pUser = productUserMapper.checkProductUser(userName, password);
        log.info("user:{}", pUser);
        if(pUser == null) {
            return Result.error("유저아이디 또는 비밀번호 오류");
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", pUser.getId());
        claims.put("userName", pUser.getUserName());
        String jwt = JwtUtils.generateJwt(claims);

        // 저장
        return Result.success(jwt);
    }

    @Override
    public Result findUserByToken(String token) {
        // 1. token이 존재하는지 확인
        if (!StringUtils.hasText(token)) {
            return Result.error("유효하지 않습니다.");
        }
        try {
            Claims claims = JwtUtils.parseJWT(token);
            String userName = (String) claims.get("userName");
            ProductUser userInfo = productUserMapper.getProductUser(userName);

            return Result.success(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("유효기간 초과");
        }
    }

    /**
     *
     * @param skuId
     * @return
     */
    @Override
    public SkuInfo getSkuById(int skuId) {
        return skuInfoMapper.getOneSkuInfo(skuId);
    }

    /**
     * 추가 및 업데이트 (skuId, userId)
     * @param cart userId,skuId,qty
     */
    @Override
    public void saveCart(Cart cart) {
        LocalDateTime d = LocalDateTime.now();
        cart.setCreateDate(d);
        cartMapper.insertCartAndUpdate(cart);
    }

    @Override
    public List<Cart> getCartListByUserId(int userId) {
        Map<String, Object> cartMap = new HashMap<>();
        List<Cart> cartList = cartMapper.getCartListByUserId(userId);
        log.info("cartList:{}", cartList);
        for (Cart cart: cartList) {
           SkuInfo skuInfo = skuInfoMapper.getOneSkuInfo(cart.getSkuId());
           cart.setSkuInfo(skuInfo);
        }
        return cartList;
    }

    /**
     *
     * @param userId 유저아이디에 의해 차트리스트의 수량
     * @return
     */
    public int getCartListTotalByUserId(Integer userId) {
        return cartMapper.getTotalByUserId(userId);
    }

    /**
     *
     * @param cart 수량 업데이트
     */
    @Override
    public void updateCart(Cart cart) {
        cartMapper.updateCartQty(cart);
    }

    /**
     * 오더할 상품리스트 가져오기
     * @param params
     * @return
     */
    @Override
    public List<OrderType> getOrderList(Map<String, Object> params) {
        List<OrderType> orderTypeList = new ArrayList<>();
        if(params.get("mode").equals("cart")){
            String str = params.get("cartIds").toString();
            String[] ids = str.split(",");
            List<Cart> cartList = cartMapper.getOrderListByCartIds(ids);
            for(Cart item: cartList) {
                OrderType orderType =new OrderType();
                orderType.setQty(item.getQty());
                orderType.setSkuInfo(skuInfoMapper.getOneSkuInfo(item.getSkuId()));
                //return orderTypeList
                orderTypeList.add(orderType);
            }

        } else if (params.get("mode").equals("buyNow")) {
            Integer skuId =Integer.parseInt((String) params.get("skuId")) ;
            SkuInfo skuInfo = skuInfoMapper.getOneSkuInfo(skuId);
            OrderType orderType =new OrderType();
            orderType.setQty(Integer.parseInt((String) params.get("qty")) );
            orderType.setSkuInfo(skuInfo);
            //return orderTypeList
            orderTypeList.add(orderType);
        }

        return orderTypeList;
    }

    /**
     * 세일 상품 리스트
     * @param pageNo
     * @param pageSize
     * @param searchParam
     * @return
     */
    @Override
    public PageBean getSaleProductList(int pageNo, int pageSize, Map<String, Object> searchParam) {
        PageHelper.startPage(pageNo, pageSize);

        List<SkuInfo> skuInfoList = skuInfoMapper.getSaleSkuInfo(searchParam);
        Page<SkuInfo> pageInfo = (Page<SkuInfo>) skuInfoList;
        PageBean pageBean = new PageBean(pageInfo.getTotal(), pageInfo.getResult());
        return pageBean;
    }

    /**
     *
     * @param params user 정보 및 orderList
     */
    @Override
    public void saveOrder(Map<String, Object> params) {
        Map<String, Object> objectMap = (Map<String, Object>) params.get("user");
       // 유저 업데이트
        productUserMapper.updateProductUser(objectMap);

        // 유저 orders 저장
        Integer userId = (Integer) objectMap.get("id");
        LocalDateTime dateTime = LocalDateTime.now();
        Orders orders = new Orders();
        orders.setUserId(userId);
        orders.setOrderDate(dateTime);
        orderMapper.insertOrder(orders);
        log.info("order: {}",orders);
        // orderItem 저장
        List<Map<String, Object>> items = (List<Map<String, Object>>) params.get("orderList");
        for (Map<String,Object> item: items) {
            item.put("orderId", orders.getId());
        }
        orderMapper.insertOrderItems(items);

    }

    /**
     * 오더완료후 차트에서 해당 아이템들을 없애기
     * @param userId
     * @param ids
     */
    @Override
    public void deleteCartByIds(Integer userId, String ids) {
        String[] cartIds = ids.split(",");
        Map<String, Object> map = new HashMap<>();
        if(cartIds.length > 0) {
            map.put("ids", cartIds);
            map.put("userId", userId);
        cartMapper.deleteCartByIds(map);
        }
    }

    /**
     * 회원가입 -userId,password,phoneNumber 필수
     * @param productUser
     */
    @Override
    public void insertUser(ProductUser productUser) {
        LocalDateTime localDateTime = LocalDateTime.now();
        productUser.setCreateDate(localDateTime);
        productUserMapper.insertProductUser(productUser);
    }

    /**
     * @param userName 있는지 확인
     * @return
     */
    @Override
    public boolean checkUserId(String userName) {
        log.info("userId:{}", userName);
        log.info("유저 유무 확인:{}", productUserMapper.checkUserId(userName));
        if(productUserMapper.checkUserId(userName) == null) {
            return true;
        }
        return false;
    }

}
