package com.pan.mib.feign.dto.usuario

data class Usuario (
    var id: Int? = null,
    var nome: String,
    var idade: Int,
    var email: String,
    var isActive: Boolean,
    var enderecos: MutableList<Endereco>,
    var telefones: MutableList<Telefone>,
    var perfil: Perfil
    )