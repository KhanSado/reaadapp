package io.berson.reaad.ui.publishingCo

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import io.berson.reaad.ui.components.GradientSurface
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import io.berson.reaad.ui.components.AppBottomBar
import io.berson.reaad.ui.components.FloatingActionButton
import io.berson.reaad.ui.navigation.DestinationScreen
import io.berson.reaad.ui.theme.PrimaryColor
import io.berson.reaad.ui.utils.mountPublishingCoList
import io.berson.reaad.ui.viewmodel.PublishingCoViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPublishingCoScreen(navController: NavController, vm: PublishingCoViewModel) {
    
    val publishingCoUiState = vm.publisingCoUiState
    vm.getPublishingCoList()

    Scaffold (
        bottomBar = {
            AppBottomBar(navController = navController)
        },
        floatingActionButton = {
            FloatingActionButton(navController = navController, DestinationScreen.CreateNewPublishingCoScreen.name)
        }
    ) {
        GradientSurface {
            LazyColumn(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .padding(bottom = 90.dp, top = 45.dp),
                verticalArrangement = Arrangement.Top
            ) {
                items(items = mountPublishingCoList(publishingCoUiState.publishingCoList), itemContent = { item ->
                    PublishingCoView(
                        name = item.name,
                        logo = item.logo,
                        clickOnItem = {
                            navController.navigate("${DestinationScreen.PublishingCoDetailScreen.name}/${item.documentId}")
                        }
                    )
                    Divider(
                        color = PrimaryColor,
                        modifier = Modifier
                            .padding(start = 24.dp, end = 24.dp)
                    )
                })
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun PublishingCoView(
    name: String = "Intrinseca",
    logo: String = "caminho da logo",
    clickOnItem: () -> Unit = {}
){
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(start = 24.dp, top = 24.dp, bottom = 24.dp, end = 24.dp),
        color = Color.Transparent,
        onClick = clickOnItem
    ) {
        Column {
            Text(
                text = name,
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}