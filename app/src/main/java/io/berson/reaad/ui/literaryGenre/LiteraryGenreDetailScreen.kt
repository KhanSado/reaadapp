package io.berson.reaad.ui.literaryGenre

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import io.berson.reaad.ui.components.GradientSurface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.berson.reaad.ui.book.BooksLazyGridList
import io.berson.reaad.ui.components.TopAppBar
import io.berson.reaad.ui.viewmodel.BookViewModel
import io.berson.reaad.ui.viewmodel.LiteraryGenreViewModel


@Composable
fun LiteraryGenreDetailScreen(
    vm: LiteraryGenreViewModel,
    bookVm: BookViewModel,
    literaryGenreId: String,
    navController: NavController
) {

    val literaryGenreUiState = vm.literaryGenreUiState
    val bookUiState = bookVm.bookUiState

    vm.getLiteraryGenreById(literaryGenreId)
    bookVm.getBooksListByLiteraryGenre(literaryGenreId)


    GradientSurface {

        TopAppBar(
            navController = navController,
            resName = "${literaryGenreUiState.selectedLiteraryGenre?.name}"
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp, start = 24.dp, end = 24.dp, bottom = 20.dp)
        ) {
            bookUiState.bookList?.let { BooksLazyGridList(books = it, navController = navController) }
        }
    }
}
