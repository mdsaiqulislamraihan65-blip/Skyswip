package com.example.ui.screens

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.FallbackStrategy
import androidx.camera.video.MediaStoreOutputOptions
import androidx.camera.video.Quality
import androidx.camera.video.QualitySelector
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
import androidx.camera.video.VideoRecordEvent
import androidx.camera.view.PreviewView
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.GridCells
import coil.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

// Liquid Glass 2026 Colors
val DeepSky = Color(0xFF0B1026)
val ElectricAzure = Color(0xFF3B82F6)
val SkyCyan = Color(0xFF06B6D4)

@Composable
fun VideoCreatorFlow(onClose: () -> Unit) {
    var currentScreen by remember { mutableStateOf(VideoScreenState.CAMERA) }
    var mediaUri by remember { mutableStateOf<Uri?>(null) }
    var isVideoMedia by remember { mutableStateOf(true) }

    Box(modifier = Modifier.fillMaxSize().background(DeepSky)) {
        // Render screens based on state
        AnimatedContent(
            targetState = currentScreen,
            transitionSpec = {
                fadeIn(animationSpec = tween(300)) togetherWith fadeOut(animationSpec = tween(300))
            }, label = "ScreenTransition"
        ) { state ->
            when (state) {
                VideoScreenState.CREATE_HOME -> CreateHomeSheet(
                    onRecord = { currentScreen = VideoScreenState.CAMERA },
                    onClose = onClose
                )
                VideoScreenState.CAMERA -> CameraScreenUI(
                    onClose = { currentScreen = VideoScreenState.CREATE_HOME },
                    onFinishRecording = { uri, isVid -> 
                        mediaUri = uri
                        isVideoMedia = isVid
                        currentScreen = VideoScreenState.EDITOR 
                    },
                    onOpenMusic = { currentScreen = VideoScreenState.MUSIC_LIBRARY }
                )
                VideoScreenState.MUSIC_LIBRARY -> MusicLibraryOverlay(
                    onClose = { currentScreen = VideoScreenState.CAMERA }
                )
                VideoScreenState.EDITOR -> VideoEditorScreen(
                    mediaUri = mediaUri,
                    isVideo = isVideoMedia,
                    onBack = { currentScreen = VideoScreenState.CAMERA },
                    onNext = { currentScreen = VideoScreenState.FINAL_PREVIEW }
                )
                VideoScreenState.FINAL_PREVIEW -> FinalPreviewUploadScreen(
                    onBack = { currentScreen = VideoScreenState.EDITOR },
                    onPost = { currentScreen = VideoScreenState.UPLOAD_PROGRESS }
                )
                VideoScreenState.UPLOAD_PROGRESS -> UploadProgressScreen(
                    onComplete = onClose
                )
            }
        }
    }
}

enum class VideoScreenState {
    CREATE_HOME, CAMERA, MUSIC_LIBRARY, EDITOR, FINAL_PREVIEW, UPLOAD_PROGRESS
}

@Composable
fun GlassmorphicContainer(
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(16.dp),
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .clip(shape)
            .background(Color.White.copy(alpha = 0.05f))
            .border(1.dp, Color.White.copy(alpha = 0.1f), shape)
            .blur(24.dp)
    )
    Box(modifier = modifier.clip(shape).padding(1.dp)) {
        content()
    }
}

@Composable
fun CreateHomeSheet(onRecord: () -> Unit, onClose: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        // Gradient dark overlay
        Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.6f)).clickable { onClose() })

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                .background(DeepSky.copy(alpha = 0.8f))
                .padding(24.dp)
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.size(40.dp, 4.dp).clip(CircleShape).background(ElectricAzure.copy(alpha = 0.5f)))
            Spacer(modifier = Modifier.height(24.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Create", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                IconButton(onClick = onClose) {
                    Icon(Icons.Default.Close, "Close", tint = Color.White)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Options
            CreateOptionCard(icon = Icons.Default.Videocam, title = "Record Video", onClick = onRecord, iconGlow = ElectricAzure)
            Spacer(modifier = Modifier.height(12.dp))
            CreateOptionCard(icon = Icons.Default.PhotoLibrary, title = "Upload from Gallery", onClick = {}, iconGlow = SkyCyan)
            Spacer(modifier = Modifier.height(12.dp))
            CreateOptionCard(
                icon = Icons.Default.Sensors, 
                title = "Go Live", 
                onClick = {}, 
                iconGlow = Color.Red,
                borderBrush = Brush.linearGradient(listOf(ElectricAzure, SkyCyan))
            )
        }
    }
}

@Composable
fun CreateOptionCard(icon: ImageVector, title: String, onClick: () -> Unit, iconGlow: Color, borderBrush: Brush? = null) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White.copy(alpha = 0.05f))
            .then(if (borderBrush != null) Modifier.border(1.dp, borderBrush, RoundedCornerShape(20.dp)) else Modifier.border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(20.dp)))
            .clickable(onClick = onClick)
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = iconGlow, modifier = Modifier.size(36.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Text(title, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreenUI(onClose: () -> Unit, onFinishRecording: (Uri?, Boolean) -> Unit, onOpenMusic: () -> Unit) {
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    val audioPermissionState = rememberPermissionState(Manifest.permission.RECORD_AUDIO)

    LaunchedEffect(Unit) {
        if (!cameraPermissionState.status.isGranted) {
            cameraPermissionState.launchPermissionRequest()
        }
        if (!audioPermissionState.status.isGranted) {
            audioPermissionState.launchPermissionRequest()
        }
    }

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var videoCapture by remember { mutableStateOf<VideoCapture<Recorder>?>(null) }
    var imageCapture by remember { mutableStateOf<ImageCapture?>(null) }
    var activeRecording by remember { mutableStateOf<Recording?>(null) }
    var isRecording by remember { mutableStateOf(false) }
    var isVideoMode by remember { mutableStateOf(true) }
    var lensFacing by remember { mutableIntStateOf(CameraSelector.LENS_FACING_BACK) }
    
    // New states for recording duration and post-recording
    var targetDuration by remember { mutableIntStateOf(15) } // limit in seconds
    var currentDuration by remember { mutableIntStateOf(0) }
    var hasRecordedContent by remember { mutableStateOf(false) }
    var showDiscardConfirm by remember { mutableStateOf(false) }
    var recordedUri by remember { mutableStateOf<Uri?>(null) }

    LaunchedEffect(isRecording) {
        if (isRecording) {
            while (currentDuration < targetDuration) {
                delay(1000)
                currentDuration++
            }
            if (currentDuration >= targetDuration && isRecording) {
                activeRecording?.stop()
                activeRecording = null
            }
        }
    }

    fun capturePhoto() {
        val ic = imageCapture ?: return
        val photoFile = java.io.File(context.cacheDir, "Skyswip_photo_${System.currentTimeMillis()}.jpg")
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        ic.takePicture(
            outputOptions, ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    Toast.makeText(context, "Photo ready", Toast.LENGTH_SHORT).show()
                    recordedUri = Uri.fromFile(photoFile)
                    hasRecordedContent = true
                }
                override fun onError(exception: ImageCaptureException) {
                    Toast.makeText(context, "Photo failed: ${exception.message}", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    fun startRecording() {
        val vc = videoCapture ?: return
        if (!audioPermissionState.status.isGranted) return

        currentDuration = 0
        val videoFile = java.io.File(context.cacheDir, "Skyswip_video_${System.currentTimeMillis()}.mp4")
        val outputOptions = androidx.camera.video.FileOutputOptions.Builder(videoFile).build()

        activeRecording = vc.output
            .prepareRecording(context, outputOptions)
            .withAudioEnabled()
            .start(ContextCompat.getMainExecutor(context)) { recordEvent ->
                when (recordEvent) {
                    is VideoRecordEvent.Start -> isRecording = true
                    is VideoRecordEvent.Finalize -> {
                        isRecording = false
                        if (!recordEvent.hasError()) {
                            Toast.makeText(context, "Video ready", Toast.LENGTH_SHORT).show()
                            recordedUri = Uri.fromFile(videoFile)
                            hasRecordedContent = true
                        } else {
                            activeRecording?.close()
                            activeRecording = null
                            Toast.makeText(context, "Video error", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
    }

    fun stopRecording() {
        activeRecording?.stop()
        activeRecording = null
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        
        if (cameraPermissionState.status.isGranted) {
            AndroidView(
                factory = { ctx ->
                    PreviewView(ctx).apply {
                        scaleType = PreviewView.ScaleType.FILL_CENTER
                    }
                },
                update = { previewView ->
                    val currentLensFacing = lensFacing
                    val currentIsVideoMode = isVideoMode
                    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
                    cameraProviderFuture.addListener({
                        val cameraProvider = cameraProviderFuture.get()
                        val preview = Preview.Builder().build().also {
                            it.setSurfaceProvider(previewView.surfaceProvider)
                        }

                        val cameraSelector = CameraSelector.Builder().requireLensFacing(currentLensFacing).build()
                        try {
                            cameraProvider.unbindAll()
                            if (currentIsVideoMode) {
                                val recorder = Recorder.Builder()
                                    .setQualitySelector(QualitySelector.from(Quality.HIGHEST, FallbackStrategy.higherQualityOrLowerThan(Quality.SD)))
                                    .build()
                                val vc = VideoCapture.withOutput(recorder)
                                videoCapture = vc
                                cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview, vc)
                            } else {
                                val ic = ImageCapture.Builder().build()
                                imageCapture = ic
                                cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview, ic)
                            }
                        } catch(exc: Exception) {
                            Toast.makeText(context, "Camera bind failed", Toast.LENGTH_SHORT).show()
                        }
                    }, ContextCompat.getMainExecutor(context))
                },
                modifier = Modifier.fillMaxSize()
            )
        } else {
            // Fallback dark UI if permission denied
            Box(Modifier.fillMaxSize().background(Color.DarkGray))
        }

        // Top controls
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onClose) { Icon(Icons.Default.Close, "Close", tint = Color.White) }
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.3f))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text(
                        "Record", 
                        color = if (isVideoMode) ElectricAzure else Color.White.copy(alpha = 0.6f), 
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { isVideoMode = true }
                    )
                    Text(
                        "Photo", 
                        color = if (!isVideoMode) ElectricAzure else Color.White.copy(alpha = 0.6f),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { isVideoMode = false }
                    )
                }
            }
            IconButton(onClick = {
                lensFacing = if (lensFacing == CameraSelector.LENS_FACING_BACK) {
                    CameraSelector.LENS_FACING_FRONT
                } else {
                    CameraSelector.LENS_FACING_BACK
                }
            }) { Icon(Icons.Default.FlipCameraAndroid, "Flip", tint = Color.White) }
        }

        // Right Rail
        if (!hasRecordedContent && !isRecording) {
            Column(
                modifier = Modifier.align(Alignment.CenterEnd).padding(end = 16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CameraToolButton(Icons.Default.Speed, "Speed")
                CameraToolButton(Icons.Default.Face, "Beauty")
                CameraToolButton(Icons.Default.Timer, "Timer")
                CameraToolButton(Icons.Default.FlashOn, "Flash")
                CameraToolButton(Icons.Default.AutoAwesome, "Filters")
            }
        }

        // Bottom Bar
        Column(
            modifier = Modifier.align(Alignment.BottomCenter).navigationBarsPadding().padding(bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (hasRecordedContent) {
                // Post-recording Confirm/Discard buttons
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 48.dp).padding(bottom = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.size(64.dp).clip(CircleShape).background(Color.White.copy(alpha = 0.2f)).clickable { showDiscardConfirm = true },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Close, "Discard", tint = Color.White, modifier = Modifier.size(32.dp))
                    }
                    Box(
                        modifier = Modifier.size(64.dp).clip(CircleShape).background(ElectricAzure).clickable { onFinishRecording(recordedUri, isVideoMode) },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Check, "Confirm", tint = Color.White, modifier = Modifier.size(32.dp))
                    }
                }
            } else {
                // Duration Selector
                if (!isRecording && isVideoMode) {
                    Row(
                        modifier = Modifier.padding(bottom = 24.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        listOf(15, 30, 60).forEach { limit ->
                            Text(
                                text = "${limit}s",
                                color = if (targetDuration == limit) Color.White else Color.White.copy(alpha = 0.5f),
                                fontWeight = if (targetDuration == limit) FontWeight.Bold else FontWeight.Normal,
                                modifier = Modifier.clickable { targetDuration = limit }.padding(8.dp)
                            )
                        }
                    }
                } else if (!isRecording && !isVideoMode) {
                    Spacer(modifier = Modifier.height(48.dp))
                } else if (isRecording) {
                    // Segments bar
                    Row(modifier = Modifier.padding(bottom = 24.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Box(modifier = Modifier.size(40.dp, 6.dp).clip(CircleShape).background(Color.White))
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Effects
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(modifier = Modifier.size(48.dp).clip(RoundedCornerShape(12.dp)).background(Color.White.copy(alpha = 0.1f)), contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.AutoAwesomeMosaic, null, tint = Color.White)
                        }
                        Text("Effects", color = Color.White, fontSize = 12.sp, modifier = Modifier.padding(top = 8.dp))
                    }

                    // Record / Photo Button
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .border(4.dp, if (isRecording) Color.Red else ElectricAzure, CircleShape)
                            .clickable {
                                if (!isVideoMode) {
                                    capturePhoto()
                                } else {
                                    if (isRecording) stopRecording() else startRecording()
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        val innerSize by animateDpAsState(if (isRecording) 32.dp else 64.dp, label = "record_size")
                        val innerShape = if (isRecording) RoundedCornerShape(8.dp) else CircleShape
                        Box(modifier = Modifier.size(innerSize).clip(innerShape).background(if (isRecording) Color.Red else Color.White))
                    }

                    // Upload
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable { onFinishRecording(null, true) }
                    ) {
                        Box(modifier = Modifier.size(48.dp).clip(RoundedCornerShape(12.dp)).background(Color.White.copy(alpha = 0.1f)), contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.UploadFile, null, tint = Color.White)
                        }
                        Text("Upload", color = Color.White, fontSize = 12.sp, modifier = Modifier.padding(top = 8.dp))
                    }
                }
            }
        }
        
        // Music Picker or Timer (Top Center under controls)
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 100.dp)
        ) {
            if (isRecording) {
                Text(
                    text = "00:${currentDuration.toString().padStart(2, '0')}",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.Black.copy(alpha = 0.5f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            } else if (!hasRecordedContent) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clip(CircleShape).background(Color.Black.copy(alpha = 0.4f)).clickable { onOpenMusic() }.padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Icon(Icons.Default.MusicNote, null, tint = Color.White, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Add Sound", color = Color.White, fontSize = 14.sp)
                }
            }
        }

        if (showDiscardConfirm) {
            IOSAlertDialog(
                title = "Discard video?",
                message = "If you go back, your video will be deleted. Are you sure?",
                confirmText = "Discard",
                cancelText = "Cancel",
                onConfirm = {
                    hasRecordedContent = false
                    showDiscardConfirm = false
                    recordedUri = null
                },
                onCancel = { showDiscardConfirm = false }
            )
        }
    }
}

@Composable
fun MusicLibraryOverlay(onClose: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.5f)).clickable { onClose() })
        
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f)
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                .background(DeepSky.copy(alpha = 0.9f))
                .blur(24.dp)
                .padding(top = 24.dp)
        ) {
            Box(modifier = Modifier.align(Alignment.CenterHorizontally).size(40.dp, 4.dp).clip(CircleShape).background(Color.White.copy(alpha=0.3f)))
            Spacer(modifier = Modifier.height(16.dp))
            
            Text("Music Library", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 24.dp))
            Spacer(modifier = Modifier.height(16.dp))
            
            // Mock Search
            Box(modifier = Modifier.padding(horizontal = 24.dp).fillMaxWidth().height(48.dp).clip(RoundedCornerShape(12.dp)).background(Color.White.copy(0.1f)), contentAlignment=Alignment.CenterStart) {
                Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Icon(Icons.Default.Search, null, tint = Color.Gray)
                    Spacer(Modifier.width(8.dp))
                    Text("Search songs, artists, sounds", color = Color.Gray)
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            
            Row(modifier = Modifier.padding(horizontal = 24.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Text("Trending", color = ElectricAzure, fontWeight = FontWeight.Bold)
                Text("Favorites", color = Color.Gray)
                Text("Saved", color = Color.Gray)
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(10) { index ->
                    Row(
                        modifier = Modifier.fillMaxWidth().clickable{}.padding(horizontal = 24.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.size(56.dp).clip(RoundedCornerShape(8.dp)).background(Color.DarkGray))
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Trending Audio ${index+1}", color = Color.White, fontWeight = FontWeight.SemiBold)
                            Text("Artist Name", color = Color.Gray, fontSize = 12.sp)
                        }
                        IconButton(onClick = {}) { Icon(Icons.Default.PlayArrow, null, tint = SkyCyan) }
                    }
                }
            }
        }
    }
}

fun saveMediaToGallery(context: Context, uri: Uri, isVideo: Boolean) {
    val resolver = context.contentResolver
    val name = java.text.SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS", java.util.Locale.US).format(System.currentTimeMillis())

    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, name)
        put(MediaStore.MediaColumns.MIME_TYPE, if (isVideo) "video/mp4" else "image/jpeg")
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
            put(MediaStore.MediaColumns.RELATIVE_PATH, if (isVideo) "Movies/Skyswip" else "Pictures/Skyswip")
        }
    }

    val collection = if (isVideo) MediaStore.Video.Media.EXTERNAL_CONTENT_URI else MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    val destUri = resolver.insert(collection, contentValues)

    if (destUri != null) {
        try {
            resolver.openInputStream(uri)?.use { input ->
                resolver.openOutputStream(destUri)?.use { output ->
                    input.copyTo(output)
                }
            }
            Toast.makeText(context, "Saved to device successfully", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Failed to save: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    } else {
        Toast.makeText(context, "Could not create file", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun VideoEditorScreen(mediaUri: Uri?, isVideo: Boolean, onBack: () -> Unit, onNext: () -> Unit) {
    var showExitConfirm by remember { mutableStateOf(false) }
    val context = LocalContext.current

    var activeFilter by remember { mutableStateOf<androidx.compose.ui.graphics.Color?>(null) }
    val appliedTexts = remember { androidx.compose.runtime.mutableStateListOf<String>() }
    val appliedStickers = remember { androidx.compose.runtime.mutableStateListOf<String>() }
    var showTextEditor by remember { mutableStateOf(false) }
    var showStickerGallery by remember { mutableStateOf(false) }
    var showFilters by remember { mutableStateOf(false) }

    if (showExitConfirm) {
        IOSAlertDialog(
            title = "Discard edit?",
            message = "If you go back, your edits will be lost. Are you sure?",
            confirmText = "Discard",
            cancelText = "Cancel",
            onConfirm = { showExitConfirm = false; onBack() },
            onCancel = { showExitConfirm = false }
        )
    }

    BackHandler {
        showExitConfirm = true
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        // Video/Image Preview
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            if (mediaUri != null) {
                if (isVideo) {
                    AndroidView(
                        factory = { ctx ->
                            VideoView(ctx).apply {
                                setVideoURI(mediaUri)
                                start()
                                setOnCompletionListener { start() } // Loop playing
                            }
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    AsyncImage(
                        model = mediaUri,
                        contentDescription = "Captured Photo",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = androidx.compose.ui.layout.ContentScale.Fit
                    )
                }
            } else {
                Icon(Icons.Default.PlayCircleOutline, null, tint = Color.White.copy(alpha=0.5f), modifier = Modifier.size(64.dp))
            }

            // Apply Filter
            if (activeFilter != null) {
                Box(modifier = Modifier.fillMaxSize().background(activeFilter!!.copy(alpha = 0.3f)))
            }

            // Render Overlays
            Column(modifier = Modifier.align(Alignment.Center).fillMaxSize()) {
                appliedStickers.forEach { sticker ->
                    Text(sticker, fontSize = 64.sp, modifier = Modifier.padding(16.dp))
                }
                appliedTexts.forEach { text ->
                    Text(text, color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(16.dp))
                }
            }
        }

        // Top Bar
        Row(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .background(androidx.compose.ui.graphics.Brush.verticalGradient(listOf(Color.Black.copy(alpha=0.8f), Color.Transparent)))
                .statusBarsPadding()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { showExitConfirm = true }) { Icon(Icons.Default.ArrowBack, null, tint = Color.White) }
            Text("Edit", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TextButton(onClick = {
                    if (mediaUri != null) {
                        saveMediaToGallery(context, mediaUri, isVideo)
                    } else {
                        Toast.makeText(context, "No media to save", Toast.LENGTH_SHORT).show()
                    }
                }) { Text("Save", color = Color.White, fontWeight = FontWeight.Bold) }
                TextButton(onClick = onNext) { Text("Next", color = ElectricAzure, fontWeight = FontWeight.Bold) }
            }
        }
        
        // Timeline & Controls
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(androidx.compose.ui.graphics.Brush.verticalGradient(listOf(Color.Transparent, Color.Black.copy(alpha=0.8f), Color.Black)))
                .padding(bottom = 32.dp, top = 32.dp)
        ) {
            // Timeline track mock
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.DarkGray.copy(alpha = 0.5f))
            ) {
                Row(modifier = Modifier.fillMaxSize()) {
                    repeat(8) {
                        Box(modifier = Modifier.weight(1f).fillMaxHeight().border(0.5.dp, Color.Black.copy(alpha=0.3f))) {
                             Box(modifier = Modifier.fillMaxSize().background(Color.Gray.copy(alpha=0.3f)))
                        }
                    }
                }
                Box(modifier = Modifier.fillMaxHeight().width(2.dp).background(Color.White).align(Alignment.Center)) {
                    Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color.White).align(Alignment.TopCenter))
                    Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color.White).align(Alignment.BottomCenter))
                }
            }
            
            // Toolbelt
            LazyRow(modifier = Modifier.fillMaxWidth().padding(top = 24.dp), horizontalArrangement = Arrangement.spacedBy(24.dp), contentPadding = PaddingValues(horizontal=24.dp)) {
                item { EditorTool(Icons.Default.ContentCut, "Trim") { Toast.makeText(context, "Trim selected - Drag timeline edges below", Toast.LENGTH_SHORT).show() } }
                item { EditorTool(Icons.Default.TextFields, "Text") { showTextEditor = true } }
                item { EditorTool(Icons.Default.EmojiEmotions, "Sticker") { showStickerGallery = true } }
                item { EditorTool(Icons.Default.AutoAwesome, "Effects") { showFilters = true } }
                item { EditorTool(Icons.Default.MusicNote, "Audio") { Toast.makeText(context, "Audio settings opened", Toast.LENGTH_SHORT).show() } }
                item { EditorTool(Icons.Default.Speed, "Speed") { Toast.makeText(context, "Playback speed changed", Toast.LENGTH_SHORT).show() } }
            }
        }

        if (showTextEditor) {
            TextEditorOverlay(
                onDone = { text ->
                    if (text.isNotBlank()) appliedTexts.add(text)
                    showTextEditor = false
                },
                onCancel = { showTextEditor = false }
            )
        }

        if (showStickerGallery) {
            StickerGalleryOverlay(
                onSelectSticker = { sticker ->
                    appliedStickers.add(sticker)
                    showStickerGallery = false
                },
                onClose = { showStickerGallery = false }
            )
        }

        if (showFilters) {
            FilterEffectsOverlay(
                onSelectFilter = { color -> activeFilter = color },
                onClose = { showFilters = false }
            )
        }
    }
}

@Composable
fun EditorTool(icon: ImageVector, label: String, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable(onClick = onClick)) {
        Box(modifier = Modifier.size(48.dp).clip(CircleShape).background(Color.White.copy(0.1f)), contentAlignment=Alignment.Center) {
            Icon(icon, null, tint = Color.White)
        }
        Text(label, color = Color.White, fontSize = 12.sp, modifier = Modifier.padding(top=8.dp))
    }
}

@Composable
fun TextEditorOverlay(onDone: (String) -> Unit, onCancel: () -> Unit) {
    var text by remember { mutableStateOf("") }
    Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(0.7f)), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)) {
            androidx.compose.material3.TextField(
                value = text,
                onValueChange = { text = it },
                textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold, textAlign = androidx.compose.ui.text.style.TextAlign.Center),
                colors = androidx.compose.material3.TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = ElectricAzure
                ),
                placeholder = { Text("Add Text...", color = Color.White.copy(alpha = 0.5f), fontSize = 28.sp, fontWeight = FontWeight.Bold, textAlign = androidx.compose.ui.text.style.TextAlign.Center, modifier = Modifier.fillMaxWidth()) },
                modifier = Modifier.fillMaxWidth()
            )
        }
        
        // Controls at bottom
        Column(modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth().padding(16.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                TextButton(onClick = onCancel, modifier = Modifier.weight(1f)) {
                    Text("Cancel", color = Color.White)
                }
                Button(onClick = { onDone(text) }, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = ElectricAzure)) {
                    Text("Done", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun StickerGalleryOverlay(onSelectSticker: (String) -> Unit, onClose: () -> Unit) {
    val stickers = listOf("😂", "😍", "🔥", "💯", "✨", "❤️", "👍", "🙏", "😎", "🎉", "👑", "🍕", "🐶", "🚀", "💀")
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Column(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(topStart=24.dp, topEnd=24.dp)).background(DeepSky).padding(16.dp)) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text("Stickers", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                IconButton(onClick = onClose) { Icon(Icons.Default.Close, null, tint=Color.White) }
            }
            androidx.compose.foundation.lazy.grid.LazyVerticalGrid(
                columns = androidx.compose.foundation.lazy.grid.GridCells.Fixed(5),
                modifier = Modifier.fillMaxWidth().height(300.dp).padding(top=16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(stickers) { sticker ->
                    Box(modifier = Modifier.size(56.dp).clip(CircleShape).background(Color.White.copy(alpha=0.1f)).clickable { onSelectSticker(sticker) }, contentAlignment = Alignment.Center) {
                        Text(sticker, fontSize = 24.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun FilterEffectsOverlay(onSelectFilter: (Color?) -> Unit, onClose: () -> Unit) {
    val filters = listOf(
        "None" to null,
        "Warm" to Color(0xFFFF9800),
        "Cool" to Color(0xFF2196F3),
        "Vintage" to Color(0xFF795548),
        "B&W" to Color.Gray,
        "Cyberpunk" to Color(0xFFE91E63)
    )
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Column(modifier = Modifier.fillMaxWidth().height(250.dp).clip(RoundedCornerShape(topStart=24.dp, topEnd=24.dp)).background(DeepSky).padding(16.dp)) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text("Filters", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                IconButton(onClick = onClose) { Icon(Icons.Default.Close, null, tint=Color.White) }
            }
            LazyRow(modifier = Modifier.padding(top=16.dp), horizontalArrangement=Arrangement.spacedBy(16.dp)) {
                items(filters) { (name, color) -> 
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable { onSelectFilter(color) }) {
                        Box(modifier=Modifier.size(80.dp, 100.dp).clip(RoundedCornerShape(8.dp)).background(color ?: Color.DarkGray))
                        Text(name, color = Color.White, fontSize = 12.sp, modifier = Modifier.padding(top = 8.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun FinalPreviewUploadScreen(onBack: () -> Unit, onPost: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().background(DeepSky)) {
        Row(modifier = Modifier.statusBarsPadding().fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null, tint=Color.White) }
            Button(onClick = onPost, colors = ButtonDefaults.buttonColors(containerColor = ElectricAzure)) {
                Text("Post", color = Color.White)
            }
        }
        
        Row(modifier = Modifier.padding(16.dp)) {
            Box(modifier = Modifier.size(100.dp, 160.dp).clip(RoundedCornerShape(8.dp)).background(Color.Black))
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text("Describe your video...", color = Color.Gray, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ChipMock("#liquidglass")
                    ChipMock("#skyswip")
                }
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        ListItemMock(Icons.Default.Lock, "Who can view", "Public")
        ListItemMock(Icons.Default.Comment, "Allow comments", "On")
        ListItemMock(Icons.Default.LocationOn, "Add location", "Select")
    }
}

@Composable
fun ChipMock(text: String) {
    Box(modifier = Modifier.clip(RoundedCornerShape(16.dp)).background(Color.White.copy(0.1f)).padding(horizontal=12.dp, vertical=6.dp)) {
        Text(text, color = ElectricAzure, fontSize = 14.sp)
    }
}

@Composable
fun ListItemMock(icon: ImageVector, title: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(horizontal=16.dp, vertical=16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, null, tint = Color.White)
            Spacer(Modifier.width(16.dp))
            Text(title, color = Color.White, fontSize = 16.sp)
        }
        Text(value, color = Color.Gray, fontSize = 14.sp)
    }
}

@Composable
fun UploadProgressScreen(onComplete: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(3000)
        onComplete()
    }
    Box(modifier = Modifier.fillMaxSize().background(DeepSky), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(color = ElectricAzure)
            Spacer(modifier = Modifier.height(24.dp))
            Text("Uploading to Skyswip...", color = Color.White, fontSize = 18.sp)
        }
    }
}

@Composable
fun CameraToolButton(icon: ImageVector, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, contentDescription = label, tint = Color.White, modifier = Modifier.size(28.dp))
        Text(label, color = Color.White, fontSize = 11.sp, modifier = Modifier.padding(top = 4.dp))
    }
}

@Composable
fun IOSAlertDialog(
    title: String,
    message: String,
    confirmText: String,
    cancelText: String,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    androidx.compose.ui.window.Dialog(onDismissRequest = onCancel) {
        Column(
            modifier = Modifier
                .width(270.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(Color(0xFFE5E5EA)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(20.dp))
            Text(title, fontWeight = FontWeight.SemiBold, fontSize = 17.sp, color = Color.Black)
            Spacer(Modifier.height(4.dp))
            Text(message, fontSize = 13.sp, color = Color.Black.copy(alpha = 0.7f), textAlign = TextAlign.Center, modifier = Modifier.padding(horizontal = 16.dp))
            Spacer(Modifier.height(20.dp))
            Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color.Gray.copy(alpha=0.3f)))
            Row(modifier = Modifier.height(44.dp).fillMaxWidth()) {
                Box(modifier = Modifier.weight(1f).fillMaxHeight().clickable { onCancel() }, contentAlignment = Alignment.Center) {
                    Text(cancelText, color = Color(0xFF007AFF), fontSize = 17.sp)
                }
                Box(modifier = Modifier.width(1.dp).fillMaxHeight().background(Color.Gray.copy(alpha=0.3f)))
                Box(modifier = Modifier.weight(1f).fillMaxHeight().clickable { onConfirm() }, contentAlignment = Alignment.Center) {
                    Text(confirmText, color = Color.Red, fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}
