package com.example.consumer.config;

import com.example.consumer.dto.OrderCompletedMessage;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Bean
    public ConsumerFactory<String, OrderCompletedMessage> orderMessageConsumerFactory() {
        JsonDeserializer<OrderCompletedMessage> orderCompletedMessageJsonDeserializer = new JsonDeserializer<>(OrderCompletedMessage.class);
        orderCompletedMessageJsonDeserializer.setRemoveTypeHeaders(false);
        orderCompletedMessageJsonDeserializer.addTrustedPackages("*");
        orderCompletedMessageJsonDeserializer.setUseTypeMapperForKey(true);

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), orderCompletedMessageJsonDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OrderCompletedMessage> orderMessageKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, OrderCompletedMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(orderMessageConsumerFactory());
        return factory;
    }
}
