package com.example.spmall.adminService;


import com.example.spmall.pojo.Category1;
import com.example.spmall.pojo.Category2;

import java.util.List;

/**
 * ProductService
 *
 * @author toy aa@nfda
 * @version 2023/10/11 10:47 PM
 * @since JDK11
 **/
public interface CategoryService {

    List<Category1> getCategoryList();

    void addCategory1(Category1 category1) throws Exception;

    void deleteCategory(Integer category1Id) throws Exception;

    void updateCategory(Category1 category1);

    List<Category2> getCategory2List(Integer category1Id);

    /**
     * 카테고리 2ID 로 삭제하기
     * @param category2Id
     */
    void deleteCategory2List(Integer category2Id);

    /**
     * 카테고리2 업데이트
     * @param category2
     */
    void updateCategory2(Category2 category2);

    void addCategory2(Category2 category2);
}
