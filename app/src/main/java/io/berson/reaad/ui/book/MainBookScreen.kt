package io.berson.reaad.ui.book

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import io.berson.reaad.ui.components.GradientSurface
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import io.berson.reaad.R
import io.berson.reaad.ui.components.AppBottomBar
import io.berson.reaad.ui.components.FloatingActionButton
import io.berson.reaad.ui.components.TopAppBar
import io.berson.reaad.ui.models.Book
import io.berson.reaad.ui.navigation.DestinationScreen
import io.berson.reaad.ui.viewmodel.BookViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainBookScreen(navController: NavController, vm: BookViewModel) {

    val bookUiState = vm.bookUiState

    Scaffold(
        bottomBar = {
            AppBottomBar(navController = navController)
        },
        floatingActionButton = {
            FloatingActionButton(
                navController = navController,
                destination = DestinationScreen.CreateNewBookScreen.name
            )
        }
    ) {
        GradientSurface {
            TopAppBar(
                navController = navController,
                resName = "Meus Livros",
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 50.dp, start = 24.dp, end = 24.dp, bottom = 20.dp)
            ) {

                TextField(
                    value = bookUiState.searchText,
                    onValueChange = { vm.onSearchTextChanged(it) },
                    label = {
                        Text(text = "buscar livro")
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_book_24),
                            contentDescription = null
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true,
                    textStyle = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                    ),
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .width(300.dp)
                        .height(80.dp)
                        .padding(top = 18.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        cursorColor = Color.Green
                    ),
                )



                if (bookUiState.searchedBookList != null) {
                    BooksLazyGridList(books = bookUiState.searchedBookList!!, navController = navController)
                } else {
                    if (bookUiState.bookList != null) {
                        BooksLazyGridList(books = bookUiState.bookList!!, navController = navController)
                    } else {
                        Text("Carregando livros...")
                    }
                }
            }
        }
    }
}

@Composable
fun BooksLazyGridList(
    books: List<Book>,
    navController: NavController
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxHeight(),
        contentPadding = PaddingValues(
            top = 60.dp,
            start = 24.dp,
            end = 24.dp,
            bottom = 24.dp
        )
    ) {
        items(items = books) { book ->
            Surface(
                modifier = Modifier
                    .background(Color.Transparent)
                    .height(200.dp)
                    .width(120.dp)
                    .padding(start = 8.dp, end = 8.dp, bottom = 8.dp, top = 8.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(start = 8.dp, end = 8.dp, bottom = 8.dp, top = 8.dp)
                        .clickable(onClick = {
                            navController.navigate("${DestinationScreen.BookDetailScreen.name}/${book.documentId}")
                        }),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Image(
                        painter = rememberImagePainter(book.cover),
                        contentDescription = "",
                        modifier = Modifier
                            .height(100.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = book.title
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
            }
        }
    }
}