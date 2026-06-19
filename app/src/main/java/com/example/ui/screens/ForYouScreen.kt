package com.example.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import com.example.ui.components.VideoPlayer
import com.example.ui.theme.ElectricViolet
import com.example.ui.theme.LiquidGlass
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.PureBlack
import com.example.ui.components.LiquidGlassContainer

val DUMMY_VIDEOS = listOf(
    "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
    "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
    "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForYouScreen() {
    val pagerState = rememberPagerState(pageCount = { DUMMY_VIDEOS.size })
    var showComments by remember { mutableStateOf(false) }
    var hearts by remember { mutableStateOf(emptyList<Offset>()) }

    Box(modifier = Modifier.fillMaxSize().background(PureBlack)) {
        VerticalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onDoubleTap = { offset ->
                                hearts = hearts + offset
                            }
                        )
                    }
            ) {
                VideoPlayer(
                    videoUrl = DUMMY_VIDEOS[page],
                    isPlaying = pagerState.currentPage == page,
                    modifier = Modifier.fillMaxSize()
                )
                
                // Hearts layer
                hearts.forEach { offset ->
                    HeartBurst(offset = offset, onComplete = { hearts = hearts - offset })
                }
                
                // Right Side Actions
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 12.dp, bottom = 128.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Profile Icon
                    Box(modifier = Modifier.size(48.dp)) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .border(2.dp, ElectricViolet, CircleShape)
                                .background(
                                    androidx.compose.ui.graphics.Brush.linearGradient(
                                        colors = listOf(ElectricViolet, NeonCyan)
                                    )
                                )
                                .align(Alignment.TopCenter),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("FE", color = Color.White, fontWeight = FontWeight.Black, fontSize = 14.sp)
                        }
                        Box(
                            modifier = Modifier
                                .size(18.dp)
                                .clip(CircleShape)
                                .background(ElectricViolet)
                                .border(2.dp, PureBlack, CircleShape)
                                .align(Alignment.BottomCenter),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Follow", tint = Color.White, modifier = Modifier.size(10.dp))
                        }
                    }
                    
                    VideoAction(icon = Icons.Default.Favorite, text = "1.2M", onClick = { })
                    VideoAction(icon = Icons.Default.MailOutline, text = "42.8K", onClick = { showComments = true })
                    VideoAction(icon = Icons.Default.Share, text = "Share", onClick = { })
                    VideoAction(icon = Icons.Default.MoreVert, text = "", onClick = { })
                    
                    // Sound Disc
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF111111))
                            .border(4.dp, Color.White.copy(alpha = 0.05f), CircleShape)
                            .padding(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                                .background(
                                    androidx.compose.ui.graphics.Brush.linearGradient(
                                        colors = listOf(ElectricViolet, NeonCyan)
                                    )
                                )
                        )
                    }
                }
                
                // Bottom Gradient Overlay + Info
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .align(Alignment.BottomCenter)
                        .background(
                            androidx.compose.ui.graphics.Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f))
                            )
                        )
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 16.dp, bottom = 120.dp, end = 80.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("@the_future_edit", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp, 
                            style = androidx.compose.ui.text.TextStyle(
                                shadow = androidx.compose.ui.graphics.Shadow(
                                    color = Color.Black,
                                    offset = androidx.compose.ui.geometry.Offset(1f, 1f),
                                    blurRadius = 4f
                                )
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(modifier = Modifier.clip(CircleShape).background(NeonCyan).padding(2.dp)) {
                            // Checkmark can go here
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Experience the Liquid Glass design language of 2026. Every interaction is fluid, every pixel is premium. ✨ #SrTok #DesignFuture", 
                        color = Color.White.copy(alpha = 0.9f), 
                        fontSize = 14.sp,
                        maxLines = 2,
                        style = androidx.compose.ui.text.TextStyle(
                            shadow = androidx.compose.ui.graphics.Shadow(
                                color = Color.Black,
                                offset = androidx.compose.ui.geometry.Offset(1f, 1f),
                                blurRadius = 4f
                            )
                        )
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    LiquidGlassContainer(
                        shape = CircleShape
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = "Music", tint = Color.White, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Original Sound - Tokyo Night Drive", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                        }
                    }
                }
            }
        }
        
        // Top Overlay
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp, start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top
        ) {
            Text("Following", color = Color.White.copy(alpha = 0.6f), fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.width(24.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("For You", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Box(modifier = Modifier.width(24.dp).height(3.dp).clip(CircleShape).background(ElectricViolet))
            }
        }

        // Comments Bottom Sheet
        if (showComments) {
            val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
            ModalBottomSheet(
                onDismissRequest = { showComments = false },
                sheetState = sheetState,
                containerColor = com.example.ui.theme.DeepObsidian,
                dragHandle = { BottomSheetDefaults.DragHandle() }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("42.8K Comments", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        IconButton(onClick = { showComments = false }) {
                            Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(bottom = 32.dp)
                    ) {
                        items(10) { index ->
                            CommentItem(index)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CommentItem(index: Int) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(Color.Gray)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text("User ${index + 1}", color = Color.White.copy(alpha = 0.6f), fontSize = 12.sp, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(4.dp))
            Text("This liquid glass effect looks absolutely stunning! ✨", color = Color.White, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Text("2d", color = Color.White.copy(alpha = 0.4f), fontSize = 12.sp)
                Text("Reply", color = Color.White.copy(alpha = 0.6f), fontSize = 12.sp, fontWeight = FontWeight.Medium)
            }
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(Icons.Default.Favorite, contentDescription = "Like", tint = Color.White.copy(alpha = 0.4f), modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.height(4.dp))
            Text("${120 - index}", color = Color.White.copy(alpha = 0.4f), fontSize = 12.sp)
        }
    }
}

@Composable
fun HeartBurst(offset: Offset, onComplete: () -> Unit) {
    val transition = updateTransition(targetState = true, label = "heartBurst")
    val scale by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 600, easing = FastOutSlowInEasing) },
        label = "scale"
    ) { state -> if (state) 1.5f else 0.5f }
    val alpha by transition.animateFloat(
        transitionSpec = { keyframes { durationMillis = 600; 1f at 0; 1f at 400; 0f at 600 } },
        label = "alpha"
    ) { state -> if (state) 1f else 0f }

    LaunchedEffect(Unit) {
        delay(600)
        onComplete()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Icon(
            imageVector = Icons.Default.Favorite,
            contentDescription = null,
            tint = Color(0xFFE91E63),
            modifier = Modifier
                .offset(
                    x = with(LocalDensity.current) { offset.x.toDp() } - 60.dp,
                    y = with(LocalDensity.current) { offset.y.toDp() } - 60.dp
                )
                .size(120.dp)
                .scale(scale)
                .alpha(alpha)
        )
    }
}

@Composable
fun VideoAction(icon: ImageVector, text: String, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        LiquidGlassContainer(
            shape = CircleShape,
            modifier = Modifier.size(40.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { onClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
            }
        }
        if (text.isNotEmpty()) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = text,
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                style = androidx.compose.ui.text.TextStyle(
                    shadow = androidx.compose.ui.graphics.Shadow(
                        color = Color.Black,
                        offset = androidx.compose.ui.geometry.Offset(1f, 1f),
                        blurRadius = 4f
                    )
                )
            )
        }
    }
}
