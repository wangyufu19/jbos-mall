package com.mall.admin.domain.entity.im;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mall.admin.domain.entity.comm.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * MaterialInfo
 *
 * @author youfu.wang
 * @date 2023/7/14
 **/
@Setter
@Getter
@TableName("IM_MATERIAL_INFO")
public class MaterialInfo extends BaseEntity {

    @TableField("materialno")
    private String materialNo;

    @TableField("materialname")
    private String materialName;

    @TableField("parentid")
    private double parentId;

    @TableField("materialdesc")
    private double materialDesc;


}
