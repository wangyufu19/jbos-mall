package com.mall.admin.infrastructure.repository.abs;

import com.mall.admin.domain.entity.abs.BasicAsset;
import com.mall.admin.infrastructure.repository.abs.mapper.BasicAssetMapper;
import com.mall.common.page.PageParam;
import com.mall.common.page.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * BasicAssetRepo
 *
 * @author youfu.wang
 * @date 2023/8/4
 **/
@Component
public class BasicAssetRepo {
    @Autowired
    private BasicAssetMapper basicAssetMapper;
    @Paging
    public List<BasicAsset> getBasicAssetList(PageParam pageParam, Map<String,Object> parameterObject){
        return basicAssetMapper.getBasicAssetList(parameterObject);
    }
    public void addBasicAsset(BasicAsset basicAsset){
        basicAssetMapper.insert(basicAsset);
    }
}
