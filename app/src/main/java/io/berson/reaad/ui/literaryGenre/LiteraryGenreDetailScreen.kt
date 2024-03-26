package io.berson.reaad.ui.literaryGenre

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import io.berson.reaad.ui.components.GradientSurface
import androidx.compose.material3.Text
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
        bookUiState.bookList?.let { BooksLazyGridList(books = it, navController = navController) }

    }
}
