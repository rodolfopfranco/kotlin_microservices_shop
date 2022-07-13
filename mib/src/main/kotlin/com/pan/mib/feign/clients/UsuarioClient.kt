package com.pan.mib.feign.clients

import com.pan.mib.feign.dto.usuario.AutenticacaoDTO
import com.pan.mib.feign.dto.usuario.TokenDTO
import com.pan.mib.feign.dto.usuario.Usuario
import com.pan.mib.feign.dto.usuario.UsuarioRequest
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(name = "Usuario", url = "http://localhost:8081")
interface UsuarioClient {

    @PostMapping("/api/v1/users/login")
    fun login (@RequestBody authForm: AutenticacaoDTO): ResponseEntity<TokenDTO>

    @GetMapping("/api/v1/users/{id}")
    fun buscarUsuarioPorId(
        @RequestHeader(value = "Authorization", required = true) authorizationHeader: String,
        @PathVariable id: Int
    ): ResponseEntity<Usuario>

    @GetMapping("/api/v1/users")
    fun listarUsuarios(
        @RequestHeader(value = "Authorization", required = true) authorizationHeader: String
    ): ResponseEntity<List<Usuario>>

    @PostMapping("/api/v1/users")
    fun salvarUsuario(
        @RequestHeader(value = "Authorization", required = true) authorizationHeader: String,
        @RequestBody request: UsuarioRequest
    ): ResponseEntity<Usuario>

    @PatchMapping("/api/v1/users/{id}")
    fun atualizarUsuario(
        @RequestHeader(value = "Authorization", required = true) authorizationHeader: String,
        @PathVariable id: Int, @RequestBody request: UsuarioRequest
    ): ResponseEntity<Usuario>

    @DeleteMapping("/api/v1/users/{id}")
    fun removerUsuario(
        @RequestHeader(value = "Authorization", required = true) authorizationHeader: String,
        @PathVariable id: Int
    )

    @PatchMapping("/api/v1/users/{id}/activate")
    fun ativar(
        @RequestHeader(value = "Authorization", required = true) authorizationHeader: String,
        @PathVariable id: Int
    ): ResponseEntity<Usuario>

    @PatchMapping("/api/v1/users/{id}/deactivate")
    fun desativar(
        @RequestHeader(value = "Authorization", required = true) authorizationHeader: String,
        @PathVariable id: Int
    ): ResponseEntity<Usuario>


}