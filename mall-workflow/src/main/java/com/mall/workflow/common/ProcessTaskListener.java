package com.mall.workflow.common;

import com.mall.workflow.application.service.ProcessInstanceService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * ProcessTaskListener
 *
 * @author youfu.wang
 * @date 2023/5/29
 **/
@Component
public class ProcessTaskListener implements TaskListener {
    @Autowired
    private ProcessInstanceService processInstanceService;
    @Override
    public void notify(DelegateTask delegateTask) {

        System.out.println("eventName="+delegateTask.getEventName());

    }
}
