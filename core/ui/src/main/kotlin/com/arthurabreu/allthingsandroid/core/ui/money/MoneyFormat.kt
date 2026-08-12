package com.arthurabreu.allthingsandroid.core.ui.money

import java.text.NumberFormat
import java.util.Locale

object MoneyFormat {
    fun fromCents(cents: Int, locale: Locale = Locale.US): String =
        NumberFormat.getCurrencyInstance(locale).format(cents / 100.0)
}
