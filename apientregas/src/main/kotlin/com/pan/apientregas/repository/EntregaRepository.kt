package com.pan.apientregas.repository

import com.pan.apientregas.document.Entrega
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface EntregaRepository: MongoRepository<Entrega, String> {
    fun findByIdCliente(id: Int): List<Entrega>?
}