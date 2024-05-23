package com.example.spmall.adminController;
import com.example.spmall.pojo.*;
import com.example.spmall.adminService.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * SpuManagerController
 *
 * @author toy aa@nfda
 * @version 2023/10/16 5:24 PM
 * @since JDK11
 **/
@Slf4j
@RestController
@RequestMapping("/admin-api/admin/product")
public class SpuManagerController {

    @Autowired
    ProductService productService;
    /**
     * url: "admin/product/1/10? category2Id = 21"
     * @param page
     * @param pageSize
     * @param category2Id
     * category2Id ==>1. Integer category2Id,2. @RequestParam Integer category2Id,3. SpuInfo spuInfo(category2Id 값이 있으면 대채)
     * @return
     */
    @GetMapping("/getSpuList/{page}/{pageSize}")
    public Result getSpuInfoList(@PathVariable Integer page,
                                 @PathVariable Integer pageSize,
                                 @RequestParam(required = false) Integer category2Id) {
        log.info("spuInfo:{},{},{}",page,pageSize, category2Id);
        //리스트 가져오기
        PageBean pageBean = productService.getSpuInfoList(page, pageSize , category2Id);
        return Result.success(pageBean);
    }

    /**
     *  /admin/product/attrInfoList
     * @param category1Id
     * @param category2Id
     * @return
     */
    @GetMapping("/attrInfoList/{category1Id}/{category2Id}")
    public Result getAttrInfoList(@PathVariable Integer category1Id,
                                  @PathVariable Integer category2Id) {

        return Result.success(productService.getBaseAttrInfo(category1Id, category2Id));
    }

    /**
     * 속성값 및 속성 새로 추가
     * @param attrInfo
     * @return
     */
    @PostMapping("/saveAttrInfo")
    public Result saveAttrInfo(@RequestBody BaseAttrInfo attrInfo) {
        log.info("attrInfo:{}", attrInfo);
        productService.saveBaseAttrInfo(attrInfo);
        return Result.success();
    }

    /**
     * 속성및 속성값 업로드
     * @param attrInfo
     * @return
     */
    @PutMapping("/updateAttrInfo")
    public Result updateAttrInfo(@RequestBody BaseAttrInfo attrInfo) {
        log.info("updateAttrInfo:{}", attrInfo);
        productService.updateBaseAttrInfo(attrInfo);
        return Result.success();
    }

    /**
     *
     * @param attrId
     * @return
     * @throws Exception
     */
    @DeleteMapping("/deleteAttr/{attrId}")
    public Result deleteAttr(@PathVariable Integer attrId) throws Exception {
        productService.deleteAttrById(attrId);
        return Result.success();
    }

    /**
     *
     * @param spuId
     * @return
     */
   @GetMapping("/getSpuById/{spuId}")
   public Result getSpuById(@PathVariable Integer spuId) {
        log.info("spuId:{}", spuId);
        log.info("spuInfoById:{}", productService.getSpuInfoById(spuId));
        return Result.success(productService.getSpuInfoById(spuId));
   }

    /**
     * spuInfo 업데이트
     * @param spuInfo
     * @return
     */
   @PutMapping("/updateSpuInfo")
    public Result updateSpuInfo(@RequestBody SpuInfo spuInfo) {
       log.info("spuInfoUpate:{}", spuInfo);
       productService.updateSpuInfo(spuInfo);
       return Result.success();
   }

    /**
     * spuInfo insert
     * @param spuInfo
     * @return
     */
   @PostMapping("/saveSpuInfo")
    public Result saveSpuInfo(@RequestBody SpuInfo spuInfo) {
       log.info("spuInfo: {}", spuInfo);
       productService.insertSpuInfo(spuInfo);
       return Result.success();
   }

    /**
     * spuInfo 삭제
     * @param spuId
     * @return
     * @throws Exception
     */
   @DeleteMapping("/deleteSpu/{spuId}")
    public Result deleteSpuInfo(@PathVariable Integer spuId) throws Exception{
       productService.deleteSpuInfoById(spuId);
       return Result.success();
   }

    /**
     * 속성 가져오기
     * @return
     */
   @GetMapping("/baseSaleAttrList")
   public Result getBaseAttrList() {
       return Result.success(productService.getBaseSaleAttrList());
   }

    /**
     * sku --> spuAttrList
     * @param spuId
     * @return
     */
   @GetMapping("/spuSaleAttrList/{spuId}")
    public Result getSpuSaleAttrList(@PathVariable Integer spuId) {
       return Result.success(productService.getSpuSaleAttrList(spuId));
   }


   @GetMapping("/getSpuImageList/{spuId}")
    public Result getSpuImageList(@PathVariable int spuId) {
       return Result.success(productService.getSpuImageList(spuId));
   }

}
