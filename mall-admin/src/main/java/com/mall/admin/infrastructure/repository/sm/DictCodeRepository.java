package com.mall.admin.infrastructure.repository.sm;

import com.mall.admin.domain.entity.sm.DictCode;
import com.mall.admin.infrastructure.repository.sm.mapper.DictCodeMapper;
import com.mall.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * DictCodeRepository
 * @author youfu.wang
 * @date 2020-06-24
 */
@Component
public class DictCodeRepository {
    @Autowired
    private DictCodeMapper dictCodeMapper;
    /**
     * 得到字典码值数据
     * @return
     */
    public List<DictCode> getDictCodeList(Map<String, Object> parameterObject){
        return dictCodeMapper.getDictCodeList(parameterObject);
    }
    /**
     * 保存业务字典
     * @param parameterObject
     */
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
    public void saveDictCode(Map<String, Object> parameterObject){
        String typeId= StringUtils.replaceNull(parameterObject.get("typeId"));
        //删除业务字典
        dictCodeMapper.deleteDictCode(typeId);
        //保存业务字典
        ArrayList dictCodes=(ArrayList)parameterObject.get("dictCodes");
        if (dictCodes!=null&&dictCodes.size()>0){
            List<DictCode> list=new ArrayList<DictCode>();
            for(Object obj:dictCodes){
                Map<String,Object> dictCodeMap=(Map<String,Object>)obj;
                DictCode dictCode=new DictCode();
                dictCode.setTypeId(typeId);
                dictCode.setDictId(StringUtils.replaceNull(dictCodeMap.get("dictId")));
                dictCode.setDictName(StringUtils.replaceNull(dictCodeMap.get("dictName")));
                dictCode.setOrderNo(Integer.parseInt(StringUtils.replaceNull(dictCodeMap.get("orderNo"))));
                list.add(dictCode);
            }
            dictCodeMapper.insertDictCode(list);
        }
    }
}

