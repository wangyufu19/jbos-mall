package com.mall.common.office.excel.annotation;

import org.apache.poi.ss.usermodel.CellType;

import java.lang.annotation.*;

/**
 * Cell
 *
 * @author youfu.wang
 * @date 2023/8/4
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Inherited
public @interface Cell {
    int index() default 0;
    CellType cellType() default CellType.STRING;
}
