package com.gu.satokenitem.common.response;


import com.gu.satokenitem.common.emnu.StatusCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 服务器返回对象
 * 服务器处理返回的统一对象
 */
@ApiModel(value = "API返回参数")
@Data
public class Result {
    /**
     * 响应码：参考`ResultCode`
     */
    @ApiModelProperty(value = "响应码", required = true)
    private Integer code;

    /**
     * 消息内容
     */
    @ApiModelProperty(value = "响应消息", required = false)
    private String message;

    /**
     * 返回数据
     */
    @ApiModelProperty(value = "响应数据", required = false)
    private Map<String,Object> data = new HashMap<>();

    public Result(Integer code, String message, Map<String,Object> data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public  Result end(){
        return this;
    }

    public  Result put(String key,Object value){
        this.data.put(key, value);
        return this;
    }
    public  Result Message(String message){
        this.message = message;
        return this;
    }

    public static Result success() {
        return new Result(StatusCode.SUCCESS.getCode(), "success");
    }
    public static Result error() {
        return new Result(StatusCode.FAILURE.getCode(), "error");
    }

}
