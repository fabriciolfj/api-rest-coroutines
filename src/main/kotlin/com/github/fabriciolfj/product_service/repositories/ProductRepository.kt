package com.github.fabriciolfj.product_service.repositories

import com.github.fabriciolfj.product_service.entities.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.awaitOne
import org.springframework.r2dbc.core.awaitOneOrNull
import org.springframework.stereotype.Repository
import java.math.BigDecimal

@Repository
class ProductRepository(private val client: DatabaseClient) {

    suspend fun findById(id: Long): Product? =
        client
            .sql("SELECT * FROM products WHERE id = :id")
            .bind("id", id)
            .map { row ->
                Product(
                    row.get("id", Long::class.java),
                    row.get("name", String::class.java)!!,
                    row.get("price", BigDecimal::class.java)!!
                )
            }
            .awaitOneOrNull()

    suspend fun findAll(): Flow<Product> =
        client
            .sql("SELECT * FROM products")
            .map { row ->
                Product(
                    row.get("id", Long::class.java),
                    row.get("name", String::class.java)!!,
                    row.get("price", BigDecimal::class.java)!!
                )
            }
            .all()
            .asFlow()

    suspend fun save(product: Product): Product {
        return if (product.id == null) {
            val id = client
                .sql("INSERT INTO products (name, price) VALUES (:name, :price) RETURNING id")
                .bind("name", product.name)
                .bind("price", product.price)
                .map { row -> row.get("id", Long::class.java)!! }
                .awaitOne()

            Product(id, product.name, product.price)
        } else {
            client
                .sql("UPDATE products SET name = :name, price = :price WHERE id = :id")
                .bind("id", product.id)
                .bind("name", product.name)
                .bind("price", product.price)
                .fetch()
                .rowsUpdated()
                .awaitSingle()

            product
        }
    }

    suspend fun deleteById(id: Long) {
        client
            .sql("DELETE FROM products WHERE id = :id")
            .bind("id", id)
            .fetch()
            .rowsUpdated()
            .awaitSingle()
    }

    suspend fun count(): Long =
        client
            .sql("SELECT COUNT(*) FROM products")
            .map { row -> row.get(0, Long::class.java)!! }
            .awaitOne()
}