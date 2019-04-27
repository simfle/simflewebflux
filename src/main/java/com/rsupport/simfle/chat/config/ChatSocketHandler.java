package com.rsupport.simfle.chat.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rsupport.simfle.chat.user.Message;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.UnicastProcessor;

import java.io.IOException;

@Component("ChatSocketHandler")
public class ChatSocketHandler implements WebSocketHandler {

    private UnicastProcessor<Message> eventPublisher;
    private Flux<String> outputMessages;
    private ObjectMapper mapper;

    public ChatSocketHandler(UnicastProcessor<Message> eventPublisher, Flux<Message> messages) {
        this.eventPublisher = eventPublisher;
        this.outputMessages = Flux.from(messages).map(this::toJSON);
        this.mapper = new ObjectMapper();
    }

    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {
        MessageSubscriber subscriber = new MessageSubscriber(eventPublisher);
        webSocketSession.receive().map(WebSocketMessage::getPayloadAsText)
                .map(this::toChatMessage)
                .subscribe(subscriber::onNext);
        return webSocketSession.send(outputMessages.map(webSocketSession::textMessage)).log();
    }

    public static class MessageSubscriber {
        private UnicastProcessor<Message> eventPublisher;
        public MessageSubscriber(UnicastProcessor<Message> eventPublisher) {
            this.eventPublisher = eventPublisher;
        }
        public void onNext(Message message) {
            eventPublisher.onNext(message);
        }
    }

    private Message toChatMessage(String json) {
        try {
            return mapper.readValue(json, Message.class);
        } catch (IOException e) {
            throw new RuntimeException("Invalid JSON:" + json, e);
        }
    }

    private String toJSON(Message message) {
        try {
            return mapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
