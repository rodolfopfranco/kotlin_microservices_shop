package com.pan.apientregas.messaging

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.pan.apientregas.constant.EXCHANGEPARAPRODUTO
import com.pan.apientregas.constant.QUEUE_ENTREGA_PROCESSADA
import com.pan.apientregas.document.Entrega
import com.pan.apientregas.service.EntregaService
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.stereotype.Service

@Service
class RabbitMQService(
    private val messageConverter: MessageConverter,
    private val entregaService: EntregaService,
    private val rabbitTemplate: RabbitTemplate
) {

    @RabbitListener(queues = [QUEUE_ENTREGA_PROCESSADA])
    fun recebeEntregaProcessada(message : Message){
        val entregaString = messageConverter.fromMessage(message).toString()
        val mapper = jacksonObjectMapper()
        val entrega = mapper.readValue<Entrega>(entregaString)
        entrega.idEntrega?.let { entregaService.atualizarEntrega(entrega, it) }
    }

    fun enviaEntrega(entrega: Entrega){
        val mapper = jacksonObjectMapper()
        val message = mapper.writeValueAsString(entrega)
        rabbitTemplate.convertAndSend(EXCHANGEPARAPRODUTO,"", message)
    }
}