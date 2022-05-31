package com.jeasion.consumer.controller;

import com.jeasion.provider.api.OpenFeignApi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author liushanping
 */
@RestController
@RequestMapping("/")
public class ConsumerController {
    @Resource
    private OpenFeignApi openFeignApi;

    @GetMapping("/hello")
    public String hello(){
        return openFeignApi.hello();
    }

}
