package com.service.servicefeign.service;


import org.springframework.stereotype.Component;

@Component
public class FeignFallback implements FeignService{
    @Override
    public String hello(String name) {
        return "server-2 接口服务问题!";
    }
}
