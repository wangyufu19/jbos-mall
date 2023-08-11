package com.mall.common.office.excel.xssf;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.springframework.objenesis.instantiator.util.ClassUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import com.mall.common.office.excel.annotation.Cell;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

/**
 * RowObject
 *
 * @author youfu.wang
 * @date 2023/8/11
 **/
public class RowObject {

    public static Object getRowObject(XSSFRow row, Class requestType){
        Object bean=  ClassUtils.newInstance(requestType);
        Field[] fields=requestType.getDeclaredFields();
        if(ObjectUtils.isEmpty(fields)){
            return bean;
        }
        for(Field field:fields) {
            int mod = field.getModifiers();
            if (!Modifier.isStatic(mod) && !Modifier.isFinal(mod)) {
                injectCell(row,bean,field);
            }
        }
        return bean;
    }
    private static void injectCell(XSSFRow row,Object bean,Field field){
        Cell cellAnnotation=field.getDeclaredAnnotation(Cell.class);
        if(!ObjectUtils.isEmpty(cellAnnotation)){
            int index=cellAnnotation.index();
            CellType cellType=cellAnnotation.cellType();
            Object value=null;
            XSSFCell cell=row.getCell(index);
            switch (cellType) {
                case STRING:
                    value=RowCell.getString(cell);
                    break;
                case NUMERIC:
                    value=RowCell.getDouble(cell);
                    break;
                default:
                    value="NULL";
            }
            ReflectionUtils.makeAccessible(field);
            ReflectionUtils.setField(field,bean,value);
        }
    }
}
