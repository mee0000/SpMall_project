package com.example.spmall.adminService.impl;

import com.example.spmall.mapper.CategoryMapper;
import com.example.spmall.pojo.Category1;
import com.example.spmall.pojo.Category2;
import com.example.spmall.adminService.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * ProductServiceImpl
 *
 * @author toy aa@nfda
 * @version 2023/10/11 10:48 PM
 * @since JDK11
 **/
@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 카테고리 리스트 확인
     * @return
     */
    @Override
    public List<Category1> getCategoryList() {
        return categoryMapper.baseCategoryList();
    }

    /**
     * 카테고리 추가
     * @param category1
     */
    @Override
    public void addCategory1(Category1 category1) throws Exception{

    }

    /**
     * 카테고리 삭제
     * @param category1Id
     */
    @Override
    @Transactional(rollbackFor = Exception.class) //모든 오류 발생시 rollback
    public void deleteCategory(Integer category1Id) throws Exception{

        try {
            categoryMapper.deleteCategory1(category1Id);// 카테고리 1 삭제
            categoryMapper.deleteCategory2ByCategory1Id(category1Id); //카테고리1 인 카테고리 2 삭제
        } finally {
            log.info("카테고리 1,2 삭제 발생");
        }

    }

    /**
     * 카테고리 1 수정
     * @param category1
     */
    @Override
    public void updateCategory(Category1 category1) {
        categoryMapper.updateCategory1(category1);
    }

    /**
     * 카테고리1 추가
     * @param category1Id
     * @return
     */
    @Override
    public List<Category2> getCategory2List(Integer category1Id) {
        return categoryMapper.selectCategory2ByCategory1Id(category1Id);
    }


    /**
     * 카테고리2 추가
     * @param category2
     */
    @Override
    public void addCategory2(Category2 category2) {
        categoryMapper.insertCategory2(category2);
    }

    /**
     * 카테고리2 삭제
     * @param category1Id
     */
    @Override
    public void deleteCategory2List(Integer category1Id) {
        categoryMapper.deleteCategory2ByCategory1Id(category1Id);
    }

    /**
     * 카테고리2 수정
     * @param category2
     */
    @Override
    public void updateCategory2(Category2 category2) {
        categoryMapper.updateCategory2(category2);
    }


}
