package com.util.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 * @author zhangzhigong
 *
 */
public class RegexUtil {
	
	/**
	 * 时间正则表达式 yyyy-MM-dd
	 */
	public static final String YYYY_MM_DD_REGEX = "([0-9]{4}[-]{1}[0-9]{2}[-]{1}[0-9]{2})$";
	
	/**
	 * 正则表达式匹配
	 * 
	 * @param outStr 匹配的字符串
	 * @param regex 正则表达式
	 * @return true：正确，false：错误
	 */
	public static boolean test(final String outStr,final String regex){
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(outStr);
		return m.matches();
	}
	
	public static void main(String[] args) {
		System.out.println(RegexUtil.test("2017-01-01", RegexUtil.YYYY_MM_DD_REGEX));
	}
}
