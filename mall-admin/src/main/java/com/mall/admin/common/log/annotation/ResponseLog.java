package com.mall.admin.common.log.annotation;

import java.lang.annotation.*;

/**
 * ResponseLog
 *
 * @author youfu.wang
 * @date 2023/11/17 9:19
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Inherited
public @interface ResponseLog {

}
