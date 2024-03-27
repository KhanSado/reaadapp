package io.berson.reaad.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.berson.reaad.ui.components.AppBottomBar
import io.berson.reaad.ui.components.GradientSurface
import io.berson.reaad.ui.components.HeaderSections
import io.berson.reaad.ui.navigation.DestinationScreen
import io.berson.reaad.ui.utils.mountAuthorList
import io.berson.reaad.ui.utils.mountBookList
import io.berson.reaad.ui.utils.mountLiteraryGenreList
import io.berson.reaad.ui.utils.mountPublishingCoList
import io.berson.reaad.ui.viewmodel.AuthorViewModel
import io.berson.reaad.ui.viewmodel.BookViewModel
import io.berson.reaad.ui.viewmodel.LiteraryGenreViewModel
import io.berson.reaad.ui.viewmodel.PublishingCoViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    publishingCoVm: PublishingCoViewModel,
    authorVm: AuthorViewModel,
    literaryGenreVm: LiteraryGenreViewModel,
    bookVm: BookViewModel,
    navController: NavController
) {
    val authorUiState = authorVm.authorUiState
    val publishingCoUiState = publishingCoVm.publisingCoUiState
    val literaryGenreUiState = literaryGenreVm.literaryGenreUiState
    val bookUiState = bookVm.bookUiState

    authorVm.getAuthorsList()
    publishingCoVm.getPublishingCoList()
    literaryGenreVm.getLiteraryGenreList()
    bookVm.getAllBooks()

    Scaffold(
        bottomBar = {
            AppBottomBar(navController = navController)
        },
    ) {

        GradientSurface {
            Column {
                HeaderSections(
                    viewMoreIsVisible = true,
                    title = "Autores"
                ) {
                    navController.navigate(DestinationScreen.MainAuthorsScreen.name)
                }

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(start = 23.dp, end = 23.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    items(
                        items = mountAuthorList(authorUiState.authorList).take(4),
                        itemContent = { item ->
                            ItemScreen(
                                item.firstname,
                                item.lastname
                            ) {
                                navController.navigate("${DestinationScreen.AuthorDetailScreen.name}/${item.documentId}")
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                        })
                }

                Divider(
                    modifier = Modifier
                        .padding(start = 24.dp, end = 24.dp, top = 10.dp)
                )
                Spacer(modifier = Modifier.height(30.dp))

                HeaderSections(
                    viewMoreIsVisible = true,
                    title = "Editoras"
                ) {
                    navController.navigate(DestinationScreen.MainPublishingCoScreen.name)
                }


                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(start = 23.dp, end = 23.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    items(
                        items = mountPublishingCoList(publishingCoUiState.publishingCoList).take(
                            4
                        ),
                        itemContent = { item ->
                            CardItem(
                                item.name,
                            ) {
                                navController.navigate("${DestinationScreen.PublishingCoDetailScreen.name}/${item.documentId}")
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                        })
                }

                Divider(
                    modifier = Modifier
                        .padding(start = 24.dp, end = 24.dp, top = 10.dp)
                )
                Spacer(modifier = Modifier.height(30.dp))

                HeaderSections(
                    viewMoreIsVisible = true,
                    title = "Gêneros Literários"
                ) {
                    navController.navigate(DestinationScreen.MainLiteraryGenreScreen.name)
                }


                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(start = 23.dp, end = 23.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    items(
                        items = mountLiteraryGenreList(literaryGenreUiState.literaryGenreList).take(
                            4
                        ), itemContent = { item ->
                            CardItem(
                                item.name,
                            ) {
                                navController.navigate("${DestinationScreen.LiteraryGenreDetailScreen.name}/${item.documentId}")
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                        })
                }

                Divider(
                    modifier = Modifier
                        .padding(start = 24.dp, end = 24.dp, top = 10.dp)
                )
                Spacer(modifier = Modifier.height(30.dp))

                HeaderSections(
                    viewMoreIsVisible = true,
                    title = "Livros"
                ) {
                    navController.navigate(DestinationScreen.MainBookScreen.name)
                }


                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(start = 23.dp, end = 23.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(
                        items = mountBookList(bookUiState.bookList).take(3),
                        itemContent = { item ->
                            CardItem(
                                item.title,
                            ) {
//                            navController.navigate("${DestinationScreen.LiteraryGenreDetailScreen.name}/${item.documentId}")
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                        })
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun ItemScreen(
    name: String = "Jhon",
    lastname: String = "Pipe",
    onItemClick: () -> Unit = {}
) {
    Surface(
        modifier = Modifier
            .background(Color.Transparent)
            .height(100.dp)
            .width(120.dp),
        onClick = onItemClick,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .padding(all = 16.dp),
        ) {
            Text(text = name)
            Text(text = lastname)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun CardItem(
    text1: String = "Jhon",
    text2: String = "Pipe",
    onItemClick: () -> Unit = {}
) {
    Surface(
        modifier = Modifier
            .background(Color.Transparent)
            .height(100.dp)
            .width(120.dp),
        onClick = onItemClick,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .padding(all = 16.dp)
        ) {
            Text(text = text1)
        }
    }
}