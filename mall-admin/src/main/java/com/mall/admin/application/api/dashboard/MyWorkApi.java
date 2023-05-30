package com.mall.admin.application.api.dashboard;

import com.mall.admin.application.service.sm.ProcessTaskService;
import com.mall.admin.domain.entity.sm.ProcessTask;
import com.mall.admin.domain.entity.sm.TaskStep;
import com.mall.common.response.BaseApi;
import com.mall.common.response.ResponseResult;
import com.mall.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
@Slf4j
@Api("我的工作台接口")
public class MyWorkApi extends BaseApi {

    @Autowired
    private ProcessTaskService processTaskService;

    /**
     * 查询我的待办列表
     * @return
     */
    @ResponseBody
    @GetMapping("/getMyWorkList")
    @ApiOperation("查询我的待办列表")
    public ResponseResult getMyWorkList(@RequestParam Map<String, Object> params){
        ResponseResult res= ResponseResult.ok();
        String workType=StringUtils.replaceNull(params.get("workType"));
        String isPage= StringUtils.replaceNull(params.get("isPage"));
        try{
            if("true".equals(isPage)){
                this.doStartPage(params);
            }
            List<ProcessTask> processTasks;
            if("yb".equals(workType)){
                processTasks=processTaskService.getUserTaskProcessedList(params);
            }else{
                processTasks=processTaskService.getUserTaskList(params);
            }

            if("true".equals(isPage)){
                this.doFinishPage(res,processTasks);
            }else{
                res.setData(processTasks);
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
    /**
     * 查询流程任务处理步骤
     * @return
     */
    @ResponseBody
    @GetMapping("/getUserTaskStepList")
    @ApiOperation("查询流程任务处理步骤")
    public ResponseResult getUserTaskStepList(@RequestParam Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        try{
            List<TaskStep> taskSteps=processTaskService.getUserTaskStepList(params);
            res.setData(taskSteps);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

}
