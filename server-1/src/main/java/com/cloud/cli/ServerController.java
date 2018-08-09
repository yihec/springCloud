package com.cloud.cli;

import com.cloud.cli.service.FeignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    ServerService serverService;
    @Autowired
    FeignService feignService;

    //本地接口
    @GetMapping("/HelloServer1")
    public String HelloServer1(){

        return serverService.HelloServer1("hi");
    }

    //调用了server-2的  HelloServer2接口
    @GetMapping("/HelloServer2")
    public String HelloServer2(){
        logger.info("===<call trace-1>===");
        return feignService.HelloServer2("hi");
    }
}
