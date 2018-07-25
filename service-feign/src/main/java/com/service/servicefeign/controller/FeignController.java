package com.service.servicefeign.controller;


import com.service.servicefeign.service.FeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignController {

    @Autowired
    FeignService feignService;

    @GetMapping(value = "/hi")
    public String sayHi() {
        return feignService.hello( "hello,springCloud" );
    }
}
