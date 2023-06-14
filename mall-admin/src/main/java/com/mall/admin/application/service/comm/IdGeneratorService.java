package com.mall.admin.application.service.comm;

import com.mall.admin.domain.entity.comm.Id;
import com.mall.admin.infrastructure.repository.comm.IdRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * IdGeneratorService
 * @author youfu.wang
 * @date 2021-08-19
 */
@Service
public class IdGeneratorService {
    @Autowired
    private IdRepo idRepo;
    private Lock lock = new ReentrantLock();
    private static Id id=new Id();

    /**
     * 得到全局唯一ID
     * @return
     */
    public Id getId(String bizType){
        lock.lock();
        try {
            //首次或用完号段,从数据库中得到最新号码
            if (this.id.getCurrentId() == this.id.getMaxId()+this.id.getStep()) {
                id=idRepo.getId(bizType);
                if(id==null){
                    id=new Id();
                }
                id.setBizType(bizType);
                id.setCurrentId(id.getMaxId()+1);
                idRepo.updateId(bizType,id.getVersion());
            } else {
                id.setCurrentId(id.getCurrentId()+1);
            }
        }finally {
            lock.unlock();
        }
        return id;
    }
}
