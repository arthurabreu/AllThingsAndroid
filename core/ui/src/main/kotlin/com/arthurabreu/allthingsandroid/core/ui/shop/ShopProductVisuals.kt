package com.arthurabreu.allthingsandroid.core.ui.shop

import androidx.annotation.DrawableRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.arthurabreu.allthingsandroid.core.ui.R

data class ShopProductVisual(
    @DrawableRes val iconRes: Int,
    val containerColor: Color,
    val iconTint: Color,
)

@Composable
fun shopProductVisual(productId: String): ShopProductVisual {
    val scheme = MaterialTheme.colorScheme
    return when (productId) {
        "sku-lager" -> ShopProductVisual(
            iconRes = R.drawable.ic_product_lager,
            containerColor = scheme.secondaryContainer,
            iconTint = scheme.onSecondaryContainer,
        )
        "sku-ipa" -> ShopProductVisual(
            iconRes = R.drawable.ic_product_ipa,
            containerColor = scheme.tertiaryContainer,
            iconTint = scheme.onTertiaryContainer,
        )
        "sku-stout" -> ShopProductVisual(
            iconRes = R.drawable.ic_product_stout,
            containerColor = scheme.primaryContainer,
            iconTint = scheme.onPrimaryContainer,
        )
        "sku-pack" -> ShopProductVisual(
            iconRes = R.drawable.ic_product_pack,
            containerColor = scheme.surfaceVariant,
            iconTint = scheme.onSurfaceVariant,
        )
        else -> ShopProductVisual(
            iconRes = R.drawable.ic_product_lager,
            containerColor = scheme.surfaceVariant,
            iconTint = scheme.onSurfaceVariant,
        )
    }
}
