package com.mall.common.utils;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Pattern;
/**
 * 数字转换帮助类
 * @author youfu.wang
 * @version 1.0
 */
public class NumberUtils {
	/**
	 * 通过java自带函数判断字符串是否数字
	 * @param value
	 * @return
	 */
	public static boolean isNumeric(Object value){
		if(value==null){
			return false;
		}
		return isNumeric(String.valueOf(value));
	}

	/**
	 * 通过java自带函数判断字符串是否数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){
		if(str==null||"".equals(str)) return false;
		for (int i = str.length();--i>=0;){   			
		    if (!Character.isDigit(str.charAt(i))){
		    	return false;
		    }
		}
	    return true;
	}
	/**
	 * 通过正值表达式判断字符串是否数字
	 * @param str
	 * @return
	 */
	public static boolean isNumericByMatch(String str){ 
		if(str==null||"".equals(str)) return false;
	    Pattern pattern = Pattern.compile("[0-9]*"); 
	    return pattern.matcher(str).matches();    
	} 
	/**
	 * 通过ASCII码判断字符串是否数字
	 * @param str
	 * @return
	 */
	public static boolean isNumericByAscii(String str){
		if(str==null||"".equals(str)) return false;
	   for(int i=str.length();--i>=0;){
	      int chr=str.charAt(i);
	      if(chr<48 || chr>57)
	         return false;
	   }
	   return true;
	}
	public static boolean isInteger(String str) {  
	    if (null == str || "".equals(str)) {  
	        return false;  
	    }  
	    Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");  
	    return pattern.matcher(str).matches();  
	}  
	/**
	 * 判断是否为浮点数，包括double和float 
	 * @param str
	 * @return
	 */
	public static boolean isDouble(String str) {  
		if(str==null||"".equals(str)) return false;
	    Pattern pattern = Pattern.compile("^[0-9]+(.[1-9]+)?$"); 
	    return pattern.matcher(str).matches();  
	}
	/**
	 * 转换字符串为数字类型
	 * @param str
	 * @param scale
	 */
	public static double stringToDobule(String str,int scale){
		double f=Double.parseDouble(str);
		return convertDobuleScale(f,scale);
	}
	/**
	 * 转换数字保留小数点位数
	 * @param value
	 * @param scale
	 * @return
	 */
	public static double convertDobuleScale(double value,int scale){
		BigDecimal b=new BigDecimal(value); 
		double d1=b.setScale(scale,BigDecimal.ROUND_HALF_UP).doubleValue();  
		return d1;
	}
	/**
	 * 转换数字为千分位字符串
	 * @param value
	 * @return
	 */
	public static String convertDoubleRMBString(double value){
		String s1="";
		BigDecimal b=new BigDecimal(value); 
		DecimalFormat df=new DecimalFormat(",###,##0.00");
		s1=df.format(b);
		return s1;
	}

	/**
	 * 转换数字为字符串，保留2位小数
	 * @param value
	 * @return
	 */
	public static String format(double value){
		String s1="";
		BigDecimal b=new BigDecimal(value);
		DecimalFormat df=new DecimalFormat("#####0.00");
		s1=df.format(b);
		return s1;
	}
	/**
	 * 转换字符串为数字类型
	 * @param str
	 */
	public static double stringToFloat(String str){
		double f=Double.parseDouble(str);
		BigDecimal b=new BigDecimal(f); 
		float f1=b.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();  
		return f1;
	}
}
