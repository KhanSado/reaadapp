package io.berson.reaad.ui.author

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import io.berson.reaad.ui.components.GradientSurface
import io.berson.reaad.ui.viewmodel.AuthorViewModel
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.berson.reaad.ui.components.AppBottomBar
import io.berson.reaad.ui.components.FloatingActionButton
import io.berson.reaad.ui.components.TopAppBar
import io.berson.reaad.ui.models.Author
import io.berson.reaad.ui.navigation.DestinationScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAuthorsScreen(navController: NavController, vm: AuthorViewModel) {
    
    val authorUiState = vm.authorUiState
    vm.getAuthorsList()
    Scaffold (
        bottomBar = {
            AppBottomBar(navController = navController)
        },
        floatingActionButton = {
            FloatingActionButton(navController = navController, destination = DestinationScreen.CreateNewAuthorScreen.name)
        }
    ) {
        GradientSurface {
            TopAppBar(
                navController = navController,
                resName = "Meus autores",
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 50.dp, start = 24.dp, end = 24.dp, bottom = 20.dp)
            ) {
                authorUiState.authorList?.let { it1 -> AuthorLazyGridList(
                    authors = it1,
                    navController = navController
                ) }
            }
        }
    }
}

@Composable
fun AuthorLazyGridList(
    authors: List<Author>,
    navController: NavController
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxHeight(),
        contentPadding = PaddingValues(top = 60.dp, start = 24.dp, end = 24.dp)
    ) {
        items(items = authors) { author ->
            Box(
                modifier = Modifier
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .height(48.dp)
                    .width(48.dp)
                    .clickable(
                        onClick = {
                            navController.navigate("${DestinationScreen.AuthorDetailScreen.name}/${author.documentId}")
                        }
                    ),

            ) {
                Text(
                    text = "${author.firstname} ${author.lastname}",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}