package com.open.springboot.starter.xxljob;

import cn.hutool.json.JSONUtil;
import com.open.springboot.starter.xxljob.config.XxlJobAutoConfiguration;
import com.open.springboot.starter.xxljob.properties.XxlJobProperties;
import com.open.springboot.starter.xxljob.request.*;
import com.open.springboot.starter.xxljob.response.ExecutorGetResponse;
import com.open.springboot.starter.xxljob.response.JobGetResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author liuxiaowei
 * @date 2022年10月18日 17:56
 * @Description
 */
@Slf4j
@SpringBootTest(classes = XxlJobAutoConfiguration.class)
@RunWith(SpringRunner.class)
public class XxlJobClientTest {

    @Autowired
    private XxlJobProperties xxlJobProperties;
    @Autowired
    private XxlJobClient xxlJobClient;

    // 执行器id
    private static final Integer jobGroup = 3;
    // 执行任务id
    private static final Integer jobId = 10;

    @Test
    public void testAddJob() {
        JobAddRequest request = new JobAddRequest();
        request.setJobGroup(1)
                .setJobDesc("支付-微信对账")
                .setScheduleType("CRON")
                .setScheduleConf("0 0/1 * * * ?")
//                .setJobCron("0 0/1 * * * ?")
                .setExecutorHandler("test")
                .setAuthor("马顶文");
        String result = xxlJobClient.execute(request);
        log.info(result);
    }

    @Test
    public void testUpdateJob() {
        JobUpdateRequest request = new JobUpdateRequest();
        request.setId(7)
                .setJobGroup(1)
//                .setJobCron("0 0 0 1 1 ? *")
                .setScheduleType("FIX_RATE")
                .setScheduleConf("5000")
                .setJobDesc("支付-微信对账")
                .setExecutorHandler("checkAccountTask")
                .setExecutorParam("paycenter-wx")
                .setAuthor("马顶文");
        String result = xxlJobClient.execute(request);
        log.info(result);
    }

    @Test
    public void testStopJob() {
        JobStopRequest request = new JobStopRequest();
        request.setId(jobId);
        String result = xxlJobClient.execute(request);
        log.info(result);
    }

    @Test
    public void testStartJob() {
        JobStartRequest request = new JobStartRequest();
        request.setId(jobId);
        String result = xxlJobClient.execute(request);
        log.info(result);
    }

    @Test
    public void testGetExecutor() {
        ExecutorGetRequest getRequest = new ExecutorGetRequest();
        getRequest.setAppname(xxlJobProperties.getAppname());
        getRequest.setStart(0);
        getRequest.setLength(1);
        ExecutorGetResponse getResponse = xxlJobClient.execute(getRequest);
        log.info(JSONUtil.toJsonStr(getResponse));
    }

    @Test
    public void testGetJob() {
        JobGetRequest jobGetRequest = new JobGetRequest();
        jobGetRequest.setJobGroup("1");
        jobGetRequest.setExecutorHandler("checkAccountTask");
        JobGetResponse getResponse = xxlJobClient.execute(jobGetRequest);
        log.info(JSONUtil.toJsonStr(getResponse));
    }

    @Test
    public void testAddExecutor() {
//        ExecutorGetRequest getRequest = new ExecutorGetRequest();
//        getRequest.setAppname(xxlJobProperties.getAppname());
//        ExecutorGetResponse list = xxlJobClient.execute(getRequest);
//        log.info(JsonUtik.toJsonStr(list.getData()));
//        log.info(String.valueOf(list.getRecordsTotal()));

        ExecutorAddRequest addRequest = new ExecutorAddRequest();
        addRequest.setTitle("对账中心执行器2");
        addRequest.setAppname(xxlJobProperties.getAppname());
        String addResponse = xxlJobClient.execute(addRequest);
        log.info("addResponse：{}", JSONUtil.toJsonStr(addResponse));

//        String ip = (xxlJobProperties.getIp() != null && xxlJobProperties.getIp().trim().length() > 0) ? xxlJobProperties.getIp() : IpUtil.getIp();
//        int port = xxlJobProperties.getPort() > 0 ? xxlJobProperties.getPort() : NetUtil.findAvailablePort(9999);
//
//        ExecutorRegistryRequest registryRequest = new ExecutorRegistryRequest();
//        // 需和xxl-job-admin配置的accessToken保持一致
//        registryRequest.setAccessToken(xxlJobProperties.getAccessToken());
//        registryRequest.setRegistryKey(xxlJobProperties.getAppname());
//        registryRequest.setRegistryValue(ip, port);
//        String registryResponse = xxlJobClient.execute(registryRequest);
//        log.info("registryResponse：{}", JsonUtik.toJsonStr(registryResponse));
    }
}