package io.berson.reaad.ui.quote

import android.annotation.SuppressLint
import android.icu.text.ListFormatter.Width
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import io.berson.reaad.ui.components.GradientSurface
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import io.berson.reaad.R
import io.berson.reaad.ui.components.AppBottomBar
import io.berson.reaad.ui.components.FloatingActionButton
import io.berson.reaad.ui.components.TopAppBar
import io.berson.reaad.ui.models.Book
import io.berson.reaad.ui.models.Quote
import io.berson.reaad.ui.navigation.DestinationScreen
import io.berson.reaad.ui.theme.SecundaryColor
import io.berson.reaad.ui.viewmodel.BookViewModel
import io.berson.reaad.ui.viewmodel.QuoteViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainQuoteScreen(navController: NavController, vm: QuoteViewModel) {

    val quoteUiState = vm.quoteUiState
    vm.getAllQuotes()

    Scaffold(
        bottomBar = {
            AppBottomBar(navController = navController)
        },
        floatingActionButton = {
            FloatingActionButton(
                navController = navController,
                destination = DestinationScreen.CreateNewQuoteScreen.name
            )
        }
    ) {
        GradientSurface {
            TopAppBar(
                navController = navController,
                resName = "Minhas citações",
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 50.dp, start = 24.dp, end = 24.dp, bottom = 20.dp)
            ) {
                quoteUiState.quoteList?.let { it1 ->
                    QuoteLazyGridList(
                        quotes = it1,
                        navController = navController
                    )
                }
            }
        }
    }
}


@Composable
fun QuoteLazyGridList(
    quotes: List<Quote>,
    navController: NavController
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxHeight(),
        contentPadding = PaddingValues(
            top = 60.dp,
            start = 24.dp,
            end = 24.dp,
            bottom = 24.dp
        )
    ) {
        items(items = quotes) { quote ->
            Surface(
                modifier = Modifier
                    .background(Color.Transparent)
                    .height(200.dp)
                    .width(120.dp)
                    .padding(start = 8.dp, end = 8.dp, bottom = 8.dp, top = 8.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(start = 8.dp, end = 8.dp, bottom = 8.dp, top = 8.dp)
                        .clickable(onClick = {
                            navController.navigate("${DestinationScreen.BookDetailScreen.name}/${quote.documentId}")
                        }),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = quote.quoteDescription
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
            }
        }
    }
}