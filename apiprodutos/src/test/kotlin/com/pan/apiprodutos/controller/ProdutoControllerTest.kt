package com.pan.apiprodutos.component

import com.pan.apiprodutos.controller.ProdutoController
import com.pan.apiprodutos.entity.Produto
import com.pan.apiprodutos.service.ProdutoService
import com.pan.apiprodutos.util.GsonConverter
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@ExtendWith(SpringExtension::class)
@WebMvcTest(ProdutoController::class)
class ProdutoControllerTest(
) {
    @TestConfiguration
    class ControllerTestConfig {
        @Bean
        fun produtoService() = mockk<ProdutoService>()
    }

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var produtoService: ProdutoService

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


}