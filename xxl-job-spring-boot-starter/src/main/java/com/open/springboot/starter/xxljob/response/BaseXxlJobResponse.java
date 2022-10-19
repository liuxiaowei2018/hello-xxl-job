package com.open.springboot.starter.xxljob.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liuxiaowei
 * @date 2022年10月18日 15:13
 * @Description
 */
@Data
public class BaseXxlJobResponse<T> implements Serializable {

    public static final long serialVersionUID = 1L;

    public static final Integer SUCCESS_CODE = 200;

    private Integer code;
    private String msg;
    private T content;

    public boolean isOk() {
        return SUCCESS_CODE.equals(code);
    }
}
