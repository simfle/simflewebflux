package com.rsupport.simfle.chat.server;

import com.rsupport.simfle.chat.room.Room;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ServerRepository extends ReactiveCrudRepository<Server, String> {

}
