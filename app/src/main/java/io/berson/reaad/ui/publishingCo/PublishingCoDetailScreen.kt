package io.berson.reaad.ui.publishingCo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import io.berson.reaad.ui.components.GradientSurface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import io.berson.reaad.ui.book.BooksLazyGridList
import io.berson.reaad.ui.viewmodel.BookViewModel
import io.berson.reaad.ui.viewmodel.PublishingCoViewModel


@Composable
fun PublishingCoDetailScreen(
    vm: PublishingCoViewModel,
    bookVm: BookViewModel,
    publishingCoId: String,
    navController: NavController
) {

    val publishingCoUiState = vm.publisingCoUiState
    val bookUiState = bookVm.bookUiState

    vm.getPublishingCoById(publishingCoId)
    bookVm.getBooksListByPublishingCo(publishingCoId)
    GradientSurface {
        Text(text = "${publishingCoUiState.selectedPublishingCo?.name}")
        bookUiState.bookList?.let { BooksLazyGridList(books = it, navController = navController) }
    }
}
