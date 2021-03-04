package com.utils;

/**
 * create by: Bernie
 * description: 异常状态码信息
 * create time: 2020/6/22 15:18
 */
public enum ExceptionEnum {
    SUCCESS_EXCEPTION(0,"success"),
    SYSTEM_ABNORMALITY_EXCEPTION(40000, "系统异常，请联系管理员"),
    TOKEN_INVALIDATION_EXCEPTION(40010, "token无效"),
    TOKEN_EXPIRED_EXCEPTION(40012, "token失效，请重新登录"),
    TOKEN_ISNULL_EXCEPTION(40011, "token为空"),
    PWD_CHECK_INTERCEPTOR_EXCEPTION(40021, "您密码已到有效期限，请您尽快修改密码"),
    PARAMETER_IS_EMPTY_EXCEPTION(40030, "必传参数不能为空！"),
    PERMISSION_DENIED_EXCEPTION(40040, "没有权限！"),
    PERMISSION_MENU_DENIED_EXCEPTION(40050, "当前用户还没有配置权限哦，请联系管理员配置！"),
    BUSINESS_TIPS_EXCEPTION(50010, "已存在"),
    INCOMPLETE_PARAMETERS_EXCEPTION(50011, "参数不完整"),
    INVALID_SYMBOL_EXCEPTION(50012, "输入内容含有非法字符")
    ;
    private int code;
    private String msg;

    ExceptionEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}

