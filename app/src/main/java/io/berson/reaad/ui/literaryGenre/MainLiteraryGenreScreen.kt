package io.berson.reaad.ui.literaryGenre

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import io.berson.reaad.ui.components.GradientSurface
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import io.berson.reaad.ui.components.AppBottomBar
import io.berson.reaad.ui.components.FloatingActionButton
import io.berson.reaad.ui.models.Author
import io.berson.reaad.ui.models.LiteraryGenre
import io.berson.reaad.ui.navigation.DestinationScreen
import io.berson.reaad.ui.theme.PrimaryColor
import io.berson.reaad.ui.utils.mountLiteraryGenreList
import io.berson.reaad.ui.utils.mountPublishingCoList
import io.berson.reaad.ui.viewmodel.LiteraryGenreViewModel
import io.berson.reaad.ui.viewmodel.PublishingCoViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainLiteraryGenreScreen(navController: NavController, vm: LiteraryGenreViewModel) {

    val literaryGenreUiState = vm.literaryGenreUiState
    vm.getLiteraryGenreList()

    Scaffold(
        bottomBar = {
            AppBottomBar(navController = navController)
        },
        floatingActionButton = {
            FloatingActionButton(
                navController = navController,
                DestinationScreen.CreateNewLiteraryGenreScreen.name
            )
        }
    ) {
        GradientSurface {
            literaryGenreUiState.literaryGenreList?.let { it1 ->
                LiteraryGenreLazyGridList(
                    literaryGenre = it1,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun LiteraryGenreLazyGridList(
    literaryGenre: List<LiteraryGenre>,
    navController: NavController
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .width(48.dp)
            .height(48.dp)
    ) {
        items(items = literaryGenre) { literaryGenre ->
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .height(48.dp)
                    .width(48.dp)
                    .clickable(
                        onClick = {
                            navController.navigate("${DestinationScreen.LiteraryGenreDetailScreen.name}/${literaryGenre.documentId}")
                        }
                    ),

                ) {
                Text(
                    text = "${literaryGenre.name}",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}