package com.rsupport.simfle.chat.config;

import com.rsupport.simfle.chat.room.RoomHandler;
import com.rsupport.simfle.chat.server.ServerHandler;
import com.rsupport.simfle.chat.user.Message;
import com.rsupport.simfle.chat.user.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

@Configuration
public class ApplicationConfig implements WebFluxConfigurer {

    @Bean
    public UnicastProcessor<Message> messagePublisher() {
        return UnicastProcessor.create();
    }

    @Bean
    public Flux<Message> messages(UnicastProcessor<Message> messagePublisher) {
        return messagePublisher.replay(25).autoConnect();
    }

    @Bean
    public HandlerMapping webSocketHandlerMapping(UnicastProcessor<Message> eventPublisher, Flux<Message> messages) {
        Map<String, WebSocketHandler> map = new HashMap<>();
        map.put("/event-emitter", new ChatSocketHandler(eventPublisher, messages));
        SimpleUrlHandlerMapping handlerMapping = new SimpleUrlHandlerMapping();
        handlerMapping.setOrder(1);
        handlerMapping.setUrlMap(map);
        return handlerMapping;
    }

    @Bean
    public WebSocketHandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter();
    }


    @Bean
    public RouterFunction<ServerResponse> routes(UserHandler userHandler,
                                                 RoomHandler roomHandler,
                                                 ServerHandler serverHandler) {


        return RouterFunctions.route(GET("/"), serverRequest ->
                ServerResponse.ok().body(BodyInserters.fromResource(new ClassPathResource("static/index.html")))
        ).andRoute(GET("/room"), roomHandler::getList)
                .andRoute(POST("/room"), roomHandler::createRoom)
                .andRoute(POST("/room/{roomId}/{userId}"), roomHandler::join)
                .andRoute(POST("/user"), userHandler::saveUser)
                .andRoute(GET("/server"), serverHandler::getList);
    }
}
