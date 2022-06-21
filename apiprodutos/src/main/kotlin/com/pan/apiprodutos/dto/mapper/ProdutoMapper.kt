package com.pan.apiprodutos.dto.mapper

import com.pan.apiprodutos.dto.request.ProdutoRequest
import com.pan.apiprodutos.entity.Produto
import org.mapstruct.Mapper
import org.mapstruct.Mappings
import org.mapstruct.ReportingPolicy

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface ProdutoMapper {

    @Mappings()
    fun toEntity(request: ProdutoRequest): Produto
}