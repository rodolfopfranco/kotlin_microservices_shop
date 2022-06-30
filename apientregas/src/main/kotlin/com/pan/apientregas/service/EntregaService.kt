package com.pan.apientregas.service

import com.pan.apientregas.document.Entrega
import com.pan.apientregas.exception.ResourceException
import com.pan.apientregas.repository.EntregaRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class EntregaService (
    private val entregaRepository: EntregaRepository
        ){

    fun listarEntregas(): List<Entrega> = entregaRepository.findAll()

    fun salvarEntrega(entrega: Entrega): Entrega {
        return entregaRepository.save(entrega)
    }

    fun encontrarPorId(id: String): Entrega{
        return entregaRepository.findById(id)
            .orElseThrow{
                ResourceException(HttpStatus.NOT_FOUND, "Entrega não encontrada")
            }
    }

    fun atualizarEntrega(entrega: Entrega, id: String): Entrega{
        encontrarPorId(id)
        entrega.idEntrega = id
        return entregaRepository.save(entrega)
    }

    fun removerEntrega(id: String){
        encontrarPorId(id)
        try{
            entregaRepository.deleteById(id)
        } catch (e: Exception) {
            throw ResourceException(HttpStatus.INTERNAL_SERVER_ERROR, e.message)
        }
    }

    fun encontrarEntregasPorUsuario(idUsuario: Int): List<Entrega>? {
        val listaEntregasDoUsuario = entregaRepository.findByIdCliente(idUsuario)
        if (listaEntregasDoUsuario?.size!! <=0)
            throw ResourceException(HttpStatus.NOT_FOUND, "Não foi encontrada nenhuma entrega para o usuário informado")
        return listaEntregasDoUsuario
    }

    fun encontrarEntregaPorUsuario(idUsuario: Int, idEntrega: String): List<Entrega>? {
        val listaEntregasDoUsuario: List<Entrega>? = encontrarEntregasPorUsuario(idUsuario)
        return listaEntregasDoUsuario?.filter { it -> idEntrega == it.idEntrega}
    }
}