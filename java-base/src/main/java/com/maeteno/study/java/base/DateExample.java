package com.maeteno.study.java.base;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

// https://www.notion.so/maeteno/Java-d09750e5c92546169a2286ff8d080465

/**
 * 时间的转换需要使用 SimpleDateFormat 进行格式化输出
 *
 * @author Alan.Fu
 */
@Slf4j
public class DateExample {

    @SneakyThrows
    public void strToDate() {
        String strDate = "2021-06-25 12:34:09";
        // java.text.SimpleDateFormat
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = format.parse(strDate);
        log.info("{}", date);
    }

    public void dateToStr() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = format.format(date);
        log.info("{}", dateStr);

        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
        log.info("{}", format2.format(date));
    }
}
