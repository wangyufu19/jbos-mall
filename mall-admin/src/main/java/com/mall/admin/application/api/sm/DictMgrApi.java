package com.mall.admin.application.api.sm;

import com.mall.admin.application.api.BaseApi;
import com.mall.admin.application.service.sm.BusinessDict;
import com.mall.admin.application.service.sm.DictCodeService;
import com.mall.admin.application.service.sm.DictTypeService;
import com.mall.admin.domain.entity.sm.DictCode;
import com.mall.admin.domain.entity.sm.DictType;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mall.common.response.ResponseResult;
import com.mall.common.utils.StringUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
/**
 * DictMgrApi
 * @author youfu.wang
 * @date 2019-01-31
 */
@RestController
@RequestMapping("/dict")
@Slf4j
@Api("字典管理接口")
public class DictMgrApi extends BaseApi {
    @Autowired
    private BusinessDict businessDict;
    @Autowired
    private DictTypeService dictTypeService;
    @Autowired
    private DictCodeService dictCodeService;

    /**
     * 得到缓存业务字典数据
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping("/getCacheDictCodeList")
    @ApiOperation("得到缓存业务字典数据")
    public ResponseResult getCacheDictCodeList(@RequestParam Map<String, Object> params){
        ResponseResult res= ResponseResult.ok();
        String typeId= StringUtils.replaceNull(params.get("typeId"));
        try{
            List<Map<String, Object>> dictCodes=businessDict.getDictCodeList(typeId);
            res.setData(dictCodes);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
    /**
     * 得到字典类型数据
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping("/getDictTypeList")
    @ApiOperation("得到字典类型列表")
    public ResponseResult getDictTypeList(@RequestParam Map<String, Object> params){
        ResponseResult res= ResponseResult.ok();
        try{
            List<DictType> dictTypes=dictTypeService.getDictTypeList();
            res.setData(dictTypes);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
    @ResponseBody
    @RequestMapping("/getDictCodeList")
    @ApiOperation("得到字典码值数据列表")
    public ResponseResult getDictCodeList(@RequestParam Map<String, Object> params){
        ResponseResult res= ResponseResult.ok();
        try{
            List<DictCode> dictCodes=dictCodeService.getDictCodeList(params);
            res.setData(dictCodes);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    /**
     * 保存业务字典
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/saveDictCode", method = RequestMethod.POST)
    @ApiOperation("保存业务字典")
    public ResponseResult saveDictCode(@RequestBody Map<String, Object> params){
        ResponseResult res= ResponseResult.ok();
        try{
            dictCodeService.saveDictCode(params);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
}
