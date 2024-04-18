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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import io.berson.reaad.R
import io.berson.reaad.ui.components.GradientSurface
import io.berson.reaad.ui.components.LogoType
import io.berson.reaad.ui.navigation.DestinationScreen
import io.berson.reaad.ui.theme.PrimaryColor
import io.berson.reaad.ui.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    vm: AuthViewModel,
    navController: NavController,
    onNavToHomePage:() -> Unit,
) {

    val empty by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }

    val loginUiState = vm.loginUiState
    val isError = loginUiState.loginError != null

    GradientSurface {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 150.dp, start = 24.dp, end = 24.dp)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {
            if (isError){
                Text(
                    text = loginUiState?.loginError ?: stringResource(R.string.unknow_erro_label),
                    color = Color.Red,
                )
            }

            LogoType()

            Text(
                text = stringResource(R.string.login_title_screen),
                fontWeight = FontWeight.Thin,
                textAlign = TextAlign.Justify,
                fontSize = 30.sp,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.barlowcondensedlight))
            )

            Spacer(modifier = Modifier.height(50.dp))

            TextField(
                value = loginUiState.email,
                onValueChange = { vm.onEmailChange(it) },
                label = {
                    Text(text = stringResource(R.string.email_label))
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.profileicon),
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    if (loginUiState.email.isNotEmpty()) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_close_24),
                                contentDescription = null,
                                Modifier.clickable { loginUiState.email = empty }
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
                isError = isError
            )
            Spacer(modifier = Modifier.height(30.dp))
            TextField(
                value = loginUiState.password,
                onValueChange = { vm.onPasswordNameChange(it) },
                label = {
                    Text(text = stringResource(R.string.password_label))
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.passwordicon),
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    if (loginUiState.password.isNotEmpty()) {
                        val visibilityIcon = if (passwordVisibility) {
                            painterResource(id = R.drawable.showpass)
                        } else {
                            painterResource(id = R.drawable.hideenpass)
                        }
                        Icon(
                            painter = visibilityIcon,
                            contentDescription = if (passwordVisibility) stringResource(R.string.hiden_password) else stringResource(
                                R.string.show_password
                            ),
                            Modifier.clickable {
                                passwordVisibility = !passwordVisibility
                            }
                        )
                    }
                },
                visualTransformation = if (passwordVisibility) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password
                ),
                singleLine = true,
                textStyle = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
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
                isError = isError
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(R.string.recovery_password_label),
                modifier = Modifier
                    .clickable( onClick = {
                        navController.navigate(DestinationScreen.PassRecoveryScreen.name)
                    },
                ),
                fontFamily = FontFamily(Font(R.font.barlowcondensedlight)),
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(50.dp))

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50.dp))
                    .background(Color(0xFFFFFFFF))
            ) {
                Button(
                    onClick = { vm.loginUser() },
                    colors = ButtonDefaults.buttonColors(
                        Color.Transparent
                    ),
                    modifier = Modifier.width(300.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.enter_button),
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily(Font(R.font.exo2))
                    )
                }
            }

            if (loginUiState.isLoading){
                CircularProgressIndicator()
            }

            LaunchedEffect(key1 = vm.hasUser){
                if (vm.hasUser){
                    onNavToHomePage.invoke()
                    vm.resetState()
                }
            }

        }
    }
}
