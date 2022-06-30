package com.pan.apiprodutos.service

import com.pan.apiprodutos.dto.mapper.ProdutoMapper
import com.pan.apiprodutos.dto.request.ProdutoEnviado
import com.pan.apiprodutos.dto.request.ProdutoRequest
import com.pan.apiprodutos.entity.Produto
import com.pan.apiprodutos.exception.ResourceException
import com.pan.apiprodutos.repository.ProdutoRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.math.BigDecimal

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

    fun reduzirEstoqueParaListaDeProdutos(produtosEnviados: List<ProdutoEnviado>): Boolean{
        if (!verificaSeTemEstoqueParaPedido(produtosEnviados))
            return false
        for (produtoEnviado in produtosEnviados){
            val produtoAtual = encontrarPorId(produtoEnviado.idProduto)
            reduzirEstoqueDoProduto(produtoAtual, produtoEnviado.qtd)
        }
        return true
    }

    fun reduzirEstoqueDoProduto(produto: Produto, quantidadeParaReduzir: BigDecimal): Produto {
        produto.quantidade = produto.quantidade.subtract(quantidadeParaReduzir)
        return produtoRepository.save(produto)
    }

    fun verificaSeTemEstoqueParaPedido(produtos: List<ProdutoEnviado>): Boolean{
        // Itera todos os itens e retorna true se tiver estoque pra todos eles
        for (produtoEnviado in produtos) {
            try{
                val produtoAtual = encontrarPorId(produtoEnviado.idProduto)
                if (!verificaEstoqueDoProduto(produtoAtual, produtoEnviado.qtd))
                    return false
            } catch (re: ResourceException){
                println(re.message)
                return false
            }
        }
        return true
    }

    fun verificaEstoqueDoProduto(produto:Produto, quantidadeParaReduzir: BigDecimal): Boolean {
        val produtoEncontrado = encontrarPorId(produto.id!!)
        val quantidadeFinal = produtoEncontrado.quantidade.subtract(quantidadeParaReduzir)
        return (quantidadeFinal.compareTo(BigDecimal(0))>0)
    }
}