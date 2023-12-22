package com.mall.common.utils;

import java.io.InputStream;
import java.util.List;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.google.common.collect.*;

/**
 * ExcelUtils
 *
 * @author youfu.wang
 * @date 2023/12/22 14:16
 */
public class ExcelUtils {
    /**
     * 读取Excel文件流数据到一个List集合
     *
     * @param in    输入流
     * @param clazz 接收数据的类
     * @param <T>
     * @return list
     */
    public static <T> List<T> readToList(InputStream in, Class<T> clazz) {
        List<T> dataList = Lists.newArrayList();
        EasyExcel.read(in, clazz, new AnalysisEventListener<T>() {
            @Override
            public void invoke(T o, AnalysisContext analysisContext) {
                dataList.add(o);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {

            }
        }).sheet().doRead();
        return dataList;
    }
}
