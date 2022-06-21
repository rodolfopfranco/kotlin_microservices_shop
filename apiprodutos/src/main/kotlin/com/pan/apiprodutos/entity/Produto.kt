package com.pan.apiprodutos.entity

import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "TB_PRODUTO")
data class Produto(
    @Id
    @Column(name = "id_produto")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column(name = "ds_nome")
    var nome: String,

    @Column(name = "nr_quantidade")
    var quantidade: BigDecimal,

    @Column(name = "vl_valor_unitario")
    var valor_unitario: BigDecimal,

    @Column(name = "ds_descricao")
    var descricao: String,

    @Column(name = "st_active")
    var isActive: Boolean
){


}