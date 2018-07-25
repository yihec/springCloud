package com.cloud.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ServerController {

    @Autowired
    private RestTemplate restTemplate;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @GetMapping("/hello2")
    public String hello(@RequestParam(value = "name", defaultValue = "forezp") String name) {
        logger.info("===<call trace-1>===");
        return restTemplate.getForObject("http://localhost:2001/hello?name="+name, String.class);
    }
}
