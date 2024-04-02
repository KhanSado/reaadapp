package io.berson.reaad.ui.quote

import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.platform.LocalContext
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
import io.berson.reaad.ui.components.CameraPreviewScreen
import io.berson.reaad.ui.components.GradientSurface
import io.berson.reaad.ui.components.TopAppBar
import io.berson.reaad.ui.models.Author
import io.berson.reaad.ui.models.Book
import io.berson.reaad.ui.models.LiteraryGenre
import io.berson.reaad.ui.models.PublishingCo
import io.berson.reaad.ui.theme.PrimaryColor
import io.berson.reaad.ui.viewmodel.AuthorViewModel
import io.berson.reaad.ui.viewmodel.BookViewModel
import io.berson.reaad.ui.viewmodel.LiteraryGenreViewModel
import io.berson.reaad.ui.viewmodel.PublishingCoViewModel
import io.berson.reaad.ui.viewmodel.QuoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNewQuoteScreen(
    vm: QuoteViewModel,
    bookVm: BookViewModel,
    onNavToMainAuthorsPage: () -> Unit,
    navController: NavController,
) {
    val quoteUiState = vm.quoteUiState
    val bookUiState = bookVm.bookUiState

    val isErrorRegister = bookUiState.registerError != null

    var isCompleteTakePhoto by remember { mutableStateOf(false) }

    GradientSurface {
        TopAppBar(
            navController = navController,
            resName = "nova citação",
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
                    text = bookUiState.registerError ?: "unknown error",
                    color = Color.Red,
                )
            }

            Text(
                text = "para relembra, salve suas citações aqui",
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Justify,
                fontSize = 30.sp,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.barlowcondensedlight))
            )

            Spacer(modifier = Modifier.height(50.dp))

            TextField(
                value = quoteUiState.quoteDescription,
                onValueChange = { vm.onQuoteDescriptionChange(it) },
                label = {
                    Text(text = "citação")
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_person_24),
                        contentDescription = null
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                singleLine = false,
                textStyle = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                ),
                shape = RoundedCornerShape(50.dp),
                maxLines = 4,
                modifier = Modifier
                    .width(300.dp)
                    .height(130.dp),
                colors = TextFieldDefaults.textFieldColors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.Green
                ),
                isError = isErrorRegister
            )

            Spacer(modifier = Modifier.height(30.dp))


            BookExposedDropdownMenuBox(
                bookUiState.bookList,
                value = quoteUiState.bookId,
                onValueChange = { vm.onBookIdChange(it) },
                isError = isErrorRegister
            )

            Spacer(modifier = Modifier.height(30.dp))

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50.dp))
                    .background(Color(0xB9FFFFFF))
            ) {
                Button(
                    onClick = {
                        vm.addQuote()
                    },
                    colors = ButtonDefaults.buttonColors(
                        Color.Transparent
                    ),
                    modifier = Modifier.width(300.dp),
                ) {
                        Text(
                            text = "nova citação",
                            color = PrimaryColor,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Normal,
                            fontFamily = FontFamily(Font(R.font.exo2))
                        )
                }
            }

            if (bookUiState.isLoading) {
                CircularProgressIndicator()
            }

            LaunchedEffect(key1 = bookUiState.isSuccessCreate){
                if (bookUiState.isSuccessCreate){
                    onNavToMainAuthorsPage.invoke()
                    bookUiState.isSuccessCreate = false
                    vm.resetState()
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookExposedDropdownMenuBox(
    bookList: List<Book>?,
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean
) {
    var expanded by remember { mutableStateOf(false) }

    var selectedBookDocumentId by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(50.dp))
            .background(Color(0xB9FFFFFF))
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                value = getValueBookToDisplay(bookList, selectedBookDocumentId),
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor(),
                isError = isError
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                bookList?.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item.title) },
                        onClick = {
                            onValueChange(item.documentId)
                            selectedBookDocumentId = item.documentId
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

private fun getValueBookToDisplay(
    bookList: List<Book>?,
    documentId: String
): String {
    return bookList?.find { it.documentId == documentId }?.title ?: "Livro"
}
