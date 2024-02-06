package com.mall.common.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * PageParam
 *
 * @author youfu.wang
 * @date 2023/6/2
 **/
@Data
@AllArgsConstructor
public class PageParam {
    /**
     *
     */
    public static final int DEFAULT_PAGE_NUM = 1;
    public static final int DEFAULT_PAGE_SIZE = 10000;
    private int pageNum = PageParam.DEFAULT_PAGE_NUM;
    private int pageSize = PageParam.DEFAULT_PAGE_SIZE;

    /**
     * 得到分页参数实例
     *
     * @return PageParam
     */
    public static PageParam getPageParam() {
        int page = PageParam.DEFAULT_PAGE_NUM;
        int limit = PageParam.DEFAULT_PAGE_SIZE;
        return new PageParam(page, limit);
    }

    /**
     * 得到分页参数实例
     *
     * @param params
     * @return PageParam
     */
    public static PageParam getPageParam(Map<String, Object> params) {
        int page = PageParam.DEFAULT_PAGE_NUM;
        int limit = PageParam.DEFAULT_PAGE_SIZE;
        if (!ObjectUtils.isEmpty(params.get("page")) && StringUtils.hasLength(String.valueOf(params.get("page")))) {
            page = Integer.parseInt(String.valueOf(params.get("page")));
        }
        if (!ObjectUtils.isEmpty(params.get("limit")) && StringUtils.hasLength(String.valueOf(params.get("limit")))) {
            limit = Integer.parseInt(String.valueOf(params.get("limit")));
        }
        return new PageParam(page, limit);
    }

    /**
     * 得到分页参数实例
     *
     * @param page
     * @param limit
     * @return PageParam
     */
    public static PageParam getPageParam(int page, int limit) {
        return new PageParam(page, limit);
    }
}
