package com.arthurabreu.allthingsandroid.core.domain

import com.arthurabreu.allthingsandroid.core.common.AppResult
import com.arthurabreu.allthingsandroid.core.model.CartLine
import com.arthurabreu.allthingsandroid.core.model.ShopProduct

class ShopCatalog {
    fun products(): List<ShopProduct> = listOf(
        ShopProduct("sku-lager", "Lager 350ml", 499, 24),
        ShopProduct("sku-ipa", "IPA 473ml", 799, 12),
        ShopProduct("sku-stout", "Stout 330ml", 899, 6),
        ShopProduct("sku-pack", "Mixed 6-pack", 2999, 3),
    )

    fun byId(id: String): ShopProduct? = products().find { it.id == id }
}

class CartCalculator {
    fun add(lines: List<CartLine>, product: ShopProduct): AppResult<List<CartLine>> {
        val existing = lines.find { it.product.id == product.id }
        val nextQty = (existing?.quantity ?: 0) + 1
        if (nextQty > product.stock) return AppResult.Err("Out of stock")
        val updated = if (existing == null) {
            lines + CartLine(product, 1)
        } else {
            lines.map { if (it.product.id == product.id) it.copy(quantity = nextQty) else it }
        }
        return AppResult.Ok(updated)
    }

    fun remove(lines: List<CartLine>, productId: String): List<CartLine> =
        lines.mapNotNull { line ->
            when {
                line.product.id != productId -> line
                line.quantity <= 1 -> null
                else -> line.copy(quantity = line.quantity - 1)
            }
        }

    fun clearLine(lines: List<CartLine>, productId: String): List<CartLine> =
        lines.filterNot { it.product.id == productId }

    fun totalCents(lines: List<CartLine>): Int = lines.sumOf { it.lineTotalCents }
}
