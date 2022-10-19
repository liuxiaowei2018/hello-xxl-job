package com.open.springboot.starter.xxljob.request;

import lombok.Data;

/**
 * @author liuxiaowei
 * @date 2022年10月18日 17:22
 * @Description 启动执行任务
 */
@Data
public class JobStartRequest extends BaseXxlJobRequest<String> {

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
        return "/jobinfo/start";
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
