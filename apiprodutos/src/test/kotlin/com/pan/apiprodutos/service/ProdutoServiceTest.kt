package com.pan.apiprodutos.service

import com.pan.apiprodutos.component.ProductComponent
import com.pan.apiprodutos.component.ProductRequestComponent
import com.pan.apiprodutos.component.ProdutoEnviadoComponent
import com.pan.apiprodutos.dto.mapper.ProdutoMapper
import com.pan.apiprodutos.dto.mapper.ProdutoMapperImpl
import com.pan.apiprodutos.dto.request.ProdutoEnviado
import com.pan.apiprodutos.exception.ResourceException
import com.pan.apiprodutos.repository.ProdutoRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import java.math.BigDecimal
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
    fun `Should throw exception when delete by Id does not work`(){
        `when`(produtoRepository.findById(1))
            .thenReturn(Optional.of(ProductComponent.createActiveProduct()))
        `when`(produtoRepository.deleteById(1)).thenThrow(ResourceException(HttpStatus.INTERNAL_SERVER_ERROR, ""))

        assertThrows<ResourceException> { produtoService.removerProduto(1) }
    }

    @Test
    fun `Should do nothing when remove by ID works`(){
        `when`(produtoRepository.findById(1))
            .thenReturn(Optional.of(ProductComponent.createActiveProduct()))

        assertDoesNotThrow { produtoService.removerProduto(1) }
    }

    @Test
    fun `Should activate inactive Product`(){
        val produtoInativo = ProductComponent.createInactiveProduct()
        `when`(produtoRepository.findById(1))
            .thenReturn(Optional.of(produtoInativo))
        val produtoAtivo = produtoInativo.copy()
        produtoAtivo.isActive = true
        `when`(produtoRepository.save(produtoInativo)).thenReturn(produtoAtivo)

        val produtoAtivado = produtoService.ativar(1)

        assertEquals(produtoAtivo, produtoAtivado)
    }

    @Test
    fun `Should throw Exception when trying to activate already active Product`(){
        val produtoAtivo = ProductComponent.createActiveProduct()
        `when`(produtoRepository.findById(1))
            .thenReturn(Optional.of(produtoAtivo))

        assertThrows<ResourceException> { produtoService.ativar(1) }
    }

    @Test
    fun `Should inactivate active Product`(){
        val produtoAtivo = ProductComponent.createActiveProduct()
        `when`(produtoRepository.findById(1))
            .thenReturn(Optional.of(produtoAtivo))

        val produtoInativo = produtoAtivo.copy()
        produtoInativo.isActive = false
        `when`(produtoRepository.save(produtoAtivo)).thenReturn(produtoInativo)

        val produtoInativado = produtoService.desativar(1)

        assertEquals(produtoInativo, produtoInativado)
    }

    @Test
    fun `Should throw Exception when trying to inactivate already inactive Product`(){
        val produtoInativo = ProductComponent.createInactiveProduct()
        `when`(produtoRepository.findById(1))
            .thenReturn(Optional.of(produtoInativo))

        assertThrows<ResourceException> { produtoService.desativar(1) }
    }

    @Test
    fun `Should reduce Product qnt on database`(){
        val produto = ProductComponent.createActiveProduct()
        val qtdParaReduzir = BigDecimal(1.0)
        val produtoEsperado = ProductComponent.createActiveProduct()
        produtoEsperado.quantidade = produtoEsperado.quantidade.subtract(qtdParaReduzir)

        `when`(produtoRepository.save(produtoEsperado))
            .thenReturn(produtoEsperado)

        val produtoReduzido = produtoService.reduzirEstoqueDoProduto(produto, qtdParaReduzir)
        assertEquals(produtoEsperado, produtoReduzido)
    }

    @Test
    fun`Should return false if a product have no stock`(){
        val produto = ProductComponent.createActiveProduct()
        val qtdParaReduzir = BigDecimal(999)

        `when`(produtoRepository.findById(1)).thenReturn(Optional.of(produto))
        assertFalse(produtoService.verificaEstoqueDoProduto(produto, qtdParaReduzir))
    }

    @Test
    fun`Should return true if a product has stock`(){
        val produto = ProductComponent.createActiveProduct()
        val qtdParaReduzir = BigDecimal(1)

        `when`(produtoRepository.findById(1)).thenReturn(Optional.of(produto))
        assertTrue(produtoService.verificaEstoqueDoProduto(produto, qtdParaReduzir))
    }

    @Test
    fun `Should return true after verifying avialiable stock for a list of products`(){
        val produtoEncontrado = ProductComponent.createActiveProduct()
        val listaDeProdutosEnviados = ProdutoEnviadoComponent.createListWithFewItens()
        `when`(produtoRepository.findById(1)).thenReturn(Optional.of(produtoEncontrado))
        assertTrue(produtoService.verificaSeTemEstoqueParaPedido(listaDeProdutosEnviados))
    }

    @Test
    fun `Should return false after verifying itens with no stock for a list of products`(){
        val produtoEncontrado = ProductComponent.createActiveProduct()
        val listaDeProdutosEnviados = ProdutoEnviadoComponent.createListWithManyItens()
        `when`(produtoRepository.findById(2)).thenReturn(Optional.of(produtoEncontrado))
        `when`(produtoRepository.findById(1)).thenReturn(Optional.of(produtoEncontrado))
        assertFalse(produtoService.verificaSeTemEstoqueParaPedido(listaDeProdutosEnviados))
    }

    @Test
    fun `Should throw ResourceException for an product which doesnt exists on DB`(){
        val listaDeProdutosEnviados = ProdutoEnviadoComponent.createListWithManyItens()
        assertFalse(produtoService.verificaSeTemEstoqueParaPedido(listaDeProdutosEnviados))
    }

    @Test
    fun `Should return true when a list with product in stock is succefully reduced`(){
        val listaDeProdutosParaReduzir = ProdutoEnviadoComponent.createListWithFewItens()
        val produtoEncontrado = ProductComponent.createActiveProduct()
        `when`(produtoRepository.findById(1)).thenReturn(Optional.of(produtoEncontrado))
        `when`(produtoRepository.save(produtoEncontrado))
            .thenReturn(produtoEncontrado)
        assertTrue(produtoService.reduzirEstoqueParaListaDeProdutos(listaDeProdutosParaReduzir))
    }

    @Test
    fun `Should return false when a list with product without stock is succefully reduced`(){
        val listaDeProdutosParaReduzir = ProdutoEnviadoComponent.createListWithManyItens()
        val produtoEncontrado = ProductComponent.createActiveProduct()
        `when`(produtoRepository.findById(2)).thenReturn(Optional.of(produtoEncontrado))
        assertFalse(produtoService.reduzirEstoqueParaListaDeProdutos(listaDeProdutosParaReduzir))
    }

}