package io.berson.reaad.ui.publishingCo

import androidx.compose.runtime.Composable
import io.berson.reaad.ui.components.GradientSurface
import androidx.compose.material3.Text
import io.berson.reaad.ui.viewmodel.PublishingCoViewModel


@Composable
fun PublishingCoDetailScreen(vm: PublishingCoViewModel, publishingCoId: String) {
    
    val publishingCoUiState = vm.publisingCoUiState
    
    vm.getPublishingCoById(publishingCoId)
    
    GradientSurface {
        
        Text(text = "${publishingCoUiState.selectedPublishingCo?.name}")
        
    }
}
