package com.open.springboot.starter.xxljob.request;

import com.open.springboot.starter.xxljob.util.AssertUtil;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author liuxiaowei
 * @date 2022年10月18日 17:20
 * @Description 添加执行任务
 */
@Data
@Accessors(chain = true)
public class JobAddRequest extends BaseXxlJobRequest<String> {

    /**
     * 主键ID
     */
    private int id;

    /**
     * <pre>
     * 执行器主键ID
     * 说明：该字段有两种获取方式
     *      1、显示创建执行器，然后将id配置进来，最简单
     *      2、如果需要动态在代码中创建执行器，则创建好后需要根据[appName]去xxl-job查询[执行器id] {@link ExecutorGetRequest}
     * </pre>
     */
    private int jobGroup;
    /**
     * 任务执行CRON表达式
     */
    private String jobCron;

    /**
     * 调度类型 CRON FIX_RATE FIX_DELAY
     */
    private String scheduleType;
    /**
     * 调度配置，值含义取决于调度类型
     */
    private String scheduleConf;
    /**
     * FIX_RATE
     */
    private String schedule_conf_FIX_RATE;
    /**
     * FIX_DELAY
     */
    private String schedule_conf_FIX_DELAY;
    /**
     * cron
     */
    private String schedule_conf_CRON;
    /**
     * 调度过期策略  com.xxl.job.admin.core.scheduler.MisfireStrategyEnum
     */
    private String misfireStrategy = "DO_NOTHING";

    /**
     * 任务描述
     */
    private String jobDesc;

    private Date addTime;

    private Date updateTime;

    /**
     * 负责人
     */
    private String author;
    /**
     * 报警邮件
     */
    private String alarmEmail;

    /**
     * 执行器，任务Handler名称
     */
    private String executorHandler;
    /**
     * 执行器，任务参数
     */
    private String executorParam;

    /**
     * <pre>
     * 执行器路由策略
     *   FIRST：第一个
     *   LAST：最后一个
     *   ROUND：轮询
     *   RANDOM：随机
     *   CONSISTENT_ASH：一致性HASH
     *   LEAST_FREQUENTLY_USED：最不经常使用
     *   LEAST_RECENTLY_USED：最近最久未使用
     *   FAILOVER：故障转移
     *   BUSYOVER：忙碌转移
     *   SHARDING_BROADCAST：分片广播
     * </pre>
     *
     * @see com.xxl.job.admin.core.route.ExecutorRouteStrategyEnum
     */
    private String executorRouteStrategy = "FIRST";

    /**
     * <pre>
     * 阻塞处理策略：
     *   单机串行：SERIAL_EXECUTION
     *   丢弃后续调度：DISCARD_LATER
     *   覆盖之前调度：COVER_EARLY
     * </pre>
     *
     * @see com.xxl.job.core.enums.ExecutorBlockStrategyEnum
     */
    private String executorBlockStrategy = "SERIAL_EXECUTION";

    /**
     * 任务执行超时时间，单位秒
     */
    private int executorTimeout = 0;

    /**
     * 失败重试次数
     */
    private int executorFailRetryCount = 0;

    /**
     * GLUE类型
     *
     * @see com.xxl.job.core.glue.GlueTypeEnum
     */
    private String glueType = "BEAN";
    /**
     * GLUE源代码
     */
    private String glueSource;
    /**
     * GLUE备注
     */
    private String glueRemark;
    /**
     * GLUE更新时间
     */
    private Date glueUpdatetime;

    /**
     * 子任务ID，多个逗号分隔
     */
    private String childJobId;

    /**
     * 调度状态：0-停止，1-运行
     */
    private int triggerStatus = 1;
    /**
     * 上次调度时间
     */
    private long triggerLastTime;
    /**
     * 下次调度时间
     */
    private long triggerNextTime;

    /**
     * 请求路径
     *
     * @return
     */
    @Override
    public String getUrl() {
        return "/jobinfo/add";
    }

    /**
     * check更新必填参数
     */
    @Override
    public void check() {
        AssertUtil.notEmpty(jobGroup, "jobGroup");
        // AssertUtil.notEmpty(jobCron, "jobCron");
        AssertUtil.notEmpty(scheduleType, "scheduleType");
        AssertUtil.notEmpty(scheduleConf, "scheduleConf");
        AssertUtil.notEmpty(jobDesc, "jobDesc");
        AssertUtil.notEmpty(author, "author");
        AssertUtil.notEmpty(executorHandler, "executorHandler");
        AssertUtil.notEmpty(executorBlockStrategy, "executorBlockStrategy");
        AssertUtil.notEmpty(executorRouteStrategy, "executorRouteStrategy");
        AssertUtil.notEmpty(glueType, "glueType");
    }

    /**
     * 返回结果的类型
     *
     * @return
     */
    @Override
    public Class<String> getResponseClass() {
        return String.class;
    }
}
