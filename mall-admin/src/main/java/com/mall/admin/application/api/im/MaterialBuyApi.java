package com.mall.admin.application.api.im;

import com.mall.admin.application.api.BaseApi;
import com.mall.admin.application.service.MaterialBuyService;
import com.mall.admin.domain.entity.im.MaterialBuy;
import com.mall.common.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * MaterialBuyApi
 * @author youfu.wang
 * @date 2019-01-31
 */
@RestController
@RequestMapping("/material/buy")
@Slf4j
@Api("物品采购业务接口")
public class MaterialBuyApi extends BaseApi {
    @Autowired
    private MaterialBuyService materialBuyService;
    /**
     * 查询物采购业务列表
     * @return
     */
    @ResponseBody
    @RequestMapping("/list")
    @ApiOperation("查询物采购业务列表")
    public ResponseData getMaterialBuyList(@RequestParam Map<String, Object> params){
        ResponseData ret=ResponseData.ok();
        try{
            this.doStartPage(params);
            List<MaterialBuy> materialBuys=materialBuyService.getMaterialBuyList(params);
            this.doFinishPage(ret,materialBuys);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            ret=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return ret;
    }
}
