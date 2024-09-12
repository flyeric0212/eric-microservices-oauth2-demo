package top.flyeric.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class HomeController {

    @GetMapping("/public/home")
    public Mono<String> home() {
        return Mono.just("Welcome to eric public home");
    }

    @GetMapping("/private/home")
    public Mono<String> privateHome() {
        return Mono.just("Welcome to eric private home");
    }

}
