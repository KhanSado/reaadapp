package io.berson.reaad.ui.book

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import io.berson.reaad.ui.components.GradientSurface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import io.berson.reaad.R
import io.berson.reaad.ui.book.BooksLazyGridList
import io.berson.reaad.ui.components.TopAppBar
import io.berson.reaad.ui.theme.PrimaryColor
import io.berson.reaad.ui.viewmodel.AuthorViewModel
import io.berson.reaad.ui.viewmodel.BookViewModel
import io.berson.reaad.ui.viewmodel.LiteraryGenreViewModel
import io.berson.reaad.ui.viewmodel.PublishingCoViewModel


@Composable
fun BookDetailScreen(
    vm: BookViewModel,
    bookId: String,
    publishingCoVm: PublishingCoViewModel,
    authorVm: AuthorViewModel,
    literaryGenreVm: LiteraryGenreViewModel,
    navController: NavController
) {

    val bookUiState = vm.bookUiState
    val publishingCoUiState = publishingCoVm.publisingCoUiState
    val authorUiState = authorVm.authorUiState
    val literaryUiState = literaryGenreVm.literaryGenreUiState

    vm.getBookById(bookId)

    GradientSurface {

        TopAppBar(
            navController = navController,
            resName = "${bookUiState.selectedBook?.title}"
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp, start = 24.dp, end = 24.dp, bottom = 20.dp)
        ) {


            Image(
                painter = rememberAsyncImagePainter(bookUiState.selectedBook?.cover),
                contentDescription = "",
                modifier = Modifier
                    .height(240.dp)
                    .padding(top = 18.dp)
            )

            bookUiState.selectedBook?.let {

                authorVm.getAuthorById(it.authorId)
                publishingCoVm.getPublishingCoById(it.publishingCoId)
                literaryGenreVm.getLiteraryGenreById(it.literaryGenreId)

                Text(
                    text = it.title,
                    fontFamily = FontFamily(Font(R.font.exo2)),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(top = 18.dp)
                )

                Text(
                    text = "${authorUiState.selectedAuthor?.firstname} ${authorUiState.selectedAuthor?.lastname}",
                    fontFamily = FontFamily(Font(R.font.exo2)),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .padding(top = 16.dp)
                )

                Row {
                    Text(
                        text = "${publishingCoUiState.selectedPublishingCo?.name}",
                        fontFamily = FontFamily(Font(R.font.exo2)),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier
                            .padding(top = 14.dp)
                    )

                    Spacer(modifier = Modifier.width(30.dp))
                    
                    Text(
                        text = "${literaryUiState.selectedLiteraryGenre?.name}",
                        fontFamily = FontFamily(Font(R.font.exo2)),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier
                            .padding(top = 14.dp)
                    )
                }
            }
            
        }
    }
}
