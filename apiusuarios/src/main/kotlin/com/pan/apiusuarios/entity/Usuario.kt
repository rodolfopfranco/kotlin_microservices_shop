package com.pan.apiusuarios.entity


import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table


@Entity
@Table(name = "TB_USUARIO")
data class Usuario (

    @Id
    @Column(name = "id_usuario")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column(name = "ds_nome")
    var nome: String,
    @Column(name = "nr_idade")
    var idade: Int,
    @Column(name = "ds_email", unique = true)
    var email: String,
    @Column(name = "vl_senha")
    var senha: String,
    @Column(name = "st_active")
    var isActive: Boolean,

    @OneToMany
    @Cascade(CascadeType.ALL)
    var enderecos: MutableList<Endereco>,
    @OneToMany
    @Cascade(CascadeType.ALL)
    var telefones: MutableList<Telefone>,

    @ManyToOne
    var perfil: Perfil

) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(perfil)
    }

    override fun getPassword(): String {
        return senha
    }

    override fun getUsername(): String {
        return email
    }

    override fun isAccountNonExpired(): Boolean {
        return isActive
    }

    override fun isAccountNonLocked(): Boolean {
        return isActive
    }

    override fun isCredentialsNonExpired(): Boolean {
        return isActive
    }

    override fun isEnabled(): Boolean {
        return isActive
    }
}