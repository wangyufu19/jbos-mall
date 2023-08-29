package com.mall.common.page;

import com.mall.common.utils.NumberUtils;
import com.mall.common.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;

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
    public static final int DEFAULT_PAGE_NUM = 1;
    public static final int DEFAULT_PAGE_SIZE = 10;
    private int pageNum;
    private int pageSize;


    public static PageParam getPageParam(Map<String, Object> params) {
        int page = PageParam.DEFAULT_PAGE_NUM;
        int limit = PageParam.DEFAULT_PAGE_SIZE;
        if (NumberUtils.isNumeric(params.get("page"))) {
            page = Integer.parseInt(StringUtils.replaceNull(params.get("page")));
        }
        if (NumberUtils.isNumeric(params.get("limit"))) {
            limit = Integer.parseInt(StringUtils.replaceNull(params.get("limit")));
        }
        return new PageParam(page, limit);
    }

    public static PageParam getPageParam(int page, int limit) {
        return new PageParam(page, limit);
    }
}
