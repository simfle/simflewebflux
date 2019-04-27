package com.rsupport.simfle.chat.room;

import com.rsupport.simfle.chat.user.User;

import com.rsupport.simfle.chat.user.UserRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class RoomHandler {

    @Autowired
    private ReactiveMongoOperations reactiveMongoOperations;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    public Mono<ServerResponse> createRoom(ServerRequest request) {
        Room room = new Room();
        room.setTitle("changheeRoom");
        Mono<Room> newRoom = roomRepository.save(room);
        return ServerResponse.ok().body(newRoom, Room.class);
    }

    public Mono<ServerResponse> getList(ServerRequest request) {
        Flux<Room> roomList = reactiveMongoOperations.findAll(Room.class);
        return ServerResponse.ok().body(roomList, Room.class);
    }

    public Mono<ServerResponse> join(ServerRequest request) {
        String roomId = request.pathVariable("roomId");
        String userId = request.pathVariable("userId");

        Query roomQuery = new Query();
        //roomQuery.addCriteria(Criteria.where("id").is(roomId).and("age").is(40));
        roomQuery.addCriteria(Criteria.where("id").is(roomId));
        Query userQuery = new Query();
        userQuery.addCriteria(Criteria.where("id").is(userId));
        Mono<Room> room = reactiveMongoOperations.findOne(roomQuery, Room.class);
        Mono<User> user = reactiveMongoOperations.findOne(userQuery, User.class);
        return ServerResponse.ok().body(room, Room.class);
    }

}
