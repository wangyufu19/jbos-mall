package com.mall.common.office.excel;

import com.github.pagehelper.PageInfo;
import com.mall.common.page.PageParam;
import com.mall.common.paralle.ParallelUtil;
import com.mall.common.utils.StringUtils;
import com.mall.common.utils.bean.BeanFactory;
import com.mall.common.utils.bean.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntFunction;

/**
 * PageExcelHandler
 *
 * @author youfu.wang
 * @date 2023/4/12
 **/
@Slf4j
public class PageExcelHandler {
    /**
     * 最大行数
     */
    private int maxRow = IPageExcel.SHEET_MAX_ROW;
    /**
     * 每页大小
     */
    private int pageSize = PageParam.DEFAULT_PAGE_SIZE;
    /**
     * 起始行数
     */
    private AtomicInteger startRow = new AtomicInteger();

    /**
     * 工作表最大内存行数
     */
    private int rowAccessWindowSize = 1000;
    /**
     * 临时目录配置
     */
    private String tempDirConfig;
    /**
     * 工作表标题
     */
    private Map<String, String> titles;
    /**
     * 输出文件
     */
    private String outputFile;

    /**
     * 构造方法
     *
     * @param titles
     */
    public PageExcelHandler(Map<String, String> titles) {
        this.tempDirConfig = System.getProperty("user.dir");
        if (!this.tempDirConfig.endsWith("/")) {
            this.tempDirConfig = this.tempDirConfig + File.separator + "temp" + File.separator;
        } else {
            this.tempDirConfig = this.tempDirConfig + "temp" + File.separator;
        }
        this.outputFile = this.tempDirConfig + System.currentTimeMillis() + ".xlsx";
        this.titles = titles;
    }

    /**
     * 设置最大行数
     * @param maxRow
     */
    public void setMaxRow(int maxRow) {
        this.maxRow = maxRow;
    }

    /**
     * 设置页码大小
     * @param pageSize
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 生成Excel
     *
     * @param iPageExcel
     * @return outputFile
     */
    public String generateExcel(IPageExcel iPageExcel) {
        File tempDir = new File(this.tempDirConfig);
        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }
        SXSSFWorkbook workbook = new SXSSFWorkbook(this.rowAccessWindowSize);

        FileOutputStream out = null;
        try {
            SXSSFSheet sheet = workbook.createSheet();
            //工作表标题
            SXSSFRow titleRow = sheet.createRow(startRow.get());
            this.setCellValue(titleRow, this.titles);
            //工作表行数据
            int page = PageParam.DEFAULT_PAGE_NUM;
            int limit = PageParam.DEFAULT_PAGE_SIZE;
            while (true) {
                //超出工作表最大行数，则退出循环写数据
                if (startRow.get() >= this.maxRow) {
                    break;
                }
                PageInfo pageInfo = this.generateData(iPageExcel, page, limit, sheet);
                if (pageInfo == null) {
                    break;
                }
                //最后一页，则退出循环写数据
                if (pageInfo.isIsLastPage()) {
                    break;
                } else {
                    page = pageInfo.getNextPage();
                }
            }
            out = new FileOutputStream(this.outputFile);
            workbook.write(out);
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            this.close(workbook, out);
        }
        return this.outputFile;
    }

    /**
     * 生成Excel
     *
     * @param outputStream
     * @param iPageExcel
     * @param limit
     */
    public void generateExcel(OutputStream outputStream, IPageExcel iPageExcel, int limit) {
        long startDate = System.currentTimeMillis();
        SXSSFWorkbook workbook = new SXSSFWorkbook(this.rowAccessWindowSize);
        int page = PageParam.DEFAULT_PAGE_NUM;
        try {
            SXSSFSheet sheet = workbook.createSheet();
            //工作表标题
            this.setTitleCell(sheet, startRow.get());
            //工作表行数据
            while (true) {
                //超出工作表最大行数，则退出循环写数据
                if (startRow.get() >= this.maxRow) {
                    break;
                }

                PageInfo pageInfo = this.generateData(iPageExcel, page, limit, sheet);
                //最后一页，则退出循环写数据
                if (pageInfo.isIsLastPage()) {
                    break;
                } else {
                    page = pageInfo.getNextPage();
                }
            }
            workbook.write(outputStream);
            log.info("******startRow={}", startRow.get());
            long endDate = System.currentTimeMillis();
            log.info("******总耗时：{} 秒", (endDate - startDate) / 1000);
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            this.close(workbook, outputStream);
        }

    }

    /**
     * 生成行数据
     *
     * @param iPageExcel
     * @param page
     * @param limit
     * @param sheet
     * @return pageInfo
     */
    private PageInfo generateData(IPageExcel iPageExcel, int page, int limit, SXSSFSheet sheet) {
        List<Map<String, String>> dataList = null;
        PageInfo pageInfo = iPageExcel.getPageDataList(page, limit);
        if (null != pageInfo) {
            dataList = pageInfo.getList();
        }
        if (null != dataList && dataList.size() > 0) {
            for (Object obj : dataList) {
                startRow.incrementAndGet();
                SXSSFRow row = sheet.createRow(startRow.get());
                this.setCellValue(row, obj);
            }
        }
        return pageInfo;
    }

    /**
     * 关闭IO流
     *
     * @param workbook
     * @param outputStream
     */
    private void close(SXSSFWorkbook workbook, OutputStream outputStream) {
        try {
            if (null != workbook) {
                workbook.close();
            }
            if (null != outputStream) {
                outputStream.flush();
                outputStream.close();
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 并发分页生成EXCEL数据
     *
     * @param outputStream
     * @param iPageExcel
     * @param limit
     */
    public void generateExcelParallel(OutputStream outputStream, IPageExcel iPageExcel, int limit) {
        long startDate = System.currentTimeMillis();
        int count = iPageExcel.getPageCount();
        int totalPage = count % this.pageSize == 0 ? count / this.pageSize : count / this.pageSize + 1;
        SXSSFWorkbook workbook = new SXSSFWorkbook(this.rowAccessWindowSize);
        try {

            SXSSFSheet sheet = workbook.createSheet();
            //工作表标题
            this.setTitleCell(sheet, startRow.get());
            IntFunction producerFunction = page -> iPageExcel.getPageDataList(page, limit);
            ParallelUtil.parallel(Object.class, totalPage)
                    .asyncProducer(producerFunction::apply)
                    .syncConsumer(data -> {
                        //超出工作表最大行数，则不消费数据
                        if (startRow.get() >= this.maxRow) {
                            return;
                        }
                        List<Map<String, String>> dataList = ((PageInfo) data).getList();
                        if (null != dataList && dataList.size() > 0) {
                            for (Object obj : dataList) {
                                startRow.incrementAndGet();
                                SXSSFRow row = sheet.createRow(startRow.get());
                                this.setCellValue(row, obj);
                            }
                        }
                    }).start();
            log.info("startRow={}", startRow.get());
            workbook.write(outputStream);
        } catch (InterruptedException | IOException e) {
            log.error(e.getMessage());
        } finally {
            this.close(workbook, outputStream);
        }
        long endDate = System.currentTimeMillis();
        log.info("******总耗时：{} 秒", (endDate - startDate) / 1000);
    }

    /**
     * 设置标题数据
     *
     * @param sheet
     * @param startRow
     */
    private void setTitleCell(SXSSFSheet sheet, int startRow) {
        SXSSFRow titleRow = sheet.createRow(startRow);
        this.setCellValue(titleRow, this.titles);
    }

    /**
     * 设置行数据
     *
     * @param row
     * @param obj
     */
    private void setCellValue(SXSSFRow row, Object obj) {
        if (obj == null) {
            return;
        }
        int startCell = 0;
        if (obj instanceof Map || obj instanceof HashMap) {
            Map<String, String> data = (Map<String, String>) obj;
            for (Map.Entry<String, String> entry : this.titles.entrySet()) {
                this.setCellValue(row, startCell, data.get(entry.getKey()));
                startCell++;
            }
        } else {
            Field[] fields = obj.getClass().getDeclaredFields();
            if (null != fields) {
                for (Field field : fields) {
                    if (this.titles.containsKey(field.getName())) {
                        Getter getter = BeanFactory.getGetter(obj.getClass(), field.getName());
                        this.setCellValue(row, startCell, getter.get(obj));
                        startCell++;
                    }
                }
            }
        }
    }

    private void setCellValue(SXSSFRow row, int startCell, Object value) {
        SXSSFCell cell = row.createCell(startCell);
        cell.setCellValue(StringUtils.replaceNull(value));
    }


}
