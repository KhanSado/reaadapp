package io.berson.reaad.ui.publishingCo

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
import io.berson.reaad.ui.viewmodel.PublishingCoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNewPublishingCoScreen(
    vm: PublishingCoViewModel,
    onNavToMainAuthorsPage: () -> Unit,
    navController:NavController
) {

    val publisingCoUiState = vm.publisingCoUiState

    val isErrorRegister = publisingCoUiState.registerError != null

    GradientSurface {
        TopAppBar(
            navController = navController,
            resName = "nova editora",
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 150.dp, start = 24.dp, end = 24.dp)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {

            if (isErrorRegister) {
                Text(
                    text = publisingCoUiState.registerError ?: "unknown error",
                    color = Color.Red,
                )
            }

            Text(
                text = "wow, que legal, vamos adicionar uma nova editora para nossos livros",
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Justify,
                fontSize = 30.sp,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.barlowcondensedlight))

            )

            Spacer(modifier = Modifier.height(50.dp))

            TextField(
                value = publisingCoUiState.name,
                onValueChange = { vm.onNameChange(it) },
                label = {
                    Text(text = "Nome da editora")
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.twotone_business_24),
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
                    .height(60.dp),
                colors = TextFieldDefaults.textFieldColors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.Green
                ),
                isError = isErrorRegister
            )

//            Spacer(modifier = Modifier.height(30.dp))
//
//
//            TextField(
//                value = publisingCoUiState.logo,
//                onValueChange = { vm.onLogoChange(it) },
//                label = {
//                    Text(text = "logo")
//                },
//                leadingIcon = {
//                    Icon(
//                        painter = painterResource(id = R.drawable.baseline_person_24),
//                        contentDescription = null
//                    )
//                },
//                keyboardOptions = KeyboardOptions(
//                    imeAction = ImeAction.Done
//                ),
//                singleLine = true,
//                textStyle = TextStyle(
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 18.sp
//                ),
//                shape = RoundedCornerShape(50.dp),
//                modifier = Modifier
//                    .width(300.dp)
//                    .height(60.dp),
//                colors = TextFieldDefaults.textFieldColors(
//                    unfocusedIndicatorColor = Color.Transparent,
//                    focusedIndicatorColor = Color.Transparent,
//                    cursorColor = Color.Green
//                )
//            )
            Spacer(modifier = Modifier.height(50.dp))

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50.dp))
                    .background(Color(0xB9FFFFFF))
            ) {
                Button(
                    onClick = { vm.addPublishingCo() },
                    colors = ButtonDefaults.buttonColors(
                        Color.Transparent
                    ),
                    modifier = Modifier.width(300.dp)
                ) {
                    Text(
                        text = "nova editora",
                        color = PrimaryColor,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily(Font(R.font.exo2))
                    )
                }
            }

            if (publisingCoUiState.isLoading) {
                CircularProgressIndicator()
            }

            LaunchedEffect(key1 = publisingCoUiState.isSuccessCreate){
                if (publisingCoUiState.isSuccessCreate){
                    onNavToMainAuthorsPage.invoke()
                    publisingCoUiState.isSuccessCreate = false
                    vm.resetState()
                }
            }
        }
    }
}
