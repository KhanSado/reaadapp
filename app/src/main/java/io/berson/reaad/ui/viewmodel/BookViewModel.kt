package io.berson.reaad.ui.viewmodel

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Tasks.await
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.FirebaseStorage
import io.berson.reaad.ui.models.Book
import io.berson.reaad.ui.repositories.BookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.UUID

class BookViewModel(
    private val repository: BookRepository = BookRepository(),
) : ViewModel() {

    var bookUiState by mutableStateOf(BookUiState())
        private set

    val hasUser: Boolean
        get() = repository.hasUser()

    val userId: String
        get() = repository.getUserId()

    private val user: FirebaseUser?
        get() = repository.user()


    fun onTitleChange(title: String) {
        bookUiState = bookUiState.copy(title = title)
    }

    fun onSubtitleChange(subtitle: String) {
        bookUiState = bookUiState.copy(subtitle = subtitle)
    }

    fun onCoverChange(cover: String) {
        bookUiState = bookUiState.copy(cover = cover)
    }

    fun onAuthorIdChange(authorid: String) {
        bookUiState = bookUiState.copy(authorId = authorid)
    }

    fun onLiteraryGenreIdChange(literaryGenreId: String) {
        bookUiState = bookUiState.copy(literaryGenreId = literaryGenreId)
    }

    fun onublishingCoIdChange(publishingCoId: String) {
        bookUiState = bookUiState.copy(publishingCoId = publishingCoId)
    }

    private fun validateRegisterForm() =
        bookUiState.title.isNotBlank() &&
                bookUiState.publishingCoId.isNotBlank() &&
                bookUiState.literaryGenreId.isNotBlank() &&
                bookUiState.authorId.isNotBlank()


    fun addBook() = viewModelScope.launch {
        try {
                if (!validateRegisterForm()) {
                    throw IllegalArgumentException("preencha todos os campos obrigatórios")
                }
                bookUiState = bookUiState.copy(isLoading = true)

                bookUiState = bookUiState.copy(registerError = null)

            if (hasUser) {
                repository.addBook(
                    userId = user!!.uid,
                    authorId = bookUiState.authorId,
                    literaryGenreId = bookUiState.literaryGenreId,
                    publishingCoId = bookUiState.publishingCoId,
                    title = bookUiState.title,
                    subtitle = bookUiState.subtitle,
                    cover = bookUiState.cover,
                    timestamp = Timestamp.now()
                ) {
                    bookUiState = bookUiState.copy(bookAddedStatus = it)
                    bookUiState = bookUiState.copy(isLoading = false)
                    bookUiState = bookUiState.copy(isSuccessCreate = true)
                }
            }
        } catch (e: Exception) {
            bookUiState = bookUiState.copy(registerError = "não foi possivel registrar seu livro")
            e.printStackTrace()
        } finally {
            bookUiState = bookUiState.copy(isLoading = false)
        }
    }


    private fun setEditFields(book: Book){
        bookUiState = bookUiState.copy(
            title = book.title,
            subtitle = book.subtitle,
            cover =  book.cover,
            authorId = book.authorId,
            literaryGenreId = book.literaryGenreId,
            publishingCoId = book.publishingCoId
        )
    }

fun getAllBooks() {
    repository.getAllBookToUser(
        userId,
        onError = {}
    ) {
        bookUiState = bookUiState.copy(bookList = it)
    }
}

fun getBooksListByAuthor(authorId: String) {
    repository.getBookByAuthorListToUser(
        authorId = authorId,
        userId,
        onError = {}
    ) {
        bookUiState = bookUiState.copy(bookList = it)
    }
}

fun getBooksListByLiteraryGenre(literaryGenreId: String) {
    repository.getBookByLiteraryGenreListToUser(
        literaryGenreId = literaryGenreId,
        userId,
        onError = {}
    ) {
        bookUiState = bookUiState.copy(bookList = it)
    }
}

fun getBooksListByPublishingCo(publishingCoId: String) {
    repository.getBookByPublishingCoListToUser(
        publishingCoId = publishingCoId,
        userId,
        onError = {}
    ) {
        bookUiState = bookUiState.copy(bookList = it)
    }
}

    fun getBookById(bookId:String){
        repository.getBook(
            bookId = bookId,
            onError = {},
        ){
            bookUiState = bookUiState.copy(selectedBook = it)
            bookUiState.selectedBook?.let { it1 -> setEditFields(it1) }
        }
    }

//    fun updateAuthors(
//        authorId: String
//    ){
//        repository.updateAuthor(
//            bookUiState.firstName,
//            bookUiState.lastName,
//            authorId,
//            Timestamp.now()
//        ){
//            bookUiState = bookUiState.copy(updateAuthorStatus = it)
//        }
//    }

fun resetAuthorAddedStatus() {
    bookUiState = bookUiState.copy(
        bookAddedStatus = false,
        updateBookStatus = false,
    )
}

fun resetState() {
    bookUiState = BookUiState()
}
}

data class BookUiState(
    val title: String = "",
    val subtitle: String = "",
    val cover: String = "",
    val authorId: String = "",
    val literaryGenreId: String = "",
    val publishingCoId: String = "",
    var bookAddedStatus: Boolean = false,
    val updateBookStatus: Boolean = false,
    val selectedBook: Book? = null,
    val bookList: List<Book>? = null,
    val isLoading: Boolean = false,
    var isSuccessCreate: Boolean = false,

    val registerError: String? = null
)