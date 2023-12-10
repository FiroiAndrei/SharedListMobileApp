package com.example.sharetasks.ui.CreateList

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sharetasks.R
import com.example.sharetasks.data.network.Group
import com.example.sharetasks.dimensions.Dimensions


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CreateListScreen(
    navController: NavController,
    onAddToList: (group : Group, items: List<String>) -> Unit,
    group: Group,
    viewModel: CreateListViewmodel,
    modifier : Modifier = Modifier
) {
    
    val uiState by viewModel.state.collectAsState()

    var itemInput by rememberSaveable {
        mutableStateOf("")
    }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {

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
                } else {
                    null
                }
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .padding(Dimensions.mediumPadding)
        ) {

            FlowRow(
                modifier = Modifier
                    .padding(8.dp)
                    .verticalScroll(rememberScrollState())
                    .weight(1f, fill = false),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (item in uiState.reversed()) {
                    CardComponent(text = item, onDelete = { viewModel.deleteItem(item) }, modifier = Modifier.padding(8.dp))
                }
            }
            TextField(
                value = itemInput,
                onValueChange = { 
                    itemInput = it
                    Log.d("A", "CreateListScreen: $uiState")
                                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Send
                ),
                keyboardActions = KeyboardActions(
                    onSend = {
                        viewModel.addItem(itemInput)
                        itemInput = ""
                    }
                ),
                label = { Text(text = stringResource(id = R.string.item)) },
                modifier = androidx.compose.ui.Modifier.padding(Dimensions.smallPadding)
            )
            Button(
                onClick = {
                    Log.d("aioci", "CreateListScreen: $uiState")
                    if (uiState.isNotEmpty()) {
                        onAddToList(group, uiState)
                        if (navController.previousBackStackEntry != null)
                            navController.popBackStack()
                    }

                },
                enabled = uiState.isNotEmpty()
            ) {
                Text(
                    text = stringResource(id = R.string.addToList),
                    modifier = androidx.compose.ui.Modifier.padding(Dimensions.smallPadding)
                )

            }

        }




    }
}

@Composable
fun CardComponent(text: String, onDelete: () -> Unit = {}, modifier: Modifier = Modifier) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = text,
                modifier = Modifier
                    .padding(8.dp),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = stringResource(id = R.string.deleteItem),
                modifier = Modifier
                    .clickable(onClick = onDelete)
                    .padding(8.dp)
            )
        }

    }
}

@Preview
@Composable
fun CardComponentPreview() {
    CardComponent(text = "Oua cu branza")

}