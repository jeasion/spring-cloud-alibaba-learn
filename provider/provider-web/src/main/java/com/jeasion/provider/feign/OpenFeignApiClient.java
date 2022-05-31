package com.jeasion.provider.feign;

import com.jeasion.provider.api.OpenFeignApi;
import com.jeasion.provider.service.ProviderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author liushanping
 */

@RestController
@RequestMapping("/api/")
public class OpenFeignApiClient implements OpenFeignApi {
    @Resource
    private ProviderService providerService;

    @Override
    @GetMapping("/hello")
    public String hello() {
        return "openFeign ;" + providerService.hello();
    }
}
