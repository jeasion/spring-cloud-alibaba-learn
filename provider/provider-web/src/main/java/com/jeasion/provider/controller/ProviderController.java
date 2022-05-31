package com.jeasion.provider.controller;

import com.jeasion.provider.service.ProviderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author liushanping
 */
@RestController
@RequestMapping("/")
public class ProviderController {

    @Resource
    private ProviderService providerService;

    @GetMapping("/hello")
    public String hello() {
        return "provider controller ;" + providerService.hello();
    }
}
