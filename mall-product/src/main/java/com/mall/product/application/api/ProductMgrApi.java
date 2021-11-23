package com.mall.product.application.api;

import com.mall.common.response.BaseApi;
import com.mall.common.response.ResponseData;
import com.mall.common.utils.StringUtils;
import com.mall.product.application.external.admin.IdGeneratorService;
import com.mall.product.application.service.ProductService;
import com.mall.product.domain.entity.Product;
import com.mall.product.domain.entity.ProductList;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ProductMgrApi
 * @author youfu.wang
 * @date 2021-08-19
 */
@RestController
@Api("商品管理接口")
@Slf4j
public class ProductMgrApi extends BaseApi {
    @Autowired
    private ProductService productService;
    @Autowired
    private IdGeneratorService idGeneratorService;
    /**
     * 得到商品列表
     * @param params
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/list")
    @ApiOperation("得到商品列表")
    public ResponseData list(@RequestParam Map<String, Object> params){
        ResponseData res= ResponseData.ok();
        String isPage= StringUtils.replaceNull(params.get("isPage"));
        try{
            if("true".equals(isPage)){
                this.doStartPage(params);
            }
            List<ProductList> productLists=productService.getProductList(params);
            if("true".equals(isPage)){
                this.doFinishPage(res,productLists);
            }else{
                res.setData(productLists);
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return res;
    }
    /**
     * 得到商品编号
     * @param params
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/getNo")
    @ApiOperation("得到商品编号")
    public ResponseData getNo(@RequestParam Map<String, Object> params){
        ResponseData res= ResponseData.ok();
        try{
            Map<String, Object> idMap=new HashMap<String, Object>();
            idMap.put("bizType","100");
            ResponseData idRes=idGeneratorService.get(idMap);
            if(idRes.getRetCode().equals(ResponseData.RETCODE_SUCCESS)){
                return idRes;
            }else{
                res=ResponseData.error(ResponseData.RETCODE_FAILURE,"生成商品编号失败");
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return res;
    }
    /**
     * 得到商品信息
     * @param params
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/get")
    @ApiOperation("得到商品信息")
    public ResponseData get(@RequestParam Map<String, Object> params){
        ResponseData res= ResponseData.ok();
        try{
            Product product=productService.getProductInfo(params);
            res.setData(product);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return res;
    }
    /**
     * 新增商品信息
     * @param params
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/add")
    @ApiOperation("新增商品信息")
    public ResponseData add(@RequestBody Map<String, Object> params){
        ResponseData res= ResponseData.ok();
        Map<String,Object> productMap=(Map<String,Object>)params.get("formObj");
        try{
            Product product=new Product();
            product.setSeqId(StringUtils.getUUID());
            product.setProductCode(StringUtils.replaceNull(productMap.get("productCode")));
            product.setTitle(StringUtils.replaceNull(productMap.get("title")));
            product.setStatus("10");
            this.productService.addProductInfo(product);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return res;
    }
    /**
     * 更新商品信息
     * @param params
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/update")
    @ApiOperation("新增商品信息")
    public ResponseData update(@RequestBody Map<String, Object> params){
        ResponseData res= ResponseData.ok();
        try{
            Product product=new Product();
            this.productService.updateProductInfo(product);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return res;
    }
}
