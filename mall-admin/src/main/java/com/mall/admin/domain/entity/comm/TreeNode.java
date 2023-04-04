package com.mall.admin.domain.entity.comm;
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
    private String id;
    private String text;
    private List<TreeNode> children=new ArrayList<TreeNode>();
    private boolean leaf;
}
