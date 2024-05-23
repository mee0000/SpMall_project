package com.example.spmall.mapper;

import com.example.spmall.pojo.Category1;
import com.example.spmall.pojo.Category2;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * category1
 *
 * @author toy aa@nfda
 * @version 2023/10/11 10:39 PM
 * @since JDK11
 **/
@Mapper
@Repository
public interface CategoryMapper {
    /**
     * categoryList -(category1 + category2)
     * @return
     */
    List<Category1> baseCategoryList();

    @Delete("delete from base_category where id = #{category1Id} ")
    void deleteCategory1(Integer category1Id);

    void insertCategory1(Category1 category1);

    @Update("update base_category set category_name = #{categoryName} where id = #{id}")
    void updateCategory1(Category1 category1);

    List<Category2> selectCategory2ByCategory1Id(Integer category1Id);

    @Select("select id, category_name, category1_id from base_category2 where category1_id =#{category1Id} and id = #{category2Id}")
    Category2 getCategory2(Integer category1Id, Integer category2Id);

    @Delete("delete  from base_category2 where category1_id = #{category1Id}")
    void deleteCategory2ByCategory1Id(Integer category1Id);

    void deleteCategory2ListByIds(List<Category2> category2List);

    void insertCategory2(Category2 category2);

    void updateCategory2(Category2 category2);
}
