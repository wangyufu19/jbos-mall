package com.mall.admin.infrastructure.repository.abs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.admin.domain.entity.abs.BasicAsset;

import java.util.List;
import java.util.Map;

/**
 * BasicAssetMapper
 *
 * @author youfu.wang
 * @date 2023/8/4
 **/
public interface BasicAssetMapper extends BaseMapper<BasicAsset> {
    /**
     * 得到基础资产池
     * @param parameterObject
     * @return List
     */
    List<BasicAsset> getBasicAssetList(Map<String, Object> parameterObject);
}
