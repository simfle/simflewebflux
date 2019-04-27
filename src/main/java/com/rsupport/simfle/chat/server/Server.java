package com.rsupport.simfle.chat.server;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Server {

    @Id
    private String id;
    private String publicHost;
    private String publicPort;
    private String privateHost;
    private String privatePort;
}
