package com.mall.admin.application.service;

import com.mall.admin.domain.entity.DictType;
import com.mall.admin.infrastructure.repository.DictTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * DictTypeService
 * @author youfu.wang
 * @date 2020-07-22
 */
@Service
public class DictTypeService {
    @Autowired
    private DictTypeRepository dictTypeRepository;

    /**
     * 得到字典类型数据
     * @return
     */
    public List<DictType> getDictTypeList(){
        return dictTypeRepository.getDictTypeList();
    }
}
