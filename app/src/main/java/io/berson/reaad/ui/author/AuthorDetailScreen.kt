package io.berson.reaad.ui.author

import androidx.compose.runtime.Composable
import io.berson.reaad.ui.components.GradientSurface
import io.berson.reaad.ui.viewmodel.AuthorViewModel
import androidx.compose.material3.Text
import androidx.navigation.NavController
import io.berson.reaad.ui.book.BooksLazyGridList
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
        Text(text = "${authorUiState.selectedAuthor?.firstname}")

        bookUiState.bookList?.let { BooksLazyGridList(books = it, navController = navController) }
    }
}
