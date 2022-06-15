package com.pan.apiusuarios.entity


import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType;
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "TB_USUARIO")
class Usuario(

    @Id
    @Column(name = "id_usuario")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column(name = "ds_nome")
    var nome: String,
    @Column(name = "nr_idade")
    var idade: Int,
    @Column(name = "ds_email")
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
    var telefones: MutableList<Telefone>

)