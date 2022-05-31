# 工程简介
Spring Cloud Alibaba 项目的学习

## 版本控制
1. Spring Cloud, Spring Cloud Alibaba, Spring Boot各版本较为混乱。如果版本错误，会导致整合出现各种问题，特别是Nacos常常出现各种问题。所以需要[版本控制](https://github.com/alibaba/spring-cloud-alibaba/wiki/版本说明)。
2. 版本约束方式：maven的``` <dependencyManagement> ```提供一种版本依赖管理方式，在此约束下，子包会默认依赖此标签定义下的父版本，不需要在额外指定版本。


## Srping Cloud Alibaba官方文档
参见[官方文档](https://spring-cloud-alibaba-group.github.io/github-pages/hoxton/zh-cn/index.html#_依赖管理)。


## 整合Nacos
1. 引入依赖文件，其中版本控制使用Spring Cloud Alibaba的全局版本约束，不建议单独指定版本
```xml
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
</dependency>
```
2. 配置项等信息常见[官方文档](https://spring-cloud-alibaba-group.github.io/github-pages/hoxton/zh-cn/index.html#_依赖管理)

## 整合OpenFeign
1. 基本原理
consumer端调用feign-api，注册中心将其分发到provider，服务端解析对应url，然后交由```DispatcherServlet -> HandlerMapping```分发调用，内部实现mv，然后将结果返回consumer
2. 依赖引入  
openFeign版本和包及其混乱，建议使用下面xml所示依赖（注意：需要同时引入OpenFeign & LoadBalancer），且版本约束为Spring Cloud 版本
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-openfeign</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-loadbalancer</artifactId>
    </dependency>
</dependencies>
```
3. 使用方式  
建议用法：在服务端provider处暴露feign的api接口，然后消费端consumer引入provider的api，然后在consumer处调用。  
3.1 provider-示例代码
```java
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
    * 当request进来，会根据url + path + mapping = absolutePath 前往{@codeorg.springframework.web.servlet.DispatcherServlet#initHandlerMappings(org.springframework.context.ApplicationContext)}中获取参数，然后进行mvc操作。
    * 这里因为实现类为RestController,且地址和absolutePath符合，实际就是调用实现类。
    *
    * @return
    */
    @GetMapping("/hello")
    String hello();

}


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

```
3.2 consumer-示例代码
```java
/**
 * 
 *{@code @EnableFeignClients(basePackages = {"com.jeasion.provider.openFeign"})}
 * {@code @EnableFeignClients} 用于开启feign
 *{@code basePackages = {"com.jeasion.provider.openFeign"})}是配置扫描哪些包
 *
 * 
 * @author liushanping
 */
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.jeasion.provider.openFeign"})
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

}



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

```
3.3 示例代码调用步骤
1. provider通过```dynamic proxy```将```OpenFeignApiClient -> OpenFeignApi```，并将```OpenFeignApiClient```的```requestMapping-url```装入```atchingBeans```，用于后续分发调用。
2. 注册中心获取provider服务。
3. consumer 引入 ```OpenFeignApi```，然后注入。
4. consumer发起request -> consumer调用OpenFeignApi -> provider获取解析对应url -> provider执行mvc操作 -> provider返回结果 -> consumer收到结果



