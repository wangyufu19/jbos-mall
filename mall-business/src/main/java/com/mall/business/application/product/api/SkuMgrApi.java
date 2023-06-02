package com.mall.business.application.product.api;

import com.mall.common.response.ResponseResult;
import com.mall.business.application.product.service.SkuService;
import com.mall.business.domain.entity.product.Sku;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * ProductMgrApi
 * @author youfu.wang
 * @date 2021-08-19
 */
@RestController
@RequestMapping("/product/sku")
@Api("商品管理接口")
@Slf4j
public class SkuMgrApi extends BaseApi {
    @Autowired
    private SkuService skuService;
    /**
     * 得到商品SKU列表
     * @param params
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/list")
    @ApiOperation("得到商品SKU列表")
    public ResponseResult list(@RequestParam Map<String, Object> params){
        ResponseResult res= ResponseResult.ok();
        try{
            List<Sku> skuList=skuService.getProductSkuList(params);
            res.setData(skuList);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
    /**
     * 新增商品SKU
     * @param params
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/add")
    @ApiOperation("新增商品SKU")
    public ResponseResult add(@RequestBody Map<String, Object> params){
        ResponseResult res= ResponseResult.ok();
        Map<String,Object> productMap=(Map<String,Object>)params.get("formObj");
        Map<String,Object> skuMap=(Map<String,Object>)params.get("sku");
        try{
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
    /**
     * 更新商品SKU
     * @param params
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/update")
    @ApiOperation("新增商品SKU")
    public ResponseResult update(@RequestBody Map<String, Object> params){
        ResponseResult res= ResponseResult.ok();
        Map<String,Object> productMap=(Map<String,Object>)params.get("formObj");
        Map<String,Object> skuMap=(Map<String,Object>)params.get("sku");
        try{
;
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
}
