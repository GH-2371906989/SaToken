package com.gu.satokenitem.common.json;


import com.gu.satokenitem.common.response.Result;

public class BaseController {

    protected Result success() {
        return Result.success();
    }
    protected Result error() {
        return Result.error();
    }

}
