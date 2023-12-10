package com.example.sharetasks.ui.ViewGroup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sharetasks.R
import com.example.sharetasks.data.network.Group
import com.example.sharetasks.ui.CreateList.CardComponent


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ViewGroupScreen(
    navController : NavController,
    group: Group,
    onRefresh: () -> Unit,
    onDelete: (group: Group, item: String) -> Unit,
    modifier: Modifier = Modifier
) {

    Scaffold(
        topBar = { TopAppBarGroupScreen(navController = navController, onRefresh = onRefresh) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("CreateList/${group.id}") },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.addItems)
                )
            }
        },
        modifier = modifier
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .padding(paddingValues)
        ) {
            FlowRow(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (items in group.items?: listOf()) {
                    CardComponent(text = items, modifier = modifier.padding(8.dp), onDelete = { onDelete(group, items) })
                }
            }

        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarGroupScreen(navController: NavController, onRefresh: () -> Unit, modifier: Modifier = Modifier) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.addItems)) },
        navigationIcon = {
            if (navController.previousBackStackEntry != null) {
                IconButton(
                    onClick = { navController.popBackStack() }
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.goBackArrow)
                    )
                }
            }
        },
        actions = {
//            IconButton(onClick = { /*TODO*/ }) {
//                AddPeopleIcon()
//            }
//            IconButton(onClick = { /*TODO*/ }) {
//                Icon(
//                    imageVector = Icons.Filled.Share,
//                    contentDescription = stringResource(id = R.string.shareItems)
//                )
//            }
                  IconButton(onClick = onRefresh) {
                      Icon(imageVector = Icons.Default.Refresh, contentDescription = stringResource(id = R.string.refreshPage))
                  }
        } ,
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}

@Composable
fun AddPeopleIcon(modifier: Modifier = Modifier) {
    Box {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = stringResource(id = R.string.addPeopleToGroup)
            )
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                tint = Color.Blue,
                modifier = Modifier
                    .scale(0.8f)
                    .offset(x = 15.dp, y = 15.dp)
                    .background(color = Color.Green, shape = RoundedCornerShape(10.dp))
                    .zIndex(1f)
            )

    }
}

@Preview
@Composable
fun AddPeopleIconPreview() {
    AddPeopleIcon()
}