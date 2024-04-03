package io.berson.reaad.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.berson.reaad.ui.components.AppBottomBar
import io.berson.reaad.ui.components.BackButton
import io.berson.reaad.ui.components.GradientSurface
import io.berson.reaad.ui.components.HeaderSections
import io.berson.reaad.ui.navigation.DestinationScreen
import io.berson.reaad.ui.profileUser.RoundedImageFromUrl
import io.berson.reaad.ui.utils.mountAuthorList
import io.berson.reaad.ui.utils.mountBookList
import io.berson.reaad.ui.utils.mountLiteraryGenreList
import io.berson.reaad.ui.utils.mountPublishingCoList
import io.berson.reaad.ui.viewmodel.AuthViewModel
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
    authVm: AuthViewModel,
    navController: NavController
) {
    val authorUiState = authorVm.authorUiState
    val publishingCoUiState = publishingCoVm.publisingCoUiState
    val literaryGenreUiState = literaryGenreVm.literaryGenreUiState
    val bookUiState = bookVm.bookUiState
    val loginUiState = authVm.loginUiState

    authorVm.getAuthorsList()
    publishingCoVm.getPublishingCoList()
    literaryGenreVm.getLiteraryGenreList()
    bookVm.getAllBooks()
    authVm.getUser()


    Scaffold(
        bottomBar = {
            AppBottomBar(navController = navController)
        },
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Olá, ${loginUiState.userLogged?.firstname}",
                            modifier = Modifier
                                .padding(top = 10.dp)
                        )

                        loginUiState.userLogged?.let {
                            RoundedImageFromUrl(
                                user = it,
                                circleSize = 40,
                                modifier = Modifier
                                    .padding(top = 5.dp, end = 10.dp)
                            )
                        }
                    }

                },
                modifier = Modifier
                    .height(50.dp)
            )
        }
    ) {

        GradientSurface {
            Column(
                modifier = Modifier
                    .padding(top = 65.dp)
            ) {

                Divider(
                    modifier = Modifier
                        .padding(start = 24.dp, end = 24.dp, top = 10.dp)
                )

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