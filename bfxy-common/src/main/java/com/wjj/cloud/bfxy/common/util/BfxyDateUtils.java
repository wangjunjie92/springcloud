package com.wjj.cloud.bfxy.common.util;




import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.util.Date;
import java.util.TimeZone;

/**
 * 日期帮助类
 *
 * @author wangjunjie
 */
public class BfxyDateUtils {

    public static final TimeZone UTC = TimeZone.getTimeZone("GMT+8");
    public static final FastDateFormat DATE_TIME_PATTERN = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss",UTC);
    public static final FastDateFormat DATE_TIME_Id = FastDateFormat.getInstance("yyMMddHHmmssSSS",UTC);
    public static final FastDateFormat MINUTE_PATTERN  = FastDateFormat.getInstance("yyyy-MM-dd HH:mm",UTC);
    public static final FastDateFormat DATE_PATTERN = FastDateFormat.getInstance("yyyy-MM-dd",UTC);


    public static void main(String[] args) {
        Date date = DateUtils.addDays(new Date(), 1);
        String format = BfxyDateUtils.DATE_TIME_PATTERN.format(date);
        System.out.println(format);
    }


}
