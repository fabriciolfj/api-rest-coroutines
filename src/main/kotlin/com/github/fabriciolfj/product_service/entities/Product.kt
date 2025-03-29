package com.github.fabriciolfj.product_service.entities


import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal

@Table("products")
data class Product(

    @Id
    val id: Long? = null,

    @Column
    val name: String,

    @Column
    val price: BigDecimal
)