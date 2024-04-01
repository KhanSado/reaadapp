package io.berson.reaad.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser
import io.berson.reaad.ui.models.Quote
import io.berson.reaad.ui.repositories.QuoteRepository
import kotlinx.coroutines.launch

class QuoteViewModel(
    private val repository: QuoteRepository = QuoteRepository(),
) : ViewModel() {

    var quoteUiState by mutableStateOf(QuoteUiState())
        private set

    val hasUser: Boolean
        get() = repository.hasUser()

    val userId: String
        get() = repository.getUserId()

    private val user: FirebaseUser?
        get() = repository.user()


    fun onQuoteDescriptionChange(quoteDescription: String) {
        quoteUiState = quoteUiState.copy(quoteDescription = quoteDescription)
    }

    fun onBookIdChange(bookId: String) {
        quoteUiState = quoteUiState.copy(bookId = bookId)
    }

    private fun validateRegisterForm() =
        quoteUiState.quoteDescription.isNotBlank() &&
                quoteUiState.bookId.isNotBlank()


    fun addQuote() = viewModelScope.launch {
        try {
            if (!validateRegisterForm()) {
                throw IllegalArgumentException("preencha todos os campos obrigatórios")
            }
            quoteUiState = quoteUiState.copy(isLoading = true)

            quoteUiState = quoteUiState.copy(registerError = null)

            if (hasUser) {
                repository.addQuote(
                    userId = user!!.uid,
                    bookId = quoteUiState.bookId,
                    quoteDescription = quoteUiState.quoteDescription,
                    timestamp = Timestamp.now()
                ) {
                    quoteUiState = quoteUiState.copy(quoteAddedStatus = it)
                    quoteUiState = quoteUiState.copy(isLoading = false)
                    quoteUiState = quoteUiState.copy(isSuccessCreate = true)
                }
            }
        } catch (e: Exception) {
            quoteUiState = quoteUiState.copy(registerError = "não foi possivel registrar sua citação")
            e.printStackTrace()
        } finally {
            quoteUiState = quoteUiState.copy(isLoading = false)
        }
    }


    private fun setEditFields(quote: Quote) {
        quoteUiState = quoteUiState.copy(
            quoteDescription = quote.quoteDescription,
            bookId = quote.bookId
        )
    }

    fun getAllQuotes() {
        repository.getAllQuotesToUser(
            userId,
            onError = {}
        ) {
            quoteUiState = quoteUiState.copy(quoteList = it)
        }
    }

    fun getQuoteListByBook(bookId: String) {
        repository.getQuotesByBooksListToUser(
            bookId = bookId,
            userId,
            onError = {}
        ) {
            quoteUiState = quoteUiState.copy(quoteList = it)
        }
    }

    fun getQuoteById(quoteId: String) {
        repository.getQuotes(
            quoteId = quoteId,
            onError = {},
        ) {
            quoteUiState = quoteUiState.copy(selectedQuote = it)
            quoteUiState.selectedQuote?.let { it1 -> setEditFields(it1) }
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
        quoteUiState = quoteUiState.copy(
            quoteAddedStatus = false,
            updateQuoteStatus = false,
        )
    }

    fun resetState() {
        quoteUiState = QuoteUiState()
    }
}

data class QuoteUiState(
    val quoteDescription: String = "",
    val bookId: String = "",
    var quoteAddedStatus: Boolean = false,
    val updateQuoteStatus: Boolean = false,
    val selectedQuote: Quote? = null,
    val quoteList: List<Quote>? = null,
    val isLoading: Boolean = false,
    var isSuccessCreate: Boolean = false,

    val registerError: String? = null
)