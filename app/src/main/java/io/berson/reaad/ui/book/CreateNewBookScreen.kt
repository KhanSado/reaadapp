package io.berson.reaad.ui.book

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import io.berson.reaad.ui.models.Author
import io.berson.reaad.ui.models.LiteraryGenre
import io.berson.reaad.ui.models.PublishingCo
import io.berson.reaad.ui.navigation.DestinationScreen
import io.berson.reaad.ui.theme.PrimaryColor
import io.berson.reaad.ui.viewmodel.BookViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNewBookScreen(
    vm: BookViewModel,
    onNavToMainAuthorsPage: () -> Unit,
    navController: NavController
) {

    val bookUiState = vm.bookUiState

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

            Spacer(modifier = Modifier.height(50.dp))

            TextField(
                value = bookUiState.title,
                onValueChange = { vm.onTitleChange(it) },
                label = {
                    Text(text = stringResource(R.string.book_title_label))
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.bookicon),
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
                    containerColor = Color.White
                ),
                isError = isErrorRegister
            )

            Spacer(modifier = Modifier.height(30.dp))

            TextField(
                value = bookUiState.subtitle,
                onValueChange = { vm.onSubtitleChange(it) },
                label = {
                    Text(text = stringResource(R.string.subtitle_field))
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.bookicon),
                        contentDescription = null
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
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
                    cursorColor = PrimaryColor,
                    containerColor = Color.White
                ),
            )

            Spacer(modifier = Modifier.height(30.dp))

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50.dp))
                    .background(Color(0xFFFFFFFF))
            ) {
                Button(
                    onClick = {
                        navController.navigate(DestinationScreen.CreateNewBookStep2Screen.name)
                    },
                    colors = ButtonDefaults.buttonColors(
                        Color.Transparent
                    ),
                    modifier = Modifier.width(300.dp),
                ) {
                    Text(
                        text = stringResource(id = R.string.continue_button),
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily(Font(R.font.exo2))
                    )
                }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthorExposedDropdownMenuBox(
    authorsList: List<Author>?,
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean
) {
    var expanded by remember { mutableStateOf(false) }

    var selectedAuthorDocumentId by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFFFFFFF))
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                value = getValueAuthorToDisplay(authorsList, selectedAuthorDocumentId),
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
                authorsList?.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = "${item.firstname} ${item.lastname}") },
                        onClick = {
                            onValueChange(item.documentId)
                            selectedAuthorDocumentId = item.documentId
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LiteraryGenreExposedDropdownMenuBox(
    literaryGenreList: List<LiteraryGenre>?,
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean
) {
    var expanded by remember { mutableStateOf(false) }

    var selectedAuthorDocumentId by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFFFFFFF))
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                value = getValueLiteraryGenreToDisplay(literaryGenreList, selectedAuthorDocumentId),
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
                literaryGenreList?.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item.name) },
                        onClick = {
                            onValueChange(item.documentId)
                            selectedAuthorDocumentId = item.documentId
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublishingCoExposedDropdownMenuBox(
    publishingCoList: List<PublishingCo>?,
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean
) {
    var expanded by remember { mutableStateOf(false) }

    var selectedAuthorDocumentId by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFFFFFFF))
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                value = getValuePublishingCoToDisplay(publishingCoList, selectedAuthorDocumentId),
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
                publishingCoList?.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item.name) },
                        onClick = {
                            onValueChange(item.documentId)
                            selectedAuthorDocumentId = item.documentId
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

private fun getValueAuthorToDisplay(authorsList: List<Author>?, documentId: String): String {
    return "${authorsList?.find { it.documentId == documentId }?.firstname ?: "Autor"} ${authorsList?.find { it.documentId == documentId }?.lastname ?: ""}"
}

private fun getValueLiteraryGenreToDisplay(
    literaryGenreList: List<LiteraryGenre>?,
    documentId: String
): String {
    return literaryGenreList?.find { it.documentId == documentId }?.name ?: "Genero Literário"
}

private fun getValuePublishingCoToDisplay(
    publishingCoList: List<PublishingCo>?,
    documentId: String
): String {
    return publishingCoList?.find { it.documentId == documentId }?.name ?: "Editora"
}
