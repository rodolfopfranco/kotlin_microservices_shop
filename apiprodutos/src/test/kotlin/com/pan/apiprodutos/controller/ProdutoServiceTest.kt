package com.pan.apiprodutos.controller

import com.pan.apiprodutos.component.ProductComponent
import com.pan.apiprodutos.component.ProductRequestComponent
import com.pan.apiprodutos.dto.mapper.ProdutoMapper
import com.pan.apiprodutos.dto.mapper.ProdutoMapperImpl
import com.pan.apiprodutos.exception.ResourceException
import com.pan.apiprodutos.repository.ProdutoRepository
import com.pan.apiprodutos.service.ProdutoService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class ProdutoServiceTest {
    //Mockito Version
    @Mock
    private lateinit var produtoRepository: ProdutoRepository
    @Mock
    private lateinit var produtoMapperImpl: ProdutoMapperImpl

    private lateinit var produtoMapper: ProdutoMapper
    private lateinit var produtoService: ProdutoService

    @BeforeEach
    fun setup(){
        produtoMapper = produtoMapperImpl
        produtoService = ProdutoService(produtoRepository, produtoMapper)
    }

    @Test
    fun `saving product receives a request and returns an entity`(){
        val produtoParaSalvar = ProductComponent.createActiveProduct()
        val produtoEsperado = ProductComponent.createActiveProduct()
        val produtoRequest = ProductRequestComponent.createBasicActive()

        `when`(produtoMapper.toEntity(produtoRequest))
            .thenReturn(produtoParaSalvar)
        `when`(produtoRepository.save(produtoParaSalvar))
            .thenReturn(produtoEsperado)

        val produtoRetornado = produtoService.salvarProduto(produtoRequest)

        assertEquals(produtoEsperado, produtoRetornado)
    }

    @Test
    fun `retrieving a product list`(){
        val listaEsperadaDeProdutos = ProductComponent.createAList()

        `when`(produtoRepository.findAll())
            .thenReturn(listaEsperadaDeProdutos)

        val listaDeProdutos = produtoService.listarProdutos()

        assertEquals(listaEsperadaDeProdutos, listaDeProdutos)
    }

    @Test
    fun `finds a product when given an ID`(){
        val produtoEsperado = ProductComponent.createActiveProduct()

        `when`(produtoRepository.findById(1))
            ?.thenReturn(Optional.of(produtoEsperado))

        val produtoEncontrado = produtoService.encontrarPorId(1)

        assertEquals(produtoEsperado, produtoEncontrado)
    }

    @Test
    fun `throws ResourceException when ID not found`(){
        `when`(produtoRepository.findById(2))
            .thenReturn(Optional.empty())
        assertThrows<ResourceException> {
            produtoService.encontrarPorId(2)
        }
    }

    @Test
    fun `updates the product with its id and returns the saved Product`(){

        val produtoAtual = ProductComponent.createActiveProduct()
        val produtoParaAtualizar = ProductComponent.createActiveProduct()
        val produtoEsperado = ProductComponent.createActiveProduct()
        produtoEsperado.id = 2
        val produtoRequest = ProductRequestComponent.createBasicActive()

        `when`(produtoMapper.toEntity(produtoRequest))
            .thenReturn(produtoParaAtualizar)
        `when`(produtoRepository.findById(2))
            .thenReturn(Optional.of(produtoAtual))
        `when`(produtoRepository.save(produtoParaAtualizar))
            .thenReturn(produtoEsperado)

        val produtoRetornado = produtoService.atualizarProduto(produtoRequest, 2)
        assertEquals(produtoEsperado, produtoRetornado)
    }

    @Test
    fun `updating a product which doesn't exist must throw ResourceException`(){
        val produtoEsperado = ProductComponent.createActiveProduct()
        produtoEsperado.id = 2
        val produtoRequest = ProductRequestComponent.createBasicActive()

        `when`(produtoRepository.findById(2))
            .thenReturn(Optional.empty())

        assertThrows<ResourceException> {
            val produtoRetornado = produtoService.atualizarProduto(produtoRequest, 2)
        }
    }

    @Test
    fun `Should remove the product with given ID`(){
        // Discover how to test void methods
        doNothing(). `when`(produtoRepository.deleteById(1))
        produtoService.removerProduto(1)
    }

}