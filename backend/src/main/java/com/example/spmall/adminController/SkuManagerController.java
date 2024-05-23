package com.example.spmall.adminController;

import com.example.spmall.pojo.PageBean;
import com.example.spmall.pojo.Result;
import com.example.spmall.pojo.SkuInfo;
import com.example.spmall.adminService.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * SpuManagerController
 *
 * @author toy aa@nfda
 * @version 2024/01/13 7:16 PM
 * @since JDK11
 **/
@RestController
@Slf4j
@RequestMapping("/admin-api/admin/sku")
public class SkuManagerController {
    @Autowired
    ProductService productService;
    //insertSKUInfo--> 한개 추가

    /**
     * sku 한개 새로 추가
     * @param skuInfo
     * @return
     */
    @PostMapping("/saveSKUById")
    public Result saveSkuBySpuId(@RequestBody SkuInfo skuInfo) throws Exception {
        log.info("skuInfo:{}", skuInfo);
        productService.saveSkuInfo(skuInfo);
        return Result.success();
    }

    /**
     * sku 업데이트
     * @param skuInfo
     * @return
     */
    //getSKUBySPUID --> spuid 로 sku 상세가져오기
    @PutMapping("/updateSku")
    public Result updateSku(@RequestBody SkuInfo skuInfo) {
        log.info("skuInfo------------:{}", skuInfo);
        productService.updateSkuInfo(skuInfo);
        return Result.success();
    }
    /**
     * ????
     * @param spuId
     * @return
     */
    @GetMapping("/findSkuBySpuId/{spuId}")
    public Result getSkuBySpuId(@PathVariable Integer spuId) {
        log.info("getSkuInfoListBySpuId: {}", spuId);
        return Result.success(productService.getSkuInfoBySpuId(spuId)) ;
    }
    //getSkuBYspuId --> spuId 로 sku 리스트 가져오기

    /**
     * page: 1 현재 페이지
     * limit: 10 노출될 수량
     * order: 'asc/desc' 순서
     * keyword: 키워드
     * categoryId:
     * min:0 금액 범위
     * max:0 금액 범위
     * @return
     */
    @GetMapping("/skuInfo/list/{page}/{pageSize}")
    public Result getSkuInfoList(@PathVariable Integer page,
                                 @PathVariable Integer pageSize,
                                 @RequestParam Map<String, Object> params){
        log.info("params:{}", params);
        PageBean pageBean = productService.getSkuInfoList(page, pageSize, params);
        return Result.success(pageBean);
    }
    // /admin/sku/changeSkuStatus
    @PutMapping("/changeSkuStatus")
    public Result updateSkuStatus(@RequestBody SkuInfo skuInfo) {
        productService.updateSkuStatus(skuInfo);
        return Result.success();
    }

    /**
     * 삭제
     * @return
     */
    @DeleteMapping("/deleteSkuById/{skuId}")
    public Result deleteSkuById(@PathVariable Integer skuId)throws Exception {
        productService.deleteSkuById(skuId);
        return Result.success();
    }
}
