package com.example.sharetasks.ui.HomeScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharetasks.data.network.Group
import com.example.sharetasks.data.network.Groups
import com.example.sharetasks.data.network.firebase.FirebaseApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenViewModel: ViewModel() {


    private var _state = MutableStateFlow(
        Groups()
    )
    val state = _state.asStateFlow()

    private val firebaseApi: FirebaseApi = FirebaseApi()


    fun setGroups(groups: Groups) {
        _state.update { it ->
            it.copy(
                groups = groups.groups ?: listOf()
            )
        }
    }

    init {
        viewModelScope.launch {
            onRefresh()
        }
    }

    suspend fun onRefresh() {
        firebaseApi.getGroups()
        Log.d("aici", "onRefresh: ${state.value} , ${firebaseApi.results.value}")
        setGroups(firebaseApi.results.value)
    }

    fun addItems(group: Group, items: List<String>) {
        viewModelScope.launch {
            firebaseApi.addItems(group, items)
        }
        viewModelScope.launch {
            onRefresh()
        }
    }

    fun deleteItem(group: Group, item: String) {
        Log.d("A", "deleteItem: $item")
//        for (gr in state.value.groups?: listOf()) {
//            if (gr.id == group.id) {
//                group.items.r
//                break
//                Log.d("A", "deleteItem: ${gr.items}")
//            }
//        }
        viewModelScope.launch {
            firebaseApi.deleteItem(group, item)
        }
        viewModelScope.launch {
            onRefresh()
        }
    }

    fun createGroup(name: String) {
        Log.d("Aici", "createGroup: $name")
        viewModelScope.launch {
            firebaseApi.createGroup(name)
        }
        viewModelScope.launch {
            onRefresh()
        }
    }


}