package io.berson.reaad.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.berson.reaad.R
import io.berson.reaad.ui.navigation.DestinationScreen
import io.berson.reaad.ui.viewmodel.BottomBarViewModel

@Composable
fun AppBottomBar(navController: NavController) {

    val viewModel = BottomBarViewModel()

    Surface(shadowElevation = 10.dp) {
        BottomAppBar(
            containerColor = Color.White,
            contentColor = Color.Black,
            modifier = Modifier
                .height(45.dp)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    if (viewModel._showHome) {
                        IconButton(
                            onClick = {
                                navController.navigate("${DestinationScreen.HomeScreen.name}")
                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.homeicon),
                                contentDescription = "Home",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                    if (viewModel._showBooks) {
                        IconButton(
                            onClick = {
                                navController.navigate("${DestinationScreen.MainBookScreen.name}")
                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.libicon),
                                contentDescription = "Livros",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                    if (viewModel._showQuotes) {
                        IconButton(
                            onClick = {
                                navController.navigate("${DestinationScreen.MainQuoteScreen.name}")
                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.qupteicon),
                                contentDescription = "Livros",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                    if (viewModel._showProfile) {
                        IconButton(
                            onClick = {
                                navController.navigate("${DestinationScreen.ProfileUserScreen.name}")
                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.profileicon),
                                contentDescription = "Autores",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
