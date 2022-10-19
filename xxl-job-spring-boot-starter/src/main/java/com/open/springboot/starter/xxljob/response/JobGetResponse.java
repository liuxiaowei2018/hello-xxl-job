package com.open.springboot.starter.xxljob.response;

import lombok.Data;

import java.util.List;

/**
 * @author liuxiaowei
 * @date 2022年10月18日 15:14
 * @Description
 */
@Data
public class JobGetResponse {

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
         * 任务ID
         */
        private int id;
        /**
         * 执行器ID
         */
        private String jobGroup;
        /**
         * 执行器方法
         */
        private String executorHandler;
    }
}
