package com.mall.product.application.api;

import com.mall.common.response.BaseApi;
import com.mall.common.response.ResponseData;
import com.mall.common.utils.DateUtils;
import com.mall.common.utils.StringUtils;
import com.mall.product.application.external.admin.IdGeneratorService;
import com.mall.product.application.service.ProductService;
import com.mall.product.application.service.SkuService;
import com.mall.product.domain.entity.Product;
import com.mall.product.domain.entity.ProductList;
import com.mall.product.domain.entity.ProductPic;
import com.mall.product.domain.entity.Sku;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    private IdGeneratorService idGeneratorService;
    @Autowired
    private ProductService productService;
    @Autowired
    private SkuService skuService;
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
            idMap.put("bizType",IdGeneratorService.BIZ_TYPE_PRODUCT);
            ResponseData idRes=idGeneratorService.get(idMap);
            if(idRes.getRetCode().equals(ResponseData.RETCODE_SUCCESS)){
                Map<String, Object> retMap=(Map<String, Object>)idRes.getData();
                idMap.put("seqId",StringUtils.getUUID());
                //商品编号=YYYYMMDD+业务类型+ID版本+ID
                idMap.put("productCode",
                        DateUtils.format(DateUtils.getCurrentDate(),DateUtils.YYYYMMDD)+""+IdGeneratorService.BIZ_TYPE_PRODUCT+""+retMap.get("version")+""+retMap.get("currentId")
                );
                res.setData(idMap);
            }else{
                res=ResponseData.error(ResponseData.RETCODE_FAILURE,"商品编号生成失败");
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
            params.put("productSeqId",StringUtils.replaceNull(params.get("seqId")));
            List<ProductPic> productMainPics=productService.getProductMainPicList(params);
            List<Sku> skuList=skuService.getProductSkuList(params);
            Map<String,Object> retMap=new HashMap<String,Object>();
            retMap.put("base",product);
            retMap.put("mainPics",productMainPics);
            retMap.put("skuList",skuList);
            res.setData(retMap);
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
        List<Map<String,Object>> skuList=(ArrayList<Map<String,Object>>)params.get("sku");
        try{
            this.productService.addProductInfo(productMap,skuList);
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
    @PostMapping(value = "/saveDraft")
    @ApiOperation("保存商品信息")
    public ResponseData saveDraft(@RequestBody Map<String, Object> params){
        ResponseData res= ResponseData.ok();
        Map<String,Object> productMap=(Map<String,Object>)params.get("formObj");
        List<Map<String,Object>> skuList=(ArrayList<Map<String,Object>>)params.get("sku");
        try{
            productMap.put("status",Product.PRODUCT_STATUS_DRAFT);
            this.productService.addProductInfo(productMap,skuList);
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
        Map<String,Object> productMap=(Map<String,Object>)params.get("formObj");
        List<Map<String,Object>> skuList=(ArrayList<Map<String,Object>>)params.get("sku");
        try{
            this.productService.updateProductInfo(productMap,skuList);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return res;
    }
    @ResponseBody
    @PostMapping(value = "/offShelfOne")
    @ApiOperation("下架一个商品")
    public ResponseData offShelfOne(@RequestBody Map<String, Object> params){
        ResponseData res= ResponseData.ok();
        try{
            this.productService.offShelfOne(params);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return res;
    }
    @ResponseBody
    @PostMapping(value = "/shelfOne")
    @ApiOperation("上架一个商品")
    public ResponseData shelfOne(@RequestBody Map<String, Object> params){
        ResponseData res= ResponseData.ok();
        try{
            this.productService.shelfOne(params);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return res;
    }
}
