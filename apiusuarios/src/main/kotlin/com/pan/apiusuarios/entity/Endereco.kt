package com.pan.apiusuarios.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "TB_ENDERECO")
class Endereco(

    @Id
    @Column(name = "id_endereco")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column(name = "ds_rua")
    var rua: String,
    @Column(name = "ds_numero")
    var numero: String,
    @Column(name = "ds_cidade")
    var cidade: String,
    @Column(name = "ds_estado")
    var estado: String
)