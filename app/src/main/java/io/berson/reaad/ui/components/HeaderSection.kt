package io.berson.reaad.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HeaderSections(viewMoreIsVisible: Boolean, title: String, onClick: () -> Unit = {}){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 12.dp)
    ) {
        Text(
            text = title,
            textAlign = TextAlign.Start,
            fontSize = 18.sp,
            color = Color.White
        )

        if (viewMoreIsVisible) {
            Text(
                text = "ver mais",
                textAlign = TextAlign.End,
                modifier = Modifier
                    .clickable { onClick() },
                color = Color.White
            )
        }
    }
    Spacer(modifier = Modifier.height(18.dp))
}