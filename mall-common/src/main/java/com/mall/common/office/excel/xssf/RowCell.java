package com.mall.common.office.excel.xssf;

import com.mall.common.utils.StringUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.springframework.util.ObjectUtils;

/**
 * RowCell
 *
 * @author youfu.wang
 * @date 2023/8/4
 **/
public class RowCell {
    public static double getDouble(XSSFCell cell){
        Object value=getValue(cell);
        if(value==null){
            return 0.00;
        }
        if(value instanceof Integer || value instanceof Float || value instanceof Double||value instanceof String){
            return Double.parseDouble(String.valueOf(value));
        }else{
            return 0.00;
        }
    }
    public static String getString(XSSFCell cell){
        Object value=getValue(cell);
        return StringUtils.replaceNull(value);
    }
    public static Object getValue(XSSFCell cell){
        Object value;
        if(ObjectUtils.isEmpty(cell)){
            return null;
        }
        DataFormatter formatter = new DataFormatter();
        CellType cellType=cell.getCellTypeEnum();
        switch (cellType){
            case STRING:
                value=cell.getStringCellValue();
                break;
            case NUMERIC:
                if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                    value = formatter.formatCellValue(cell);
                }else{
                    double doubleValue = cell.getNumericCellValue();
                    int intValue = (int) doubleValue;
                    value = doubleValue - intValue == 0 ? String.valueOf(intValue): doubleValue;
                }
                break;
            case BOOLEAN:
                value=String.valueOf(cell.getBooleanCellValue());
                break;
            default:
                value=cell.getRichStringCellValue();
        }
        return value;
    }
}
