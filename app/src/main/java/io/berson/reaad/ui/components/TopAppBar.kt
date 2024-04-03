package io.berson.reaad.ui.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    navController: NavController,
    resName: String = "",
) {
    Column {
        TopAppBar(
            title = {
                Text(
                    text = resName,
                    modifier = Modifier
                        .padding(top = 10.dp)
                )
            },
            navigationIcon = {
               BackButton(navController = navController)
            },
            modifier = Modifier
                .height(50.dp)
        )
    }
}


@Composable
fun BackButton(navController: NavController) {
    IconButton(
        onClick = { navController.popBackStack() },
        modifier = Modifier.height(50.dp)
    ) {
        Icon(Icons.Filled.ArrowBack, null)
    }
}