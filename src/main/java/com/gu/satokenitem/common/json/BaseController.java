package com.gu.satokenitem.common.json;


import com.gu.satokenitem.common.response.Result;

public class BaseController {

    protected Result success() {
        return Result.success();
    }
    protected Result error() {
        return Result.error();
    }

    protected Result result(Integer code,String message) {
        return new Result(code,message);
    }

}
