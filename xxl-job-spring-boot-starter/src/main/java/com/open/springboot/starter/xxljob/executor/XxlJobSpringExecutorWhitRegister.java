package com.open.springboot.starter.xxljob.executor;

import cn.hutool.core.util.StrUtil;
import com.open.springboot.starter.xxljob.XxlJobClient;
import com.open.springboot.starter.xxljob.annotation.XxlJobCron;
import com.open.springboot.starter.xxljob.properties.XxlJobProperties;
import com.open.springboot.starter.xxljob.request.ExecutorAddRequest;
import com.open.springboot.starter.xxljob.request.ExecutorGetRequest;
import com.open.springboot.starter.xxljob.request.JobAddRequest;
import com.open.springboot.starter.xxljob.request.JobGetRequest;
import com.open.springboot.starter.xxljob.response.ExecutorGetResponse;
import com.open.springboot.starter.xxljob.response.JobGetResponse;
import com.open.springboot.starter.xxljob.util.JsonUtils;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import com.xxl.job.core.glue.GlueFactory;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author liuxiaowei
 * @date 2022年10月18日 17:26
 * @Description
 */
@Slf4j
public class XxlJobSpringExecutorWhitRegister extends XxlJobSpringExecutor {

    @Autowired
    private XxlJobProperties xxlJobProperties;
    @Autowired
    private XxlJobClient xxlJobClient;

    public XxlJobSpringExecutorWhitRegister() {
    }

    @Override
    public void afterSingletonsInstantiated() {
        this.initJobHandlerMethodRepository(getApplicationContext());
        GlueFactory.refreshInstance(1);

        try {
            super.start();
        } catch (Exception var2) {
            throw new RuntimeException(var2);
        }
    }

    private void initJobHandlerMethodRepository(ApplicationContext applicationContext) {
        if (applicationContext == null) {
            return;
        }
        String jobGroup = null;
        if (xxlJobProperties.isAutoRegister()) {
            jobGroup = registExcutor();
            log.info("jobGroup:" + jobGroup);
        }
        // init job handler from method
        String[] beanDefinitionNames = applicationContext.getBeanNamesForType(Object.class, false, true);
        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = applicationContext.getBean(beanDefinitionName);

            Map<Method, XxlJob> annotatedMethods = null;   // referred to ：org.springframework.context.event.EventListenerMethodProcessor.processBean
            try {
                annotatedMethods = MethodIntrospector.selectMethods(bean.getClass(),
                        new MethodIntrospector.MetadataLookup<XxlJob>() {
                            @Override
                            public XxlJob inspect(Method method) {
                                return AnnotatedElementUtils.findMergedAnnotation(method, XxlJob.class);
                            }
                        });
            } catch (Throwable ex) {
                log.error("xxl-job method-jobhandler resolve error for bean[" + beanDefinitionName + "].", ex);
            }
            if (annotatedMethods == null || annotatedMethods.isEmpty()) {
                continue;
            }

            for (Map.Entry<Method, XxlJob> methodXxlJobEntry : annotatedMethods.entrySet()) {
                Method executeMethod = methodXxlJobEntry.getKey();
                XxlJob xxlJob = methodXxlJobEntry.getValue();
                // regist
                registJobHandler(xxlJob, bean, executeMethod);
                // regist cron task
                if (xxlJobProperties.isAutoRegister()) {
                    XxlJobCron xxlJobCron = AnnotationUtils.findAnnotation(executeMethod, XxlJobCron.class);
                    // 有注解，并且jobGroup不为空
                    if (xxlJobCron != null && StrUtil.isNotBlank(jobGroup)) {
                        // 注册任务
                        registJobHandlerCronTask(jobGroup, xxlJob.value(), xxlJobCron);
                    }
                }
            }
        }
    }

    private String registExcutor() {
        // 初始化执行器
        ExecutorGetRequest getRequest = new ExecutorGetRequest();
        getRequest.setAppname(xxlJobProperties.getAppname());
        getRequest.setStart(0);
        getRequest.setLength(1);
        ExecutorGetResponse getResponse = xxlJobClient.execute(getRequest);
        if (getResponse.getRecordsTotal() == 0) {
            // 添加执行器
            ExecutorAddRequest addRequest = new ExecutorAddRequest();
            addRequest.setTitle("自动注册服务器");
            addRequest.setAppname(xxlJobProperties.getAppname());
            String addResponse = xxlJobClient.execute(addRequest);
            log.info("addResponse：{}", JsonUtils.obj2json(addResponse));
            // 再次获取
            getResponse = xxlJobClient.execute(getRequest);
        }
        String jobGroup = String.valueOf(getResponse.getData().get(0).getId());
        return jobGroup;
    }

    private void registJobHandlerCronTask(String jobGroup, String executorHandler, XxlJobCron xxlJobCron) {
        // 判断是否存在任务
        JobGetRequest jobGetRequest = new JobGetRequest();
        jobGetRequest.setJobGroup(jobGroup);
        jobGetRequest.setExecutorHandler(executorHandler);
        JobGetResponse getResponse = xxlJobClient.execute(jobGetRequest);
        // 不存在则添加
        if (getResponse.getRecordsTotal() == 0) {
            JobAddRequest request = new JobAddRequest();
            request.setJobGroup(Integer.parseInt(jobGroup))
                    .setJobDesc(StrUtil.isNotBlank(xxlJobCron.desc()) ? xxlJobCron.desc() : "自动创建" + executorHandler)
                    .setExecutorHandler(executorHandler)
                    .setExecutorParam(xxlJobCron.param())
                    .setExecutorTimeout(xxlJobCron.timeout())
                    .setExecutorFailRetryCount(xxlJobCron.failRetryCount())
                    .setAlarmEmail(StrUtil.isNotBlank(xxlJobCron.alarmEmail()) ? xxlJobCron.alarmEmail() : xxlJobProperties.getAlarmEmail())
                    .setAuthor(StrUtil.isNotBlank(xxlJobCron.author()) ? xxlJobCron.author() : "xxl-job");
            if (StrUtil.isNotBlank(xxlJobCron.cron())) {
                request.setScheduleType("CRON")
                        .setScheduleConf(xxlJobCron.cron())
                        .setSchedule_conf_CRON(xxlJobCron.cron());
            } else if (xxlJobCron.fixedRate() != -1) {
                request.setScheduleType("FIX_RATE")
                        .setScheduleConf(String.valueOf(xxlJobCron.fixedRate() / 1000 == 0 ? 1 : xxlJobCron.fixedRate() / 1000))
                        .setSchedule_conf_FIX_RATE(String.valueOf(xxlJobCron.fixedRate() / 1000 == 0 ? 1 : xxlJobCron.fixedRate() / 1000));
            } else if (xxlJobCron.fixedDelay() != -1) {
                // 当前版本不支持FIX_DELAY
                request.setScheduleType("FIX_RATE")
                        .setScheduleConf(String.valueOf(xxlJobCron.fixedDelay() / 1000 == 0 ? 1 : xxlJobCron.fixedDelay() / 1000))
                        .setSchedule_conf_FIX_RATE(String.valueOf(xxlJobCron.fixedDelay() / 1000 == 0 ? 1 : xxlJobCron.fixedDelay() / 1000));
            }
            String result = xxlJobClient.execute(request);
            log.info(result);
        }
    }
}
