package com.pan.apicompras.service

import com.pan.apicompras.document.Compra
import com.pan.apicompras.exception.ResourceException
import com.pan.apicompras.repository.CompraRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class CompraService(
    private val compraRepository: CompraRepository
){

    fun listarCompras(): List<Compra> = compraRepository.findAll()

    fun salvarCompra(compra: Compra): Compra {
        return compraRepository.save(compra)
    }

    fun encontrarPorId(id: String): Compra{
        return compraRepository.findById(id)
            .orElseThrow{
                ResourceException(HttpStatus.NOT_FOUND, "Compra não encontrado")
            }
    }

    fun atualizarCompra(compra: Compra, id: String): Compra{
        encontrarPorId(id)
        compra.id = id
        return compraRepository.save(compra)
    }

    fun removerCompra(id: String){
        encontrarPorId(id)
        try{
            compraRepository.deleteById(id)
        } catch (e: Exception) {
            throw ResourceException(HttpStatus.INTERNAL_SERVER_ERROR, e.message)
        }
    }

    fun encontrarComprasPorUsuario(idUsuario: Int): List<Compra>? {
        val listaComprasDoUsuario = compraRepository.findByIdCliente(idUsuario)
        if (listaComprasDoUsuario?.size!! <=0)
            throw ResourceException(HttpStatus.NOT_FOUND, "Não foi encontrada nenhuma compra do usuário informado")
        return listaComprasDoUsuario
    }

    fun encontrarCompraPorUsuario(idUsuario: Int, purchaseId: String): List<Compra>? {
        val listaComprasDoUsuario = encontrarComprasPorUsuario(idUsuario)
        return listaComprasDoUsuario?.filter { it -> purchaseId == it.id}
    }

}