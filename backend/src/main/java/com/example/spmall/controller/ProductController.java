package com.example.spmall.controller;

import com.example.spmall.adminService.CategoryService;
import com.example.spmall.adminService.ProductService;
import com.example.spmall.pojo.Cart;
import com.example.spmall.pojo.PageBean;
import com.example.spmall.pojo.ProductUser;
import com.example.spmall.pojo.Result;
import com.example.spmall.service.ProductUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * ProductController
 *
 * @author toy aa@nfda
 * @version 2024/04/03 9:31 PM
 * @since JDK11
 **/
@RestController
@RequestMapping("/api/product")
@Slf4j
public class ProductController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductUserService productUserService;

    /**
     * shop-> 모든상품 가져오기
     * @param pageNo
     * @param pageSize
     * @param searchParams
     * @return
     */
     @GetMapping("/getSkuList/{pageNo}/{pageSize}")
        public Result getSkuInfoList(@PathVariable Integer pageNo,
                                     @PathVariable Integer pageSize,
                                     @RequestParam(required = false) Map<String, Object> searchParams) {
         log.info("spuInfo:{},{},{}", pageNo, pageSize, searchParams);
         //리스트 가져오기
         PageBean pageBean = productService.getSkuInfoList(pageNo, pageSize, searchParams);
         return Result.success(pageBean);
     }

     @GetMapping("/getSkuById/{skuId}")
     public  Result getSkuById(@PathVariable int skuId) {
         log.info("skuId:{}", skuId);
         return Result.success(productUserService.getSkuById(skuId));
     }
     /**
     * 리서치 카테고리 값 가져오기
     *
     * @return
     */
    @GetMapping("/getBaseCategoryList")
    public Result getCategoryList() {
        return Result.success(categoryService.getCategoryList());
    }

    /**
     * @param category2Id 의해 baseCategory2Id 가져오기
     * @return
     */
    @GetMapping("/getBaseAttrList/{category1Id}/{category2Id}")
    public Result getBaseAttrList(@PathVariable Integer category2Id, @PathVariable Integer category1Id) {
        log.info("c2id: {}", category2Id);
        return Result.success(productService.getBaseAttrInfo(category1Id, category2Id));
    }

    /**
     * 한개의 spu관련 가져오기
     * @param spuId
     * @return
     */
    @GetMapping("/getSpuById/{spuId}")
    public Result getSpuById(@PathVariable Integer spuId) {
        log.info("spuInfoById:{}", productService.getSpuInfoById(spuId));
        return Result.success(productService.getSpuInfoById(spuId));
    }

    /**
     * Guest용 바로구매
     * @param params
     * @return
     */
    @PostMapping("/paymentByGuest")
    public Result insertOrderByGuest(@RequestParam Map<String,Object> params) {
        log.info("paymentByGuest:{}", params);
        return Result.success();
    }

    /**
     * User 구매
     * @param userId
     * @param params
     * @return
     */
    @PostMapping("/paymentByUser/{userId}")
    public Result insertOrderByUserId(@PathVariable int userId,@RequestParam Map<String,Object> params) {
        log.info("paymentByGuest:{}", params);
        return Result.success();
    }

    /**
     * 회원가입 -> userId,password,lastName
     * @param productUser
     * @return
     */
    @PostMapping("/register")
    public Result insertProductUser(@RequestBody ProductUser productUser){
        log.info("productUser:{}", productUser);
        productUserService.insertUser(productUser);
        return Result.success();
    }

    /**
     * 회원가입전 userId 유일한지 확인하기
     * @param userName
     * @return
     */
    @PostMapping("/checkUserId")
    public Result checkUserName(@RequestParam String userName) {
        log.info("userId:{}", userName);
        return Result.success(productUserService.checkUserId(userName));
    }

    /**
     * 로그인
     * @param params ->userName, password
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody Map<String, Object> params) {
        log.info("param:{}", params);
      return productUserService.login(params);
    }

    /**
     * 로그인후 token을 이용해 유저 정보 가져오기
     * @param
     * @return
     */
    @GetMapping("/userInfo")
    public Result userInfo(@RequestHeader String token){
        return  productUserService.findUserByToken(token);
    }

    /**
     *
     * @param cart userId,skuId,qty
     * @return
     */
    @PostMapping("/saveCart")
    public Result saveCart(@RequestBody Cart cart) {
        log.info("cart:{}", cart);
        productUserService.saveCart(cart);
        return Result.success();
    }

    @DeleteMapping("/deleteCart/{userId}")
    public Result deleteCart(@PathVariable Integer userId,@RequestBody String ids) {
        log.info("ids:{}", ids);
        productUserService.deleteCartByIds(userId, ids);
        return Result.success();
    }
    /**
     *
     * @param cart ---> 수량만 수정
     * @return
     */
    @PostMapping("/updateCart")
    public Result updateCart(@RequestBody Cart cart){
        productUserService.updateCart(cart);
        return Result.success();
    }

    /**
     * 차트 리스트
     * @param userId
     * @return
     */
    @GetMapping("/cartList/{userId}")
    public Result getCartListByUserId(@PathVariable int userId) {
      return Result.success(productUserService.getCartListByUserId(userId));
    }

    /**
     * 차트 토탈 수량
     * @param userId
     * @return
     */
    @GetMapping("/cartTotal/{userId}")
    public Result getCartTotal(@PathVariable int userId) {
        return  Result.success(productUserService.getCartListTotalByUserId(userId));
    }

    /**
     * 오더 리스트
     * @param params
     * @return
     */
    @GetMapping("/getOrder")
    public Result getOrder(@RequestParam(required = false) Map<String, Object> params ) {
        log.info("params:{}", params);
        return Result.success(productUserService.getOrderList(params));
    }

    /**
     * 세일 상품 리스트
     * @return
     */
    @GetMapping("/getSaleProductList/{pageNo}/{pageSize}")
    public Result getSaleProductList(   @PathVariable int pageNo,
                                        @PathVariable int pageSize,
                                        @RequestParam(required = false) Map<String, Object> searchParam) {

        return Result.success(productUserService.getSaleProductList(pageNo, pageSize, searchParam));
    }
    @PostMapping("/payment")
    public Result payment(@RequestBody Map<String, Object> params) {
        productUserService.saveOrder(params);
        return Result.success();
    }
}
