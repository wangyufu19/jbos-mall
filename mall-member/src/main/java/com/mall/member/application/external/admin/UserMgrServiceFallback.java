package com.mall.member.application.external.admin;

import com.mall.common.response.ResponseResult;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Map;
/**
 * UserMgrServiceFallback
 * @author youfu.wang
 * @date 2021-08-24
 */
@Component
public class UserMgrServiceFallback implements UserMgrService{
    /**
     * 新增用户信息数据
     * @param params
     * @return
     */
    public ResponseResult add(@RequestBody Map<String, Object> params){
        return ResponseResult.error();
    }
}
