package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.Games
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.LiquidGlassContainer
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.PureBlack
import androidx.compose.ui.draw.clip

@Composable
fun DiscoverScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PureBlack)
            .padding(16.dp)
    ) {
        // Search bar
        LiquidGlassContainer(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.White)
                Spacer(modifier = Modifier.width(12.dp))
                Text("Search 'cooking tips'...", color = Color.White.copy(alpha = 0.6f), modifier = Modifier.weight(1f))
                Icon(Icons.Default.Mic, contentDescription = "Voice Search", tint = Color.White)
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Categories
        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(1) {
                CategoryChip(icon = Icons.Default.LocalFireDepartment, text = "Trending", active = true)
                CategoryChip(icon = Icons.Default.MusicNote, text = "Music")
                CategoryChip(icon = Icons.Default.LiveTv, text = "Live")
                CategoryChip(icon = Icons.Default.Games, text = "Gaming")
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        Text("Trending Hashtags", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        
        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(5) {
                LiquidGlassContainer(
                    shape = androidx.compose.foundation.shape.CircleShape
                ) {
                    Text(
                        text = "#LiquidGlass", 
                        color = NeonCyan, 
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(10) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(9f / 16f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.DarkGray)
                ) {
                    LiquidGlassContainer(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(8.dp)
                    ) {
                        Text(
                            text = "1.2M views",
                            color = Color.White,
                            fontSize = 10.sp,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryChip(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String, active: Boolean = false) {
    LiquidGlassContainer(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(end = 12.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = if (active) NeonCyan else Color.White, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text, color = if (active) NeonCyan else Color.White, fontSize = 14.sp)
        }
    }
}
