package com.rsupport.simfle.chat.room;

import com.rsupport.simfle.chat.user.User;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends ReactiveCrudRepository<Room,String>{
}
