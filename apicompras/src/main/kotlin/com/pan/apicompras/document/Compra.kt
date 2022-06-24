package com.pan.apicompras.document

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal

@Document(collection = "cl_compra")
data class Compra(
    @Id
    var id: String? = null,
    val idCliente: Int,
    val valorTotal: BigDecimal,
    var produtos: MutableList<ProdutoComprado>
) {
}