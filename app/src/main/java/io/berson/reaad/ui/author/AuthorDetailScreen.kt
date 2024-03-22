package io.berson.reaad.ui.author

import androidx.compose.runtime.Composable
import io.berson.reaad.ui.components.GradientSurface
import io.berson.reaad.ui.viewmodel.AuthorViewModel
import androidx.compose.material3.Text


@Composable
fun AuthorDetailScreen(vm: AuthorViewModel, authorId: String) {
    
    val authorUiState = vm.authorUiState
    
    vm.getAuthorById(authorId)
    
    GradientSurface {
        
        Text(text = "${authorUiState.selectedAuthor?.firstname}")
        
    }
}
