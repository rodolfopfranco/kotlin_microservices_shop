package com.pan.apicompras.controller

import com.pan.apicompras.document.Compra
import com.pan.apicompras.service.CompraService
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
@RequestMapping("/api/v1/purchases")
class CompraController(
    private val compraService: CompraService
) {

    @ApiOperation(value = "Retrieve every purchase on the database")
    @GetMapping
    fun listarCompras(): ResponseEntity<List<Compra>> = ResponseEntity.ok(compraService.listarCompras())

    @ApiOperation(value = "Find a Purchase by its ID")
    @GetMapping("/{id}")
    fun buscarCompraPorId(@PathVariable id: String): ResponseEntity<Compra>{
        return ResponseEntity.ok(compraService.encontrarPorId(id))
    }

    @ApiOperation(value = "Saves a new Purchase")
    @PostMapping
    fun salvarCompra(@RequestBody request: Compra): ResponseEntity<Compra> {
        return ResponseEntity.ok(compraService.salvarCompra(request))
    }

    @ApiOperation(value = "Updates a new Purchase")
    @PatchMapping("/{id}")
    fun atualizarCompra(@RequestBody request: Compra, @PathVariable id:String): ResponseEntity<Compra>{
        return ResponseEntity.ok(compraService.atualizarCompra(request, id))
    }

    @ApiOperation(value = "Removes a Purchase by its ID")
    @DeleteMapping("/{id}")
    fun removerCompra(@PathVariable id: String): ResponseEntity<Unit> {
        return ResponseEntity.ok(compraService.removerCompra(id))
    }

    @ApiOperation(value = "Finds purchases of an user, given its ID")
    @GetMapping("/users/{idUsuario}")
    fun listarComprasPorUsuario(@PathVariable idUsuario: Int): ResponseEntity<List<Compra>>{
        return ResponseEntity.ok(compraService.encontrarComprasPorUsuario(idUsuario))
    }

    @ApiOperation(value = "Finds purchases of an user, given its ID")
    @GetMapping("/users/{idUsuario}/{purchaseId}")
    fun listarComprasPorUsuario(@PathVariable idUsuario: Int, @PathVariable purchaseId: String): ResponseEntity<List<Compra>>{
        return ResponseEntity.ok(compraService.encontrarCompraPorUsuario(idUsuario, purchaseId))
    }

}