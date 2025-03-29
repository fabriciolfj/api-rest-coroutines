package com.github.fabriciolfj.product_service.controller

import com.github.fabriciolfj.product_service.dtos.ProductDTO
import com.github.fabriciolfj.product_service.services.ProductService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/products")
class ProductController(private val productService: ProductService) {

    @GetMapping("/{id}")
    fun findProduct(@PathVariable("id") id: Long) =
        runBlocking { productService.findProductById(id) }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createProduct(@RequestBody dto: ProductDTO) = runBlocking {  productService.persist(dto.toEntity()) }

    @GetMapping
    fun getProducts() = runBlocking {  productService.findAllProducts() }
}