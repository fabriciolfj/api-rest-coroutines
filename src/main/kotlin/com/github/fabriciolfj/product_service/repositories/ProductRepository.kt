package com.github.fabriciolfj.product_service.repositories

import com.github.fabriciolfj.product_service.entities.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long> {

}