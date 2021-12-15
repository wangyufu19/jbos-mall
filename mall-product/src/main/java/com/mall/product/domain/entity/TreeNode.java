package com.mall.product.domain.entity;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * TreeNode
 * @author youfu.wang
 * @date 2020-06-23
 */
@Setter
@Getter
public class TreeNode {
    /**
     * 根菜单id
     */
    public static String ROOT_ID="0";

    private String code;
    private String codeTree;
    private String text;
    //private List<TreeNode> children=new ArrayList<TreeNode>();
    private boolean leaf;
}
