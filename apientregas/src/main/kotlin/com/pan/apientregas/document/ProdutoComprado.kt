package com.pan.apientregas.document

import java.math.BigDecimal

data class ProdutoEnviado(
    val idProduto: Int,
    val nome: String,
    val qtd: BigDecimal,
)