package io.berson.reaad.ui.quote

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import io.berson.reaad.ui.components.GradientSurface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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

    val context = LocalContext.current

    GradientSurface {

        TopAppBar(
            navController = navController,
            resName = ""
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 76.dp
                )
        )  {
            quoteUiState.selectedQuote?.let { bookVm.getBookById(it.bookId) }
            var selectedBook = bookUiState.selectedBook

            Text(
                text = "${quoteUiState.selectedQuote?.quoteDescription}",
                fontSize = 24.sp,
                textAlign = TextAlign.Justify,
                fontFamily = FontFamily(Font(R.font.confortaa)),
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 24.dp, end = 24.dp, bottom = 24.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))

            if (selectedBook != null) {
                Text(
                    text = selectedBook.title,
                    fontSize = 16.sp,
                    textAlign = TextAlign.End,
                    fontFamily = FontFamily(Font(R.font.exo2)),
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(24.dp)
                )

                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "${quoteUiState.quoteDescription}\n${selectedBook.title}")
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)

                Button(
                    modifier = Modifier
                        .align(Alignment.BottomCenter),
                    onClick = {
                    context.startActivity(shareIntent)
                }
                ){
                    Text("Compartilhar Citação")
                }
            }
        }
    }
}
