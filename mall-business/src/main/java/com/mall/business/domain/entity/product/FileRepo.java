package com.mall.business.domain.entity.product;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * FileRepo
 * @author youfu.wang
 * @date 2021-08-19
 */
@Setter
@Getter
@TableName("COMM_PIC_REPO")
public class FileRepo {
    @TableField("seq_id")
    private String seqId;
    /**
     * 文件存储
     */
    private String fileRepo;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 文件别名
     */
    private String fileAsName;
}
