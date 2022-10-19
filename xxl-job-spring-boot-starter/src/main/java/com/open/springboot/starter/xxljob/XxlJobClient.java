package com.open.springboot.starter.xxljob;

import com.open.springboot.starter.xxljob.exception.XxlJobException;
import com.open.springboot.starter.xxljob.request.BaseXxlJobApiRequest;
import com.open.springboot.starter.xxljob.request.BaseXxlJobRequest;

/**
 * @author liuxiaowei
 * @date 2022年10月18日 15:03
 * @Description
 */
public interface XxlJobClient {

    /**
     * 执行普通请求
     *
     * @param request
     * @param <T>
     * @return
     */
    <T> T execute(BaseXxlJobRequest<T> request) throws XxlJobException;

    /**
     * 执行开放API的请求
     *
     * @param request
     * @param <T>
     * @return
     */
    <T> T execute(BaseXxlJobApiRequest<T> request);
}
