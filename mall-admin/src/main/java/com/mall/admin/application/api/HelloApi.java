package com.mall.admin.application.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloApi {
    @GetMapping("/hello")
    public String hello() {
        return "Hello, WebFlux !";
    }

    @GetMapping("/user")
    public Mono<Map> getUser() {
        Map user = new HashMap();
        user.put("p1","犬小哈");
        user.put("p2","欢迎关注我的公众号: 小哈学Java");
        return Mono.just(user);
    }
}
