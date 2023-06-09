package com.mall.admin.domain.entity.wf;

import lombok.Getter;
import lombok.Setter;

/**
 * TaskStep
 *
 * @author youfu.wang
 * @date 2023/5/30
 **/
@Setter
@Getter
public class TaskStep {
    public static final String STEP_WAIT="wait";
    public static final String STEP_PROCESS="process";
    public static final String STEP_FINISH="finish";

    private String title;
    private String description;
    private String status=TaskStep.STEP_PROCESS;

}
