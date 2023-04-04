package com.mall.admin.application.service.sm;
import com.mall.admin.domain.entity.sm.DictCode;
import com.mall.admin.infrastructure.repository.sm.DictCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

/**
 * DictCodeService
 * @author youfu.wang
 * @date 2020-07-22
 */
@Service
public class DictCodeService {
    @Autowired
    private DictCodeRepository dictCodeRepository;
    /**
     * 得到字典码值数据
     * @return
     */
    public List<DictCode> getDictCodeList(Map<String, Object> parameterObject){
        return dictCodeRepository.getDictCodeList(parameterObject);
    }
    /**
     * 保存业务字典
     * @param parameterObject
     */
    public void saveDictCode(Map<String, Object> parameterObject){
        dictCodeRepository.saveDictCode(parameterObject);
    }
}
