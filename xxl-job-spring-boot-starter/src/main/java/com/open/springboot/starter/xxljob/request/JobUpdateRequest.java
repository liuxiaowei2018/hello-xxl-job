package com.open.springboot.starter.xxljob.request;

import com.open.springboot.starter.xxljob.util.AssertUtil;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author liuxiaowei
 * @date 2022年10月18日 17:23
 * @Description 更新执行任务
 * 注意：某些字段xxl-job虽然不会校验必填，但是会将空值更新进数据库
 * 改进：先查询job的信息，然后改动要更新的字段，但是xxl-job没有提供根据id查询任务的接口
 *  1、要么改动xxl-job-admin，增加1个查询接口
 *  2、要么在动态添加任务时，自行保存任务参数，以便下次更新时使用
 */
@Data
@Accessors(chain = true)
public class JobUpdateRequest extends BaseXxlJobRequest<String> {

    /**
     * 主键ID
     */
    private int id;

    /**
     * 执行器主键ID
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
     * 注意：非必填，但如果为null，会被xxl-job更新到数据库中
     */
    private String executorHandler;
    /**
     * 执行器，任务参数
     * 注意：非必填，但如果为null，会被xxl-job更新到数据库中
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
        return "/jobinfo/update";
    }

    /**
     * check更新必填参数
     */
    @Override
    public void check() {
        AssertUtil.notEmpty(id, "id");
        AssertUtil.notEmpty(jobGroup, "jobGroup");
        AssertUtil.notEmpty(scheduleType, "scheduleType");
        AssertUtil.notEmpty(scheduleConf, "scheduleConf");
        AssertUtil.notEmpty(jobDesc, "jobDesc");
        AssertUtil.notEmpty(author, "author");
        AssertUtil.notEmpty(executorBlockStrategy, "executorBlockStrategy");
        AssertUtil.notEmpty(executorRouteStrategy, "executorRouteStrategy");
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
