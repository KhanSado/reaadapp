package io.berson.reaad.ui.book

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import io.berson.reaad.R
import io.berson.reaad.ui.components.GradientSurface
import io.berson.reaad.ui.components.TopAppBar
import io.berson.reaad.ui.navigation.DestinationScreen
import io.berson.reaad.ui.viewmodel.AuthorViewModel
import io.berson.reaad.ui.viewmodel.BookViewModel
import io.berson.reaad.ui.viewmodel.LiteraryGenreViewModel
import io.berson.reaad.ui.viewmodel.PublishingCoViewModel

@Composable
fun CreateNewBookStep2Screen(
    vm: BookViewModel,
    authorVm: AuthorViewModel,
    publishingCoVm: PublishingCoViewModel,
    literaryGenreVm: LiteraryGenreViewModel,
    onNavToMainAuthorsPage: () -> Unit,
    navController: NavController
) {

    val bookUiState = vm.bookUiState
    val authorUiState = authorVm.authorUiState
    val publishingCoUiState = publishingCoVm.publisingCoUiState
    val literaryGenreUiState = literaryGenreVm.literaryGenreUiState

    val isErrorRegister = bookUiState.registerError != null

    GradientSurface {
        TopAppBar(
            navController = navController,
            resName = stringResource(R.string.new_book_title),
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp, start = 24.dp, end = 24.dp)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {

            if (isErrorRegister) {
                Text(
                    text = bookUiState.registerError ?: stringResource(R.string.unknow_erro_label),
                    color = Color.Red,
                )
            }

            Text(
                text = stringResource(R.string.new_book_title_body),
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Justify,
                fontSize = 30.sp,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.barlowcondensedlight))
            )

            Spacer(modifier = Modifier.height(30.dp))

            AuthorExposedDropdownMenuBox(
                authorUiState.authorList,
                value = bookUiState.authorId,
                onValueChange = { vm.onAuthorIdChange(it) },
                isError = isErrorRegister
            )

            Spacer(modifier = Modifier.height(30.dp))

            PublishingCoExposedDropdownMenuBox(
                publishingCoUiState.publishingCoList,
                value = bookUiState.publishingCoId,
                onValueChange = { vm.onublishingCoIdChange(it) },
                isError = isErrorRegister
            )

            Spacer(modifier = Modifier.height(30.dp))

            LiteraryGenreExposedDropdownMenuBox(
                literaryGenreUiState.literaryGenreList,
                value = bookUiState.literaryGenreId,
                onValueChange = { vm.onLiteraryGenreIdChange(it) },
                isError = isErrorRegister
            )

            Spacer(modifier = Modifier.height(30.dp))

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50.dp))
                    .background(Color(0xFFFFFFFF))
            ) {
                Button(
                    onClick = {
                        navController.navigate(DestinationScreen.CreateNewBookStep3Screen.name)
                    },
                    colors = ButtonDefaults.buttonColors(
                        Color.Transparent
                    ),
                    modifier = Modifier.width(300.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.continue_button),
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily(Font(R.font.exo2))
                    )
                }

                if (bookUiState.isLoading) {
                    CircularProgressIndicator()
                }

                LaunchedEffect(key1 = bookUiState.isSuccessCreate) {
                    if (bookUiState.isSuccessCreate) {
                        onNavToMainAuthorsPage.invoke()
                        bookUiState.isSuccessCreate = false
                    }
                }
            }
        }
    }
}