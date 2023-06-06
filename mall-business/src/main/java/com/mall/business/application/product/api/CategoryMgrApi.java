package com.mall.business.application.product.api;

import com.mall.common.response.ResponseResult;
import com.mall.common.utils.StringUtils;
import com.mall.business.application.product.service.CategoryService;
import com.mall.business.domain.entity.product.Category;
import com.mall.business.domain.entity.TreeNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * CategoryMgrApi
 * @author youfu.wang
 * @date 2021-08-19
 */
@RestController
@RequestMapping("/product/category")
@Api("商品分类接口")
@Slf4j
public class CategoryMgrApi{
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
    public ResponseResult tree(@RequestParam Map<String, Object> params){
        ResponseResult res= ResponseResult.ok();
        String parentId = StringUtils.replaceNull(params.get("parentId"));
        if (StringUtils.isNUll(parentId)) {
            parentId = TreeNode.ROOT_ID;
        }
        try{
            List<TreeNode> treeNodeList=categoryService.getCateGoryTreeNode(parentId);
            res.setData(treeNodeList);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
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
    public ResponseResult list(@RequestParam Map<String, Object> params){
        ResponseResult res;
        String parentCode=StringUtils.replaceNull(params.get("parentCode"));
        if(StringUtils.isNUll(parentCode)){
            parentCode="0";
        }
        try{
            res=categoryService.getProductCategory(parentCode);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

}
