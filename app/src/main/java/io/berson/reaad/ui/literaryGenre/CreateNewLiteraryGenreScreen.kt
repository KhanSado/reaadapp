package io.berson.reaad.ui.literaryGenre

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import io.berson.reaad.R
import io.berson.reaad.ui.components.GradientSurface
import io.berson.reaad.ui.components.TopAppBar
import io.berson.reaad.ui.theme.PrimaryColor
import io.berson.reaad.ui.viewmodel.LiteraryGenreViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNewLiteraryGenreScreen(
    vm: LiteraryGenreViewModel,
    onNavToMainAuthorsPage: () -> Unit,
    navController: NavController
) {

    val literaryGenreUiState = vm.literaryGenreUiState

    val isErrorRegister = literaryGenreUiState.registerError != null

    GradientSurface {
        TopAppBar(
            navController = navController,
            resName = stringResource(R.string.literary_genre_title),
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp, start = 24.dp, end = 24.dp)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {

            if (isErrorRegister) {
                Text(
                    text = literaryGenreUiState.registerError ?: stringResource(R.string.unknow_erro_label),
                    color = Color.Red,
                )
            }

            Text(
                text = stringResource(R.string.create_genre_title_body),
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Justify,
                fontSize = 30.sp,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.barlowcondensedlight))
            )

            Spacer(modifier = Modifier.height(50.dp))

            TextField(
                value = literaryGenreUiState.name,
                onValueChange = { vm.onNameChange(it) },
                label = {
                    Text(text = stringResource(R.string.genre_label))
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.literarygenreicon),
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
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .width(300.dp)
                    .height(60.dp),
                colors = TextFieldDefaults.textFieldColors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    cursorColor = PrimaryColor,
                    containerColor = Color.White,
                    textColor = PrimaryColor
                ),
                isError = isErrorRegister
            )

            Spacer(modifier = Modifier.height(30.dp))


            TextField(
                value = literaryGenreUiState.description,
                onValueChange = { vm.onDescriptionChange(it) },
                label = {
                    Text(text = stringResource(R.string.genreLabel))
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.descriptionicon),
                        contentDescription = null
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                singleLine = false,
                textStyle = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .width(300.dp)
                    .height(200.dp),
                colors = TextFieldDefaults.textFieldColors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    cursorColor = PrimaryColor,
                    containerColor = Color.White,
                    textColor = PrimaryColor
                ),
                isError = isErrorRegister
            )
            Spacer(modifier = Modifier.height(50.dp))

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50.dp))
                    .background(Color.Transparent)
            ) {
                val buttonIsEnable = literaryGenreUiState.name.isNotEmpty() && literaryGenreUiState.description.isNotEmpty()
                Button(
                    onClick = {
                        vm.addLiteraryGenre() },
                    colors = ButtonDefaults.buttonColors(
                        disabledContentColor = Color.LightGray,
                        disabledContainerColor = Color.LightGray,
                        contentColor = Color.White,
                        containerColor = Color.White
                    ),
                    modifier = Modifier.width(300.dp),
                    enabled = buttonIsEnable
                ) {
                    Text(
                        text = stringResource(R.string.new_genre_button),
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily(Font(R.font.exo2))
                    )
                }
            }

            if (literaryGenreUiState.isLoading) {
                CircularProgressIndicator()
            }

            LaunchedEffect(key1 = literaryGenreUiState.isSuccessCreate){
                if (literaryGenreUiState.isSuccessCreate){
                    onNavToMainAuthorsPage.invoke()
                    literaryGenreUiState.isSuccessCreate = false
                    vm.resetState()
                }
            }
        }
    }
}
