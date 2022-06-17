package com.pan.apiusuarios.entity

import org.springframework.security.core.GrantedAuthority
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "TB_PERFIL")
class Perfil(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    val nome: String
) : GrantedAuthority {
    override fun getAuthority(): String {
        return nome
    }
}