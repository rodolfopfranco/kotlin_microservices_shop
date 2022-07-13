package com.pan.mib.feign.dto.usuario

class UsuarioRequest(
    val nome: String,
    val idade: Int,
    val email: String,
    val senha: String,
    val isActive: Boolean,
    val enderecos: MutableList<Endereco>,
    val telefones: MutableList<Telefone>,
    val perfil: Perfil
)