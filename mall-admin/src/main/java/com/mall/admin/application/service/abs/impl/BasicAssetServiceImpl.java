package com.mall.admin.application.service.abs.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.admin.application.service.abs.BasicAssetService;
import com.mall.admin.domain.entity.abs.BasicAsset;
import com.mall.admin.infrastructure.repository.abs.BasicAssetRepo;
import com.mall.admin.infrastructure.repository.abs.mapper.BasicAssetMapper;
import com.mall.common.page.PageParam;
import com.mall.common.response.ResponsePageResult;
import com.mall.common.utils.ExcelUtils;
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
        return ResponsePageResult.ok().isPage(true).setData(basicAssetList);
    }

    /**
     * 入池
     *
     * @param file
     */
    @Transactional
    public void inPool(MultipartFile file) {
        try (InputStream is = file.getInputStream()) {
            List<BasicAsset> basicAssetList = ExcelUtils.readToList(is, BasicAsset.class);
            this.saveBatch(basicAssetList);
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
