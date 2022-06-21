package com.pan.apiprodutos.dto.request

import java.math.BigDecimal

class ProdutoRequest(
    var nome: String,
    var quantidade: BigDecimal,
    var valor_unitario: BigDecimal,
    var descricao: String,
    var isActive: Boolean
)