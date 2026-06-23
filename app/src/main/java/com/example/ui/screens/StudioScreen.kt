package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset

@Composable
fun StudioDialog(onDismiss: () -> Unit) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .statusBarsPadding()
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Studio", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 28.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "Manage your content and grow your audience",
                        color = Color.LightGray,
                        fontSize = 14.sp
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Icon(
                        Icons.Outlined.HelpOutline,
                        contentDescription = "Help",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                    Icon(
                        Icons.Outlined.Notifications,
                        contentDescription = "Notifications",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            // Tabs
            val tabs = listOf("Overview", "Content", "Analytics", "Audience", "Earnings")
            var selectedTab by remember { mutableStateOf(0) }
            
            ScrollableTabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.Transparent,
                edgePadding = 16.dp,
                divider = {},
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = Color(0xFFA55EFC),
                        height = 3.dp
                    )
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Text(
                                text = title,
                                color = if (selectedTab == index) Color.White else Color.Gray,
                                fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 15.sp
                            )
                        }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))

            val scrollState = rememberScrollState()

            Column(modifier = Modifier.fillMaxWidth().weight(1f).verticalScroll(scrollState).padding(horizontal = 16.dp)) {
                // Overview stats row
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    // Total Earnings
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(180.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF161122))
                            .padding(12.dp)
                    ) {
                        Column {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text("Total Earnings", color = Color.LightGray, fontSize = 12.sp)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text("$4,758.60", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text("+12.5% vs last 30 days", color = Color(0xFF00FFD1), fontSize = 10.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                }
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFFA55EFC)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("$", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                }
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            // Simple Area Chart for earnings
                            androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxWidth().height(60.dp)) {
                                val pts = listOf(40f, 60f, 50f, 80f, 70f, 100f, 90f, 130f, 120f, 160f)
                                val maxVal = 200f
                                val stepX = size.width / (pts.size - 1)
                                
                                val path = Path()
                                pts.forEachIndexed { i, v ->
                                    val x = i * stepX
                                    val y = size.height - (v / maxVal * size.height)
                                    if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
                                }
                                
                                drawPath(
                                    path = path,
                                    color = Color(0xFFA55EFC),
                                    style = Stroke(width = 2.dp.toPx())
                                )
                                // Points
                                pts.forEachIndexed { i, v ->
                                    val x = i * stepX
                                    val y = size.height - (v / maxVal * size.height)
                                    drawCircle(
                                        color = Color.White,
                                        radius = 3.dp.toPx(),
                                        center = Offset(x, y)
                                    )
                                }
                            }
                        }
                    }
                    
                    // Minor stats
                    Column(
                        modifier = Modifier.weight(1f).height(180.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(modifier = Modifier.weight(1f).fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            StudioStatBox(
                                modifier = Modifier.weight(1f).fillMaxHeight(),
                                title = "Views",
                                value = "1.2M",
                                change = "+18.6%",
                                icon = Icons.Default.Visibility
                            )
                            StudioStatBox(
                                modifier = Modifier.weight(1f).fillMaxHeight(),
                                title = "Watch Time",
                                value = "12.4Kh",
                                change = "+14.3%",
                                icon = Icons.Default.Schedule
                            )
                        }
                        Row(modifier = Modifier.weight(1f).fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            StudioStatBox(
                                modifier = Modifier.weight(1f).fillMaxHeight(),
                                title = "Likes",
                                value = "74.8K",
                                change = "+9.1%",
                                icon = Icons.Default.ThumbUpOffAlt
                            )
                            StudioStatBox(
                                modifier = Modifier.weight(1f).fillMaxHeight(),
                                title = "Comments",
                                value = "3.6K",
                                change = "+7.3%",
                                icon = Icons.Default.ChatBubbleOutline
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                Text("Content", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    val filterScroll = rememberScrollState()
                    Row(
                        modifier = Modifier.weight(1f).horizontalScroll(filterScroll),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilterChip(text = "All", isSelected = true)
                        FilterChip(text = "Shorts", isSelected = false)
                        FilterChip(text = "Long Videos", isSelected = false)
                        FilterChip(text = "Live", isSelected = false)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF1C1C1E))
                            .border(1.dp, Color(0xFF2C2C2E), RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Sort, contentDescription = null, tint = Color.LightGray, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Latest", color = Color.LightGray, fontSize = 12.sp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, tint = Color.LightGray, modifier = Modifier.size(16.dp))
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Video List
                val videos = listOf(
                    StudioVideo(
                        "Tokyo Night Drive \uD83C\uDFD9\uFE0F",
                        "May 20, 2025 • Published",
                        "1.2M", "$ 312.60", "89K", "1.2K",
                        true, "0:28", Color(0xFFE91E63)
                    ),
                    StudioVideo(
                        "How I Created Liquid Glass Effect",
                        "May 18, 2025 • Published",
                        "856K", "$ 245.80", "67K", "892",
                        false, "10:45", Color(0xFFFF9800)
                    ),
                    StudioVideo(
                        "My Editing Room Setup 2025",
                        "May 15, 2025 • Published",
                        "642K", "$ 185.40", "52K", "654",
                        false, "8:32", Color(0xFF2196F3)
                    ),
                    StudioVideo(
                        "Neon Vibes Only ✨",
                        "May 12, 2025 • Published",
                        "1.8M", "$ 452.30", "112K", "1.5K",
                        true, "0:21", Color(0xFF00BCD4)
                    ),
                    StudioVideo(
                        "Cinematic Color Grading in CapCut",
                        "May 10, 2025 • Published",
                        "928K", "$ 274.10", "73K", "983",
                        false, "12:11", Color(0xFF9C27B0)
                    )
                )

                var videoToEdit by remember { mutableStateOf<StudioVideo?>(null) }
                if (videoToEdit != null) {
                    EditVideoDialog(video = videoToEdit!!, onDismiss = { videoToEdit = null })
                }

                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    videos.forEach { video ->
                        StudioVideoItem(video, onEditClick = { videoToEdit = video })
                    }
                }
                
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@Composable
fun TabRowDefaults.Indicator(
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    height: androidx.compose.ui.unit.Dp = 3.dp
) {
    Box(
        modifier
            .fillMaxWidth()
            .height(height)
            .background(color)
    )
}

@Composable
fun StudioStatBox(modifier: Modifier, title: String, value: String, change: String, icon: ImageVector) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFF16161C))
            .padding(12.dp)
    ) {
        Column {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(title, color = Color.Gray, fontSize = 11.sp, maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.width(4.dp))
                Icon(icon, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(16.dp))
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(value, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Spacer(modifier = Modifier.height(2.dp))
            Text(change, color = Color(0xFF00FFD1), fontSize = 10.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}

@Composable
fun FilterChip(text: String, isSelected: Boolean) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(if (isSelected) Color.Transparent else Color(0xFF1C1C1E))
            .border(
                1.dp,
                if (isSelected) Color(0xFFA55EFC) else Color.Transparent,
                RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
        Text(text, color = if (isSelected) Color(0xFFA55EFC) else Color.LightGray, fontSize = 13.sp)
    }
}

data class StudioVideo(
    val title: String,
    val date: String,
    val views: String,
    val earnings: String,
    val likes: String,
    val comments: String,
    val isShort: Boolean,
    val duration: String,
    val placeholderColor: Color
)

@Composable
fun StudioVideoItem(video: StudioVideo, onEditClick: () -> Unit) {
    var showMenu by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth().height(90.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Thumbnail
        Box(
            modifier = Modifier
                .width(160.dp)
                .fillMaxHeight()
                .clip(RoundedCornerShape(8.dp))
                .background(video.placeholderColor.copy(alpha = 0.3f))
        ) {
            // Placeholder gradient mimicking an image
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.linearGradient(
                            colors = listOf(Color.Black.copy(alpha=0.5f), Color.Transparent),
                            start = Offset(0f, Float.POSITIVE_INFINITY),
                            end = Offset(0f, 0f)
                        )
                    )
            )

            // Top left badge
            Box(
                modifier = Modifier
                    .padding(6.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.Black.copy(alpha = 0.6f))
                    .padding(horizontal = 4.dp, vertical = 2.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (video.isShort) {
                        Icon(Icons.Default.Whatshot, contentDescription = null, tint = Color.Red, modifier = Modifier.size(10.dp))
                        Spacer(modifier = Modifier.width(2.dp))
                        Text("Shorts", color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                    } else {
                        // Using a simple square-like icon for long video as substitute
                        Icon(Icons.Default.VideoLibrary, contentDescription = null, tint = Color(0xFFFFD700), modifier = Modifier.size(10.dp))
                        Spacer(modifier = Modifier.width(2.dp))
                        Text("Long Video", color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // Bottom right duration
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(6.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.Black.copy(alpha = 0.8f))
                    .padding(horizontal = 4.dp, vertical = 2.dp)
            ) {
                Text(video.duration, color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
        }

        // Details
        Column(modifier = Modifier.weight(1f).fillMaxHeight()) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                Text(
                    video.title,
                    color = Color.White,
                    fontSize = 14.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                Box {
                    IconButton(onClick = { showMenu = true }, modifier = Modifier.size(24.dp)) {
                        Icon(Icons.Default.MoreVert, contentDescription = "More", tint = Color.Gray, modifier = Modifier.size(16.dp))
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false },
                        modifier = Modifier.background(Color(0xFF1C1C1E))
                    ) {
                        DropdownMenuItem(
                            text = { Text("Edit video", color = Color.White) },
                            onClick = {
                                showMenu = false
                                onEditClick()
                            },
                            leadingIcon = { Icon(Icons.Default.Edit, contentDescription = null, tint = Color.White) }
                        )
                        DropdownMenuItem(
                            text = { Text("Delete", color = Color.Red) },
                            onClick = { showMenu = false },
                            leadingIcon = { Icon(Icons.Default.Delete, contentDescription = null, tint = Color.Red) }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(video.date, color = Color.Gray, fontSize = 11.sp)
            Spacer(modifier = Modifier.weight(1f))
            
            // Stats row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Views
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.PlayArrow, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(video.views, color = Color.LightGray, fontSize = 10.sp, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    }
                    Text("Views", color = Color.DarkGray, fontSize = 9.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
                
                // Earnings
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(video.earnings, color = Color(0xFF00FFD1), fontSize = 10.sp, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    }
                    Text("Earnings", color = Color.DarkGray, fontSize = 9.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }

                // Likes
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.ThumbUpOffAlt, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(video.likes, color = Color.LightGray, fontSize = 10.sp, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    }
                    Text("Likes", color = Color.DarkGray, fontSize = 9.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }

                // Comments
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.ChatBubbleOutline, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(video.comments, color = Color.LightGray, fontSize = 10.sp, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    }
                    Text("Comments", color = Color.DarkGray, fontSize = 9.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditVideoDialog(video: StudioVideo, onDismiss: () -> Unit) {
    var titleText by remember { mutableStateOf(video.title) }
    var descriptionText by remember { mutableStateOf("Learn about ") }
    var visibility by remember { mutableStateOf("Public") }
    var visibilityExpanded by remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF161122))
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text("Edit video", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA55EFC))
                ) {
                    Text("SAVE", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
            ) {
                // Thumbnail preview
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(video.placeholderColor.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.AddPhotoAlternate, contentDescription = null, tint = Color.White, modifier = Modifier.size(48.dp))
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Title
                OutlinedTextField(
                    value = titleText,
                    onValueChange = { titleText = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFA55EFC),
                        unfocusedBorderColor = Color.Gray,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedLabelColor = Color(0xFFA55EFC),
                        unfocusedLabelColor = Color.Gray
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Description
                OutlinedTextField(
                    value = descriptionText,
                    onValueChange = { descriptionText = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth().height(120.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFA55EFC),
                        unfocusedBorderColor = Color.Gray,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedLabelColor = Color(0xFFA55EFC),
                        unfocusedLabelColor = Color.Gray
                    ),
                    maxLines = 5
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Visibility
                ExposedDropdownMenuBox(
                    expanded = visibilityExpanded,
                    onExpandedChange = { visibilityExpanded = !visibilityExpanded }
                ) {
                    OutlinedTextField(
                        value = visibility,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Visibility") },
                        modifier = Modifier.menuAnchor().fillMaxWidth(),
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = visibilityExpanded) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFFA55EFC),
                            unfocusedBorderColor = Color.Gray,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedLabelColor = Color(0xFFA55EFC),
                            unfocusedLabelColor = Color.Gray
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = visibilityExpanded,
                        onDismissRequest = { visibilityExpanded = false },
                        modifier = Modifier.background(Color(0xFF1C1C1E))
                    ) {
                        DropdownMenuItem(
                            text = { Text("Public", color = Color.White) },
                            onClick = { visibility = "Public"; visibilityExpanded = false },
                            leadingIcon = { Icon(Icons.Default.Public, contentDescription = null, tint = Color.White) }
                        )
                        DropdownMenuItem(
                            text = { Text("Unlisted", color = Color.White) },
                            onClick = { visibility = "Unlisted"; visibilityExpanded = false },
                            leadingIcon = { Icon(Icons.Default.Link, contentDescription = null, tint = Color.White) }
                        )
                        DropdownMenuItem(
                            text = { Text("Private", color = Color.White) },
                            onClick = { visibility = "Private"; visibilityExpanded = false },
                            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = Color.White) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Tags
                OutlinedTextField(
                    value = "",
                    onValueChange = { },
                    label = { Text("Tags") },
                    placeholder = { Text("Add tags (comma separated)") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFA55EFC),
                        unfocusedBorderColor = Color.Gray,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedLabelColor = Color(0xFFA55EFC),
                        unfocusedLabelColor = Color.Gray
                    )
                )
                
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}
