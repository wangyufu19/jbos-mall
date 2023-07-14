package com.mall.admin.infrastructure.repository.im.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.admin.domain.entity.im.MaterialStore;

import java.util.List;

/**
 * MaterialStoreMapper
 *
 * @author youfu.wang
 * @date 2023/7/14
 **/
public interface MaterialStoreMapper extends BaseMapper<MaterialStore> {

    public void addMaterialStore(List<MaterialStore> materialStoreList);
}
