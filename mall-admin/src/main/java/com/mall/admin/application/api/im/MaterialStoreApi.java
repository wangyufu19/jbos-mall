package com.mall.admin.application.api.im;

import com.mall.admin.application.service.im.MaterialStoreService;
import com.mall.admin.domain.entity.im.MaterialStore;
import com.mall.common.page.PageParam;
import com.mall.common.response.ResponseResult;
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
@Api("物品库存业务接口")
public class MaterialStoreApi {
    @Autowired
    private MaterialStoreService materialStoreService;




    @ResponseBody
    @GetMapping("/sumList")
    @ApiOperation("查询物品库存汇总列表")
    public ResponseResult getMaterialStoreSumList(@RequestParam Map<String, Object> params) {
        ResponseResult res=ResponseResult.ok();
        try {
            PageParam pageParam = PageParam.getPageParam(params);
            List<MaterialStore> materialStoreList = materialStoreService.getMaterialStoreSumList(pageParam, params);
            res.isPage(true).data(materialStoreList);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    @ResponseBody
    @GetMapping("/list")
    @ApiOperation("查询物品库存明细列表")
    public ResponseResult getMaterialStoreList(@RequestParam Map<String, Object> params) {
        ResponseResult res=ResponseResult.ok();
        try {
            List<MaterialStore> materialStoreList = materialStoreService.getMaterialStoreList(params);
            res.data(materialStoreList);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
}
