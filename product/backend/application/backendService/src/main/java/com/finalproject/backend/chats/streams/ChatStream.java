package com.finalproject.backend.chats.streams;

import com.finalproject.backend.common.dynamodb.tables.Chat;
import lombok.Getter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;


@Component
public class ChatStream {

    private final Sinks.Many<Chat> chatSink = Sinks.many().multicast().onBackpressureBuffer();

    @Getter
    private final Flux<Chat> chatStream = chatSink.asFlux();

    public void publish(Chat chat) {
        chatSink.tryEmitNext(chat);
    }
}