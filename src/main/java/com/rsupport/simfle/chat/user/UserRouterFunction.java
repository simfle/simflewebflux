package com.rsupport.simfle.chat.user;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
public class UserRouterFunction {

//    @Bean
//    public RouterFunction<ServerResponse> routes(UserHandler userHandler) {
//        return RouterFunctions.route(
//                GET("/"), serverRequest -> ServerResponse.ok().body(BodyInserters.fromResource(new ClassPathResource("static/index.html")))
//        );
//    }
}
