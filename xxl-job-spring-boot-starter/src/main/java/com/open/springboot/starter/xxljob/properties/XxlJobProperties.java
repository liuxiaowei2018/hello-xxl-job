package com.open.springboot.starter.xxljob.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author liuxiaowei
 * @date 2022年10月18日 11:17
 * @Description
 */
@Data
@ConfigurationProperties(prefix = "xxl-job")
public class XxlJobProperties {

    /**
     * xxl job admin address.
     */
    private String adminAddresses;
    /**
     * xxl job admin registry access token.
     */
    private String accessToken;
    /**
     * xxl job registry name.
     */
    private String appname;
    /**
     * xxl job registry address.
     */
    private String address;
    /**
     * xxl job registry ip.
     */
    private String ip;
    /**
     * xxl job registry port.
     */
    private Integer port;
    /**
     * xxl job log files path.
     */
    private String logPath;
    /**
     * xxl job log files retention days.
     */
    private Integer logRetentionDays;

    private boolean autoRegister;
    private String alarmEmail;

    private String loginUserName = "admin";
    private String loginPwd = "123456";

}
