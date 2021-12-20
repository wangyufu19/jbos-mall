package com.mall.product.infrastructure.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.product.domain.entity.ProductPic;

import java.util.List;
import java.util.Map;

/**
 * ProductPic
 * @author youfu.wang
 * @date 2021-08-19
 */
public interface ProductPicMapper extends BaseMapper<ProductPic> {


    public List<ProductPic> getProductMainPicList(Map<String, Object> parameterObject);
}
