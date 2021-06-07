package com.wangxinenpu.springbootdemo.redismq;

/**
 * @author robust
 * @date 2021/6/7
 */
public interface MessagePublisher {
    void publish(String message);
}
