package com.open.springboot.starter.xxljob.request;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author liuxiaowei
 * @date 2022年10月18日 15:16
 * @Description 注册执行器
 * 1、可以调用xxl-job的客户端API {@link com.xxl.job.core.biz.client.AdminBizClient}
 * 2、直接调用xxl-admin的api的方式，本项目采用此方式
 */
@Data
@Accessors(chain = true)
public class ExecutorRegistryRequest extends BaseXxlJobApiRequest<String> {

    private static final long serialVersionUID = 42L;

    private String registryGroup = "EXECUTOR";

    /**
     * 执行器AppName
     */
    private String registryKey;

    /**
     * 执行器地址(例：http://120.1.1.1:9999/)
     */
    private String registryValue;

    /**
     * 请求路径
     *
     * @return
     */
    @Override
    public String getUrl() {
        return "/api/registry";
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

    public void setRegistryValue(String ip, Integer port) {
        this.registryValue = "http://{ip_port}/".replace("{ip_port}", ip + ":" + port);
    }
}
