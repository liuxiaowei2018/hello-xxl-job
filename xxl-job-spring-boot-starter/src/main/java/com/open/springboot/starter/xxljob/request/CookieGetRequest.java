package com.open.springboot.starter.xxljob.request;

import lombok.Data;

/**
 * @author liuxiaowei
 * @date 2022年10月18日 15:10
 * @Description 模拟登录，获取Cookie
 */
@Data
public class CookieGetRequest extends BaseXxlJobRequest<String> {

    private String userName;
    private String password;

    /**
     * 请求路径
     *
     * @return
     */
    @Override
    public String getUrl() {
        return "/login";
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
