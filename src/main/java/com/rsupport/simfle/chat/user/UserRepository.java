package com.rsupport.simfle.chat.user;

import com.rsupport.simfle.chat.room.Room;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User,String> {

}
