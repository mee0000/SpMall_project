package com.example.spmall.mapper;

import com.example.spmall.pojo.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * SpuInfoMapper
 *
 * @author toy aa@nfda
 * @version 2023/10/16 6:46 PM
 * @since JDK11
 **/
@Mapper
@Repository
public interface SpuInfoMapper {

    @Select("select id, attr_name from base_sale_attr")
    List<BaseSaleAttr> getBaseSaleAttrList();
    List<SpuInfo> getSpuInfo(Integer category2Id);
    /**
     * spuId에의한  세일속성 및 값을 가져오기
     * @param spuId
     * @return
     */
    SpuInfo getSpuBySpuId(Integer spuId);

    /**
     * insertSpuInfo
     * @param spuInfo
     */

    void insertSpuInfo(SpuInfo spuInfo);
    /**
     * updateInfo
     * @param spuInfo
     */
    void saveSpuInfo(SpuInfo spuInfo);

    /**
     * spuInfo 삭제
     * @param SpuId
     */
    @Delete("delete from spu_info where id = #{spuId} ")
    void deleteSpuInfo(Integer SpuId);

    List<BaseAttrInfo> getAttrInfo(Integer category2Id);

    void insertAttrInfo(BaseAttrInfo attrInfo);
    void insertAttrValues(List<BaseAttrValue> attrValueList);
    void insertOneAttrValue(BaseAttrValue attrValue);

    @Update("update base_attr_value set value_name = #{valueName} where attr_id = #{attrId} and id = #{id} ")
    void updateAttrValue(BaseAttrValue attrValue);
    void deleteAttrValues(Map<String, Object> params);
    @Delete("delete from base_attr_info where id = #{attrId}")
    void deleteAttrById(Integer attrId);

    @Delete("delete from base_attr_value where attr_id = #{attrId}")
    void deleteAttrValueByAttrId(Integer attrId);

    @Update("update base_attr_info set attr_name = #{attrName} where id = #{id}")
    void updateAttrInfo(BaseAttrInfo attrInfo);


    @Update("update spu_sale_attr set spu_id =#{spuId},base_attr_id = #{baseAttrId}, base_attr_name= #{baseAttrName} where id = #{id}")
    void updateSpuAttr(SpuSaleAttr spuSaleAttr);

    /**
     * spuSaleAttr 추가
     * @param spuSaleAttr
     */
    void insertSpuSaleAttr(SpuSaleAttr spuSaleAttr);

    /**
     * insert
     * @param spuSaleAttrValue
     */
    void insertSpuSaleAttrValue(SpuSaleAttrValue spuSaleAttrValue);

    /**
     * spuSaleAttrValueList insert
     * @param spuSaleAttrValueList
     */
    void insertSpuSaleAttrValueList(List<SpuSaleAttrValue> spuSaleAttrValueList);

    void deleteSpuSaleAttrValueList(List<SpuSaleAttrValue> spuSaleAttrValueList);
    /**
     *
     * @param spuSaleAttrValue
     */
    @Update("update spu_sale_attr_value set spu_id = #{spuId}, spu_sale_attr_id=#{spuSaleAttrId}, spu_attr_value_name= #{spuAttrValueName} where id = #{id}")
    void updateSpuSaleAttrValue(SpuSaleAttrValue spuSaleAttrValue);
    void deleteSpuSaleAttrList(List<SpuSaleAttr> spuSaleAttrList);
    /**
     * spuSaleAttrList spuId에 의해 전부 삭제
     * @param spuId
     */
    @Delete("delete from spu_sale_attr where spu_id = #{spuId}")
    void deleteSpuSaleAttrListBySpuId(Integer spuId);


    /**
     * sku---> spuAttrList
     * @param spuId
     * @return
     */
    List<SpuSaleAttr> getSpuSaleAttrList(Integer spuId);

    /**
     * spu관련 정보 가져오기
     * @param category2Id
     * @return
     */
    List<SpuInfo> getProductInfo(Integer category2Id);

    /**
     * attrValueList 가져오기
     * @param attrId
     * @return
     */
    @Select("select * from base_attr_value where attr_id= #{attrId}")
    List<BaseAttrValue> getBaseAttrValueByAttrId(Integer attrId);
}
