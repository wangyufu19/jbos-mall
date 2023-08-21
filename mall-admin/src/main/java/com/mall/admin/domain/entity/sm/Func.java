package com.mall.admin.domain.entity.sm;

import com.mall.admin.domain.entity.comm.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Func
 *
 * @author youfu.wang
 * @date 2019-01-31
 */
@Setter
@Getter
public class Func extends BaseEntity implements Serializable {
    /**
     * 根菜单id
     */
    public static final String ROOTFUNC_ID = "0";
    /**
     * 功能类型 0:目录
     */
    public static final String FUNCTYPE_DIR = "0";
    /**
     * 功能类型 1:菜单
     */
    public static final String FUNCTYPE_MENU = "1";
    /**
     * 父Id
     */
    private String parentId;
    /**
     * 功能编码
     */
    private String funcCode;
    /**
     * 功能名称
     */
    private String funcName;
    /**
     * 功能类型
     */
    private String funcType;
    /**
     * 功能路径
     */
    private String funcPath;
    /**
     * 路由URL
     */
    private String funcUrl;
    /**
     * 路由Icon
     */
    private String icon;
    /**
     * 排序
     */
    private int orderNo;
    /**
     * 子功能
     */
    private List<Func> children;
    /**
     * 功能列表
     */
    private List<Func> funcList;

    public void setFuncList(List<Func> funcList) {
        this.funcList = funcList;
    }
}
