package com.pan.apiprodutos.dto.request

import java.math.BigDecimal

data class ProdutoEnviado(
    val idProduto: Int,
    val nome: String,
    val qtd: BigDecimal,
)