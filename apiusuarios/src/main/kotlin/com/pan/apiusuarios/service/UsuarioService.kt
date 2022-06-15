package com.pan.apiusuarios.service

import com.pan.apiusuarios.dto.mapper.UsuarioMapper
import com.pan.apiusuarios.dto.request.UsuarioRequest
import com.pan.apiusuarios.entity.Usuario
import com.pan.apiusuarios.repository.UsuarioRepository
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class UsuarioService(
    private val usuarioRepository: UsuarioRepository,
    private val usuarioMapper: UsuarioMapper
) {

    fun listarUsuarios(): List<Usuario> = usuarioRepository.findAll()

    fun salvarUsuario(request: UsuarioRequest) = usuarioRepository.save(usuarioMapper.toEntity(request))

    fun encontrarPorId(id: Int){
        usuarioRepository.findById(id)
            .orElseThrow{
                EntityNotFoundException("Usuário não encontrado")
            }
    }

    fun atualizarUsuario(request: UsuarioRequest, id: Int){
        encontrarPorId(id)
        var usuarioParaSalvar = usuarioMapper.toEntity(request)
        usuarioParaSalvar.id = id
        usuarioRepository.save(usuarioParaSalvar)
    }

    fun removerUsuario(id: Int) = usuarioRepository.deleteById(id)

}