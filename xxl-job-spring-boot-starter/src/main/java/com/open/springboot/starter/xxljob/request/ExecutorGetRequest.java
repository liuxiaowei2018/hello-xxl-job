package com.open.springboot.starter.xxljob.request;

import com.open.springboot.starter.xxljob.response.ExecutorGetResponse;
import lombok.Data;

/**
 * @author liuxiaowei
 * @date 2022年10月18日 15:12
 * @Description 分页查询执行器
 */
@Data
public class ExecutorGetRequest extends BaseXxlJobRequest<ExecutorGetResponse>{

    private String appname;
    private Integer start=0;
    private Integer length = 10;

    /**
     * 请求路径
     *
     * @return
     */
    @Override
    public String getUrl() {
        return "/jobgroup/pageList";
    }

    /**
     * 返回结果的类型
     *
     * @return
     */
    @Override
    public Class<ExecutorGetResponse> getResponseClass() {
        return ExecutorGetResponse.class;
    }
}
