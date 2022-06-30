package com.pan.apiusuarios.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "TB_TELEFONE")
class Telefone(
    @Id
    @Column(name = "id_telefone")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column(name = "nr_ddi")
    var ddi: Int,
    @Column(name = "nr_ddd")
    var ddd: Int,
    @Column(name = "nr_numero")
    var numero: Int
)