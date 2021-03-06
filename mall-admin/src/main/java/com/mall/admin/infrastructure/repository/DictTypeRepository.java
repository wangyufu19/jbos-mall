package com.mall.admin.infrastructure.repository;

import com.mall.admin.domain.entity.DictType;
import com.mall.admin.infrastructure.repository.mapper.DictTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * DictTypeRepository
 * @author youfu.wang
 * @date 2020-06-24
 */
@Component
public class DictTypeRepository {
    @Autowired
    private DictTypeMapper dictTypeMapper;

    /**
     * 得到字典类型数据
     * @return
     */
    public List<DictType> getDictTypeList(){
        return dictTypeMapper.getDictTypeList();
    }
}
