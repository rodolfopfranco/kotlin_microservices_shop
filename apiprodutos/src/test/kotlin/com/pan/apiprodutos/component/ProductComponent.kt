package com.pan.apiprodutos.component

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.matching.EqualToPattern
import com.pan.apiprodutos.entity.Produto
import com.pan.apiprodutos.util.URL_PRODUCTS
import com.pan.apiprodutos.util.URL_PRODUCTS_CHANGE_STATUS
import java.math.BigDecimal

class ProductComponent {

    companion object {
        fun createMockActivateProductRequest(productId: String?, produto: Produto){
            // Using WireMock to mock a Product request
            produto.isActive=true
            WireMockServer()
                .stubFor(get(URL_PRODUCTS+ URL_PRODUCTS_CHANGE_STATUS)
                .withQueryParam("id", EqualToPattern(productId, true))
                .willReturn(aResponse()
                    .withStatus(200)
                    .withBody(produto.toString())))
        }

        fun createInactiveProduct() =
            Produto(
                id = 1,
                nome = "Produto Inativo",
                quantidade = BigDecimal("1.0"),
                valor_unitario = BigDecimal("2.0"),
                descricao = "descricao",
                isActive = false
            )

        fun createActiveProduct() =
            Produto(
                id = 1,
                nome = "Produto ativo",
                quantidade = BigDecimal("1.0"),
                valor_unitario = BigDecimal("2.0"),
                descricao = "Descricao do ativo",
                isActive = true
            )

        fun createAList() = listOf<Produto>(
            createActiveProduct(),
            createActiveProduct(),
            createActiveProduct(),
            createInactiveProduct(),
            createInactiveProduct(),
            createInactiveProduct()
        )
    }
}