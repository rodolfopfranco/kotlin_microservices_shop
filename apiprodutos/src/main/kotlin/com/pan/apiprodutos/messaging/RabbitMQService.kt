package com.pan.apiprodutos.messaging

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.pan.apiprodutos.dto.request.Entrega
import com.pan.apiprodutos.dto.request.EntregaStatus
import com.pan.apiprodutos.service.ProdutoService
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.stereotype.Service

@Service
class RabbitMQService(
    private val messageConverter: MessageConverter,
    private val produtoService: ProdutoService,
    private val rabbitTemplate: RabbitTemplate
) {

    @RabbitListener(queues = [QUEUE_PROCESSA_ENTREGA])
    fun recebeEProcessaEntrega(message : Message){
        val entregaString = messageConverter.fromMessage(message).toString()
        val mapper = jacksonObjectMapper()
        val entrega = mapper.readValue<Entrega>(entregaString)
        val statusProcessamento = produtoService.reduzirEstoqueParaListaDeProdutos(entrega.produtos)
        val entregaProcessada = alteraStatusEntrega(entrega, statusProcessamento)
        enviaEntregaDeVoltaComStatusAtualizado(entregaProcessada)
    }

    fun alteraStatusEntrega(entrega: Entrega, statusProcessamento: Boolean): Entrega{
        if (!statusProcessamento) {
            entrega.status = EntregaStatus.AGUARDANDO_ESTOQUE
            return entrega
        }
        entrega.status = EntregaStatus.ENTREGUE
        return entrega
    }

    fun enviaEntregaDeVoltaComStatusAtualizado(entrega: Entrega){
        val mapper = jacksonObjectMapper()
        val message = mapper.writeValueAsString(entrega)
        rabbitTemplate.convertAndSend(EXCHANGE_ENTREGA,KEY_PROCESSADA, message)
    }
}