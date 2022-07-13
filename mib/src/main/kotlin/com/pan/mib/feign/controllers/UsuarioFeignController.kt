package com.pan.mib.feign.controllers

import com.pan.mib.feign.clients.UsuarioClient
import com.pan.mib.feign.dto.usuario.AutenticacaoDTO
import com.pan.mib.feign.dto.usuario.TokenDTO
import com.pan.mib.feign.dto.usuario.Usuario
import com.pan.mib.feign.dto.usuario.UsuarioRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UsuarioFeignController(
    @Autowired private val usuarioClient: UsuarioClient
    ){

    @PostMapping("/login")
    fun login (@RequestBody authForm: AutenticacaoDTO): ResponseEntity<TokenDTO> =
        usuarioClient.login(authForm)

    @GetMapping
    fun listarUsuarios(
        @RequestHeader(value = "Authorization", required = true) authorizationHeader: String
    ):ResponseEntity<List<Usuario>> = usuarioClient.listarUsuarios(authorizationHeader)

    @GetMapping("/{id}")
    fun buscarUsuarioPorId(
        @RequestHeader(value = "Authorization", required = true) authorizationHeader: String,
        @PathVariable id: Int
    ) = usuarioClient.buscarUsuarioPorId(authorizationHeader, id)

    @PostMapping
    fun salvarUsuario(
        @RequestHeader(value = "Authorization", required = true) authorizationHeader: String,
        @RequestBody request: UsuarioRequest
    ) = usuarioClient.salvarUsuario(authorizationHeader, request)

    @PatchMapping("/{id}")
    fun atualizarUsuario(
        @RequestHeader(value = "Authorization", required = true) authorizationHeader: String,
        @RequestBody request: UsuarioRequest, @PathVariable id: Int
    ) = usuarioClient.atualizarUsuario(authorizationHeader, id, request)

    @DeleteMapping("/{id}")
    fun removerUsuario(
        @RequestHeader(value = "Authorization", required = true) authorizationHeader: String,
        @PathVariable id: Int
    ) = usuarioClient.removerUsuario(authorizationHeader, id)

    @PatchMapping("/{id}/activate")
    fun ativar(
        @RequestHeader(value = "Authorization", required = true) authorizationHeader: String,
        @PathVariable id: Int
    ) = usuarioClient.ativar(authorizationHeader, id)

    @PatchMapping("/{id}/deactivate")
    fun desativar(
        @RequestHeader(value = "Authorization", required = true) authorizationHeader: String,
        @PathVariable id: Int
    ) = usuarioClient.desativar(authorizationHeader, id)

}