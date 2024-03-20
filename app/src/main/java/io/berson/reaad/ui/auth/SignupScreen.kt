package io.berson.reaad.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
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
import io.berson.reaad.ui.navigation.DestinationScreen
import io.berson.reaad.ui.theme.PrimaryColor
import io.berson.reaad.ui.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(
    vm: AuthViewModel,
    onNavToHomePage:() -> Unit,
    onNavToLoginPage:() -> Unit
) {
    val emty by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var cpasswordVisibility by remember { mutableStateOf(false) }
    var errorConfirmPassword by remember { mutableStateOf(false) }
    var plength by remember { mutableStateOf(false) }

    val loginUiState = vm.loginUiState
    val isErrorSignup = loginUiState.signUpError != null
    val context = LocalContext.current

    GradientSurface {

        Column(
            horizontalAlignment = CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 150.dp, start = 24.dp, end = 24.dp)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {

            if (isErrorSignup){
                Text(
                    text = loginUiState.signUpError ?: "unknown error",
                    color = Color.Red,
                )
            }

            Text(
                text = "para começar, precisamos saber algumas informações",
                textAlign = TextAlign.Justify,
                fontWeight = FontWeight.Thin,
                fontSize = 30.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(50.dp))

            TextField(
                value = loginUiState.userNameSignUp,
                onValueChange = {vm.onUserNameChangeSignup(it)},
                label = {
                    Text(text = "email")
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_person_24),
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    if (loginUiState.userNameSignUp.isNotEmpty()) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_close_24),
                            contentDescription = null,
                            Modifier.clickable { email = emty }
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
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .width(300.dp)
                    .height(60.dp),
                colors = TextFieldDefaults.textFieldColors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    containerColor = Color(0xB9FFFFFF),
                    cursorColor = Color.Red,
                ),
                isError = isErrorSignup
            )


            Spacer(modifier = Modifier.height(30.dp))

            if (plength) {
                Text(
                    text = "sua senha deve ter mais de 6 caracteres",
                    color = Color.Red,
                )
            }

            TextField(
                value = loginUiState.passwordSignUp,
                onValueChange = {
                    vm.onPasswordChangeSignup(it)
                    plength = it.length < 6
                },

                label = {
                    Text(text = "senha")
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_lock_person_24),
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    if (loginUiState.passwordSignUp.isNotEmpty()) {
                        val visibilityIcon = if (passwordVisibility) {
                            painterResource(id = R.drawable.baseline_visibility_24)
                        } else {
                            painterResource(id = R.drawable.baseline_disabled_visible_24)
                        }
                        Icon(
                            painter = visibilityIcon,
                            contentDescription = if (passwordVisibility) "esconder senha" else "mostrar senha",
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
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Password
                ),
                singleLine = true,
                textStyle = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                ),
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .width(300.dp)
                    .height(60.dp),
                colors = TextFieldDefaults.textFieldColors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    containerColor = Color(0xB9FFFFFF),
                    cursorColor = Color.Red
                ),
                isError = isErrorSignup
            )
            Spacer(modifier = Modifier.height(30.dp))
            if (errorConfirmPassword) {
                Text(
                    text = "senhas não correspondem",
                    color = Color.Red,
                )
            }

            TextField(
                value = loginUiState.confirmPasswordSignUp,
                onValueChange = { vm.onConfirmPasswordChange(it) },
                label = {
                    Text(text = "confirmação de senha")
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_lock_person_24),
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    if (loginUiState.confirmPasswordSignUp.isNotEmpty()) {
                        val visibilityIcon = if (cpasswordVisibility) {
                            painterResource(id = R.drawable.baseline_visibility_24)
                        } else {
                            painterResource(id = R.drawable.baseline_disabled_visible_24)
                        }
                        Icon(
                            painter = visibilityIcon,
                            contentDescription = if (passwordVisibility) "esconder senha" else "mostrar senha",
                            Modifier.clickable {
                                cpasswordVisibility = !cpasswordVisibility
                            }
                        )
                    }
                },
                visualTransformation = if (cpasswordVisibility) {
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
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .width(300.dp)
                    .height(60.dp),
                colors = TextFieldDefaults.textFieldColors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    containerColor = Color(0xB9FFFFFF),
                    cursorColor = Color.Red,
                ),
                isError = isErrorSignup
            )


            Spacer(modifier = Modifier.height(50.dp))



            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50.dp))
                    .background(Color(0xB9FFFFFF))
            ) {
                Button(
                    onClick = { vm.createUser(context) },
                    colors = ButtonDefaults.buttonColors(
                        Color.Transparent
                    ),
                    modifier = Modifier.width(300.dp)
                ) {
                    Text(
                        text = "vamos lá",
                        color = PrimaryColor,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Thin
                    )
                }
            }

            if (loginUiState.isLoading){
                CircularProgressIndicator()
            }

            LaunchedEffect(key1 = vm.hasUser){
                if (vm.hasUser){
                    onNavToHomePage.invoke()
                }
            }
        }
    }
}


