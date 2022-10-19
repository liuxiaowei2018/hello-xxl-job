package com.open.springboot.starter.xxljob.request;

import com.open.springboot.starter.xxljob.util.AssertUtil;
import lombok.Data;

/**
 * @author liuxiaowei
 * @date 2022年10月18日 15:03
 * @Description xxl-job开放API的抽象请求类
 */
@Data
public abstract class BaseXxlJobApiRequest<T> {

    /**
     * accessToken，开放API和普通API不同，普通API需要cookie，开放API需要accessToken
     *
     * @return
     */
    private String accessToken;

    /**
     * 请求路径
     *
     * @return
     */
    public abstract String getUrl();

    /**
     * 返回结果的类型
     *
     * @return
     */
    public abstract Class<T> getResponseClass();


    /**
     * 参数校验
     */
    public void check() {
        AssertUtil.notEmpty(accessToken, "accessToken");
    }
}
