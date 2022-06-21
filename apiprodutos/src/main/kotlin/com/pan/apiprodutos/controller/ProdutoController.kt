package com.pan.apiprodutos.controller

import com.pan.apiprodutos.dto.request.ProdutoRequest
import com.pan.apiprodutos.entity.Produto
import com.pan.apiprodutos.service.ProdutoService
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
@RequestMapping("/api/v1/products")
class ProdutoController(
    private val produtoService: ProdutoService
) {
    @ApiOperation(value = "Retrieve every product on the database")
    @GetMapping
    fun listarProdutos(): ResponseEntity<List<Produto>> = ResponseEntity.ok(produtoService.listarProdutos())

    @ApiOperation(value = "Find a Product by its ID")
    @GetMapping("/{id}")
    fun buscarProdutoPorId(@PathVariable id: Int): ResponseEntity<Produto>{
        return ResponseEntity.ok(produtoService.encontrarPorId(id))
    }

    @ApiOperation(value = "Saves a new Product")
    @PostMapping
    fun salvarProduto(@RequestBody request: ProdutoRequest): ResponseEntity<Produto> {
        return ResponseEntity.ok(produtoService.salvarProduto(request))
    }

    @ApiOperation(value = "Updates a new Product")
    @PatchMapping("/{id}")
    fun atualizarProduto(@RequestBody request: ProdutoRequest, @PathVariable id:Int): ResponseEntity<Produto>{
        return ResponseEntity.ok(produtoService.atualizarProduto(request, id))
    }

    @ApiOperation(value = "Removes a Product by its ID")
    @DeleteMapping("/{id}")
    fun removerProduto(@PathVariable id: Int): ResponseEntity<Unit> {
        return ResponseEntity.ok(produtoService.removerProduto(id))
    }

    @PatchMapping("/{id}/activate")
    fun ativar(@PathVariable id: Int) = ResponseEntity.ok(produtoService.ativar(id))

    @PatchMapping("/{id}/deactivate")
    fun desativar(@PathVariable id: Int) = ResponseEntity.ok(produtoService.desativar(id))

}