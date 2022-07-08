package com.pan.apiprodutos.component

import com.pan.apiprodutos.dto.request.ProdutoRequest
import java.math.BigDecimal

class ProductRequestComponent {

    companion object {
        fun createBasicActive() =
            ProdutoRequest(
                nome = "Produto ativo",
                quantidade = BigDecimal("1.0"),
                valor_unitario = BigDecimal("2.0"),
                descricao = "Descrição do ativo",
                isActive = true
            )

        fun createBasicInactive() =
            ProdutoRequest(
                nome = "Produto inativo",
                quantidade = BigDecimal("1.0"),
                valor_unitario = BigDecimal("2.0"),
                descricao = "Descrição do inativo",
                isActive = false
            )
    }
}