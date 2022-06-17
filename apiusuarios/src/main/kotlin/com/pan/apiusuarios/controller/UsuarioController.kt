package com.pan.apiusuarios.controller

import com.pan.apiusuarios.dto.request.UsuarioRequest
import com.pan.apiusuarios.dto.security.AutenticacaoDTO
import com.pan.apiusuarios.service.AuthenticationService
import com.pan.apiusuarios.service.UsuarioService
import io.swagger.annotations.ApiOperation
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UsuarioController(
    private val usuarioService: UsuarioService,
    private val authenticationService: AuthenticationService
) {

    @ApiOperation(value = "Retrieve every user on the database")
    @GetMapping
    fun listarUsuarios() = ResponseEntity.ok(usuarioService.listarUsuarios())

    @ApiOperation(value = "Find a User by its ID")
    @GetMapping("/{id}")
    fun buscarUsuarioPorId(@PathVariable id: Int){
        usuarioService.encontrarPorId(id)
    }

    @ApiOperation(value = "Saves a new User")
    @PostMapping
    fun salvarUsuario(@RequestBody request: UsuarioRequest){
        usuarioService.salvarUsuario(request)
    }

    @ApiOperation(value = "Updates a new User")
    @PatchMapping("/{id}")
    fun atualizarUsuario(@RequestBody request: UsuarioRequest, @PathVariable id:Int){
        usuarioService.atualizarUsuario(request, id)
    }

    @ApiOperation(value = "Removes a User by its ID")
    @DeleteMapping("/{id}")
    fun removerUsuario(@PathVariable id: Int){
        usuarioService.removerUsuario(id)
    }

    @PostMapping("/login")
    @ApiOperation(value = "Authenticates User")
    fun autenticar(@RequestBody authForm: AutenticacaoDTO){
        ResponseEntity.ok(authenticationService.autenticar(authForm))
    }

    @PatchMapping("/{id}/activate")
    fun ativar(@PathVariable id: Int) = ResponseEntity.ok(usuarioService.ativar(id))

    @PatchMapping("/{id}/deactivate")
    fun desativar(@PathVariable id: Int) = ResponseEntity.ok(usuarioService.desativar(id))


}