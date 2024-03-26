package io.berson.reaad.ui.profileUser

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import io.berson.reaad.ui.components.GradientSurface
import io.berson.reaad.ui.utils.mountAuthorList
import io.berson.reaad.ui.viewmodel.AuthorViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import io.berson.reaad.R
import io.berson.reaad.ui.components.AppBottomBar
import io.berson.reaad.ui.components.FloatingActionButton
import io.berson.reaad.ui.navigation.DestinationScreen
import io.berson.reaad.ui.theme.PrimaryColor
import io.berson.reaad.ui.viewmodel.AuthViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileUserScreen(
    navController: NavController,
    vm: AuthViewModel,
    onNavToHomePage: () -> Unit
) {

    val authorUiState = vm.loginUiState

    Scaffold(
        bottomBar = {
            AppBottomBar(navController = navController)
        }
    ) {
        GradientSurface {
            Box(modifier = Modifier.fillMaxSize()) {
                // ... outros elementos da tela ...
                Text(
                    text = "sair",
                    modifier = Modifier
                        .clickable(
                            onClick = {
                                vm.logout()
                                onNavToHomePage()
                            }
                        )
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 60.dp)
                        .align(Alignment.BottomCenter),
                    fontFamily = FontFamily(Font(R.font.exo2)),
                    fontSize = 16.sp,
                    color = PrimaryColor,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}