package io.berson.reaad.ui.profileUser

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import io.berson.reaad.ui.components.GradientSurface
import io.berson.reaad.ui.utils.mountAuthorList
import io.berson.reaad.ui.viewmodel.AuthorViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import io.berson.reaad.R
import io.berson.reaad.ui.components.AppBottomBar
import io.berson.reaad.ui.components.FloatingActionButton
import io.berson.reaad.ui.models.User
import io.berson.reaad.ui.navigation.DestinationScreen
import io.berson.reaad.ui.theme.PrimaryColor
import io.berson.reaad.ui.viewmodel.AuthViewModel
import io.berson.reaad.ui.viewmodel.BookViewModel
import io.berson.reaad.ui.viewmodel.LiteraryGenreViewModel
import io.berson.reaad.ui.viewmodel.PublishingCoViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileUserScreen(
    navController: NavController,
    vm: AuthViewModel,
    bookVm: BookViewModel,
    authorVm: AuthorViewModel,
    publishingCoVm: PublishingCoViewModel,
    literaryGenreVm: LiteraryGenreViewModel,
    onNavToHomePage: () -> Unit
) {

    val loginUiState = vm.loginUiState
    val bookUiState = bookVm.bookUiState
    val publishingCoUiState = publishingCoVm.publisingCoUiState
    val authorUiState = authorVm.authorUiState
    val literaryGenreUiState = literaryGenreVm.literaryGenreUiState

    vm.getUser()
    bookVm.getAllBooks()
    publishingCoVm.getPublishingCoList()
    authorVm.getAuthorsList()
    literaryGenreVm.getLiteraryGenreList()

    Scaffold(
        bottomBar = {
            AppBottomBar(navController = navController)
        }
    ) {
        GradientSurface {
            Box(modifier = Modifier.fillMaxSize()) {

                loginUiState.userLogged?.let { it1 ->
                    RoundedImageFromUrl(
                        user = it1,
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 60.dp)
                            .align(Alignment.TopCenter),
                        circleSize = 75
                    )
                }

                Text(
                    text = "${loginUiState.userLogged?.firstname} ${loginUiState.userLogged?.lastname}",
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 100.dp, bottom = 60.dp)
                        .align(Alignment.TopCenter),
                    fontFamily = FontFamily(Font(R.font.barlowcondensedsemibold)),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                DataUser(
                    booksQtd = bookUiState.bookList!!.size,
                    publisherCoQtd = publishingCoUiState.publishingCoList!!.size,
                    literaryGenreQtd = literaryGenreUiState.literaryGenreList!!.size,
                    authorsQtd = authorUiState.authorList!!.size,
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .align(Alignment.Center),
                )

                Text(
                    text = stringResource(R.string.exit_lavel),
                    modifier = Modifier
                        .clickable(
                            onClick = {
                                vm.logout()
                                onNavToHomePage()
                            }
                        )
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 60.dp)
                        .align(Alignment.BottomCenter),
                    fontFamily = FontFamily(Font(R.font.exo2)),
                    fontSize = 16.sp,
                    color = PrimaryColor,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun RoundedImageFromUrl(user: User, modifier: Modifier = Modifier, circleSize: Int) {
    Image(
        painter = if (user.avatarUrl == "") painterResource(id = R.drawable.logoall) else rememberAsyncImagePainter(
            model = user.avatarUrl
        ),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .size(circleSize.dp)
            .clip(CircleShape)
    )
}

@Composable
@Preview
fun DataUser(
    booksQtd: Int = 0,
    publisherCoQtd: Int = 0,
    literaryGenreQtd: Int = 0,
    authorsQtd: Int = 0,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .height(200.dp)
            .fillMaxWidth()
    ) {

        Box {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .align(Alignment.TopStart),
            ) {
                Text(
                    text = booksQtd.toString(),
                    fontFamily = FontFamily(Font(R.font.exo2)),
                    fontSize = 24.sp,
                    color = PrimaryColor,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Text(
                    text = "Livros",
                    fontFamily = FontFamily(Font(R.font.barlowcondensedsemibold)),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .align(Alignment.TopEnd),
            ) {
                Text(
                    text = publisherCoQtd.toString(),
                    fontFamily = FontFamily(Font(R.font.exo2)),
                    fontSize = 24.sp,
                    color = PrimaryColor,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Text(
                    text = "Editoras",
                    fontFamily = FontFamily(Font(R.font.barlowcondensedsemibold)),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .align(Alignment.BottomStart),
            ) {
                Text(
                    text = authorsQtd.toString(),
                    fontFamily = FontFamily(Font(R.font.exo2)),
                    fontSize = 24.sp,
                    color = PrimaryColor,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()

                )
                Text(
                    text = "Autores",
                    fontFamily = FontFamily(Font(R.font.barlowcondensedsemibold)),
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .align(Alignment.BottomEnd),
            ) {
                Text(
                    text = literaryGenreQtd.toString(),
                    fontFamily = FontFamily(Font(R.font.exo2)),
                    fontSize = 24.sp,
                    color = PrimaryColor,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Text(
                    text = "Generos Literario",
                    fontFamily = FontFamily(Font(R.font.barlowcondensedsemibold)),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }

}