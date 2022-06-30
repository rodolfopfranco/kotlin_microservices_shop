package com.pan.apiprodutos.dto.request

data class Entrega (
    var idEntrega: String? = null,
    var idCliente: Int,
    var produtos: MutableList<ProdutoEnviado>,
    var status: EntregaStatus
)