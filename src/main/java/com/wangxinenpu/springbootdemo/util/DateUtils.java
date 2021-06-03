package com.wangxinenpu.springbootdemo.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	/**yyyy-MM-dd*/
	public static String date = "yyyy-MM-dd";
	/**yyyy/MM/dd*/
	public static String date2 = "yyyy/MM/dd";
	
	/**yyyyMMdd**/
	public static String date3 = "yyyyMMdd";
	
	/**HH:mm:ss*/
	public static String time = "HH:mm:ss";
	/**yyyy-MM-dd HH:mm:ss*/
	public static String dateTime = "yyyy-MM-dd HH:mm:ss";
	
	/**yyyyMMddHHmmss*/
	public static String date4 = "yyyyMMddHHmmss";

	/**
	 * date转string
	 * @param d
	 * @param formatStr
	 * @return
	 */
	public static String parseDate(Date d, String formatStr){
		return new SimpleDateFormat(formatStr).format(d);
	}

	/**
	 * string转date
	 * @param date
	 * @param formatStr
	 * @return
	 */
	public static Date paserStringToDate(String date, String formatStr) throws Exception {
		return new SimpleDateFormat(formatStr).parse(date);
	}
	/**
	 * long转换date
	 * @param d
	 * @param formatStr
	 * @return
	 */
	public static String parseLongtoDate(long d, String formatStr){
		return new SimpleDateFormat(formatStr).format(d);
	}

	/**
	 * 获取当前时间 yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static Date getCurrentDateDetail(){
		return new Date(System.currentTimeMillis());
	}

	/**
	 * 获取当前时间 yyyy-MM-dd
	 * @return
	 */
	public static Date getCurrentDate() throws Exception {
		Date currentTime = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(date);
		String dataFormat = sdf.format(currentTime);
		return  sdf.parse(dataFormat);
	}
	
	/**
	 * 获取当前时间 yyyyMMdd
	 * @return
	 */
	public static String getStringCurrentDate(){
		Date currentTime = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(date3);
		String dataFormat = sdf.format(currentTime);
		return  dataFormat;
	}
	
	/**
	 * 获取当前时间 yyyyMMddHHmmss
	 * @return
	 */
	public static String getDate4CurrentDate(){
		Date currentTime = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(date4);
		String dataFormat = sdf.format(currentTime);
		return  dataFormat;
	}

	/**
	 * 日期加减
	 * @param dt
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Date calculateDate(Date dt, int year, int month, int day){
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dt);
		rightNow.add(Calendar.YEAR,year);//日期减1年
		rightNow.add(Calendar.MONTH,month);//日期加3个月
		rightNow.add(Calendar.DAY_OF_YEAR,day);//日期加10天
		return rightNow.getTime();
	}

	/**
	 * string转换为时间戳
	 * @param date
	 * @return
	 */
	public static Long strToLong(String date) throws Exception {
		return paserStringToDate(date,dateTime).getTime();
	}
	public static String formatTime(Long ms) {
		Integer ss = 1000;
		Integer mi = ss * 60;
		Integer hh = mi * 60;
		Integer dd = hh * 24;

		Long day = ms / dd;
		Long hour = (ms - day * dd) / hh;
		Long minute = (ms - day * dd - hour * hh) / mi;
		Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
		Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

		StringBuffer sb = new StringBuffer();
		if (day > 0) {
			sb.append(day + "天");
		}
		if (hour > 0) {
			sb.append(hour + "小时");
		}
		if (minute > 0) {
			sb.append(minute + "分");
		}
		if (second > 0) {
			sb.append(second + "秒");
		}
		if (milliSecond > 0) {
			sb.append(milliSecond + "毫秒");
		}
		return sb.toString();
	}
}
