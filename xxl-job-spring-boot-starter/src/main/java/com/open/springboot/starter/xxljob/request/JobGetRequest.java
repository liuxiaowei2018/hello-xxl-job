package com.open.springboot.starter.xxljob.request;

import com.open.springboot.starter.xxljob.response.JobGetResponse;
import lombok.Data;

/**
 * @author liuxiaowei
 * @date 2022年10月18日 17:21
 * @Description 分页查询执行器
 */
@Data
public class JobGetRequest extends BaseXxlJobRequest<JobGetResponse> {

    private String jobGroup;
    private String executorHandler;
    private String jobDesc;
    private String author;
    private String triggerStatus = "-1";
    private Integer start=0;
    private Integer length = 10;

    /**
     * 请求路径
     *
     * @return
     */
    @Override
    public String getUrl() {
        return "/jobinfo/pageList";
    }

    /**
     * 返回结果的类型
     *
     * @return
     */
    @Override
    public Class<JobGetResponse> getResponseClass() {
        return JobGetResponse.class;
    }
}
