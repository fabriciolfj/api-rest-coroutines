package com.github.fabriciolfj.product_service.repositories

import com.github.fabriciolfj.product_service.entities.Product
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : CoroutineCrudRepository<Product, Long>