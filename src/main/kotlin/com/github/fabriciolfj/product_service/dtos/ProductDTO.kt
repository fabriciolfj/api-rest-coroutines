package com.github.fabriciolfj.product_service.dtos

import com.github.fabriciolfj.product_service.entities.Product
import java.math.BigDecimal

data class ProductDTO(val name: String, val price: BigDecimal) {


    fun toEntity() = Product(null, name, price)
}