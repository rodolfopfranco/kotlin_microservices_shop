package com.pan.apientregas.controller

import com.pan.apientregas.document.Entrega
import com.pan.apientregas.messaging.RabbitMQService
import com.pan.apientregas.service.EntregaService
import io.swagger.annotations.ApiOperation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/deliveries")
class EntregaController(
    private val entregaService: EntregaService,
    private val rabbitMQService: RabbitMQService
) {
    @ApiOperation(value = "Retrieve every delivery on the database")
    @GetMapping
    fun listarEntregas(): ResponseEntity<List<Entrega>> = ResponseEntity.ok(entregaService.listarEntregas())

    @ApiOperation(value = "Find a Delivery by its ID")
    @GetMapping("/{id}")
    fun buscarEntregaPorId(@PathVariable id: String): ResponseEntity<Entrega>{
        return ResponseEntity.ok(entregaService.encontrarPorId(id))
    }

    @ApiOperation(value = "Creates a new Delivery")
    @PostMapping
    fun salvarEntrega(@RequestBody request: Entrega): ResponseEntity<Entrega> {
        val entregaSalva = entregaService.salvarEntrega(request)
        rabbitMQService.enviaEntrega(entregaSalva)
        println(entregaSalva)
        return ResponseEntity.ok(entregaSalva)
    }

    @ApiOperation(value = "Updates a new Delivery")
    @PatchMapping("/{id}")
    fun atualizarEntrega(@RequestBody request: Entrega, @PathVariable id:String): ResponseEntity<Entrega>{
        return ResponseEntity.ok(entregaService.atualizarEntrega(request, id))
    }

    @ApiOperation(value = "Removes a Delivery by its ID")
    @DeleteMapping("/{id}")
    fun removerEntrega(@PathVariable id: String): ResponseEntity<Unit> {
        return ResponseEntity.ok(entregaService.removerEntrega(id))
    }

    @ApiOperation(value = "Finds deliveries of an user, given its ID")
    @GetMapping("/users/{idUsuario}")
    fun listarEntregasPorUsuario(@PathVariable idUsuario: Int): ResponseEntity<List<Entrega>>{
        return ResponseEntity.ok(entregaService.encontrarEntregasPorUsuario(idUsuario))
    }

    @ApiOperation(value = "Finds deliveries of an user, given its ID")
    @GetMapping("/users/{idUsuario}/{deliveryId}")
    fun listarEntregasPorUsuario(@PathVariable idUsuario: Int, @PathVariable deliveryId: String): ResponseEntity<List<Entrega>>{
        return ResponseEntity.ok(entregaService.encontrarEntregaPorUsuario(idUsuario, deliveryId))
    }
}