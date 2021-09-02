package com.mall.admin.application.api;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mall.common.response.ResponseData;
import com.mall.common.utils.StringUtils;
import java.util.List;
import java.util.Map;

/**
 * BaseApi
 * @author youfu.wang
 * @date 2019-01-31
 */
public class BaseApi {
	/**
	 * 开始分页
	 * @param page
	 */
	public void doStartPage(Map<String, Object> page){
		int pageNum=Integer.parseInt(StringUtils.replaceNull(page.get("page")));
		int pageSize=Integer.parseInt(StringUtils.replaceNull(page.get("limit")));
		PageHelper.startPage(pageNum,pageSize);
	}

	/**
	 * 完成分页
	 * @param ret
	 * @param datas
	 */
	public void doFinishPage(ResponseData res, List datas){
		PageInfo pageInfo=new PageInfo(datas);
		res.setData(pageInfo);
	}
}
