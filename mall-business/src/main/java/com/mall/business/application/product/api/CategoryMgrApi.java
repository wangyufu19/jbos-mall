package com.mall.business.application.product.api;

import com.mall.common.response.ResponsePageResult;
import com.mall.common.response.ResponseResult;
import com.mall.business.application.product.service.CategoryService;
import com.mall.business.domain.entity.TreeNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
        String parentId = String.valueOf(params.get("parentId"));
        if (StringUtils.isEmpty(parentId)) {
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
    public ResponsePageResult list(@RequestParam Map<String, Object> params){
        ResponsePageResult res;
        String parentCode=String.valueOf(params.get("parentCode"));
        if(StringUtils.isEmpty(parentCode)){
            parentCode="0";
        }
        try{
            res=categoryService.getProductCategory(parentCode);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponsePageResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

}
