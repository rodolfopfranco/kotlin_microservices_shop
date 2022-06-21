package com.pan.apicep.service

import com.pan.apicep.dto.CepResponse
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class CepService {

    fun generateCep(): CepResponse {
        return CepResponse(Random.nextInt(10000000, 99999999))
    }
}