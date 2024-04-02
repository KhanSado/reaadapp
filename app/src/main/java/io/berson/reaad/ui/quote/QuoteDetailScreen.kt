package io.berson.reaad.ui.quote

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import io.berson.reaad.ui.components.GradientSurface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import io.berson.reaad.R
import io.berson.reaad.ui.book.BooksLazyGridList
import io.berson.reaad.ui.components.TopAppBar
import io.berson.reaad.ui.viewmodel.BookUiState
import io.berson.reaad.ui.viewmodel.BookViewModel
import io.berson.reaad.ui.viewmodel.LiteraryGenreViewModel
import io.berson.reaad.ui.viewmodel.QuoteViewModel


@Composable
fun QuoteDetailScreen(
    vm: QuoteViewModel,
    quoteId: String,
    navController: NavController,
    bookVm: BookViewModel
) {

    val quoteUiState = vm.quoteUiState
    val bookUiState = bookVm.bookUiState

    vm.getQuoteById(quoteId)

    GradientSurface {

        TopAppBar(
            navController = navController,
            resName = ""
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp, start = 24.dp, end = 24.dp, bottom = 20.dp)
        ) {
            quoteUiState.selectedQuote?.let { bookVm.getBookById(it.bookId) }
            var selectedBook = bookUiState.selectedBook

            Text(
                text = "${quoteUiState.selectedQuote?.quoteDescription}",
                fontSize = 24.sp,
                textAlign = TextAlign.Justify,
                fontFamily = FontFamily(Font(R.font.confortaa))
            )

            Spacer(modifier = Modifier.height(30.dp))

            if (selectedBook != null) {
                Text(
                    text = selectedBook.title,
                    fontSize = 16.sp,
                    textAlign = TextAlign.End,
                    fontFamily = FontFamily(Font(R.font.exo2))

                )
            }
        }
    }
}
