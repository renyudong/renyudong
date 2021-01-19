package com.baidu.shop.base;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data //生成set 和 get 函數
@NoArgsConstructor //生成无参构造函数
public class Result<T> {

    private Integer code;//返回码

    private String message;//返回消息

    private T data;//返回数据

    public Result(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = (T) data;
    }
}

