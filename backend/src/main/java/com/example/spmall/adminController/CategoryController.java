package com.example.spmall.adminController;

import com.example.spmall.pojo.Category1;
import com.example.spmall.pojo.Category2;
import com.example.spmall.pojo.Result;
import com.example.spmall.adminService.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ProductController
 *
 * @author toy aa@nfda
 * @version 2023/10/12 1:04 AM
 * @since JDK11
 **/
@Slf4j
@RestController
@RequestMapping("/admin-api/admin")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 리서치 카테고리
     * @return
     */
    @GetMapping("/getBaseCategoryList")
    public Result getCategoryList()  {
        log.info("getCategoryList");
        return Result.success(categoryService.getCategoryList());
    }

    /**
     * 카테고리 추가
     */
    @PostMapping("/addCategory")
    public Result addCategory1(@RequestBody Category1 category1) throws Exception{
        log.info("category1Name: {}", category1);
        categoryService.addCategory1(category1);
        return Result.success();
    }
    /**
     * 카테고리 삭제
     */
    @DeleteMapping("/deleteCategory/{id}")
    public Result deleteCategory(@PathVariable Integer id) throws  Exception{
        categoryService.deleteCategory(id);
        return Result.success();
    }

    /**
     * 카테고리 1 업데이트
     * @param category1
     * @return
     */
    @PutMapping("/updateCategory")
    public Result updateCategory(@RequestBody Category1 category1) {
        log.info("updateCategory:{}",category1);
        categoryService.updateCategory(category1);
        return Result.success();
    }

    /**
     * 카테고리 1로 카테고리2 찾기
     * @param category1Id
     * @return
     */
    @GetMapping("/getCategory2/{category1Id}")
    public Result getCategory2List(@PathVariable Integer category1Id) {
        log.info("category1Id: {}", category1Id);
        List<Category2> data = categoryService.getCategory2List(category1Id);
        return Result.success(data);
    }

    /**
     * 카테고리2 추가
     */
    @PostMapping("/addCategory2")
    public Result addCategory2(Category2 category2) {
        categoryService.addCategory2(category2);
        return Result.success();
    }

    /**
     * 카테고리 2 업데이트
     * @param category2
     * @return
     */
    @PutMapping("/updateCategory2")
    public Result updateCategory2(@RequestBody Category2 category2) {
        log.info("category:{}",category2);
        categoryService.updateCategory2(category2);
        return Result.success();
    }



}
