package com.mall.product.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mall.product.domain.entity.FileRepo;
import com.mall.product.infrastructure.repository.mapper.FileRepoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * FileRepoRepo
 * @author youfu.wang
 * @date 2021-08-19
 */
@Component
public class FileRepoRepo {
    @Autowired
    private FileRepoMapper fileRepoMapper;


    public void addFileRepo(FileRepo fileRepo){
        fileRepoMapper.insert(fileRepo);
    }
    public void deleteFileRepo(FileRepo fileRepo){
        UpdateWrapper<FileRepo> updateWrapper=new UpdateWrapper<FileRepo>();
        updateWrapper.eq("seq_id",fileRepo.getSeqId());
        fileRepoMapper.delete(updateWrapper);
    }
}
