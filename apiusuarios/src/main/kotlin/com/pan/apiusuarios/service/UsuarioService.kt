package com.pan.apiusuarios.service

import com.pan.apiusuarios.dto.mapper.UsuarioMapper
import com.pan.apiusuarios.dto.request.UsuarioRequest
import com.pan.apiusuarios.entity.Usuario
import com.pan.apiusuarios.exception.ResourceException
import com.pan.apiusuarios.repository.UsuarioRepository
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class UsuarioService(
    private val usuarioRepository: UsuarioRepository,
    private val usuarioMapper: UsuarioMapper
): UserDetailsService {

    fun listarUsuarios(): List<Usuario> = usuarioRepository.findAll()

    fun salvarUsuario(request: UsuarioRequest): Usuario {
        val usuarioEntidade: Usuario =  usuarioMapper.toEntity(request)
        val senhaCriptografada: String = criptografarSenha(senha = usuarioEntidade.senha)
        usuarioEntidade.senha = senhaCriptografada
        return usuarioRepository.save(usuarioEntidade)
    }

    fun encontrarPorId(id: Int): Usuario{
        return usuarioRepository.findById(id)
            .orElseThrow{
                ResourceException(HttpStatus.NOT_FOUND, "Usuário não encontrado")
            }
    }

    fun atualizarUsuario(request: UsuarioRequest, id: Int): Usuario{
        encontrarPorId(id)
        var usuarioParaSalvar = usuarioMapper.toEntity(request)
        usuarioParaSalvar.id = id
        return usuarioRepository.save(usuarioParaSalvar)
    }

    fun removerUsuario(id: Int) {
        encontrarPorId(id)
        try{
            usuarioRepository.deleteById(id)
        } catch (e: Exception) {
            throw ResourceException(HttpStatus.INTERNAL_SERVER_ERROR, e.message)
        }
    }

    fun buscarUsuarioPorEmail(email: String): Usuario {
        return usuarioRepository.findByEmail(email).orElseThrow(){
            ResourceException(HttpStatus.NOT_FOUND, "Usuário não encontrado com o e-mail fornecido")
        }
    }

    override fun loadUserByUsername(username: String): UserDetails {
            return buscarUsuarioPorEmail(username)
    }

    fun buscarUsuarioPorId(idUsuario: Long): Usuario {
        return usuarioRepository.findById(idUsuario.toInt()).orElseThrow{
            ResourceException(HttpStatus.NOT_FOUND, "Usuário não encontrado com o ID informado")
        }
    }

    fun ativar(id: Int): Usuario {
        var usuarioEncontrado = buscarUsuarioPorId(id.toLong())
        if(usuarioEncontrado.isActive) throw ResourceException(HttpStatus.BAD_REQUEST, "Usuário já está ativo")
        usuarioEncontrado.isActive = true
        return usuarioRepository.save(usuarioEncontrado)
    }

    fun desativar(id: Int): Usuario {
        var usuarioEncontrado = buscarUsuarioPorId(id.toLong())
        if(!usuarioEncontrado.isActive) throw ResourceException(HttpStatus.BAD_REQUEST, "Usuário já está inativo")
        usuarioEncontrado.isActive = false
        return usuarioRepository.save(usuarioEncontrado)
    }

    fun criptografarSenha(senha: String): String {
        val bcrypEnconder = BCryptPasswordEncoder()
        return bcrypEnconder.encode(senha)
    }

}