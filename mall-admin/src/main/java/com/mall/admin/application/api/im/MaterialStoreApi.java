package com.mall.admin.application.api.im;

import com.mall.admin.application.service.im.MaterialStoreService;
import com.mall.admin.domain.entity.im.MaterialStore;
import com.mall.common.page.PageParam;
import com.mall.common.response.ResponsePageResult;
import com.mall.common.response.ResponseResult;
import com.mall.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * MaterialStoreApi
 *
 * @author youfu.wang
 * @date 2023/7/17
 **/
@RestController
@RequestMapping("/material/store")
@Slf4j
@Api(tags = "物品库存接口")
public class MaterialStoreApi {
    @Autowired
    private MaterialStoreService materialStoreService;


    @ResponseBody
    @GetMapping("/sumList")
    @ApiOperation("查询物品库存汇总列表")
    public ResponsePageResult getMaterialStoreSumList(@RequestParam Map<String, Object> params) {
        ResponsePageResult res = ResponsePageResult.ok();
        try {
            PageParam pageParam = PageParam.getPageParam(params);
            List<MaterialStore> materialStoreList = materialStoreService.getMaterialStoreSumList(pageParam, params);
            res.isPage(true).setData(materialStoreList);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponsePageResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    @ResponseBody
    @GetMapping("/list")
    @ApiOperation("查询物品库存明细列表")
    public ResponseResult getMaterialStoreList(@RequestParam Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        try {
            List<MaterialStore> materialStoreList = materialStoreService.getMaterialStoreList(params);
            res.setData(materialStoreList);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    @ResponseBody
    @GetMapping("/getMaterialStoreSurplusAmt")
    @ApiOperation("根据物品Id查询物品库存剩余数量")
    public ResponseResult getMaterialStoreSurplusAmt(@RequestParam Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        String materialId = StringUtils.replaceNull(params.get("materialId"));
        try {
            double surplusAmt = materialStoreService.getMaterialStoreSurplusAmt(materialId);
            res.setData(surplusAmt);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

}
