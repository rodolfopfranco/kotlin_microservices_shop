package com.pan.apiusuarios.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.pan.apiusuarios.dto.security.AutenticacaoDTO
import com.pan.apiusuarios.dto.security.TokenDTO
import com.pan.apiusuarios.entity.Usuario
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthenticationService(
    @Autowired val authManager: AuthenticationManager
) {

    @Value("\${apiusuarios.jwt.expiration}")
    val expiration: String? = null

    @Value("\${apiusuarios.jwt.secret}")
    val secret: String? = null

    @Value("\${apiusuarios.jwt.issuer}")
    val issuer: String? = null

    @Throws(AuthenticationException::class)
    fun autenticar(authForm: AutenticacaoDTO): TokenDTO {
        val authenticate: Authentication = authManager.authenticate(
            UsernamePasswordAuthenticationToken(
                authForm.usuario,
                authForm.senha
            )
        )
        val token = gerarToken(authenticate)
        return TokenDTO(token)
    }

    fun criarAlgoritmo(): Algorithm {
        return Algorithm.HMAC256(secret)
    }

    fun gerarToken(authenticate: Authentication): String {
        val principal = authenticate.getPrincipal() as Usuario
        val hoje = Date()
        val dataExpiracao = Date(hoje.getTime() + expiration!!.toLong())
        return JWT.create()
            .withIssuer(issuer)
            .withExpiresAt(dataExpiracao)
            .withSubject(principal.id.toString())
            .sign(criarAlgoritmo())
    }

    fun verificaToken(token: String?): Boolean {
        return try {
            if (token == null) return false
            JWT.require(criarAlgoritmo()).withIssuer(issuer).build().verify(token)
            true
        } catch (e: JWTVerificationException) {
            false
        }
    }

    fun retornarIdUsuario(token: String?): Long {
        val subject = JWT.require(criarAlgoritmo())
            .withIssuer(issuer).build()
            .verify(token)
            .subject
        return subject.toLong()
    }
}