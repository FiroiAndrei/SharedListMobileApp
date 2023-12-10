package com.example.sharetasks.ui.CreateList

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CreateListViewmodel: ViewModel() {



    private var _state = MutableStateFlow(
        emptyList<String>()
    )
    val state = _state.asStateFlow()

    fun addItem(item: String) {
        _state.update { it ->
            it + item
        }
    }

    fun deleteItem(item : String) {
        Log.d("aici", "deleteItem: $item, ${state.value}")
        _state.update { it ->
            it - item
        }
    }

}