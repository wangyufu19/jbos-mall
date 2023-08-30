package com.mall.common.office.excel;

import com.github.pagehelper.PageInfo;
import com.mall.common.response.ResponseResult;

import java.util.Map;

/**
 * IPageExcel
 *
 * @author youfu.wang
 * @date 2023/4/12
 **/
public interface IPageExcel {
    /**
     * 工作表最大行数
     */
    int SHEET_MAX_ROW = 1000000;
    /**
     * 每页写入行数
     */
    int LENGTH = 10000;

    /**
     * 得到工作表行数据列表
     *
     * @param params
     * @return
     */
    default PageInfo getPageDataList(Map<String, Object> params) {
        return new PageInfo();
    }

    /**
     * 得到工作表行数据列表
     *
     * @param page
     * @param limit
     * @return
     */
    PageInfo getPageDataList(int page, int limit);
}
