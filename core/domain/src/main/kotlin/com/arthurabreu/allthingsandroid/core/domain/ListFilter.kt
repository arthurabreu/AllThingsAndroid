package com.arthurabreu.allthingsandroid.core.domain

import com.arthurabreu.allthingsandroid.core.model.ListRow

class ListFilter {
    fun apply(rows: List<ListRow>, query: String): List<ListRow> {
        val q = query.trim().lowercase()
        if (q.isEmpty()) return rows
        return rows.filter { it.title.lowercase().contains(q) || it.body.lowercase().contains(q) }
    }

    fun page(rows: List<ListRow>, page: Int, pageSize: Int = 20): List<ListRow> {
        val from = page.coerceAtLeast(0) * pageSize
        if (from >= rows.size) return emptyList()
        return rows.subList(from, minOf(from + pageSize, rows.size))
    }
}

object SeedRows {
    fun generate(count: Int = 80): List<ListRow> =
        (1..count).map { i ->
            ListRow(
                id = "row-$i",
                title = "Ticket #$i",
                body = if (i % 7 == 0) "Blocked on sync" else "Ready for review",
            )
        }
}
