package io.berson.reaad.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.berson.reaad.R
import io.berson.reaad.ui.navigation.DestinationScreen

@Composable
fun AppBottomBar(navController: NavController) {
    Surface(shadowElevation = 10.dp) {
        BottomAppBar(
            containerColor =
            Color.White,
            contentColor = Color.Black,
            modifier = Modifier
                .height(45.dp)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,

                ) {
                    IconButton(
                        onClick = {
                            navController.navigate("${DestinationScreen.HomeScreen.name}")
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.twotone_house_24),
                            contentDescription = "Home",
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    IconButton(
                        onClick = {
                            navController.navigate("${DestinationScreen.ProfileUserScreen.name}")
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_person_outline_24),
                            contentDescription = "Autores",
                            modifier = Modifier.size(24.dp)
                        )
                    }
            }
        }
    }
}