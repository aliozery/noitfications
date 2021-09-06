package com.notifications.noitfications.Config;


import org.mockito.internal.stubbing.OngoingStubbingImpl;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.PushBuilder;

@Configuration
public class QueueConfig {

    //bc: broadcast

    public static final String QUEUE= "email_queue";
    public static final String EXCHANGE= "queue_exchange";
    public static final String ROUTING_KEY="queue_routingkey";

    public static final String QUEUE_BC= "broadcast_queue";
    public static final String ROUTING_KEY_BC="bc_queue_routingkey";


    @Bean
    public Queue queue(){
        return new Queue(QUEUE);
    }
    @Bean
    public Queue queue_bc(){
        return new Queue(QUEUE_BC);
    }

    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(EXCHANGE);
    }
    @Bean
    public Binding binding(Queue queue, TopicExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }
    @Bean
    public Binding binding_bc(@Qualifier("queue_bc") Queue queue, TopicExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_BC);
    }

    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory){
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }

}
