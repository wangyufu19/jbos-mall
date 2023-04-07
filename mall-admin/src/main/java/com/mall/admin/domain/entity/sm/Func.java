package com.mall.admin.domain.entity.sm;
import com.mall.admin.domain.entity.comm.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Func
 * @author youfu.wang
 * @date 2019-01-31
 */
@Setter
@Getter
public class Func  extends BaseEntity implements Serializable {
    /**
     * 根菜单id
     */
    public static String ROOTFUNC_ID="0";
    /**
     * 功能类型0:目录;1:菜单
     */
    public static final String FUNCTYPE_DIR="0";
    public static final String FUNCTYPE_MENU="1";

    private String parentId;
    private String funcCode;
    private String funcName;
    private String funcType;
    private String funcPath;
    private String funcUrl;
    private String icon;
    int orderNo;
    private List<Func> children;
    private List<Func> funcList;
    public void setFuncList(List<Func> funcList){
        this.funcList=funcList;
    }
}
