package io.berson.reaad.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.berson.reaad.ui.navigation.DestinationScreen
import io.berson.reaad.ui.theme.PrimaryColor

@Composable
fun FloatingActionButton(navController: NavController, destination: String) {
    Surface {
        FloatingActionButton(
            onClick = {
                navController.navigate(destination)
            },
            containerColor = PrimaryColor,
            contentColor = Color.White,
            shape = RoundedCornerShape(24.dp),
            ) {
            Icon(imageVector = Icons.Rounded.Add, contentDescription = "Add FAB")
        }
    }
}