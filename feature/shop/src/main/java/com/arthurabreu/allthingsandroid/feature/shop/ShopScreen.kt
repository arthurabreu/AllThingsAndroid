package com.arthurabreu.allthingsandroid.feature.shop

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

import com.arthurabreu.allthingsandroid.core.common.AppResult
import com.arthurabreu.allthingsandroid.core.domain.CartCalculator
import com.arthurabreu.allthingsandroid.core.domain.ShopCatalog
import com.arthurabreu.allthingsandroid.core.model.CartLine
import com.arthurabreu.allthingsandroid.core.model.ShopProduct

data class ShopState(
    val products: List<ShopProduct> = emptyList(),
    val lines: List<CartLine> = emptyList(),
    val totalCents: Int = 0,
    val message: String? = null,
)

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
        _state.update { it.copy(lines = lines, totalCents = cart.totalCents(lines)) }
    }

    fun checkout() {
        if (_state.value.lines.isEmpty()) {
            _state.update { it.copy(message = "Cart is empty") }
        } else {
            _state.update { it.copy(lines = emptyList(), totalCents = 0, message = "Order placed") }
        }
    }
}


@Composable
fun ShopScreen(viewModel: ShopViewModel, onBack: () -> Unit = {}) {
    val state by viewModel.state.collectAsState()
    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp).testTag("shop-screen"),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text("Shop", style = MaterialTheme.typography.headlineSmall)

        Text("Total: ${state.totalCents} cents", modifier = Modifier.testTag("cart-total"))
        state.message?.let { Text(it, modifier = Modifier.testTag("shop-message")) }
        state.products.forEach { product ->
            Button(onClick = { viewModel.add(product.id) }, modifier = Modifier.testTag("add-${product.id}")) {
                Text("Add ${product.name}")
            }
        }
        state.lines.forEach { line ->
            Button(onClick = { viewModel.remove(line.product.id) }) {
                Text("${line.product.name} x${line.quantity}")
            }
        }
        Button(onClick = viewModel::checkout) { Text("Checkout") }

        Button(onClick = onBack) { Text("Back") }
    }
}
