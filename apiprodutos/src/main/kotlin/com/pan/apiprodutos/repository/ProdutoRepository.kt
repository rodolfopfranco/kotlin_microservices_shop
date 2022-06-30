package com.pan.apiprodutos.repository

import com.pan.apiprodutos.entity.Produto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ProdutoRepository : JpaRepository<Produto, Int>{
    fun findByIsActive(isActive: Boolean): Optional<List<Produto>>
}