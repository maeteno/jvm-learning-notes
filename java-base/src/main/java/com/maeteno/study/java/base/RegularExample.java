package com.maeteno.study.java.base;

import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则示例
 * Java 的正则功能由 java.util.regex.* 包提供。主要有两个类提供服务<br/>
 * - Pattern 通过正则规则字符串创建一个匹配的模板<br/>
 * - Matcher 匹配查找器<br/>
 *
 * @author Alan.Fu
 */
@Slf4j
public class RegularExample {
    /**
     * 基础使用
     */
    public void baseDemo() {
        // 按指定模式在字符串查找
        String line = "This order was placed for QT3000! OK?";
        // 匹配表达式
        String pattern = "(\\D*)(\\d+)(.*)";

        // 创建 Pattern 对象, 用于创建匹配的模板
        Pattern r = Pattern.compile(pattern);

        // 通过 Pattern::matcher 返回一个Matcher 匹配查找器
        Matcher m = r.matcher(line);
        if (m.find()) {
            log.info("Group 0: " + m.group(0));
            log.info("Group 1: " + m.group(1));
            log.info("Group 2: " + m.group(2));
            log.info("Group 3: " + m.group(3));
        } else {
            log.info("NO MATCH");
        }
    }

    public void groupDemo() {
        // 按指定模式在字符串查找
        String line = "18666667879";

        // 匹配表达式
        String pattern = "(?<name1>\\d{3})(\\d{5})(\\d{3})";

        // 创建 Pattern 对象, 用于创建匹配的模板
        Pattern r = Pattern.compile(pattern);

        // 通过 Pattern::matcher 返回一个Matcher 匹配查找器
        Matcher m = r.matcher(line);
        if (m.find()) {
            log.info("Group 0: " + m.group(0));
            log.info("Group 1: " + m.group(1));
            log.info("Group name1: " + m.group("name1"));
            log.info("Group 2: " + m.group(2));
            log.info("Group 3: " + m.group(3));
        } else {
            log.info("NO MATCH");
        }

        String newLine = m.replaceAll("${name1} - $3");
        log.info("newLine:{}", newLine);
    }

    /**
     * 字符串匹配
     */
    public void strDemo() {
        // 按指定模式在字符串查找
        String line = "18666667879";
        // 匹配表达式
        String regex = "^(?<h>\\d{3})(\\d{5})(\\d{3})$";

        String replace = line.replaceAll(regex, "${h}*****$3");

        String replace2 = Pattern.compile(regex).matcher(line).replaceAll("${h}*****$3");

        log.info("String rep:{}", replace);
        log.info("String rep2:{}", replace2);
    }
}
