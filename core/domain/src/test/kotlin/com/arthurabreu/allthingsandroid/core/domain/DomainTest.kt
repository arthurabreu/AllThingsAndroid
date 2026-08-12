package com.arthurabreu.allthingsandroid.core.domain

import com.arthurabreu.allthingsandroid.core.common.AppResult
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class DomainTest {
    private val cart = CartCalculator()
    private val catalog = ShopCatalog()
    private val filter = ListFilter()
    private val feedback = FeedbackRules()

    @Test
    fun catalogHasFourProducts() {
        assertEquals(4, catalog.products().size)
    }

    @Test
    fun addRespectsStock() {
        val product = catalog.byId("sku-pack")!!
        var lines = emptyList<com.arthurabreu.allthingsandroid.core.model.CartLine>()
        repeat(3) {
            lines = (cart.add(lines, product) as AppResult.Ok).value
        }
        val overflow = cart.add(lines, product)
        assertTrue(overflow is AppResult.Err)
        assertEquals(8997, cart.totalCents(lines))
    }

    @Test
    fun removeDropsLineAtZero() {
        val product = catalog.byId("sku-lager")!!
        val added = (cart.add(emptyList(), product) as AppResult.Ok).value
        assertTrue(cart.remove(added, product.id).isEmpty())
    }

    @Test
    fun clearLineRemovesEntireLine() {
        val product = catalog.byId("sku-lager")!!
        var lines = (cart.add(emptyList(), product) as AppResult.Ok).value
        lines = (cart.add(lines, product) as AppResult.Ok).value
        assertEquals(2, lines.single().quantity)
        assertTrue(cart.clearLine(lines, product.id).isEmpty())
    }

    @Test
    fun listFilterAndPaging() {
        val rows = SeedRows.generate(25)
        assertEquals(3, filter.apply(rows, "blocked").size)
        assertEquals(20, filter.page(rows, 0).size)
        assertEquals(5, filter.page(rows, 1).size)
        assertTrue(filter.page(rows, 4).isEmpty())
    }

    @Test
    fun feedbackRequiresCommentOnLowScore() {
        assertTrue(feedback.validate(2, "") is AppResult.Err)
        assertTrue(feedback.validate(5, "") is AppResult.Ok)
        assertTrue(feedback.validate(9, "x") is AppResult.Err)
    }

    @Test
    fun catalogSectionsIncludeLabAtEnd() {
        val sections = PortfolioCatalog.sections()
        assertEquals("Lab / Design System", sections.last().title)
        assertTrue(sections.first().items.any { it.route == "shop" })
        assertTrue(sections.first().items.any { it.route == "listsPaged" })
    }

    @Test
    fun schemaStoryHasThreeVersions() {
        assertEquals(3, SchemaStory.versions().size)
        assertEquals("manual Migration", SchemaStory.versions().last().migrationKind)
    }
}
