package com.pan.apicep.controller

import com.pan.apicep.dto.CepResponse
import com.pan.apicep.dto.EnderecoRequest
import com.pan.apicep.service.CepService
import io.swagger.annotations.ApiOperation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/cep")
class CepController (
    private val cepService: CepService
    ){

    @ApiOperation(value = "Simulates a CEP generation based on given Address")
    @PostMapping
    fun gerarCep(@RequestBody enderecoRequest: EnderecoRequest): ResponseEntity<CepResponse> = ResponseEntity.ok(cepService.generateCep())

}