package com.mall.admin.application.service.abs.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.admin.application.service.abs.BasicAssetService;
import com.mall.admin.domain.entity.abs.BasicAsset;
import com.mall.admin.infrastructure.repository.abs.BasicAssetRepo;
import com.mall.admin.infrastructure.repository.abs.mapper.BasicAssetMapper;
import com.mall.common.page.PageParam;
import com.mall.common.response.ResponsePageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * BasicAssetServiceImpl
 *
 * @author youfu.wang
 * @date 2023/8/4
 **/
@Service
@Slf4j
public class BasicAssetServiceImpl extends ServiceImpl<BasicAssetMapper, BasicAsset> implements BasicAssetService {
    /**
     * BasicAssetRepo
     */
    @Autowired
    private BasicAssetRepo basicAssetRepo;

    /**
     * 判断合法文件
     *
     * @param file
     * @return true/false
     */
    public boolean includeExtensions(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            return false;
        }
        return originalFilename.contains(".xls") || originalFilename.contains(".xlsx");
    }

    /**
     * 查询基础资产数据
     *
     * @param pageParam
     * @param parameterObject
     * @return List
     */
    public ResponsePageResult getBasicAssetList(PageParam pageParam, Map<String, Object> parameterObject) {
        List<BasicAsset> basicAssetList = basicAssetRepo.getBasicAssetList(pageParam, parameterObject);
        return ResponsePageResult.ok().setData(basicAssetList);
    }

    /**
     * 入池
     *
     * @param file
     */
    @Transactional
    public void inPool(MultipartFile file) {
        try (InputStream in = file.getInputStream()) {
            EasyExcel.read(in, BasicAsset.class, new PageReadListener<BasicAsset>(dataList -> {
                this.saveBatch(dataList);
            }, 10000)).sheet().doRead();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 出池
     *
     * @param id
     * @param acctNo
     */
    public void outPool(String id, String acctNo) {
        basicAssetRepo.deleteBasicAsset(acctNo);
    }
}
