package com.pan.apiusuarios.repository

import com.pan.apiusuarios.entity.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UsuarioRepository: JpaRepository<Usuario, Int> {
    fun findByEmail(email: String): Optional<Usuario>
}