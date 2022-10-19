package com.open.springboot.starter.xxljob.request;

import lombok.Data;

/**
 * @author liuxiaowei
 * @date 2022年10月18日 17:23
 * @Description
 */
@Data
public class JobStopRequest extends BaseXxlJobRequest<String> {

    /**
     * 任务id
     */
    private Integer id;

    /**
     * 请求路径
     *
     * @return
     */
    @Override
    public String getUrl() {
        return "/jobinfo/stop";
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
