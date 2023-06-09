package com.example.producer.kafka;

import com.example.producer.dto.OrderCompletedMessage;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventAdapter {

    private static final String TOPIC = "test-topic";
    private static final String ORDER_MESSAGE_KEY = "test-key";
    private final KafkaTemplate<String, OrderCompletedMessage> kafkaTemplate;

    public void send(OrderCompletedMessage orderCompletedMessage) {
        CompletableFuture<SendResult<String, OrderCompletedMessage>> future = kafkaTemplate.send(
                TOPIC, ORDER_MESSAGE_KEY, orderCompletedMessage);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Sent OrderCompletedMessage: {}", orderCompletedMessage);
                // 성공적으로 전송되었을 때의 처리 작업
            } else {
                log.error("Error sending OrderCompletedMessage: {}", ex.getMessage(), ex);
                // 전송 실패 시의 처리 작업
            }
        });
    }


}
