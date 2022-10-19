package com.open.springboot.starter.xxljob.response;

import lombok.Data;

import java.util.List;

/**
 * @author liuxiaowei
 * @date 2022年10月18日 15:14
 * @Description
 */
@Data
public class ExecutorGetResponse {

    /**
     * 过滤后的总记录数
     */
    private int recordsFiltered;
    /**
     * 总记录数
     */
    private int recordsTotal;
    /**
     * 分页列表
     */
    private List<XxlJobGroupVo> data;

    @Data
    public static class XxlJobGroupVo {

        /**
         * 执行器id
         */
        private int id;
        /**
         * 执行器名称
         */
        private String title;
        /**
         * 执行器AppName
         */
        private String appname;
        private int addressType;
        private String addressList;
        private List<String> registryList;

    }
}
