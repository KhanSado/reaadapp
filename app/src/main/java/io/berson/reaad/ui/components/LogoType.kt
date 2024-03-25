package io.berson.reaad.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.berson.reaad.R

@Composable
fun LogoType(){
    Image(
        painter = painterResource(
            id = R.drawable.logotipo),
        contentDescription = "reaad logo",
        modifier = Modifier
            .height(90.dp)
            .width(90.dp)
    )
    Text(
        text = "Reaad",
        color = Color.White,
        fontWeight = FontWeight.Bold,
        fontSize = 40.sp,
        fontFamily = FontFamily(Font(R.font.confortaa))
    )
}