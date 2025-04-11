package com.finalproject.backend.chats.streams;

import com.finalproject.backend.common.config.logging.AppLogger;
import com.finalproject.backend.common.dynamodb.tables.Chat;
import lombok.Getter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

/**
 * This class contains the chat stream.
 */
@Component
public class ChatStream {

  /**
   * the chat sink for the stream.
   */
  private final Sinks.Many<Chat> chatSink = Sinks.many().multicast().onBackpressureBuffer();

  /**
   * A getter for the stream.
   */
  @Getter
  private final Flux<Chat> chatStream = chatSink.asFlux();

  /**
   * Function to publish a chat to the sink.
   *
   * @param chat the chat to publish.
   */
  public void publish(Chat chat) {
    AppLogger.info("Publishing chat to sink: " + chat.getChatId());
    Sinks.EmitResult result = chatSink.tryEmitNext(chat);
    if (result.isFailure()) {
      AppLogger.error("Failed to emit chat: " + result);
    }
  }
}
