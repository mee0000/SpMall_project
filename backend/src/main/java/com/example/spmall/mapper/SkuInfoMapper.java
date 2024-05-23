package com.example.spmall.mapper;

import com.example.spmall.pojo.SkuAttr;
import com.example.spmall.pojo.SkuInfo;
import com.example.spmall.pojo.SkuSaleAttr;
import com.example.spmall.pojo.SpuInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * SkuMapper
 *
 * @author toy aa@nfda
 * @version 2023/11/05 2:36 PM
 * @since JDK11
 **/
@Mapper
public interface SkuInfoMapper {
    /**
     * 업데이트
     * @param skuSaleAttr
     */
    void insertSkuSaleAttr(SkuSaleAttr skuSaleAttr);

    /**
     * 업데이트
     * @param skuAttr
     */
    void insertSkuAttr(SkuAttr skuAttr);

    /**
     * insert
     * @param skuInfo
     */
    void insertSkuInfo(SkuInfo skuInfo);

    /**
     *
     * @param skuAttrValList
     */
    void insertSkuAttrList(List<SkuAttr> skuAttrValList);

    /**
     *
     * @param spuId
     * @return
     */
    List<SkuInfo> getSkuInfoList(Integer spuId);

    /**
     *
     * @param skuSaleAttrList
     */
    void insertSkuSaleAttrList(List<SkuSaleAttr> skuSaleAttrList);

    /**
     * admin
     * @param params
     * @return
     */
    List<SkuInfo> getAllSkuInfo(Map<String, Object> params);

    /**
     * product
     * @param params
     * @return
     */
    List<SkuInfo> getSaleSkuInfo(Map<String, Object> params);

    @Update("update sku_info set status = #{status} where id = #{id}")
    void updateSkuStatus(SkuInfo skuInfo);
    @Delete("delete from sku_info where id = #{skuId}")
    void deleteSkuById(Integer skuId);

    @Delete("delete from sku_attr where sku_id = #{skuId}")
    void deleteSkuAttrById(Integer skuId);
    @Delete("delete from sku_sale_attr where sku_id= #{skuId}")
    void deleteSkuSaleAttrById(Integer skuId);

    SkuInfo getOneSkuInfo(Integer skuId);

    void updateSkuInfo(SkuInfo skuInfo);
}
