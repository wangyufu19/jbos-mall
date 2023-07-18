package com.mall.admin.application.api.im;

import com.mall.admin.application.service.im.MaterialInfoService;
import com.mall.admin.domain.entity.comm.TreeNode;
import com.mall.admin.domain.entity.sm.Func;
import com.mall.common.response.ResponseResult;
import com.mall.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * MaterialInfoApi
 *
 * @author youfu.wang
 * @date 2023/7/18
 **/
@Slf4j
@RestController
@RequestMapping("/material")
public class MaterialInfoApi {
    @Autowired
    private MaterialInfoService materialInfoService;
    /**
     * 查询物品下级节点
     * @param params
     * @res
     */
    @ResponseBody
    @RequestMapping("/getMaterialChildrenNode")
    public ResponseResult getMaterialChildrenNode(@RequestParam Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        String parentId = StringUtils.replaceNull(params.get("parentId"));
        if (StringUtils.isNUll(parentId)) {
            parentId = Func.ROOTFUNC_ID;
        }
        try{
            List<TreeNode> childrenNode=materialInfoService.getMaterialChildrenNode(parentId);
            res.setData(childrenNode);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
}
