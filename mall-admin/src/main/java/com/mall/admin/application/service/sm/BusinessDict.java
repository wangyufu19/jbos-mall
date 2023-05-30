package com.mall.admin.application.service.sm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BusinessDict
 * @author youfu.wang
 * @date 2020-07-23
 */
@Slf4j
@Service
public class BusinessDict {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static Map<String,Object> dictCodeMaps= new HashMap<String,Object>();
    /**
     * 初始化业务字典
     */
    @PostConstruct
    public void init(){
        String sql="";
        //查询业务字典类型
        sql="SELECT TYPEID,TYPENAME FROM JBOS_DICT_TYPE";
        List<Map<String, Object>> dictTypes=this.jdbcTemplate.queryForList(sql);
        if(null==dictTypes){
            return;
        }
        //查询业务字典码值
        for(int i=0;i<dictTypes.size();i++){
            Map<String,Object> dictType=dictTypes.get(i);
            sql="SELECT DICTID,DICTNAME FROM JBOS_DICT_CODE WHERE TYPEID=? ORDER BY ORDERNO";
            List<Map<String, Object>> dictCodes=this.jdbcTemplate.queryForList(sql,dictType.get("TYPEID"));
            dictCodeMaps.put(String.valueOf(dictType.get("TYPEID")),dictCodes);
        }
    }

    /**
     * 刷新业务字典缓存
     */
    public void refresh(){
        dictCodeMaps.clear();
        init();
    }
    /**
     * 得到业务字典列表
     * @param typeId
     * @return
     */
    public List<Map<String, Object>> getDictCodeList(String typeId){
        if(dictCodeMaps.containsKey(typeId)){
            return (List<Map<String, Object>>) dictCodeMaps.get(typeId);
        }else{
            String sql="";
            sql="SELECT DICTID,DICTNAME FROM JBOS_DICT_CODE WHERE TYPEID=? ORDER BY ORDERNO";
            List<Map<String, Object>> dictCodes=this.jdbcTemplate.queryForList(sql,typeId);
            dictCodeMaps.put(typeId,dictCodes);
            return dictCodes;
        }
    }
    /**
     * 得到业务字典码值
     * @param typeId
     * @return
     */
    public String getDictValue(String typeId,String dictId){
        String dictValue="";
        if(dictCodeMaps.containsKey(typeId)){
            List<Map<String, Object>> dictCodes=(List<Map<String, Object>>) dictCodeMaps.get(typeId);
            if(null!=dictCodes){
                for(Map<String, Object> dictCode:dictCodes){
                    if(dictCode.containsValue(dictId)){
                        dictValue=String.valueOf(dictCode.get("DICTNAME"));
                        break;
                    }
                }
            }
        }
        return dictValue;
    }
}
