package com.arthurabreu.allthingsandroid.feature.shop

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.arthurabreu.allthingsandroid.core.model.CartLine
import com.arthurabreu.allthingsandroid.core.model.ShopProduct
import com.arthurabreu.allthingsandroid.core.ui.components.CartBadgeIconButton
import com.arthurabreu.allthingsandroid.core.ui.components.CartLineRow
import com.arthurabreu.allthingsandroid.core.ui.components.ProductListItem
import com.arthurabreu.allthingsandroid.core.ui.money.MoneyFormat
import com.arthurabreu.allthingsandroid.core.ui.shop.ShopColors
import com.arthurabreu.allthingsandroid.core.ui.shop.ShopTheme
import com.arthurabreu.allthingsandroid.core.ui.shop.shopProductVisual
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopScreen(viewModel: ShopViewModel, onBack: () -> Unit = {}) {
    ShopTheme {
        val state by viewModel.state.collectAsState()
        var cartOpen by remember { mutableStateOf(false) }
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        val snackbarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()

        LaunchedEffect(state.message) {
            val message = state.message ?: return@LaunchedEffect
            snackbarHostState.showSnackbar(message)
            if (message == "Order placed") {
                cartOpen = false
            }
        }

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .testTag("shop-screen"),
            containerColor = MaterialTheme.colorScheme.background,
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Shop",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold,
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onBack, modifier = Modifier.testTag("shop-back")) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                            )
                        }
                    },
                    actions = {
                        CartBadgeIconButton(
                            itemCount = state.itemCount,
                            onClick = { cartOpen = true },
                        )
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        titleContentColor = MaterialTheme.colorScheme.onBackground,
                        navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                        actionIconContentColor = MaterialTheme.colorScheme.onBackground,
                    ),
                )
            },
            snackbarHost = { SnackbarHost(snackbarHostState) },
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(bottom = 24.dp),
            ) {
                items(state.products, key = { it.id }) { product ->
                    val inCart = state.lines.find { it.product.id == product.id }?.quantity ?: 0
                    ShopProductRow(
                        product = product,
                        quantityInCart = inCart,
                        onAdd = { viewModel.add(product.id) },
                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = ShopColors.Divider,
                        thickness = 1.dp,
                    )
                }
            }
        }

        if (cartOpen) {
            ModalBottomSheet(
                onDismissRequest = { cartOpen = false },
                sheetState = sheetState,
                containerColor = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
                modifier = Modifier.testTag("cart-sheet"),
            ) {
                ShopCartSheet(
                    lines = state.lines,
                    totalCents = state.totalCents,
                    onIncrement = viewModel::add,
                    onDecrement = viewModel::remove,
                    onRemove = viewModel::clearLine,
                    onCheckout = {
                        viewModel.checkout()
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) cartOpen = false
                        }
                    },
                )
            }
        }
    }
}

@Composable
private fun ShopProductRow(
    product: ShopProduct,
    quantityInCart: Int,
    onAdd: () -> Unit,
) {
    val visual = shopProductVisual(product.id)
    val remaining = (product.stock - quantityInCart).coerceAtLeast(0)
    ProductListItem(
        name = product.name,
        subtitle = visual.subtitle,
        priceLabel = MoneyFormat.fromCents(product.priceCents),
        iconRes = visual.iconRes,
        onAddClick = onAdd,
        addEnabled = remaining > 0,
        addTestTag = "add-${product.id}",
        avatarContainerColor = visual.containerColor,
        avatarIconTint = visual.iconTint,
    )
}

@Composable
private fun ShopCartSheet(
    lines: List<CartLine>,
    totalCents: Int,
    onIncrement: (String) -> Unit,
    onDecrement: (String) -> Unit,
    onRemove: (String) -> Unit,
    onCheckout: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(bottom = 20.dp)
            .testTag("cart-sheet-content"),
    ) {
        Text(
            text = "Cart",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
        )

        if (lines.isEmpty()) {
            Text(
                text = "Your cart is empty",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .padding(16.dp)
                    .testTag("cart-empty"),
            )
        } else {
            lines.forEach { line ->
                val visual = shopProductVisual(line.product.id)
                val remaining = line.product.stock - line.quantity
                CartLineRow(
                    name = line.product.name,
                    subtitle = visual.subtitle,
                    unitPriceLabel = MoneyFormat.fromCents(line.product.priceCents),
                    quantity = line.quantity,
                    iconRes = visual.iconRes,
                    onIncrement = { onIncrement(line.product.id) },
                    onDecrement = { onDecrement(line.product.id) },
                    onRemove = { onRemove(line.product.id) },
                    incrementEnabled = remaining > 0,
                    avatarContainerColor = visual.containerColor,
                    avatarIconTint = visual.iconTint,
                    testTagPrefix = "cart-line-${line.product.id}",
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider(color = ShopColors.Divider)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Total",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = MoneyFormat.fromCents(totalCents),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.testTag("cart-total"),
            )
        }
        Button(
            onClick = onCheckout,
            enabled = lines.isNotEmpty(),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContainerColor = ShopColors.Divider,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(52.dp)
                .testTag("cart-checkout"),
        ) {
            Icon(
                imageVector = Icons.Filled.Lock,
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(18.dp),
            )
            Text(
                text = "Checkout",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}
