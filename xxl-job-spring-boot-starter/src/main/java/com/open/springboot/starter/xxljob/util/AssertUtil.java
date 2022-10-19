package com.open.springboot.starter.xxljob.util;

import com.open.springboot.starter.xxljob.exception.ErrorConstant;
import com.open.springboot.starter.xxljob.exception.XxlJobException;

/**
 * @author liuxiaowei
 * @date 2022年10月18日 15:04
 * @Description
 */
public class AssertUtil {

    public static void notEmpty(Object value, String fieldName) {
        if (value == null) {
            throw new XxlJobException(String.format(ErrorConstant.PARAM_MISS, fieldName));
        }

        if (value instanceof String) {
            if (((String) value).trim().length() == 0) {
                throw new XxlJobException(String.format(ErrorConstant.PARAM_MISS, fieldName));
            }
        }
    }
}
