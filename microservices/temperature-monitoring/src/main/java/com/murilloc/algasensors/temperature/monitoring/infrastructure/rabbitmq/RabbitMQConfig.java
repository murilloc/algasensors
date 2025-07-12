package com.murilloc.algasensors.temperature.monitoring.infrastructure.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    private static final String PROCESS_TEMPERATURE_NAME = "temperature-monitoring.process-temperature.v1";
    public static final String DEAD_LETTER_QUEUE_PROCESS_TEMPERATURE_NAME = PROCESS_TEMPERATURE_NAME + ".dlq";
    public static final String QUEUE_PROCESS_TEMPERATURE_NAME = PROCESS_TEMPERATURE_NAME + ".q";
    public static final String QUEUE_ALERTING_NAME = "temperature-monitoring.alerting.v1.q";


    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory rabbitConnectionFactory) {
        return new RabbitAdmin(rabbitConnectionFactory);
    }

    @Bean
    public Queue queueAlerting() {
        return QueueBuilder.durable(QUEUE_ALERTING_NAME).build();
    }

    @Bean
    public Queue queueProcessTemperature() {

        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", "");
        arguments.put("x-dead-letter-routing-key", DEAD_LETTER_QUEUE_PROCESS_TEMPERATURE_NAME);

        return QueueBuilder.durable(QUEUE_PROCESS_TEMPERATURE_NAME)
                .withArguments(arguments)
                .build();
    }

    @Bean
    public Queue deadLetterQueueProcessTemperature() {

        return QueueBuilder.durable(DEAD_LETTER_QUEUE_PROCESS_TEMPERATURE_NAME)
                .build();
    }

    public FanoutExchange exchange() {
        return ExchangeBuilder.fanoutExchange("temperature-processing.temperature-received.v1.e")
                .build();
    }


    @Bean
    public Binding bindingAlerting() {
        return BindingBuilder.bind(queueAlerting())
                .to(exchange());
    }

    @Bean
    public Binding bindingProcessTemperatureAlerting() {
        return BindingBuilder.bind(queueProcessTemperature())
                .to(exchange());
    }


}
