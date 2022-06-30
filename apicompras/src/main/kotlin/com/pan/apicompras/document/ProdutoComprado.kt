package com.pan.apicompras.document

import java.math.BigDecimal

data class ProdutoComprado(
    val idProduto: Int,
    val nome: String,
    val qtd: BigDecimal,
    val valorUnitario: BigDecimal
) {
}