package com.arthurabreu.allthingsandroid.feature.lists

import androidx.lifecycle.ViewModel
import com.arthurabreu.allthingsandroid.core.domain.ListFilter
import com.arthurabreu.allthingsandroid.core.domain.SeedRows
import com.arthurabreu.allthingsandroid.core.model.ListRow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class ListsLayout {
    Inbox,
    Board,
}

data class ListsState(
    val query: String = "",
    val page: Int = 0,
    val pageSize: Int = 20,
    val totalCount: Int = 0,
    val visible: List<ListRow> = emptyList(),
    val layout: ListsLayout = ListsLayout.Inbox,
    val error: String? = null,
) {
    val totalPages: Int
        get() = if (totalCount == 0) 0 else ((totalCount - 1) / pageSize) + 1

    val hasPrevious: Boolean get() = page > 0

    val hasNext: Boolean get() = page + 1 < totalPages

    val rangeLabel: String
        get() {
            if (totalCount == 0 || visible.isEmpty()) return "0 of 0"
            val start = page * pageSize + 1
            val end = start + visible.size - 1
            return "$start–$end of $totalCount"
        }
}

class ListsViewModel(
    private val filter: ListFilter = ListFilter(),
    private val seed: List<ListRow> = SeedRows.generate(),
) : ViewModel() {
    private val _state = MutableStateFlow(initialState())
    val state: StateFlow<ListsState> = _state.asStateFlow()

    fun onQuery(value: String) {
        _state.update { refresh(it.copy(query = value, page = 0, error = null)) }
    }

    fun setLayout(layout: ListsLayout) {
        _state.update { it.copy(layout = layout) }
    }

    fun nextPage() {
        _state.update { current ->
            if (!current.hasNext || current.error != null) current
            else refresh(current.copy(page = current.page + 1))
        }
    }

    fun previousPage() {
        _state.update { current ->
            if (!current.hasPrevious || current.error != null) current
            else refresh(current.copy(page = current.page - 1))
        }
    }

    fun fail() {
        _state.update { it.copy(error = "Network unavailable", visible = emptyList(), totalCount = 0) }
    }

    fun retry() {
        _state.update { refresh(it.copy(error = null)) }
    }

    private fun initialState(): ListsState = refresh(ListsState())

    private fun refresh(base: ListsState): ListsState {
        val filtered = filter.apply(seed, base.query)
        val page = base.page.coerceIn(0, maxOf(0, ((filtered.size - 1) / base.pageSize)))
        return base.copy(
            page = page,
            totalCount = filtered.size,
            visible = filter.page(filtered, page, base.pageSize),
            error = null,
        )
    }
}
