package com.mall.product.application.service;

import com.mall.common.utils.StringUtils;
import com.mall.product.domain.entity.FileRepo;
import com.mall.product.infrastructure.repository.FileRepoRepo;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * FileUploadService
 * @author youfu.wang
 * @date 2021-08-19
 */
@Service
public class FileUploadService {
    @Value("${spring.servlet.upload.repo}")
    private String uploadRepo="/nas/upload";

    @Autowired
    private FileRepoRepo fileRepoRepo;

    /**
     * 判断合法的文件类型
     * @param file
     * @return
     */
    public boolean includeExtensions(MultipartFile file){
        if(file.getContentType().indexOf("image/")!=-1){
            return true;
        }else{
            return false;
        }
    }
    /**
     * 上传一个文件
     * @param file
     */
    public void upload(MultipartFile file) throws IOException {
        String fileAsName=System.currentTimeMillis()+"."+file.getContentType().substring(file.getContentType().indexOf("image")+6);
        File repo=new File(uploadRepo);
        if(!repo.exists()){
            repo.mkdirs();
        }
        try {
            //写入图片到存储
            File dest=new File(repo,fileAsName);
            FileUtils.writeByteArrayToFile(dest,file.getBytes());
            //新增图片的存储信息
            FileRepo fileRepo=new FileRepo();
            fileRepo.setSeqId(StringUtils.getUUID());
            fileRepo.setFileRepo(uploadRepo);
            fileRepo.setFileName(file.getOriginalFilename());
            fileRepo.setFileAsName(fileAsName);
            fileRepoRepo.addFileRepo(fileRepo);
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * 上传多个文件
     * @param files
     */
    public void upload(MultipartFile[] files) throws IOException {
        if(files!=null){
            for(MultipartFile file:files){
                String fileAsName=System.currentTimeMillis()+file.getContentType();
                String dest=uploadRepo+ File.separator+fileAsName;
                file.transferTo(new File(dest));
            }
        }
    }
}
