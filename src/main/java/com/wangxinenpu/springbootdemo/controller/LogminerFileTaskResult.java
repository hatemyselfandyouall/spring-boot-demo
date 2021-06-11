package com.wangxinenpu.springbootdemo.controller;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LogminerFileTaskResult {

    private Long lastScn;

    private String lastTime;

}
