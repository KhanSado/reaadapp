package io.berson.reaad.ui.author

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.berson.reaad.R
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
    Scaffold(
        bottomBar = {
            AppBottomBar(navController = navController)
        },
        floatingActionButton = {
            FloatingActionButton(
                navController = navController,
                destination = DestinationScreen.CreateNewAuthorScreen.name
            )
        }
    ) {
        GradientSurface {
            TopAppBar(
                navController = navController,
                resName = stringResource(R.string.authors_list_title),
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 50.dp, start = 24.dp, end = 24.dp, bottom = 55.dp)
            ) {
                authorUiState.authorList?.let { it1 ->
                    AuthorLazyGridList(
                        authors = it1,
                        navController = navController
                    )
                }
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
        contentPadding = PaddingValues(start = 24.dp, end = 24.dp)
    ) {
        items(items = authors) { author ->
            Surface(
                modifier = Modifier
                    .background(Color.Transparent)
                    .height(100.dp)
                    .width(120.dp)
                    .padding(start = 8.dp, end = 8.dp, bottom = 8.dp, top = 8.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp)
                        .padding(start = 8.dp, end = 8.dp, bottom = 8.dp, top = 8.dp)
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
}