package com.mall.common.response;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mall.common.utils.StringUtils;
import com.mall.common.utils.NumberUtils;
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
	 *
	 * @param page
	 */
	public void doStartPage(Map<String, Object> page) {
		int pageNum = 1;
		int pageSize = 10;
		if (NumberUtils.isNumeric(page.get("page"))) {
			pageNum = Integer.parseInt(StringUtils.replaceNull(page.get("page")));
		}
		if (NumberUtils.isNumeric(page.get("limit"))) {
			pageSize = Integer.parseInt(StringUtils.replaceNull(page.get("limit")));
		}
		PageHelper.startPage(pageNum, pageSize);
	}

	/**
	 * 完成分页
	 *
	 * @param datas
	 */
	public void doFinishPage(ResponseResult res, List datas) {
		PageInfo pageInfo = new PageInfo(datas);
		res.setData(pageInfo);
	}
}
