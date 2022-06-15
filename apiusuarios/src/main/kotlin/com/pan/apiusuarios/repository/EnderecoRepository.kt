package com.pan.apiusuarios.repository

import com.pan.apiusuarios.entity.Endereco
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EnderecoRepository: JpaRepository<Endereco, Int> {
}