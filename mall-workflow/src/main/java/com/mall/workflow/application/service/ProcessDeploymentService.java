package com.mall.workflow.application.service;

import org.apache.commons.io.FileUtils;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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

    public Deployment deploy(MultipartFile file) throws IOException {
        File dest;
        try {
            ClassPathResource deploymentResource = new ClassPathResource("processes");
            String destPath=deploymentResource.getFile().getPath();
            dest=new File(destPath,file.getOriginalFilename());
            FileUtils.writeByteArrayToFile(dest,file.getBytes());
        } catch (IOException e) {
            throw e;
        }
        return repositoryService.createDeployment()
                .addInputStream(file.getOriginalFilename(),new FileInputStream(dest))
                .deploy();
    }
}
