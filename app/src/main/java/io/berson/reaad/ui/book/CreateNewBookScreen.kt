package io.berson.reaad.ui.book

import android.widget.Toast
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import androidx.compose.ui.text.input.TextFieldValue
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
import io.berson.reaad.ui.publishingCo.PublishingCoLazyGridList
import io.berson.reaad.ui.theme.PrimaryColor
import io.berson.reaad.ui.viewmodel.AuthorViewModel
import io.berson.reaad.ui.viewmodel.BookViewModel
import io.berson.reaad.ui.viewmodel.LiteraryGenreViewModel
import io.berson.reaad.ui.viewmodel.PublishingCoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNewBookScreen(
    vm: BookViewModel,
    authorVm: AuthorViewModel,
    publishingCoVm: PublishingCoViewModel,
    literaryGenreVm: LiteraryGenreViewModel,
    onNavToMainAuthorsPage: () -> Unit,
    navController: NavController
) {

    val bookUiState = vm.bookUiState
    val authorUiState = authorVm.authorUiState
    val publishingCoUiState = publishingCoVm.publisingCoUiState
    val literaryGenreUiState = literaryGenreVm.literaryGenreUiState

    GradientSurface {
        TopAppBar(
            navController = navController,
            resName = "novo livro",
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

            Text(
                text = "agora sim, vamos cadastrar os livors na nossa biblioteca virtual",
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
                    Text(text = "titulo do livro")
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
                )
            )

            Spacer(modifier = Modifier.height(30.dp))


            TextField(
                value = bookUiState.subtitle,
                onValueChange = { vm.onSubtitleChange(it) },
                label = {
                    Text(text = "subtitulo do livro")
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_person_24),
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
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .width(300.dp)
                    .height(60.dp),
                colors = TextFieldDefaults.textFieldColors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.Green
                ),
            )

            Spacer(modifier = Modifier.height(30.dp))

            AuthorExposedDropdownMenuBox(
                authorUiState.authorList,
                value = bookUiState.authorId,
                onValueChange = { vm.onAuthorIdChange(it)}
            )

            Spacer(modifier = Modifier.height(30.dp))

            PublishingCoExposedDropdownMenuBox(
                publishingCoUiState.publishingCoList,
                value = bookUiState.publishingCoId,
                onValueChange = { vm.onublishingCoIdChange(it)}
            )

            Spacer(modifier = Modifier.height(30.dp))

            LiteraryGenreExposedDropdownMenuBox(
                literaryGenreUiState.literaryGenreList,
                value = bookUiState.literaryGenreId,
                onValueChange = { vm.onLiteraryGenreIdChange(it)}
            )

            Spacer(modifier = Modifier.height(30.dp))

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50.dp))
                    .background(Color(0xB9FFFFFF))
            ) {
                Button(
                    onClick = {
                        vm.addBook()
                        onNavToMainAuthorsPage.invoke() },
                    colors = ButtonDefaults.buttonColors(
                        Color.Transparent
                    ),
                    modifier = Modifier.width(300.dp)
                ) {
                    Text(
                        text = "novo livro",
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
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthorExposedDropdownMenuBox(
    authorsList: List<Author>?,
    value: String,
    onValueChange: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    var selectedAuthorDocumentId by remember { mutableStateOf("") }

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
                value = getValueAuthorToDisplay(authorsList, selectedAuthorDocumentId),
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
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
) {
    var expanded by remember { mutableStateOf(false) }

    var selectedAuthorDocumentId by remember { mutableStateOf("") }

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
                value = getValueLiteraryGenreToDisplay(literaryGenreList, selectedAuthorDocumentId),
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
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
) {
    var expanded by remember { mutableStateOf(false) }

    var selectedAuthorDocumentId by remember { mutableStateOf("") }

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
                value = getValuePublishingCoToDisplay(publishingCoList, selectedAuthorDocumentId),
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
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
private fun getValueLiteraryGenreToDisplay(literaryGenreList: List<LiteraryGenre>?, documentId: String): String {
    return literaryGenreList?.find { it.documentId == documentId }?.name ?: "Genero Literário"
}
private fun getValuePublishingCoToDisplay(publishingCoList: List<PublishingCo>?, documentId: String): String {
    return publishingCoList?.find { it.documentId == documentId }?.name ?: "Editora"
}