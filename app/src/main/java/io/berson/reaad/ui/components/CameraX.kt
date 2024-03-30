package io.berson.reaad.ui.components

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import coil.compose.rememberImagePainter
import com.google.firebase.storage.FirebaseStorage
import io.berson.reaad.ui.viewmodel.BookViewModel
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraPreviewScreen(
    viewModel: BookViewModel,
    isComplete: (Boolean) -> Unit
) {
    val lensFacing = CameraSelector.LENS_FACING_BACK
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    var imageUri: String? by remember { mutableStateOf(null) }

    val preview = Preview.Builder().build()
    val previewView = remember {
        PreviewView(context)
    }
    val cameraxSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()
    val imageCapture = remember {
        ImageCapture.Builder().build()
    }

    LaunchedEffect(lensFacing) {
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(lifecycleOwner, cameraxSelector, preview, imageCapture)
        preview.setSurfaceProvider(previewView.surfaceProvider)
    }

    Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxSize()) {
        if (imageUri == null) {
            AndroidView({ previewView }, modifier = Modifier.fillMaxSize())
        } else {
            Image(
                painter = rememberImagePainter(imageUri!!),
                contentDescription = "Capturar capa do livro",
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth(),
            )
        }
        if (imageUri == null) {
            Button(onClick = {
                captureImage(imageCapture = imageCapture, context = context, onImageCaptured = {imageUri = it}) {Uri ->
                    uploadImageToFirebase(Uri, isComplete) { downloadUrl ->
                        if (downloadUrl != null) {
                            isComplete(true)
                            viewModel.onCoverChange(downloadUrl)
                        } else {
                            Log.w("UPLOAD", "Failed to retrieve download URL.")
                        }
                    }
                }
            }
            ) {
                Text(text = "Nova Capa")
            }
        }
    }
}

private suspend fun Context.getCameraProvider(): ProcessCameraProvider =
    suspendCoroutine { continuation ->
        ProcessCameraProvider.getInstance(this).also { cameraProvider ->
            cameraProvider.addListener({
                continuation.resume(cameraProvider.get())
            }, ContextCompat.getMainExecutor(this))
        }
    }

private fun captureImage(
    imageCapture: ImageCapture,
    context: Context,
    onImageCaptured: (String?) -> Unit,
    onUploadToFirebase: (Uri?) -> Unit
) {
    val name = "CameraxImage${System.currentTimeMillis()}.jpeg"
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, name)
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
        }
    }
    val outputOptions = ImageCapture.OutputFileOptions
        .Builder(
            context.contentResolver,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )
        .build()

    imageCapture.takePicture(
        outputOptions,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                val newImageUri = outputFileResults.savedUri?.toString()
                val newImageUriToFirebase = outputFileResults.savedUri
                onImageCaptured(newImageUri)
                Log.d("CAMERA TAKE", "onImageSaved: Success")
                onUploadToFirebase(newImageUriToFirebase)
            }

            override fun onError(exception: ImageCaptureException) {
                onImageCaptured(null)
                Log.d("CAMERA TAKE", "onImageSaved: Failed $exception")
            }
        }
    )

}
fun uploadImageToFirebase(
    imageUri: Uri?,
    onUploadComplete: (Boolean) -> Unit,
    callback: (String?) -> Unit
) {
    if (imageUri == null) {
        Log.w("UPLOAD", "No image selected to upload.")
        return
    }

    val storageRef = FirebaseStorage.getInstance().reference
    val imageRef = storageRef.child("imagens/${System.currentTimeMillis()}.jpeg")

    val uploadTask = imageRef.putFile(imageUri)

    uploadTask.addOnProgressListener { snapshot ->
        val progress = (100.0 * snapshot.bytesTransferred) / snapshot.totalByteCount
        Log.d("UPLOAD", "Progresso: $progress%")
    }.addOnSuccessListener {
        Log.d("UPLOAD", "Imagem enviada com sucesso!")
//        imageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
//            callback(downloadUrl.toString())
//            isComplete(true)
//        }
        imageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
            // Upload bem-sucedido
            callback(downloadUrl.toString())
            onUploadComplete(true)
        }.addOnFailureListener { exception ->
            // Falha no upload
            callback(null)
            onUploadComplete(false)
        }
    }.addOnFailureListener { exception ->
        Log.e("UPLOAD", "Falha ao enviar imagem: ${exception.message}")
        callback(null)
    }
}