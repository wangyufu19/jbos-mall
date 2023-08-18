package com.mall.common.office.excel;

import com.github.pagehelper.PageInfo;
import com.mall.common.page.PageParam;
import com.mall.common.response.ResponseResult;
import com.mall.common.utils.StringUtils;
import com.mall.common.utils.bean.BeanFactory;
import com.mall.common.utils.bean.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PageExcelHandler
 *
 * @author youfu.wang
 * @date 2023/4/12
 **/
@Slf4j
public class PageExcelHandler {
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
     * 生成工作表
     *
     * @param iPageExcel
     * @return outputFile
     */
    public String generateExcelSheet(IPageExcel iPageExcel) {
        File tempDir = new File(this.tempDirConfig);
        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }
        XSSFWorkbook workbook = new XSSFWorkbook();
        int startRow = 0;
        FileOutputStream out = null;
        try {
            XSSFSheet sheet = workbook.createSheet();
            //工作表标题
            XSSFRow titleRow = sheet.createRow(startRow);
            this.setCellValue(titleRow, this.titles);
            //工作表行数据
            Map<String, Object> params = new HashMap<>();
            params.put("page", PageParam.DEFAULT_PAGE_NUM);
            params.put("limit", PageParam.DEFAULT_PAGE_SIZE);
            while (true) {
                if (startRow >= IPageExcel.length) {
                    sheet = workbook.createSheet();
                    startRow = 0;
                }
                List<Map<String, String>> dataList;
                ResponseResult res = iPageExcel.getSheetRowDataList(params);
                PageInfo pageInfo;
                if (null != res) {
                    pageInfo = (PageInfo) res.getData();
                    dataList = pageInfo.getList();
                } else {
                    break;
                }
                if (null != dataList && dataList.size() > 0) {
                    for (Object obj : dataList) {
                        startRow++;
                        XSSFRow row = sheet.createRow(startRow);
                        this.setCellValue(row, obj);
                    }
                }
                if (pageInfo.isIsLastPage()) {
                    break;
                } else {
                    params.put("page", (pageInfo.getTotal() / pageInfo.getPageSize()) + 1);
                }
            }
            out = new FileOutputStream(this.outputFile);
            workbook.write(out);
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }
        return this.outputFile;
    }

    /**
     * 生成工作表
     *
     * @param outputStream
     * @param iPageExcel
     */
    public void generateExcelSheet(OutputStream outputStream, IPageExcel iPageExcel) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        int startRow = 0;
        try {
            XSSFSheet sheet = workbook.createSheet();
            //工作表标题
            this.setTitleCell(sheet, startRow);
            //工作表行数据
            Map<String, Object> params = new HashMap<>();
            params.put("page", PageParam.DEFAULT_PAGE_NUM);
            params.put("limit", PageParam.DEFAULT_PAGE_SIZE);
            while (true) {
                if (startRow >= IPageExcel.length) {
                    sheet = workbook.createSheet();
                    startRow = 0;
                    //工作表标题
                    this.setTitleCell(sheet, startRow);
                }
                List<Map<String, String>> dataList;
                ResponseResult res = iPageExcel.getSheetRowDataList(params);
                PageInfo pageInfo;

                if (null != res) {
                    pageInfo = (PageInfo) res.getData();
                    dataList = pageInfo.getList();
                } else {
                    break;
                }
                if (null != dataList && dataList.size() > 0) {
                    for (Object obj : dataList) {
                        startRow++;
                        XSSFRow row = sheet.createRow(startRow);
                        this.setCellValue(row, obj);
                    }
                }
                if (pageInfo.isIsLastPage()) {
                    break;
                } else {
                    params.put("page", (pageInfo.getTotal() / pageInfo.getPageSize()) + 1);
                }
            }
            workbook.write(outputStream);
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            if (null != outputStream) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
            if (null != workbook) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }
    }

    private void setTitleCell(XSSFSheet sheet, int startRow) {
        XSSFRow titleRow = sheet.createRow(startRow);
        this.setCellValue(titleRow, this.titles);
    }

    private void setCellValue(XSSFRow row, Object obj) {
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

    private void setCellValue(XSSFRow row, int startCell, Object value) {
        XSSFCell cell = row.createCell(startCell);
        cell.setCellValue(StringUtils.replaceNull(value));
    }
}
