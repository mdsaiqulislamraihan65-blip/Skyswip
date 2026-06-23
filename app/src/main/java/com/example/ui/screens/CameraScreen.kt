package com.example.ui.screens

import androidx.compose.runtime.Composable

@Composable
fun CameraScreen(onClose: () -> Unit = {}) {
    VideoCreatorFlow(onClose = onClose)
}
