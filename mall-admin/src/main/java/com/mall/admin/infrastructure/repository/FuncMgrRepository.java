package com.mall.admin.infrastructure.repository;

import com.mall.admin.domain.entity.Func;
import com.mall.admin.domain.entity.TreeNode;
import com.mall.admin.infrastructure.repository.mapper.FuncMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FuncMgrRepository
 * @author youfu.wang
 * @date 2020-06-24
 */
@Component
public class FuncMgrRepository {
    @Autowired
    private FuncMapper funcMapper;
    /**
     * 查询所有菜单权限列表
     * @return
     */
    public List<Func> getAllFuncList(){
        Map<String, Object> parameterObject=new HashMap<String, Object>();
        return funcMapper.getAllFuncList(parameterObject);
    }
    /**
     * 查询用户菜单权限列表
     * @param userid
     * @return
     */
    public List<Func> getUserFuncList(String userid, String loginName){
        return this.getUserFuncTree(loginName,Func.ROOTFUNC_ID);
    }

    /**
     * 查询用户功能树
     * @param loginName
     * @param parentId
     * @return
     */
    public List<Func> getUserFuncTree(String loginName,String parentId){
        List<Func> funcs=new ArrayList<Func>();
        Map<String,Object> parameterObject=new HashMap<String,Object>();
        parameterObject.put("userId",loginName);
        parameterObject.put("parentId",parentId);
        if("admin".equals(loginName)){
            funcs=funcMapper.getFuncList(parameterObject);
        }else{
            funcs=funcMapper.getUserFuncList(parameterObject);
        }
        if(null!=funcs){
            for(Func func:funcs){
                func.setChildren(this.getUserFuncTree(loginName,func.getId()));
            }
        }
        return funcs;
    }
    /**
     * 查询下级功能节点
     * @param parentId
     * @return
     */
    public List<TreeNode> getFuncChildrenNode(String parentId){
        List<TreeNode> funcTree=null;
        Map<String, Object> parameterObject=new HashMap<String, Object>();
        parameterObject.put("parentId",parentId);
        funcTree=funcMapper.getFuncChildrenNode(parameterObject);
        return funcTree;
    }

    /**
     * 查询功能树所有节点
     * @param parentId
     * @return
     */
    public List<TreeNode> getFuncTree(String parentId){
        List<TreeNode> funcTree=new ArrayList<TreeNode>();
        funcTree=this.getFuncChildrenNode(parentId);
        if(null!=funcTree){
            for(TreeNode treeNode:funcTree){
                treeNode.setChildren(this.getFuncTree(treeNode.getId()));
            }
        }
        return funcTree;
    }
    /**
     * 查询功能列表
     * @param parameterObject
     * @return
     */
    public List<Func> getFuncList(Map<String, Object> parameterObject){
        return funcMapper.getFuncList(parameterObject);
    }
    /**
     * 新增功能
     * @param parameterObject
     */
    public void insertFunc(Map<String, Object> parameterObject){
        funcMapper.insertFunc(parameterObject);
    }

    /**
     * 修改功能
     * @param parameterObject
     */
    public void updateFunc(Map<String, Object> parameterObject){
        funcMapper.updateFunc(parameterObject);
    }

    /**
     * 删除功能
     * @param id
     */
    public void deleteFunc(String id){
        funcMapper.deleteFunc(id);
    }
}
