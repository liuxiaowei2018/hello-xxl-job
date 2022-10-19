package com.open.springboot.starter.xxljob;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.open.springboot.starter.xxljob.exception.ErrorConstant;
import com.open.springboot.starter.xxljob.exception.XxlJobException;
import com.open.springboot.starter.xxljob.properties.XxlJobProperties;
import com.open.springboot.starter.xxljob.request.BaseXxlJobApiRequest;
import com.open.springboot.starter.xxljob.request.BaseXxlJobRequest;
import com.open.springboot.starter.xxljob.request.CookieGetRequest;
import com.open.springboot.starter.xxljob.response.BaseXxlJobResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liuxiaowei
 * @date 2022年10月18日 15:02
 * @Description
 */
@Slf4j
public abstract class AbstractXxlJobClient implements XxlJobClient {

    @Autowired
    private XxlJobProperties xxlJobProperties;

    /**
     * 有些接口的返回不一样，没有code，坑爹，特殊处理一下吧
     * 例：比如/jobgroup/pageList，直接用的Map返回
     */
    private static final List<String> UNCHECK_RESULT_API = new ArrayList<>();
    static {
        UNCHECK_RESULT_API.add("/jobgroup/pageList");
        UNCHECK_RESULT_API.add("/jobinfo/pageList");
    }

    @Override
    public <T> T execute(BaseXxlJobRequest<T> request) throws XxlJobException {
        request.check();

        String uri = request.getUrl();
        log.info("请求xxl-job api,url：{},req：{}", uri, JSONUtil.toJsonStr(request));
        String resultBody = HttpRequest.post(xxlJobProperties.getAdminAddresses() + uri)
                .form(request.toMap())
                .cookie(getCookie())
                .execute()
                .body();
        log.info("xxl-job api响应,url：{},result：{}", uri, resultBody);

        if (UNCHECK_RESULT_API.contains(uri)) {
            return JSONUtil.toBean(resultBody, request.getResponseClass());
        }

        BaseXxlJobResponse<T> response = JSONUtil.toBean(resultBody, BaseXxlJobResponse.class);
        if (!response.isOk()) {
            log.error("xxl-job api[{}]执行失败,result：{}", uri, JSONUtil.toJsonStr(response));
            throw new XxlJobException(response.getMsg());
        }
        return response.getContent();
    }

    /**
     * 模拟登录，获取cookie
     *
     * @return
     */
    private String getCookie() {
        CookieGetRequest request = new CookieGetRequest();
        request.setUserName(xxlJobProperties.getLoginUserName());
        request.setPassword(xxlJobProperties.getLoginPwd());

        log.debug("获取xxl cookie,req：{}", JSONUtil.toJsonStr(request));
        HttpResponse response = HttpRequest.post(xxlJobProperties.getAdminAddresses() + request.getUrl())
                .form(request.toMap())
                .execute();
        if (!response.isOk()) {
            log.error("调用xxl获取cookie失败,response：{}", JSONUtil.toJsonStr(response.body()));
            throw new XxlJobException(ErrorConstant.INVOKE_XXL_GET_COOKIE_FAILED);
        }

        List<HttpCookie> cookies = response.getCookies();
        log.debug("获取xxl cookie成功,cookies：{}", JSONUtil.toJsonStr(cookies));

        StringBuilder sb = new StringBuilder();
        cookies.stream().forEach(cookie -> sb.append(cookie.toString()));
        return sb.toString();
    }

    /**
     * 执行开放API的请求
     *
     * @param request
     * @return
     */
    @Override
    public <T> T execute(BaseXxlJobApiRequest<T> request) {
        request.check();
        String uri = request.getUrl();
        log.info("请求xxl-job 开放api,url：{},req：{}", uri, JSONUtil.toJsonStr(request));
        String resultBody = HttpRequest.post(xxlJobProperties.getAdminAddresses() + uri)
                .body(JSONUtil.toJsonStr(request))
                .header("XXL-JOB-ACCESS-TOKEN", xxlJobProperties.getAccessToken())
                .execute()
                .body();
        log.info("xxl-job 开放api响应,url：{},result：{}", uri, resultBody);

        BaseXxlJobResponse<T> response = JSONUtil.toBean(resultBody, BaseXxlJobResponse.class);
        if (!response.isOk()) {
            log.error("xxl-job 开放api[{}]执行失败,result：{}", uri, JSONUtil.toJsonStr(response));
            throw new XxlJobException(response.getMsg());
        }
        return response.getContent();
    }

}
