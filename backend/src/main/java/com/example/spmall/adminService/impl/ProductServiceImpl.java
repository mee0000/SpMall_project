package com.example.spmall.adminService.impl;


import com.example.spmall.mapper.CategoryMapper;
import com.example.spmall.mapper.SkuInfoMapper;
import com.example.spmall.mapper.SpuImageMapper;
import com.example.spmall.mapper.SpuInfoMapper;
import com.example.spmall.pojo.*;
import com.example.spmall.adminService.ProductService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

/**
 * ProductServiceImpl
 *
 * @author toy aa@nfda
 * @version 2023/10/16 6:17 PM
 * @since JDK11
 **/
@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private SpuInfoMapper spuInfoMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private SkuInfoMapper skuInfoMapper;
    @Autowired
    private SpuImageMapper spuImageMapper;

    private static final String UPLOAD_PATH = "/Users/meiyingzheng/Downloads/tmp";

    @Override
    public List<BaseAttrInfo> getBaseAttrInfo(Integer category1Id, Integer category2Id) {
        List<BaseAttrInfo> baseAttrInfoList = new ArrayList<>();
        if(categoryMapper.getCategory2(category1Id, category2Id) != null) {
           baseAttrInfoList = spuInfoMapper.getAttrInfo(category2Id);
        }
        return baseAttrInfoList;
    }

    @Override
    public PageBean getSpuInfoList(Integer page, Integer pageSize, Integer category2Id) {
       // 1.page
        PageHelper.startPage(page, pageSize);

        // 2.리서치 시작
        List<SpuInfo> spuInfoList =  spuInfoMapper.getSpuInfo(category2Id);
        Page<SpuInfo> pageInfo = (Page<SpuInfo>) spuInfoList;
        log.info("pageList", pageInfo);
        PageBean pageBean = new PageBean(pageInfo.getTotal(), pageInfo.getResult());
        return pageBean;
    }

    @Override
    public void saveBaseAttrInfo(BaseAttrInfo attrInfo) {

        spuInfoMapper.insertAttrInfo(attrInfo);
        log.info("attrInfo:{}", attrInfo);
        saveAndUpdateAttrValueList(attrInfo);
    }

    @Override
    public void updateBaseAttrInfo(BaseAttrInfo attrInfo) {
        spuInfoMapper.updateAttrInfo(attrInfo);
        saveAndUpdateAttrValueList(attrInfo);
    }

    /**
     * attrValue -> insert and update
     * @param attrInfo
     */
    public void saveAndUpdateAttrValueList(BaseAttrInfo attrInfo) {
        log.info("attrInfo:{}", attrInfo);
        List<BaseAttrValue> attrValueList = attrInfo.getAttrValueList();
        List<String> values = new ArrayList<>();
        if(attrValueList.size() > 0 ) {
            for(BaseAttrValue attrValue: attrValueList){
                attrValue.setAttrId(attrInfo.getId());
                values.add(attrValue.getValueName());
                spuInfoMapper.insertOneAttrValue(attrValue);
            }
        }

        log.info("values: {}", values);
        Map<String, Object> params = new HashMap<>();
        params.put("attrId", attrInfo.getId());
        params.put("values", values);
        spuInfoMapper.deleteAttrValues(params);
    }
    @Override
    @Transactional(rollbackFor = Exception.class) //모든 오류 발생시 rollback
    public void deleteAttrById(Integer attrId) throws Exception {
        log.info("deleteAttrId:{}", attrId);
        try {
          spuInfoMapper.deleteAttrById(attrId);
          spuInfoMapper.deleteAttrValueByAttrId(attrId);
        } finally {
            log.info("속성 삭제시 속성값도 삭제 발생");
        }
    }

    @Override
    public List<BaseSaleAttr> getBaseSaleAttrList() {
        return spuInfoMapper.getBaseSaleAttrList();
    }

    @Override
    public SpuInfo getSpuInfoById(Integer spuId) {
        SpuInfo spuInfo = spuInfoMapper.getSpuBySpuId(spuId);
        // spuId 에 의해 skuInfoList 가져오기
        List<SkuInfo> skuInfoList = skuInfoMapper.getSkuInfoList(spuId);
        if( skuInfoList.size() > 0 ) {
            spuInfo.setSkuInfoList(skuInfoList);
        }
        return spuInfo;
    }


    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
    public void updateSpuInfo(SpuInfo spuInfo) {
        spuInfoMapper.saveSpuInfo(spuInfo);
        try {
           saveSpuSaleAttr(spuInfo);
           saveSpuImageList(spuInfo.getSpuImageList());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveSpuImageList(List<SpuImage> imageList) {
        List<SpuImage> newImageList = new ArrayList<>();
        if(imageList == null || imageList.size() == 0) {
            // 이미지 삭제
            log.info("이미지 없음");
        } else {
            for(SpuImage image: imageList) {
                // id 갔고 있지 않으면 새로 추가
                if(image.getId() == 0) {
                    newImageList.add(image);
                }
            }
            spuImageMapper.deleteSpuImages(imageList);
            if (newImageList.size() > 0) {
                spuImageMapper.insertSpuImages(newImageList);
            }



        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
    public void insertSpuInfo(SpuInfo spuInfo) {
        spuInfoMapper.insertSpuInfo(spuInfo);
        insertSpuSaleAttr(spuInfo);
    }

    public void insertSpuSaleAttr(SpuInfo spuInfo) {
        List<SpuSaleAttr> spuSaleAttrList = spuInfo.getSpuSaleAttrList();
        if(spuSaleAttrList.size()> 0 ) {
            for(int i=0;i<spuSaleAttrList.size(); i++) {
                SpuSaleAttr spuSaleAttr = spuSaleAttrList.get(i);
                spuSaleAttr.setSpuId(spuInfo.getId());
                spuInfoMapper.insertSpuSaleAttr(spuSaleAttr);
                insertSpuSaleAttrValue(spuSaleAttr);
            }
        }
    }

    public void insertSpuSaleAttrValue(SpuSaleAttr spuSaleAttr) {
         log.info("spuSaleAttr:{}", spuSaleAttr);
         List<SpuSaleAttrValue> spuSaleAttrValueList = spuSaleAttr.getSpuSaleAttrValueList();
        for(SpuSaleAttrValue spuSaleAttrValue: spuSaleAttrValueList) {
            spuSaleAttrValue.setSpuSaleAttrId(spuSaleAttr.getId());
            spuSaleAttrValue.setSpuId(spuSaleAttr.getSpuId());
        }
         spuInfoMapper.insertSpuSaleAttrValueList(spuSaleAttrValueList);
    }
    /**
     * spuInfo -> spuSaleAttr 추가 삭제 업데이트 진행
     * @param spuInfo
     */
    public void saveSpuSaleAttr(SpuInfo spuInfo) throws Exception{
        Integer spuId = spuInfo.getId();
        List<SpuSaleAttr> spuSaleAttrList = spuInfo.getSpuSaleAttrList();
        if(spuSaleAttrList.size()>0) {
            for(int i=0;i<spuSaleAttrList.size(); i++) {
                SpuSaleAttr spuSaleAttr = spuSaleAttrList.get(i);
                if(spuSaleAttr.getId()!=null) {
                    spuInfoMapper.updateSpuAttr(spuSaleAttr);
                    // insert or update or delete SpuSaleAttrValue
                    saveSpuSaleAttrValues(spuSaleAttr);
                } else {
                    spuInfoMapper.insertSpuSaleAttr(spuSaleAttr);
                    // insertValues
                    insertSpuSaleAttrValue(spuSaleAttr);
                }
            }
            spuInfoMapper.deleteSpuSaleAttrList(spuSaleAttrList);

        } else {
            spuInfoMapper.deleteSpuSaleAttrListBySpuId(spuId);
        }

    }

    /**
     * spuInfo --> spuSaleAttr --> spuSaleAttrValue
     * @param spuSaleAttr
     */
    public void saveSpuSaleAttrValues(SpuSaleAttr spuSaleAttr) throws Exception{
        log.info("spuSaleAttr=============:{}", spuSaleAttr);
        List<SpuSaleAttrValue> spuSaleAttrValueList = spuSaleAttr.getSpuSaleAttrValueList();
        if (spuSaleAttrValueList.size() > 0) {
            for(int i=0; i< spuSaleAttrValueList.size();i++) {
                if(spuSaleAttrValueList.get(i).getId() != null) {
                    spuInfoMapper.updateSpuSaleAttrValue(spuSaleAttrValueList.get(i));
                } else {
                    spuSaleAttrValueList.get(i).setSpuId(spuSaleAttr.getSpuId());
                    spuSaleAttrValueList.get(i).setSpuSaleAttrId(spuSaleAttr.getId());
                    spuInfoMapper.insertSpuSaleAttrValue(spuSaleAttrValueList.get(i));
                }
            }
        }
        // 삭제하기
          log.info("spuSaleAttrValueList=============:{}", spuSaleAttr.getSpuSaleAttrValueList());
         spuInfoMapper.deleteSpuSaleAttrValueList(spuSaleAttrValueList);
    }

    /**
     * spuInfo 삭제
     * @param spuId
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class) //모든 오류 발생시 rollback
    public void deleteSpuInfoById(Integer spuId) throws Exception{
            spuInfoMapper.deleteSpuInfo(spuId);
        try {
            spuInfoMapper.deleteSpuSaleAttrListBySpuId(spuId);
        } finally {
            log.info("속성 삭제시 속성값도 삭제 발생");
        }

    }

    @Override
    public List<SpuSaleAttr> getSpuSaleAttrList(Integer spuId) {
        return spuInfoMapper.getSpuSaleAttrList(spuId);
    }

    @Override
    public void saveSkuInfo(SkuInfo skuInfo)  {
        skuInfo.setCreateTime(LocalDateTime.now());
        skuInfoMapper.insertSkuInfo(skuInfo);
        List<SkuAttr> skuAttrValList = skuInfo.getSkuAttrValueList();
        if(skuAttrValList.size() > 0 || skuAttrValList != null ) {
            for(int i=0; i<skuAttrValList.size(); i++) {
                skuAttrValList.get(i).setSkuId(skuInfo.getId());
            }

            skuInfoMapper.insertSkuAttrList(skuAttrValList);
        }
        List<SkuSaleAttr> skuSaleAttrList = skuInfo.getSkuSaleAttrValueList();
        if(skuSaleAttrList.size()> 0) {
            for(SkuSaleAttr skuSaleAttr: skuSaleAttrList) {
                skuSaleAttr.setSkuId(skuInfo.getId());
            }
        }
        log.info("skuAttrSSSSSSSValList:{}", skuSaleAttrList);
        skuInfoMapper.insertSkuSaleAttrList(skuSaleAttrList);
    }

    @Override
    public List<SkuInfo> getSkuInfoBySpuId(Integer spuId) {
        return skuInfoMapper.getSkuInfoList(spuId);
    }

    @Override
    public List<SpuImage> getSpuImageList(int spuId) {
        return spuImageMapper.getSpuImagesBySpuId(spuId);
    }

    @Override
    public PageBean getSkuInfoList(Integer page, Integer pageSize, Map<String, Object> params) {
        // 1.page
        PageHelper.startPage(page, pageSize);

        // 2.리서치 시작
        List<SkuInfo> skuInfoList =  skuInfoMapper.getAllSkuInfo(params);
        Page<SkuInfo> pageInfo = (Page<SkuInfo>) skuInfoList;
        log.info("pageList:{}", pageInfo);
        PageBean pageBean = new PageBean(pageInfo.getTotal(), pageInfo.getResult());
        return pageBean;
    }

    @Override
    public void updateSkuStatus(SkuInfo skuInfo) {
        skuInfoMapper.updateSkuStatus(skuInfo);
    }

    /**
     * 삭제......
     * @param skuId
     */
    @Override
    @Transactional(rollbackFor = Exception.class) //모든 오류 발생시 rollback
    public void deleteSkuById(Integer skuId) throws Exception{

        try {
            // skuInfo 삭제
            skuInfoMapper.deleteSkuById(skuId);
            // sku_attr삭제
            skuInfoMapper.deleteSkuAttrById(skuId);
            // sku_sale_attr 삭제
            skuInfoMapper.deleteSkuSaleAttrById(skuId);
        } finally {
            log.info("속성 삭제시 속성값도 삭제 발생");
        }

    }


    /**
     * skuInfo 업데이트 및 skuAttr ,skuSaleAttr 업데이트
     * @param skuInfo
     */
    @Override
    public void updateSkuInfo(SkuInfo skuInfo) {
        // skuInfo 업데이트
        skuInfoMapper.updateSkuInfo(skuInfo);
        // skuSaleAttr 업데이트
        updateSkuSaleAttr(skuInfo);
        // skuAttr 업데이트
        updateSkuAttr(skuInfo);
    }

    /**
     *insert 혹은 update
     * @param skuInfo
     */
    public void updateSkuSaleAttr(SkuInfo skuInfo) {
        List<SkuSaleAttr> skuSaleAttrList = skuInfo.getSkuSaleAttrValueList();
        for(SkuSaleAttr skuSaleAttr: skuSaleAttrList) {
            skuSaleAttr.setSkuId(skuInfo.getId());
            log.info("skuSaleAttr: {}", skuSaleAttr);
            skuInfoMapper.insertSkuSaleAttr(skuSaleAttr);
        }
    }

    /**
     * insert 혹은 update
     * @param skuInfo
     */
    public void updateSkuAttr(SkuInfo skuInfo) {
        List<SkuAttr> skuAttrList = skuInfo.getSkuAttrValueList();
        for(SkuAttr skuAttr: skuAttrList) {
            skuAttr.setSkuId(skuInfo.getId());
            skuInfoMapper.insertSkuAttr(skuAttr);
        }
    }


}
