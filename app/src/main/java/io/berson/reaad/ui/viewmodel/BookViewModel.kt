package io.berson.reaad.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser
import io.berson.reaad.ui.models.Book
import io.berson.reaad.ui.repositories.BookRepository

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

    fun onAuthorIdChange(authorid: String) {
        bookUiState = bookUiState.copy(authorId = authorid)
    }

    fun addBook(){
        bookUiState = bookUiState.copy(isLoading = true)
        if (hasUser){
            repository.addBook(
                userId = user!!.uid,
                authorId = bookUiState.authorId,
                title = bookUiState.title,
                subtitle = bookUiState.subtitle,
                timestamp = Timestamp.now()
            ){
                bookUiState = bookUiState.copy(bookAddedStatus = it)
                bookUiState = bookUiState.copy(isLoading = false)
                bookUiState.copy(isSuccessCreate = true)
            }
        }
    }

//    private fun setEditFields(author: Author){
//        bookUiState = bookUiState.copy(
//            firstName = author.firstname,
//            lastName = author.lastname,
//        )
//    }

    fun getBooksList(authorid: String? = null){
        repository.getBookbyAuthorListToUser(
            authorId = authorid,
            userId,
            onError = {}
        ){
            bookUiState = bookUiState.copy(bookList = it)
        }
    }

//    fun getAuthorById(authorId:String){
//        repository.getAuthor(
//            authorId = authorId,
//            onError = {},
//        ){
//            bookUiState = bookUiState.copy(selectedAuthor = it)
//            bookUiState.selectedAuthor?.let { it1 -> setEditFields(it1) }
//        }
//    }

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

    fun resetAuthorAddedStatus(){
        bookUiState = bookUiState.copy(
            bookAddedStatus = false,
            updateBookStatus = false,
        )
    }

    fun resetState(){
        bookUiState = BookUiState()
    }
}

data class BookUiState(
    val title: String = "",
    val subtitle: String = "",
    val authorId: String = "",
    val bookAddedStatus: Boolean = false,
    val updateBookStatus: Boolean = false,
    val selectedBook: Book? = null,
    val bookList: List<Book>? = null,
    val isLoading: Boolean = false,
    val isSuccessCreate: Boolean = false,
    )