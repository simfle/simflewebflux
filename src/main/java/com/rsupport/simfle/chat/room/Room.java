package com.rsupport.simfle.chat.room;

import com.rsupport.simfle.chat.user.User;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document
public class Room {

    @Id
    private String id;
    private String title;
    private List<User> userList = new ArrayList<>();
}
