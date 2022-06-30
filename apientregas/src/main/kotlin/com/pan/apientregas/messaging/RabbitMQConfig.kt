package com.pan.apientregas.messaging

import com.fasterxml.jackson.databind.ObjectMapper
import com.pan.apientregas.constant.EXCHANGE_ENTREGA
import com.pan.apientregas.constant.QUEUE_ENTREGA_PROCESSADA
import com.pan.apientregas.constant.QUEUE_PROCESSA_ENTREGA
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Exchange
import org.springframework.amqp.core.ExchangeBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.QueueBuilder
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMQConfig {

    @Bean
    fun messageConverter(objectMapper: ObjectMapper) : MessageConverter {
        return Jackson2JsonMessageConverter(objectMapper)
    }

    @Bean
    fun createCompraQueue() : Queue?{
        return Queue(QUEUE_PROCESSA_ENTREGA, true)
    }

    @Bean
    fun entregaModelBinding() : Binding {
        return BindingBuilder
            .bind(entregaModelQueue())
            .to(entregaExchange())
            .with("")
            .noargs()
    }

    @Bean
    fun entregaExchange() : Exchange {
        return ExchangeBuilder
            .directExchange(EXCHANGE_ENTREGA)
            .durable(true)
            .build()
    }

    @Bean
    fun entregaModelQueue() : Queue {
        return QueueBuilder
            .durable(QUEUE_ENTREGA_PROCESSADA)
            .build()
    }
}