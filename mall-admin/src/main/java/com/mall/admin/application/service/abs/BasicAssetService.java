package com.mall.admin.application.service.abs;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.admin.domain.entity.abs.BasicAsset;
import com.mall.common.page.PageParam;
import com.mall.common.response.ResponsePageResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * BasicAssetService
 *
 * @author youfu.wang
 * @date 2023/12/22 14:43
 */
public interface BasicAssetService extends IService<BasicAsset> {

    /**
     * 判断合法文件
     *
     * @param file
     * @return true/false
     */
    boolean includeExtensions(MultipartFile file);
    /**
     * 查询基础资产数据
     *
     * @param pageParam
     * @param parameterObject
     * @return List
     */
    ResponsePageResult getBasicAssetList(PageParam pageParam, Map<String, Object> parameterObject);
    /**
     * 入池
     *
     * @param file
     */
    void inPool(MultipartFile file);
    /**
     * 出池
     *
     * @param id
     * @param acctNo
     */
    void outPool(String id, String acctNo);
}
