package io.berson.reaad.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import io.berson.reaad.R
import io.berson.reaad.ui.components.GradientSurface
import io.berson.reaad.ui.components.LogoType
import io.berson.reaad.ui.navigation.DestinationScreen
import io.berson.reaad.ui.theme.PrimaryColor

@Composable
fun MainScreen(navController: NavController) {
    GradientSurface {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 100.dp)

        ) {
            LogoType()

            Spacer(modifier = Modifier.fillMaxHeight(.5f))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50.dp))
                    .background(Color.White)
            ) {
                Button(onClick = {
                    navController.navigate(DestinationScreen.SignupScreen.name)
                },
                    colors = ButtonDefaults.buttonColors(
                        Color.Transparent
                    ),
                    modifier = Modifier.width(300.dp)
                ) {
                    Text(
                        text = stringResource(R.string.new_user_button_label),
                        color = PrimaryColor,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily(Font(R.font.exo2))
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50.dp))
                    .background(Color.White)
            ) {
                Button(onClick = {
                    navController.navigate(DestinationScreen.LoginScreen.name)
                },
                    colors = ButtonDefaults.buttonColors(
                        Color.Transparent
                    ),
                    modifier = Modifier.width(300.dp)
                ) {
                    Text(
                        text = stringResource(R.string.make_login_button_label),
                        color = PrimaryColor,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily(Font(R.font.exo2))
                    )
                }
            }
        }
    }
}
