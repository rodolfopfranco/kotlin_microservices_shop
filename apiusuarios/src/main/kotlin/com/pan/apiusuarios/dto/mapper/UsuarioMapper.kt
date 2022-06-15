package com.pan.apiusuarios.dto.mapper

import com.pan.apiusuarios.dto.request.UsuarioRequest
import com.pan.apiusuarios.entity.Usuario
import org.mapstruct.Mapper
import org.mapstruct.Mappings
import org.mapstruct.ReportingPolicy

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface UsuarioMapper {

    @Mappings()
    fun toEntity(request: UsuarioRequest): Usuario
}