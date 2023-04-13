package com.mall.common.response;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mall.common.utils.NumberUtils;
import com.mall.common.utils.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * PageResult
 * @author youfu.wang
 * @date 2023/4/13
 **/
public class PageResult {
    public final static int DEFAULT_PAGE_NUM=1;
    public final static int DEFAULT_PAGE_SIZE=10;
    private int pageNum;
    private int pageSize;

    public PageResult(){
        this.pageNum=PageResult.DEFAULT_PAGE_NUM;
        this.pageSize=PageResult.DEFAULT_PAGE_SIZE;
    }
    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 开始分页
     * @param page
     */
    public void doStartPage(Map<String, Object> page){
        if(NumberUtils.isNumeric(page.get("page"))){
            pageNum=Integer.parseInt(StringUtils.replaceNull(page.get("page")));
        }
        if(NumberUtils.isNumeric(page.get("limit"))){
            pageSize=Integer.parseInt(StringUtils.replaceNull(page.get("limit")));
        }
        PageHelper.startPage(pageNum,pageSize);
    }

    /**
     * 完成分页
     * @param dataList
     */
    public PageInfo doFinishPage(List dataList){
        PageInfo pageInfo=new PageInfo(dataList);
        return pageInfo;
    }
}
