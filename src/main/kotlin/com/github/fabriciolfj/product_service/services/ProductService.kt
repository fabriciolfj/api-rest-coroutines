package com.github.fabriciolfj.product_service.services

import com.github.fabriciolfj.product_service.entities.Product
import com.github.fabriciolfj.product_service.repositories.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(private val repository: ProductRepository) {

    @Transactional(propagation = Propagation.REQUIRED)
    suspend fun persist(product: Product) {
        return withContext(Dispatchers.IO) {
            repository.save(product)
        }
    }

    @Transactional(readOnly = true)
    suspend fun findProductById(id: Long): Product {
        return withContext(Dispatchers.IO) {
            repository.findById(id).orElseThrow { throw RuntimeException("Product with ID $id not found") }
        }
    }

    @Transactional(readOnly = true)
    suspend fun findAllProducts(): List<Product> {
        return withContext(Dispatchers.IO) {
            repository.findAll()
        }
    }
}