package com.open.springboot.starter.xxljob.request;

import cn.hutool.json.JSONUtil;
import com.open.springboot.starter.xxljob.util.JsonUtils;

import java.util.Map;

/**
 * @author liuxiaowei
 * @date 2022年10月18日 15:06
 * @Description
 */
public abstract class BaseXxlJobRequest<T> {

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
     * 对象转换为Map
     *
     * @return
     */
    public Map<String, Object> toMap() {
        //TypeReference<Map<String, Object>> typeReference = new TypeReference<Map<String, Object>>() {};
        return JsonUtils.json2map(JsonUtils.obj2json(this));
    }

    /**
     * 参数校验
     */
    public void check() {}
}
