package io.berson.reaad.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import io.berson.reaad.R
import io.berson.reaad.ui.components.GradientSurface
import io.berson.reaad.ui.components.TopAppBar
import io.berson.reaad.ui.navigation.DestinationScreen
import io.berson.reaad.ui.theme.PrimaryColor
import io.berson.reaad.ui.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(
    vm: AuthViewModel,
    onNavToHomePage: () -> Unit,
    navController: NavController
) {
    val empty by remember { mutableStateOf("") }
//    var email by remember { mutableStateOf("") }
    var firstname by remember { mutableStateOf("") }
    var lastname by remember { mutableStateOf("") }
//    var passwordVisibility by remember { mutableStateOf(false) }
//    var confirmPasswordVisibility by remember { mutableStateOf(false) }
//    var errorConfirmPassword by remember { mutableStateOf(false) }
//    var passwordLength by remember { mutableStateOf(false) }

    val loginUiState = vm.loginUiState
    val isErrorSignup = loginUiState.signUpError != null

    GradientSurface {
        TopAppBar(
            navController = navController
        )

        Column(
            horizontalAlignment = CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp, start = 24.dp, end = 24.dp, bottom = 20.dp)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {

            if (isErrorSignup) {
                Text(
                    text = loginUiState.signUpError ?: stringResource(R.string.unknow_erro_label),
                    color = Color.Red
                )
            }

            Text(
                text = stringResource(R.string.signup_title_screen_one),
                textAlign = TextAlign.Justify,
                fontWeight = FontWeight.Normal,
                fontSize = 30.sp,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.barlowcondensedlight))
            )

            Spacer(modifier = Modifier.height(30.dp))

            TextField(
                value = loginUiState.firstname,
                onValueChange = { vm.onFirstNameChangeSignup(it) },
                label = {
                    Text(text = stringResource(R.string.name_label))
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.profileicon),
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    if (loginUiState.firstname.isNotEmpty()) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_close_24),
                            contentDescription = null,
                            Modifier.clickable { firstname = empty }
                        )
                    }
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
                    containerColor = Color.White,
                    cursorColor = PrimaryColor
                ),
                isError = isErrorSignup
            )

            Spacer(modifier = Modifier.height(30.dp))

            TextField(
                value = loginUiState.lastname,
                onValueChange = { vm.onLastNameChangeSignup(it) },
                label = {
                    Text(text = stringResource(R.string.lastname_label))
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.profileicon),
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    if (loginUiState.lastname.isNotEmpty()) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_close_24),
                            contentDescription = null,
                            Modifier.clickable { lastname = empty }
                        )
                    }
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
                    containerColor = Color.White,
                    cursorColor = PrimaryColor
                ),
                isError = isErrorSignup
            )

            Spacer(modifier = Modifier.height(30.dp))

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50.dp))
                    .background(Color(0xFFFFFFFF))
            ) {
                Button(
                    onClick = { navController.navigate(DestinationScreen.SignupStep2Screen.name) },
                    colors = ButtonDefaults.buttonColors(
                        Color.Transparent
                    ),
                    modifier = Modifier.width(300.dp)
                ) {
                    Text(
                        text = stringResource(R.string.continue_button),
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily(Font(R.font.exo2))
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            if (loginUiState.isLoading) {
                CircularProgressIndicator()
            }

            Spacer(modifier = Modifier.height(30.dp))

            LaunchedEffect(key1 = vm.hasUser) {
                if (vm.hasUser) {
                    onNavToHomePage.invoke()
                }
            }
        }
    }
}
