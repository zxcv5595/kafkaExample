package com.example.producer;

import com.example.producer.dto.OrderCompletedMessage;
import com.example.producer.kafka.OrderEventAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final OrderEventAdapter orderEventAdapter;

    @PostMapping("/order")
    public void order(@RequestBody String name) {
        OrderCompletedMessage newMessage = OrderCompletedMessage.builder()
                .name("James")
                .price(7777)
                .build();

        orderEventAdapter.send(newMessage);

    }

}
