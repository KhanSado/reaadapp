package io.berson.reaad.ui.literaryGenre

import androidx.compose.runtime.Composable
import io.berson.reaad.ui.components.GradientSurface
import androidx.compose.material3.Text
import io.berson.reaad.ui.viewmodel.LiteraryGenreViewModel


@Composable
fun LiteraryGenreDetailScreen(vm: LiteraryGenreViewModel, literaryGenreId: String) {
    
    val literaryGenreUiState = vm.literaryGenreUiState
    
    vm.getLiteraryGenreById(literaryGenreId)
    
    GradientSurface {
        
        Text(text = "${literaryGenreUiState.selectedLiteraryGenre?.name}")
        
    }
}
