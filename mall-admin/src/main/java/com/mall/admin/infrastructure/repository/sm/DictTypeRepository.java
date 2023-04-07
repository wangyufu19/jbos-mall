package com.mall.admin.infrastructure.repository.sm;

import com.mall.admin.domain.entity.sm.DictType;
import com.mall.admin.infrastructure.repository.sm.mapper.DictTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * DictTypeRepository
 * @author youfu.wang
 * @date 2020-06-24
 */
@Component
public class DictTypeRepository{
    @Autowired
    private DictTypeMapper dictTypeMapper;

    /**
     * 得到字典类型数据
     * @return
     */
    public List<DictType> getDictTypeList(Map<String, String> params){
        return dictTypeMapper.getDictTypeList(params);
    }
}
