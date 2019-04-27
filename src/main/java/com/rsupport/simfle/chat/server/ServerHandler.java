package com.rsupport.simfle.chat.server;

import com.rsupport.simfle.chat.room.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ServerHandler {

    @Autowired
    private ServerRepository serverRepository;

    public Mono<ServerResponse> getList(ServerRequest request) {
        Flux<Server> serverList = serverRepository.findAll();
        return ServerResponse.ok().body(serverList, Server.class);
    }
}
