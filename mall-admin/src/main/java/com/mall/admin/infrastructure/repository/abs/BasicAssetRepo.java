package com.mall.admin.infrastructure.repository.abs;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
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

    /**
     * 得到基础资产池
     * @param pageParam
     * @param parameterObject
     * @return List
     */
    @Paging
    public List<BasicAsset> getBasicAssetList(PageParam pageParam, Map<String, Object> parameterObject) {
        return basicAssetMapper.getBasicAssetList(parameterObject);
    }

    /**
     * 新增基础资产池
     * @param basicAsset
     */
    public void addBasicAsset(BasicAsset basicAsset) {
        basicAssetMapper.insert(basicAsset);
    }

    /**
     * 删除基础资产池
     * @param acctNo
     */
    public void deleteBasicAsset(String acctNo) {
        UpdateWrapper<BasicAsset> updateWrapper = new UpdateWrapper<BasicAsset>();
        updateWrapper.eq("acct_no", acctNo);
        basicAssetMapper.delete(updateWrapper);
    }
}
