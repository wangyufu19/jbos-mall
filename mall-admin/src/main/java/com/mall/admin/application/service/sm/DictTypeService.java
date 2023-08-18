package com.mall.admin.application.service.sm;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.admin.domain.entity.sm.DictType;
import com.mall.admin.infrastructure.repository.sm.DictTypeRepository;
import com.mall.admin.infrastructure.repository.sm.mapper.DictTypeMapper;
import com.mall.common.page.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * DictTypeService
 *
 * @author youfu.wang
 * @date 2020-07-22
 */
@Service
public class DictTypeService extends ServiceImpl<DictTypeMapper, DictType> implements IService<DictType> {
    /**
     * DictTypeRepository
     */
    @Autowired
    private DictTypeRepository dictTypeRepository;

    /**
     * 得到字典类型数据
     * @param pageParam
     * @param params
     * @return list
     */
    public List<DictType> getDictTypeList(PageParam pageParam, Map<String, Object> params) {
        return dictTypeRepository.getDictTypeList(pageParam, params);
    }
}
