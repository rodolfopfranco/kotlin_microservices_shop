package com.pan.apiusuarios.controller

import com.pan.apiusuarios.dto.request.UsuarioRequest
import com.pan.apiusuarios.service.UsuarioService
import io.swagger.annotations.ApiOperation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/users")
class UsuarioController(
    private val usuarioService: UsuarioService
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

}