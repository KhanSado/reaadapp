package io.berson.reaad.ui.author

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import io.berson.reaad.ui.components.GradientSurface
import io.berson.reaad.ui.viewmodel.AuthorViewModel
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.berson.reaad.ui.book.BooksLazyGridList
import io.berson.reaad.ui.components.TopAppBar
import io.berson.reaad.ui.viewmodel.BookViewModel


@Composable
fun AuthorDetailScreen(
    vm: AuthorViewModel,
    bookVm: BookViewModel,
    authorId: String,
    navController: NavController
) {

    val authorUiState = vm.authorUiState
    val bookUiState = bookVm.bookUiState

    vm.getAuthorById(authorId)
    bookVm.getBooksListByAuthor(authorId)

    GradientSurface {
        TopAppBar(
            navController = navController,
            resName = "${authorUiState.selectedAuthor?.firstname} ${authorUiState.selectedAuthor?.lastname}"
        )

        Column{
            bookUiState.bookList?.let { BooksLazyGridList(books = it, navController = navController) }
        }
    }
}
