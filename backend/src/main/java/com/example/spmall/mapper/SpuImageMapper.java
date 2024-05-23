package com.example.spmall.mapper;

import com.example.spmall.pojo.SpuImage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * SpuImageMapper
 *
 * @author toy aa@nfda
 * @version 2024/01/12 8:46 PM
 * @since JDK11
 **/
@Mapper
public interface SpuImageMapper {
    /**
     * insert imageList
     * @param spuImageList
     */
    void insertSpuImages(List<SpuImage> spuImageList);

    /**
     * update imageList
     * @param spuImageList
     */
    void deleteSpuImages(List<SpuImage> spuImageList);

    /**
     * spuId 에 의해 이미지 가져오기
     * @param spuId
     * @return
     */
    @Select("select id, spu_id, img_name, img_url from spu_image where spu_id = #{spuId}")
    List<SpuImage> getSpuImagesBySpuId(Integer spuId);

}
