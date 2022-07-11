package com.pan.apiprodutos.component

import com.pan.apiprodutos.dto.request.ProdutoEnviado
import com.pan.apiprodutos.dto.request.ProdutoRequest
import java.math.BigDecimal

class ProdutoEnviadoComponent {
    companion object{

        fun createBasic1() =
            ProdutoEnviado(
                idProduto = 1,
                nome = "Produto inativo",
                qtd = BigDecimal(1)
            )

        fun createBasic2() =
            ProdutoEnviado(
                idProduto = 2,
                nome = "Produto ativo",
                qtd = BigDecimal(99)
            )

        fun createListWithFewItens() =
            listOf(
                createBasic1(),
                createBasic1()
            )
        fun createListWithManyItens() =
            listOf(
                createBasic2(),
                createBasic2()
            )

    }
}