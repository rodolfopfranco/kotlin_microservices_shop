package com.pan.apiusuarios.repository

import com.pan.apiusuarios.entity.Telefone
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TelefoneRepository: JpaRepository<Telefone, Int> {
}