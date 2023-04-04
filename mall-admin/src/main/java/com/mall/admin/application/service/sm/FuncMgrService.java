package com.mall.admin.application.service.sm;

import com.mall.admin.domain.entity.sm.Func;
import com.mall.admin.domain.entity.comm.TreeNode;
import com.mall.admin.infrastructure.repository.sm.FuncMgrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

/**
 * FuncMgrService
 * @author youfu.wang
 * @date 2019-01-31
 */
@Service
public class FuncMgrService {
    @Autowired
    private FuncMgrRepository funcMgrRepository;
    /**
     * 查询所有菜单权限列表
     * @return
     */
    public List<Func> getAllFuncList(){
        return funcMgrRepository.getAllFuncList();
    }
    /**
     * 查询用户菜单权限列表
     * @param userid
     * @return
     */
    public List<Func> getUserFuncList(String userid, String loginName){
        return funcMgrRepository.getUserFuncList(userid,loginName);
    }

    /**
     * 查询用户功能树
     * @param loginName
     * @param parentId
     * @return
     */
    private List<Func> getUserFuncTree(String loginName,String parentId){
        return funcMgrRepository.getUserFuncTree(loginName,parentId);
    }
    /**
     * 查询下级功能节点
     * @param parentId
     * @return
     */
    public List<TreeNode> getFuncChildrenNode(String parentId){
        return funcMgrRepository.getFuncChildrenNode(parentId);
    }

    /**
     * 查询功能树所有节点
     * @param parentId
     * @return
     */
    public List<TreeNode> getFuncTree(String parentId){
        return funcMgrRepository.getFuncTree(parentId);
    }
    /**
     * 查询功能列表
     * @param parameterObject
     * @return
     */
    public List<Func> getFuncList(Map<String, Object> parameterObject){
         return funcMgrRepository.getFuncList(parameterObject);
    }
    /**
     * 新增功能
     * @param parameterObject
     */
    public void insertFunc(Map<String, Object> parameterObject){
        funcMgrRepository.insertFunc(parameterObject);
    }

    /**
     * 修改功能
     * @param parameterObject
     */
    public void updateFunc(Map<String, Object> parameterObject){
        funcMgrRepository.updateFunc(parameterObject);
    }

    /**
     * 删除功能
     * @param id
     */
    public void deleteFunc(String id){
        funcMgrRepository.deleteFunc(id);
    }
}
