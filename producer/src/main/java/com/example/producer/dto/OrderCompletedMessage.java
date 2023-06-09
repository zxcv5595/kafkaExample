package com.example.producer.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderCompletedMessage {

    private String name;
    private int price;


}
