package com.cloud.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ServerController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "forezp") String name,HttpServletRequest request) {
        logger.info("===<call trace-2, TraceId={}, SpanId={}>===",
                request.getHeader("X-B3-TraceId"), request.getHeader("X-B3-SpanId"));
        return "hi " + name + " ,i am from port:";
    }
}
