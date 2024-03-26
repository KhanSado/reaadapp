package io.berson.reaad.ui.publishingCo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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

        TopAppBar(
            navController = navController,
            resName = "${publishingCoUiState.selectedPublishingCo?.name}"
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

