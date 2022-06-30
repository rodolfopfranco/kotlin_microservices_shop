package com.pan.apicompras.repository

import com.pan.apicompras.document.Compra
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface CompraRepository: MongoRepository<Compra, String> {
    fun findByIdCliente(id: Int): List<Compra>?
}