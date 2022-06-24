package com.pan.apientregas.document

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "cl_entrega")
data class Entrega (
    @Id
    var idEntrega: String? = null,
    var idCliente: Int,
    var produtos: MutableList<ProdutoEnviado>
)