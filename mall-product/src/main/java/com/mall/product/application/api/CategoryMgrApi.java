package com.mall.product.application.api;

import com.mall.common.response.BaseApi;
import com.mall.common.response.ResponseData;
import com.mall.common.utils.StringUtils;
import com.mall.product.application.service.CategoryService;
import com.mall.product.domain.entity.Category;
import com.mall.product.domain.entity.TreeNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CategoryMgrApi
 * @author youfu.wang
 * @date 2021-08-19
 */
@RestController
@RequestMapping("/category")
@Api("商品分类接口")
@Slf4j
public class CategoryMgrApi extends BaseApi {
    @Autowired
    private CategoryService categoryService;
    /**
     * 得到商品分类树
     * @param params
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/tree")
    @ApiOperation("得到商品列表")
    public ResponseData tree(@RequestParam Map<String, Object> params){
        ResponseData res= ResponseData.ok();
        String parentId = StringUtils.replaceNull(params.get("parentId"));
        if (StringUtils.isNUll(parentId)) {
            parentId = TreeNode.ROOT_ID;
        }
        try{
            List<TreeNode> treeNodeList=categoryService.getCateGoryTreeNode(parentId);
            res.setData(treeNodeList);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return res;
    }
    /**
     * 得到商品分类列表
     * @param params
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/list")
    @ApiOperation("得到商品列表")
    public ResponseData list(@RequestParam Map<String, Object> params){
        ResponseData res= ResponseData.ok();
        String isPage= StringUtils.replaceNull(params.get("isPage"));
        String parentCode=StringUtils.replaceNull(params.get("parentCode"));
        if(StringUtils.isNUll(parentCode)){
            parentCode="0";
        }
        try{
            if("true".equals(isPage)){
                this.doStartPage(params);
            }
            List<Category> categoryList=categoryService.getProductCategory(parentCode);
            if("true".equals(isPage)){
                this.doFinishPage(res,categoryList);
            }else{
                res.setData(categoryList);
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return res;
    }

}
