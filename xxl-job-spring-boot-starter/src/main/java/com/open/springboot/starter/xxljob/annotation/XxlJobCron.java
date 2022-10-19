package com.open.springboot.starter.xxljob.annotation;

import java.lang.annotation.*;

/**
 * @author liuxiaowei
 * @date 2022年10月18日 15:01
 * @Description
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface XxlJobCron {

    /*
     * 任务执行CRON表达式
     */
    String cron() default "";

    /**
     * 固定fixed 最小秒
     * @return
     */
    long fixedDelay() default -1;

    /**
     * 固定rate 最小秒
     * @return
     */
    long fixedRate() default -1;

    /**
     * 延迟
     * @return
     */
    long initialDelay() default -1;

    /*
     * 负责人
     */
    String author() default "xxl-job";

    /*
     * 报警邮件
     */
    String alarmEmail() default "";

    /*
     * 执行器描述
     */
    String desc() default "";

    /*
     * 执行器，任务参数
     */
    String param() default "";

    /*
     * 失败重试次数
     */
    int failRetryCount() default 0;

    /*
     * 任务执行超时时间，单位秒
     */
    int timeout() default 0;
}
