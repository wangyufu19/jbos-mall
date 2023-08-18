package com.mall.admin.application.api.sm;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mall.admin.application.service.sm.BusinessDict;
import com.mall.admin.application.service.sm.DictCodeService;
import com.mall.admin.application.service.sm.DictTypeService;
import com.mall.admin.domain.entity.sm.DictCode;
import com.mall.admin.domain.entity.sm.DictType;
import com.mall.common.page.PageParam;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import com.mall.common.response.ResponseResult;
import com.mall.common.utils.StringUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * DictMgrApi
 *
 * @author youfu.wang
 * @date 2019-01-31
 */
@RestController
@RequestMapping("/dict")
@Slf4j
@Api("字典管理接口")
public class DictMgrApi {
    /**
     * BusinessDict
     */
    @Autowired
    private BusinessDict businessDict;
    @Autowired
    private DictTypeService dictTypeService;
    @Autowired
    private DictCodeService dictCodeService;

    /**
     * 得到缓存业务字典数据
     *
     * @param params
     * @return ResponseResult
     */
    @ResponseBody
    @RequestMapping("/getCacheDictCodeList")
    @ApiOperation("得到缓存业务字典数据")
    public ResponseResult getCacheDictCodeList(@RequestParam Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        String typeId = StringUtils.replaceNull(params.get("typeId"));
        try {
            List<Map<String, Object>> dictCodes = businessDict.getDictCodeList(typeId);
            res.setData(dictCodes);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    /**
     * 得到字典类型数据
     *
     * @param params
     * @return ResponseResult
     */
    @ResponseBody
    @RequestMapping("/getDictTypeList")
    @ApiOperation("得到字典类型列表")
    public ResponseResult getDictTypeList(@RequestParam Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        try {
            PageParam pageParam = PageParam.getPageParam(params);
            List<DictType> dictTypes = dictTypeService.getDictTypeList(pageParam, params);
            res.isPage(true).setData(dictTypes);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    /**
     * 得到字典码值数据列表
     *
     * @param params
     * @return ResponseResult
     */
    @ResponseBody
    @RequestMapping("/getDictCodeList")
    @ApiOperation("得到字典码值数据列表")
    public ResponseResult getDictCodeList(@RequestParam Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        try {
            List<DictCode> dictCodes = dictCodeService.getDictCodeList(params);
            res.setData(dictCodes);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    /**
     * 新增字典类型
     *
     * @param params
     * @return ResponseResult
     */
    @ResponseBody
    @RequestMapping(value = "/addDictType", method = RequestMethod.POST)
    @ApiOperation("新增字典类型")
    public ResponseResult addDictType(@RequestBody Map<String, String> params) {
        ResponseResult res = ResponseResult.ok();
        try {
            DictType dictType = new DictType();
            dictType.setTypeId(params.get("typeId"));
            dictType.setTypeName(params.get("typeName"));
            dictTypeService.save(dictType);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    /**
     * 更新字典类型
     *
     * @param params
     * @return ResponseResult
     */
    @ResponseBody
    @RequestMapping(value = "/updateDictType", method = RequestMethod.POST)
    @ApiOperation("更新字典类型")
    public ResponseResult updateDictType(@RequestBody Map<String, String> params) {
        ResponseResult res = ResponseResult.ok();
        try {
            DictType dictType = new DictType();
            dictType.setTypeId(params.get("typeId"));
            dictType.setTypeName(params.get("typeName"));
            UpdateWrapper<DictType> updateWrapper = new UpdateWrapper<DictType>();
            updateWrapper.eq("typeid", dictType.getTypeId());
            dictTypeService.update(dictType, updateWrapper);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    /**
     * 刷新业务字典缓存
     *
     * @param params
     * @return ResponseResult
     */
    @ResponseBody
    @RequestMapping(value = "/refresh", method = RequestMethod.POST)
    @ApiOperation("刷新业务字典缓存")
    public ResponseResult refresh(@RequestBody Map<String, String> params) {
        ResponseResult res = ResponseResult.ok();
        try {
            this.businessDict.refresh();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    /**
     * 删除字典类型
     *
     * @param params
     * @return ResponseResult
     */
    @ResponseBody
    @RequestMapping(value = "/deleteDictType", method = RequestMethod.POST)
    @ApiOperation("删除字典类型")
    public ResponseResult deleteDictType(@RequestBody Map<String, String> params) {
        ResponseResult res = ResponseResult.ok();
        try {
            DictType dictType = new DictType();
            dictType.setTypeId(params.get("typeId"));
            dictType.setTypeName(params.get("typeName"));
            UpdateWrapper<DictType> updateWrapper = new UpdateWrapper<DictType>();
            updateWrapper.eq("typeid", dictType.getTypeId());
            dictTypeService.remove(updateWrapper);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    /**
     * 保存业务字典
     *
     * @param params
     * @return ResponseResult
     */
    @ResponseBody
    @RequestMapping(value = "/saveDictCode", method = RequestMethod.POST)
    @ApiOperation("保存业务字典")
    public ResponseResult saveDictCode(@RequestBody Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        try {
            dictCodeService.saveDictCode(params);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
}
