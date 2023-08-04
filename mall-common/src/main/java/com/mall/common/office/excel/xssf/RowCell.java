package com.mall.common.office.excel.xssf;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;

/**
 * RowCell
 *
 * @author youfu.wang
 * @date 2023/8/4
 **/
public class RowCell {

    public static Object getCellValue(XSSFCell cell){
        Object value=null;
        if(ObjectUtils.isEmpty(cell)){
            return null;
        }
        CellType cellType=cell.getCellTypeEnum();
        switch (cellType){
            case NUMERIC:
                BigDecimal b=new BigDecimal(String.valueOf(cell.getNumericCellValue()));
                value=b.doubleValue();
                break;
            default:
                value=cell.getRichStringCellValue();
        }
        return value;
    }
}
