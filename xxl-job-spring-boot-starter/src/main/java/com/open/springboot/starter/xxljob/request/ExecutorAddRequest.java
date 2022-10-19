package com.open.springboot.starter.xxljob.request;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author liuxiaowei
 * @date 2022年10月18日 15:11
 * @Description  添加执行器
 * 说明：
 *  添加完执行器后，若实时性要求不高，无需显示调用注册接口(/api/registry)，客户端会自动定时注册
 *  客户端自动注册任务(每30s注册1次)：{@link com.xxl.job.core.thread.ExecutorRegistryThread}
 */
@Data
@Accessors(chain = true)
public class ExecutorAddRequest extends BaseXxlJobRequest<String>{

    private String appname;
    private String title;

    /**
     * 执行器地址类型：0=自动注册 1=手动录入
     */
    private int addressType = 0;

    /**
     * 请求路径
     *
     * @return
     */
    @Override
    public String getUrl() {
        return "/jobgroup/save";
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
