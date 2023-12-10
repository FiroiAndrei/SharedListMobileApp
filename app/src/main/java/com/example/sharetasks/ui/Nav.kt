package com.example.sharetasks.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHost
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sharetasks.data.network.firebase.FirebaseApi
import com.example.sharetasks.ui.CreateGroup.CreateGroupScreen
import com.example.sharetasks.ui.CreateList.CreateListScreen
import com.example.sharetasks.ui.CreateList.CreateListViewmodel
import com.example.sharetasks.ui.HomeScreen.HomeScreen
import com.example.sharetasks.ui.HomeScreen.HomeScreenViewModel
import com.example.sharetasks.ui.ViewGroup.ViewGroupScreen
import kotlinx.coroutines.launch

@Composable
fun Nav(viewModel: HomeScreenViewModel) {

//
//    val FirebaseApi = remember {
//        FirebaseApi()
//    }
//
//
//    val firebaseApiGroups = remember {
//        FirebaseApi.results
//    }


    val TAG = "NAV"

    val uiState by viewModel.state.collectAsState()
//    Log.d(TAG, "Nav: ${FirebaseApi.results}")

    val coroutineScope = rememberCoroutineScope()

    Log.d(TAG, "Nav: ${uiState}")

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "HomeScreen"
    ) {

        composable(route = "HomeScreen") {
            HomeScreen(navController = navController, groups = uiState.groups ?: listOf(), onRefresh = { coroutineScope.launch { viewModel.onRefresh() } })
        }
        composable(route = "CreateGroup") {
            CreateGroupScreen(navController = navController, onCreate = viewModel::createGroup )
        }
        composable(route = "CreateList/{groupId}") { backStackEntry ->
            Log.d(TAG, "Nav Create List: ${backStackEntry.arguments?.getString("groupId")}")
            uiState.groups?.forEach { group ->
                val id = backStackEntry.arguments?.getString("groupId")!!
                if (group.id == id) {
                    val createListViewModel = viewModel<CreateListViewmodel>()
                    CreateListScreen(navController, onAddToList = viewModel::addItems, viewModel = createListViewModel, group = group)
                }
            }
        }
        composable(route = "ViewGroupScreen/{groupId}", arguments = listOf(navArgument("groupId") {type = NavType.StringType})) { backStackEntry ->
            Log.d(TAG, "NavComposable: ${backStackEntry.arguments?.getString("groupId")}")
            uiState.groups?.forEach { group ->
                val id = backStackEntry.arguments?.getString("groupId")!!
                if (group.id == id) {
                    ViewGroupScreen(navController = navController, group = group, onRefresh = { coroutineScope.launch { viewModel.onRefresh() } }, onDelete = viewModel::deleteItem)
                }
            }
        }
//        composable(route = "ViewGroupScreen", arguments = listOf(navArgument("groupId") {
//            type = NavType.StringType
//        })) {
//            ViewGroupScreen(navController = navController)
//        }
    }
}