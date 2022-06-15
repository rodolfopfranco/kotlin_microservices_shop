package com.pan.apiusuarios.dto.request

import com.pan.apiusuarios.entity.Endereco
import com.pan.apiusuarios.entity.Telefone
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class UsuarioRequest(
    @NotBlank
    @Size(min=3)
    val nome: String,
    val idade: Int,
    @NotBlank
    @Size(min = 3, max = 320)
    val email: String,
    @NotBlank
    @Size(min = 1)
    val senha: String,
    val isActive: Boolean,
    val enderecos: MutableList<Endereco>,
    val telefones: MutableList<Telefone>
)