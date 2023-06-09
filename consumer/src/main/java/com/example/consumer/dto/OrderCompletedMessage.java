package com.example.consumer.dto;

import lombok.Data;

@Data
public class OrderCompletedMessage {

    private String name;
    private int price;


}
