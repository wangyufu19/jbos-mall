package com.mall.workflow.application.service;

import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.impl.persistence.entity.DeploymentEntity;
import org.camunda.bpm.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.camunda.bpm.engine.impl.task.TaskDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ProcessDeploymentService
 *
 * @author youfu.wang
 * @date 2023/6/9
 **/
@Service
public class ProcessDeploymentService {
    @Autowired
    private RepositoryService repositoryService;
    /**
     * 判断合法的文件类型
     * @param resource
     * @return
     */
    public boolean includeExtensions(String resource){
        if(resource.endsWith(".bpmn")){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 部署流程
     * @param file
     * @return
     * @throws IOException
     */
    public Map<String,Object> deploy(MultipartFile file) throws IOException {
        Map<String,Object> data=new HashMap<String,Object>();
        List<ProcessDefinitionEntity> processDefinitionEntityList = null;

        DeploymentEntity deploymentEntity= (DeploymentEntity)repositoryService.createDeployment()
                .addInputStream(file.getOriginalFilename(),file.getInputStream())
                .deploy();
        Map<Class<?>, List> artifacts=deploymentEntity.getDeployedArtifacts();
        if(!ObjectUtils.isEmpty(artifacts)){
            for(Map.Entry<Class<?>, List> artifact:artifacts.entrySet()){
                processDefinitionEntityList=artifact.getValue();
                break;
            }
        }
        if(!ObjectUtils.isEmpty(processDefinitionEntityList)&&processDefinitionEntityList.size()>0){
            for(ProcessDefinitionEntity processDefinitionEntity:processDefinitionEntityList){
                data.put("id",processDefinitionEntity.getId());
                data.put("deploymentId",processDefinitionEntity.getDeploymentId());
                data.put("key",processDefinitionEntity.getKey());
                data.put("name",processDefinitionEntity.getName());
                data.put("source",processDefinitionEntity.getResourceName());
                data.put("version",processDefinitionEntity.getVersion());
                break;
            }
        }
        return data;
    }

    /**
     * 删除流程
     * @param id
     * @param cascade
     */
    public void deleteDeployment(String id,boolean cascade){
        repositoryService.deleteDeployment(id,cascade);
    }

    /**
     * 得到流程定义任务
     * @param processDefinitionId
     * @return
     */
    public List<Map<String, String>> getProcessDefinitionList(String processDefinitionId){
        List<Map<String, String>> taskDefList=new ArrayList<>();
        ProcessDefinitionEntity processDefinition=(ProcessDefinitionEntity)repositoryService.getProcessDefinition(processDefinitionId);
        Map<String, TaskDefinition> taskDefs=processDefinition.getTaskDefinitions();
        for(Map.Entry<String,TaskDefinition> taskDefinition:taskDefs.entrySet()){
            Map<String, String> taskDef=new HashMap<>();
            taskDef.put("activityId",taskDefinition.getKey());
            taskDef.put("activityName",taskDefinition.getValue().getNameExpression().getExpressionText());
            taskDefList.add(taskDef);
        }
        return taskDefList;
    }



}
