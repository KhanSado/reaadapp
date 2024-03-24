package io.berson.reaad.ui.author

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import io.berson.reaad.ui.components.GradientSurface
import io.berson.reaad.ui.viewmodel.AuthorViewModel
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.berson.reaad.ui.home.CardItem
import io.berson.reaad.ui.utils.mountBookList
import io.berson.reaad.ui.viewmodel.BookViewModel


@Composable
fun AuthorDetailScreen(vm: AuthorViewModel, bookVm: BookViewModel, authorId: String) {
    
    val authorUiState = vm.authorUiState
    val bookUiState = bookVm.bookUiState

    vm.getAuthorById(authorId)
    bookVm.getBooksListByAuthor(authorId)

    GradientSurface {
        
        Text(text = "${authorUiState.selectedAuthor?.firstname}")

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = 23.dp, end = 23.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(items = mountBookList(bookUiState.bookList), itemContent = { item ->
                CardItem(
                    item.title,
                ){
//                      navController.navigate("${DestinationScreen.LiteraryGenreDetailScreen.name}/${item.documentId}")
                }
                Spacer(modifier = Modifier.width(12.dp))
            })
        }
        
    }
}
