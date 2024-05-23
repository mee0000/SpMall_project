package com.example.spmall.adminService;

import com.example.spmall.pojo.*;

import java.util.List;
import java.util.Map;

/**
 * ProductService
 *
 * @author toy aa@nfda
 * @version 2023/10/13 4:16 PM
 * @since JDK11
 **/
public interface ProductService {

    /**
     * attrId 상품속성아이디의해 리스트 획득
     *
     * @param category1Id
     * @param category2Id
     * @return
     */
    List<BaseAttrInfo> getBaseAttrInfo(Integer category1Id, Integer category2Id);

    /**
     * 카
     * @param page
     * @param pageSize
     * @param category2Id
     * @return
     */
    PageBean getSpuInfoList(Integer page, Integer pageSize, Integer category2Id);

    /**
     * insert BaseAttrInfo
     * @param attrInfo
     */
    void saveBaseAttrInfo(BaseAttrInfo attrInfo);

    /**
     * update BaseAttrInfo
     * @param attrInfo
     */
    void updateBaseAttrInfo(BaseAttrInfo attrInfo);
    /**
     * 속성삭제시 해당 속성값도 같이 삭제
     * @param attrId
     */
    void deleteAttrById(Integer attrId) throws Exception;

    List<BaseSaleAttr> getBaseSaleAttrList();
    /**
     * spu->spuAttr->sku 받기
     * @param spuId
     * @return
     */
    SpuInfo getSpuInfoById(Integer spuId);

    /**
     * updateInfo
     * @param spuInfo
     */
    void updateSpuInfo(SpuInfo spuInfo);

    /**
     * insertSpuInfo
     * @param spuInfo
     */
    void insertSpuInfo(SpuInfo spuInfo);

    /**
     * deleteSpuInfo
     * @param spuId
     */
    void deleteSpuInfoById(Integer spuId) throws Exception;

    /**
     * spuSaleAttrList ㄱㅏ져오기
     * @param spuId
     * @return
     */
    List<SpuSaleAttr> getSpuSaleAttrList(Integer spuId);

    /**
     * insert skuInfo and skuAttr
     * @param skuInfo
     */
    void saveSkuInfo(SkuInfo skuInfo)throws Exception;

    /**
     * getSkuInfoBySpuId
     * @param spuId
     * @return
     */
    List<SkuInfo> getSkuInfoBySpuId(Integer spuId);

    /**
     * getSpuImageList
     * @param spuId
     * @return
     */
    List<SpuImage> getSpuImageList(int spuId);


    PageBean getSkuInfoList(Integer page, Integer pageSize, Map<String, Object> params);

    /**
     * status 1 : 상품 올림 2: 상품 내림 3: 상품 삭제
     * @param skuInfo
     */
    void updateSkuStatus(SkuInfo skuInfo);

    /**
     * sku 삭제
     * @param skuId
     */
    void deleteSkuById(Integer skuId) throws Exception;


    void updateSkuInfo(SkuInfo skuInfo);
}
