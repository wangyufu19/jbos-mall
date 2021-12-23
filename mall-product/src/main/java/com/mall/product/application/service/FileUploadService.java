package com.mall.product.application.service;

import com.mall.common.utils.StringUtils;
import com.mall.product.domain.entity.FileRepo;
import com.mall.product.domain.entity.ProductPic;
import com.mall.product.infrastructure.repository.FileRepoRepo;
import com.mall.product.infrastructure.repository.ProductPicRepo;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

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
    @Autowired
    private ProductPicRepo productPicRepo;

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
    public void upload(MultipartFile file,Map<String, Object> params) throws IOException {
        String fileAsName=System.currentTimeMillis()+"."+file.getContentType().substring(file.getContentType().indexOf("image")+6);
        File repo=new File(uploadRepo);
        if(!repo.exists()){
            repo.mkdirs();
        }
        try {
            //写入图片到存储
            File dest=new File(repo,fileAsName);
            FileUtils.writeByteArrayToFile(dest,file.getBytes());
            //新增图片存储信息
            FileRepo fileRepo=new FileRepo();
            String seqId=StringUtils.getUUID();
            fileRepo.setSeqId(seqId);
            fileRepo.setFileRepo(uploadRepo);
            fileRepo.setFileName(file.getOriginalFilename());
            fileRepo.setFileAsName(fileAsName);
            fileRepoRepo.addFileRepo(fileRepo);
            //新增商品图片信息
            ProductPic productPic=new ProductPic();
            productPic.setSeqId(StringUtils.getUUID());
            productPic.setPicSeqId(seqId);
            productPic.setProductSeqId(StringUtils.replaceNull(params.get("productSeqId")));
            productPicRepo.addProductPic(productPic);
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * 上传多个文件
     * @param files
     */
    public void upload(MultipartFile[] files,Map<String, Object> params) throws IOException {
        if(files!=null){
            for(MultipartFile file:files){
                String fileAsName=System.currentTimeMillis()+file.getContentType();
                String dest=uploadRepo+ File.separator+fileAsName;
                file.transferTo(new File(dest));
            }
        }
    }

    /**
     * 删除一个文件
     * @param params
     */
    public void delete(Map<String, Object> params){
        //删除图片存储信息
        FileRepo fileRepo=new FileRepo();
        fileRepo.setSeqId(StringUtils.replaceNull(params.get("picSeqId")));
        fileRepoRepo.deleteFileRepo(fileRepo);
        //删除商品图片信息
        ProductPic productPic=new ProductPic();
        productPic.setSeqId(StringUtils.replaceNull(params.get("seqId")));
        productPicRepo.deleteProductPic(productPic);

    }
}
