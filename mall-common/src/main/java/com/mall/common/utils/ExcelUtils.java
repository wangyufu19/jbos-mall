package com.mall.common.utils;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.google.common.collect.Lists;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

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
    /**
     * 导出Excel
     *
     * @param outputStream
     * @param headMap
     * @param dataList
     * @param sheetName
     * @param fileName
     * @param <T>
     */
    public static <T> void exportExcel(
            OutputStream outputStream,
            Map<String, String> headMap,
            List<T> dataList,
            String sheetName,
            String fileName) {
        if (ObjectUtils.isEmpty(headMap)) {
            throw new BusinessException("导出excel表头不能为空!");
        }
        if (ObjectUtils.isEmpty(dataList)) {
            throw new BusinessException("导出excel数据不能为空!");
        }
        List<List<String>> headList = Lists.newArrayListWithCapacity(headMap.size());
        for (Map.Entry<String, String> entry : headMap.entrySet()) {
            if (!StringUtils.hasLength(entry.getKey())) {
                continue;
            }
            headList.add(Lists.newArrayList(entry.getValue()));
        }
        EasyExcel.write(outputStream)
                .head(headList)
                .sheet(sheetName)
                .doWrite(dataList);
    }
}
