package com.arthurabreu.allthingsandroid.core.ui.shop

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.arthurabreu.allthingsandroid.core.ui.R

data class ShopProductVisual(
    @param:DrawableRes val iconRes: Int,
    val containerColor: Color,
    val iconTint: Color,
    val subtitle: String,
)

@Composable
fun shopProductVisual(productId: String): ShopProductVisual {
    return when (productId) {
        "sku-lager" -> ShopProductVisual(
            iconRes = R.drawable.ic_product_lager,
            containerColor = ShopColors.AvatarFill,
            iconTint = ShopColors.AmberDark,
            subtitle = "350ml • Lager",
        )
        "sku-ipa" -> ShopProductVisual(
            iconRes = R.drawable.ic_product_ipa,
            containerColor = Color(0xFFE8DCC8),
            iconTint = ShopColors.AmberDark,
            subtitle = "473ml • IPA",
        )
        "sku-stout" -> ShopProductVisual(
            iconRes = R.drawable.ic_product_stout,
            containerColor = Color(0xFFE0D5C8),
            iconTint = ShopColors.Amber,
            subtitle = "330ml • Stout",
        )
        "sku-pack" -> ShopProductVisual(
            iconRes = R.drawable.ic_product_pack,
            containerColor = Color(0xFFEDE6DA),
            iconTint = ShopColors.AmberDark,
            subtitle = "6 bottles • Mixed",
        )
        else -> ShopProductVisual(
            iconRes = R.drawable.ic_product_lager,
            containerColor = ShopColors.AvatarFill,
            iconTint = ShopColors.AmberDark,
            subtitle = "Beer",
        )
    }
}
