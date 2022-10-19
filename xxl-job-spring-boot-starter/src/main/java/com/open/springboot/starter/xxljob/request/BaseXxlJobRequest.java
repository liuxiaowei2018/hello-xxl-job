package com.open.springboot.starter.xxljob.request;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;

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
        return JSONUtil.toBean(JSONUtil.toJsonStr(this),Map.class);
    }

    /**
     * 参数校验
     */
    public void check() {}
}
