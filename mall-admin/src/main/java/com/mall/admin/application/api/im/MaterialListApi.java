package com.mall.admin.application.api.im;

import com.mall.admin.application.service.im.MaterialListService;
import com.mall.admin.domain.entity.im.MaterialList;
import com.mall.common.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * MaterialListApi
 * @author youfu.wang
 * @date 2019-01-31
 */
@RestController
@RequestMapping("/material/list")
@Slf4j
@Api("物品清单业务接口")
public class MaterialListApi {
    @Autowired
    private MaterialListService materialListService;
    /**
     * 查询物品清单业务列表
     * @return
     */
    @ResponseBody
    @GetMapping("/list")
    @ApiOperation("查询物品清单业务列表")
    public ResponseResult getMaterialBuyList(@RequestParam Map<String, Object> params){
        ResponseResult res= ResponseResult.ok();
        try{
            List<MaterialList> materialLists=materialListService.getMaterialListList(params);
            res.setData(materialLists);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
}
