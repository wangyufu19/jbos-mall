package com.mall.common.office.excel;

import com.github.pagehelper.PageInfo;
import com.mall.common.response.PageResult;
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
    private Map<String,String> titles;
    /**
     * 输出文件
     */
    private String outputFile;

    public PageExcelHandler(Map<String,String> titles){
        this.tempDirConfig=System.getProperty("user.dir");
        if(!this.tempDirConfig.endsWith("/")){
            this.tempDirConfig=this.tempDirConfig+File.separator+"temp"+File.separator;
        }else{
            this.tempDirConfig=this.tempDirConfig+"temp"+File.separator;
        }
        this.outputFile=this.tempDirConfig+System.currentTimeMillis()+".xlsx";
        this.titles=titles;
    }
    /**
     * 生成工作表
     * @param iPageExcel
     */
    public String generateExcelSheet(IPageExcel iPageExcel){
        File tempDir=new File(this.tempDirConfig);
        if(!tempDir.exists()){
            tempDir.mkdirs();
        }
        XSSFWorkbook workbook=new XSSFWorkbook();
        int startRow=0;
        int startCell=0;
        FileOutputStream out=null;
        try {
            XSSFSheet sheet=workbook.createSheet();
            //工作表标题
            XSSFRow titleRow = sheet.createRow(startRow);
            for (Map.Entry<String, String> entry : this.titles.entrySet()) {
                XSSFCell cell = titleRow.createCell(startCell);
                cell.setCellValue(entry.getValue());
                startCell++;
            }
            //工作表行数据
            List<Map<String, String>> dataList=null;
            PageInfo pageInfo=iPageExcel.getSheetRowDataList(null);
            if(null!=pageInfo){
                dataList=pageInfo.getList();
            }
            if (null != dataList&&dataList.size()>0) {
                for (Map<String, String> data : dataList) {
                    startRow++;
                    startCell = 0;
                    XSSFRow row = sheet.createRow(startRow);
                    for (Map.Entry<String, String> entry : data.entrySet()) {
                        XSSFCell cell = row.createCell(startCell);
                        cell.setCellValue(entry.getValue());
                        startCell++;
                    }
                }
            }
            out=new FileOutputStream(this.outputFile);
            workbook.write(out);
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            if(null!=out){
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
     * @param outputStream
     * @param iPageExcel
     */
    public void generateExcelSheet(OutputStream outputStream, IPageExcel iPageExcel){
        XSSFWorkbook workbook=new XSSFWorkbook();
        int startRow=0;
        int startCell=0;
        try {
            XSSFSheet sheet=workbook.createSheet();
            //工作表标题
            XSSFRow titleRow = sheet.createRow(startRow);
            for (Map.Entry<String, String> entry : this.titles.entrySet()) {
                XSSFCell cell = titleRow.createCell(startCell);
                cell.setCellValue(entry.getValue());
                startCell++;
            }
            Map<String,Object> params=new HashMap<>();
            params.put("page", PageResult.DEFAULT_PAGE_NUM);
            params.put("limit", PageResult.DEFAULT_PAGE_SIZE);
            //工作表行数据
            List<Map<String, String>> dataList = null;
            PageInfo pageInfo=iPageExcel.getSheetRowDataList(params);
            if(null!=pageInfo){
                dataList=pageInfo.getList();
            }
            if (null != dataList&&dataList.size()>0) {
                for (Object obj : dataList) {
                    startRow++;
                    startCell = 0;
                    XSSFRow row = sheet.createRow(startRow);
                    if(obj instanceof Map || obj instanceof HashMap){
                        Map<String, String> data=(Map<String, String>)obj;
                        for (Map.Entry<String, String> entry : data.entrySet()) {
                            this.setCellValue(row,startCell,entry.getValue());
                            startCell++;
                        }
                    }else{
                        Field[] fields=obj.getClass().getDeclaredFields();
                        if(null!=fields){
                            for(Field field:fields){
                                Getter getter=BeanFactory.getGetter(obj.getClass(),field.getName());
                                this.setCellValue(row,startCell,getter.get(obj));
                                startCell++;
                            }
                        }
                    }
                }
            }
            workbook.write(outputStream);
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            if(null!=outputStream){
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
            if(null!=workbook){
                try {
                    workbook.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }
    }
    private void setCellValue(XSSFRow row,int startCell,Object value){
        XSSFCell cell = row.createCell(startCell);
        cell.setCellValue(StringUtils.replaceNull(value));
    }
}
