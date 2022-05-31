package com.jeasion.provider.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * 这里会有dynamic proxy。
 *
 * @FeignClient用于声明feign-api，
 * 其中name表示服务在注册中心的名称，
 * contextId表示此feign-api的名称
 * 注意url参数，其会全部拼接，也是是如果写的是```127.0.0.1/api/```，其和```127.0.0.1/api```不等价
 *
 * @author liushanping
 */

@FeignClient(name = "provider", path = "/api/")
public interface OpenFeignApi {
    /**
     *
     * 此处的url定义为feign-api的调度mapping，也就是会写入{@code org.springframework.web.servlet.DispatcherServlet.initHandlerMappings}
     *
     * 当request进来，会根据url + path + mapping = absolutePath 前往{@codeorg.springframework.web.servlet.DispatcherServlet#initHandlerMappings(org.springframework.context.ApplicationContext)}中获取参数
     * 然后进行mvc操作，这里因为实现类为RestController,且地址和absolutePath符合，实际就是调用实现类
     *
     * @return
     */
    @GetMapping("/hello")
    String hello();

}
