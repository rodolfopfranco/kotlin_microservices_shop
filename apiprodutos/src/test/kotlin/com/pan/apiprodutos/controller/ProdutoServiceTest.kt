package com.pan.apiprodutos.controller

import com.pan.apiprodutos.component.ProductComponent
import com.pan.apiprodutos.component.ProductRequestComponent
import com.pan.apiprodutos.dto.mapper.ProdutoMapper
import com.pan.apiprodutos.dto.mapper.ProdutoMapperImpl
import com.pan.apiprodutos.repository.ProdutoRepository
import com.pan.apiprodutos.service.ProdutoService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

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
    fun `should save product`(){
        val produtoParaSalvar = ProductComponent.createActiveProduct()
        val produtoSalvo = ProductComponent.createActiveProduct()
        val produtoRequest = ProductRequestComponent.createBasicActive()

        `when`(produtoMapper.toEntity(produtoRequest))
            .thenReturn(produtoParaSalvar)
        `when`(produtoRepository.save(produtoParaSalvar))
            .thenReturn(produtoSalvo)

        val produtoRetornado = produtoService.salvarProduto(produtoRequest)

        assertEquals(produtoSalvo, produtoRetornado)
    }
}