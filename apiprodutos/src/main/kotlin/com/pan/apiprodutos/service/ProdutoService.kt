package com.pan.apiprodutos.service

import com.pan.apiprodutos.dto.mapper.ProdutoMapper
import com.pan.apiprodutos.dto.request.ProdutoRequest
import com.pan.apiprodutos.entity.Produto
import com.pan.apiprodutos.exception.ResourceException
import com.pan.apiprodutos.repository.ProdutoRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class ProdutoService(
    private val produtoRepository: ProdutoRepository,
    private val produtoMapper: ProdutoMapper
) {

    fun listarProdutos(): List<Produto> = produtoRepository.findAll()

    fun salvarProduto(request: ProdutoRequest): Produto {
        val produtoEntidade: Produto =  produtoMapper.toEntity(request)
        return produtoRepository.save(produtoEntidade)
    }

    fun encontrarPorId(id: Int): Produto{
        return produtoRepository.findById(id)
            .orElseThrow{
                ResourceException(HttpStatus.NOT_FOUND, "Produto não encontrado")
            }
    }

    fun atualizarProduto(request: ProdutoRequest, id: Int): Produto{
        encontrarPorId(id)
        val produtoParaSalvar = produtoMapper.toEntity(request)
        produtoParaSalvar.id = id
        return produtoRepository.save(produtoParaSalvar)
    }

    fun removerProduto(id: Int){
        encontrarPorId(id)
        try{
            produtoRepository.deleteById(id)
        } catch (e: Exception) {
            throw ResourceException(HttpStatus.INTERNAL_SERVER_ERROR, e.message)
        }
    }

    fun ativar(id: Int): Produto {
        val produtoEncontrado = encontrarPorId(id)
        if(produtoEncontrado.isActive) throw ResourceException(HttpStatus.BAD_REQUEST, "Produto já está ativo")
        produtoEncontrado.isActive = true
        return produtoRepository.save(produtoEncontrado)
    }

    fun desativar(id: Int): Produto {
        val produtoEncontrado = encontrarPorId(id)
        if(!produtoEncontrado.isActive) throw ResourceException(HttpStatus.BAD_REQUEST, "Produto já está inativo")
        produtoEncontrado.isActive = false
        return produtoRepository.save(produtoEncontrado)
    }
}