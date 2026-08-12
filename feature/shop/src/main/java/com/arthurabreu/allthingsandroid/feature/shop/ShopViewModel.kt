package com.arthurabreu.allthingsandroid.feature.shop

import androidx.lifecycle.ViewModel
import com.arthurabreu.allthingsandroid.core.common.AppResult
import com.arthurabreu.allthingsandroid.core.domain.CartCalculator
import com.arthurabreu.allthingsandroid.core.domain.ShopCatalog
import com.arthurabreu.allthingsandroid.core.model.CartLine
import com.arthurabreu.allthingsandroid.core.model.ShopProduct
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class ShopState(
    val products: List<ShopProduct> = emptyList(),
    val lines: List<CartLine> = emptyList(),
    val totalCents: Int = 0,
    val message: String? = null,
) {
    val itemCount: Int get() = lines.sumOf { it.quantity }
}

class ShopViewModel(
    private val catalog: ShopCatalog = ShopCatalog(),
    private val cart: CartCalculator = CartCalculator(),
) : ViewModel() {
    private val _state = MutableStateFlow(ShopState(products = catalog.products()))
    val state: StateFlow<ShopState> = _state.asStateFlow()

    fun add(id: String) {
        val product = catalog.byId(id) ?: return
        when (val result = cart.add(_state.value.lines, product)) {
            is AppResult.Ok -> _state.update {
                it.copy(lines = result.value, totalCents = cart.totalCents(result.value), message = null)
            }
            is AppResult.Err -> _state.update { it.copy(message = result.message) }
        }
    }

    fun remove(id: String) {
        val lines = cart.remove(_state.value.lines, id)
        _state.update { it.copy(lines = lines, totalCents = cart.totalCents(lines), message = null) }
    }

    fun clearLine(id: String) {
        val lines = cart.clearLine(_state.value.lines, id)
        _state.update { it.copy(lines = lines, totalCents = cart.totalCents(lines), message = null) }
    }

    fun checkout() {
        if (_state.value.lines.isEmpty()) {
            _state.update { it.copy(message = "Cart is empty") }
        } else {
            _state.update {
                it.copy(lines = emptyList(), totalCents = 0, message = "Order placed")
            }
        }
    }
}
