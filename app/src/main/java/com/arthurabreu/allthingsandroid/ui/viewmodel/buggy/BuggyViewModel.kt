package com.arthurabreu.allthingsandroid.ui.viewmodel.buggy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arthurabreu.allthingsandroid.core.navigation.AppNavigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BuggyViewModel(
    private val appNavigator: AppNavigator
) : ViewModel() {

    // SIMPLE BUG: Using a plain variable instead of StateFlow/MutableState. 
    // The UI will read this once and never update when it changes.
    var simpleCounter = 0

    // For the complex bug state
    private val _items = MutableStateFlow<List<String>>(emptyList())
    val items = _items.asStateFlow()

    private val sharedList = mutableListOf<String>()

    fun incrementCounter() {
        simpleCounter++
        // Log to show it IS actually changing in memory
        println("BuggyViewModel: simpleCounter is now $simpleCounter")
    }

    /**
     * COMPLEX BUG: ConcurrentModificationException.
     * We're launching multiple coroutines that modify a non-thread-safe list 
     * while another coroutine might be reading it (or they might conflict with each other).
     * Even though it's wrapped in MutableStateFlow, the underlying 'sharedList' 
     * is mutated across multiple coroutines without synchronization.
     */
    fun triggerComplexBug() {
        viewModelScope.launch {
            // Clear previous state
            sharedList.clear()
            _items.value = emptyList()

            // Launch 5 concurrent "fetchers"
            repeat(5) { i ->
                launch(Dispatchers.Default) {
                    repeat(20) { j ->
                        delay(10) // Small delay to increase chance of interleaving
                        sharedList.add("Item $i-$j")
                        
                        // This might cause ConcurrentModificationException if 
                        // another coroutine is also modifying it or if we're 
                        // copying it to StateFlow here.
                        _items.value = ArrayList(sharedList) 
                    }
                }
            }
        }
    }

    fun onBackClick() {
        viewModelScope.launch {
            appNavigator.navigateBack()
        }
    }
}
