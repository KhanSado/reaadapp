package io.berson.reaad.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.berson.reaad.ui.models.Book


@Composable
fun BookGenreLazyGridList(
    books: List<Book>,
    navController: NavController
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .width(48.dp)
            .height(48.dp)
    ) {
        items(items = books) { book ->
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .height(48.dp)
                    .width(48.dp)
                    .clickable(
                        onClick = {
//                            navController.navigate("${DestinationScreen.LiteraryGenreDetailScreen.name}/${literaryGenre.documentId}")
                        }
                    ),

                ) {
                Text(
                    text = "${book.title}",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}