package com.example.sharetasks.ui.CreateGroup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sharetasks.R
import com.example.sharetasks.data.network.firebase.FirebaseApi
import com.example.sharetasks.dimensions.Dimensions


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateGroupScreen(
    navController: NavController,
    onCreate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var groupNameInput by rememberSaveable {
        mutableStateOf("")
    }
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {

        TopAppBar(
            title = { Text(text = stringResource(id = R.string.addGroup)) },
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
            TextField(
                value = groupNameInput,
                onValueChange = { groupNameInput = it },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                label = { Text(text = stringResource(id = R.string.groupNameLabel)) },
                modifier = Modifier.padding(Dimensions.smallPadding)
            )
            Button(
                onClick = {
                    if (groupNameInput != "")
                        onCreate(groupNameInput)
                    if (navController.previousBackStackEntry != null) {
                        navController.popBackStack()
                    }
                }
            ) {
                Text(
                    text = stringResource(id = R.string.createGroup),
                    modifier = Modifier.padding(Dimensions.smallPadding)
                )

            }
        }
    }


}

