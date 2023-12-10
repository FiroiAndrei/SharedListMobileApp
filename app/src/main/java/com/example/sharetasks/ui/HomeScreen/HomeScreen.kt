package com.example.sharetasks.ui.HomeScreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sharetasks.R
import com.example.sharetasks.data.network.Group
import com.example.sharetasks.data.network.firebase.FirebaseApi
import com.example.sharetasks.dimensions.Dimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    groups: List<Group>,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
        .fillMaxSize()
) {

    Scaffold(
        topBar = {
            SharedListAppBar(onRefresh = onRefresh)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("CreateGroup")
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.addGroup)
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
        ) {
            if (groups.isEmpty()) {
                NoGroupError(
                    modifier = modifier
                        .padding(Dimensions.mediumPadding)
                )
            } else {
                GroupList(
                    navController = navController,
                    groupList = groups,
                    modifier = modifier
                )
            }
        }
    }
}

@Composable
fun GroupList(
    navController: NavController,
    groupList: List<Group>,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(top = Dimensions.largePadding)
            .verticalScroll(rememberScrollState())
    ) {
        for (group in groupList) {
            Row (
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(Dimensions.mediumPadding)
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(24.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .clickable {
                        Log.d("a", "GroupList: ${group.id}")
                        navController.navigate(
                            "ViewGroupScreen/{groupId}"
                                .replace(
                                    oldValue = "{groupId}",
                                    newValue = group.id!!
                                )
                        )
                    }
            ) {



                Text(
                    text = group.name!!,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(top = Dimensions.smallPadding, bottom = Dimensions.smallPadding, start = Dimensions.smallPadding)
                )

//                Spacer(modifier = Modifier.weight(1f))
//
//                Icon(
//                    imageVector = Icons.Rounded.Add,
//                    contentDescription = "Invite People Icon",
//                    modifier = Modifier
//                        .clip(RoundedCornerShape(16.dp))
//                        .background(MaterialTheme.colorScheme.primaryContainer)
//                        .padding(
//                            top = Dimensions.smallPadding,
//                            bottom = Dimensions.smallPadding,
//                            end = Dimensions.smallPadding
//                        )
//                )


            }
        }
    }
}

@Composable
fun NoGroupError(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.emptyfolder),
            contentDescription = "No groups icon",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth(0.5f)
        )

        Box(
            modifier = Modifier.background(MaterialTheme.colorScheme.errorContainer)
        ) {
            Text(
                text = stringResource(id = R.string.noGroups),
                color = MaterialTheme.colorScheme.onErrorContainer,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(Dimensions.smallPadding)
            )
        }


    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharedListAppBar(onRefresh: () -> Unit,modifier: Modifier = Modifier) {
    TopAppBar(
        title = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.homeTitle),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.weight(1f)
                )

                IconButton(onClick = onRefresh) {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = stringResource(id = R.string.refreshPage))
                }

            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}
