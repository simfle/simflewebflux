package com.rsupport.simfle.chat.user;

import com.rsupport.simfle.chat.room.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class UserHandler {

    @Autowired
    private ReactiveMongoOperations reactiveMongoOperations;


    public Mono<ServerResponse> saveUser(ServerRequest request) {
        User user = new User();
        user.setJob("Student");
        user.setName("changhee");
        Query query = new Query();
        Mono<User> userMono = reactiveMongoOperations.save(user);
        return ServerResponse.ok().body(userMono, User.class);
    }
}
