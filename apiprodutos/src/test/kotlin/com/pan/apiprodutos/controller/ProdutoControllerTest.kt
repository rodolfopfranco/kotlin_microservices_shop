package com.pan.apiprodutos.component

import com.fasterxml.jackson.databind.ObjectMapper
import com.pan.apiprodutos.controller.ProdutoController
import com.pan.apiprodutos.dto.mapper.ProdutoMapper
import com.pan.apiprodutos.dto.mapper.ProdutoMapperImpl
import com.pan.apiprodutos.dto.request.ProdutoRequest
import com.pan.apiprodutos.entity.Produto
import com.pan.apiprodutos.exception.ResourceException
import com.pan.apiprodutos.service.ProdutoService
import com.pan.apiprodutos.util.GsonConverter
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@ExtendWith(SpringExtension::class)
@WebMvcTest(ProdutoController::class)
class ProdutoControllerTest(
    //Using Mockk
) {
    @TestConfiguration
    class ControllerTestConfig {
        @Bean
        fun produtoService() = mockk<ProdutoService>()
        @Bean
        fun produtoMapper() = mockk<ProdutoMapperImpl>()
    }

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var produtoService: ProdutoService

    @Autowired
    private lateinit var produtoMapper: ProdutoMapper

    @Test
    fun `ListAll Returns a list with every Product`() {
        every { produtoService.listarProdutos() } returns ProductComponent.createAList()

        val result = mockMvc.perform(get("/api/v1/products"))
            .andExpect(status().isOk)
            .andDo(print())
            .andReturn()
        val stringResult = result.response.contentAsString
        val resultAsList: List<Produto> = GsonConverter.getList(stringResult, Produto::class.java)!!

        assertEquals(ProductComponent.createAList(), resultAsList)
        verify { produtoService.listarProdutos() }
    }

    @Test
    fun `should find a product given its ID`(){
        val produtoExperado = ProductComponent.createActiveProduct()
        every { produtoService.encontrarPorId(1) } returns produtoExperado

        val result = mockMvc.perform(get("/api/v1/products/1"))
            .andExpect(status().isOk)
            .andReturn()
        val stringResult = result.response.contentAsString
        val produtoEncontrado: Produto = GsonConverter.getObject(stringResult, Produto::class.java)

        verify { produtoService.encontrarPorId(1) }
        assertEquals(produtoExperado, produtoEncontrado)
    }

    @Test
    fun `should catch and return thrown exception for not found ID`(){
        val excecaoExperada = ResourceException(HttpStatus.NOT_FOUND, "Produto não encontrado")
        every { produtoService.encontrarPorId(1) } throws excecaoExperada

        mockMvc.perform(get("/api/v1/products/1"))
            .andExpect(status().isNotFound)
            .andExpect {result -> assertTrue(result.resolvedException is ResourceException)}
            .andExpect{result -> assertEquals("Produto não encontrado", result.resolvedException?.message) }
    }

    @Test
    fun `should save product and return it`(){
        val produtoExperado = ProductComponent.createActiveProduct()
        val produtoRequest = ProductRequestComponent.createBasicActive()
        val objectMapper = ObjectMapper()
        val json: String = objectMapper.writeValueAsString(produtoRequest)
        every { produtoService.salvarProduto(any())} returns produtoExperado


        val result = mockMvc.perform(
            post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isOk)
            .andReturn()
        val stringResult = result.response.contentAsString
        val produtoSalvo: Produto = GsonConverter.getObject(stringResult, Produto::class.java)

        verify { produtoService.salvarProduto(any()) }
        assertEquals(produtoExperado, produtoSalvo)
    }

    @Test
    fun `Should update product and return it`(){
        val produtoExperado = ProductComponent.createInactiveProduct()
        val produtoRequest = ProductRequestComponent.createBasicActive()
        val objectMapper = ObjectMapper()
        val json: String = objectMapper.writeValueAsString(produtoRequest)
        every { produtoService.atualizarProduto(any(), 1)} returns produtoExperado

        val result = mockMvc.perform(
            patch("/api/v1/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isOk)
            .andReturn()
        val stringResult = result.response.contentAsString
        val produtoSalvo: Produto = GsonConverter.getObject(stringResult, Produto::class.java)


        verify { produtoService.atualizarProduto(any(), 1) }
        assertEquals(produtoExperado, produtoSalvo)
    }


}