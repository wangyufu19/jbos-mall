package com.mall.admin.infrastructure.repository.mapper;

import com.mall.admin.domain.entity.DictCode;

import java.util.List;
import java.util.Map;

/**
 * DictCodeMapper
 * @author youfu.wang
 * @date 2020-07-22
 */
public interface DictCodeMapper {
    /**
     * 得到字典码值数据
     * @param parameterObject
     * @return
     */
    public List<DictCode> getDictCodeList(Map<String, Object> parameterObject);

    /**
     * 删除业务字典
     * @param typeId
     */
    public void deleteDictCode(String typeId);

    /**
     * 保存业务字典
     * @param dictCodes
     */
    public void insertDictCode(List<DictCode> dictCodes);
}
