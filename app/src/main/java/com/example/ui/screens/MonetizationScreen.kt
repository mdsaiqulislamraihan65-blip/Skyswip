package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun MonetizationDialog(onDismiss: () -> Unit) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0D0D12))
                .statusBarsPadding()
        ) {
            // App Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onDismiss, modifier = Modifier.size(24.dp)) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text("Earnings", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier.weight(1f))
                Icon(Icons.Outlined.HelpOutline, contentDescription = "Help", tint = Color.White, modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(16.dp))
                Icon(Icons.Outlined.AccountBalanceWallet, contentDescription = "Wallet", tint = Color(0xFFA55EFC), modifier = Modifier.size(24.dp))
            }

            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(scrollState)
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                // Total Balance Card
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            Brush.linearGradient(
                                colors = listOf(Color(0xFF1E0E3E), Color(0xFF120822))
                            )
                        )
                        .border(1.dp, Color(0xFF3B1E71), RoundedCornerShape(16.dp))
                        .padding(20.dp)
                ) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color(0xFF3B1E71).copy(alpha = 0.5f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Default.AccountBalanceWallet, contentDescription = null, tint = Color(0xFFA55EFC), modifier = Modifier.size(20.dp))
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Text("Total Balance", color = Color.Gray, fontSize = 14.sp)
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("$4,758.60", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("All Earnings", color = Color.LightGray, fontSize = 14.sp)
                                Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.LightGray, modifier = Modifier.size(16.dp))
                            }
                        }
                        // Wallet Illustration Placeholder
                        Box(
                            modifier = Modifier
                                .size(100.dp),
                            contentAlignment = Alignment.Center
                        ) {
                             Icon(Icons.Default.AccountBalanceWallet, contentDescription = null, tint = Color(0xFF6B21A8), modifier = Modifier.size(80.dp))
                             // Coin
                             Box(modifier = Modifier.align(Alignment.TopEnd).padding(top = 10.dp, end = 10.dp).size(24.dp).clip(CircleShape).background(Color(0xFFFFD700)).border(2.dp, Color(0xFFB8860B), CircleShape), contentAlignment = Alignment.Center) {
                                 Text("$", color = Color(0xFFB8860B), fontSize = 14.sp, fontWeight = FontWeight.Bold)
                             }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Stats Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatCard(
                        modifier = Modifier.weight(1f),
                        title = "Today's Earnings",
                        amount = "$128.40",
                        change = "12.5% vs yesterday",
                        isPositive = true,
                        icon = Icons.Default.TrendingUp,
                        iconColor = Color(0xFF00FFD1)
                    )
                    StatCard(
                        modifier = Modifier.weight(1f),
                        title = "This Month",
                        amount = "$2,340.80",
                        change = "18.6% vs last month",
                        isPositive = true,
                        icon = Icons.Default.CalendarToday,
                        iconColor = Color(0xFFA55EFC)
                    )
                    StatCard(
                        modifier = Modifier.weight(1f),
                        title = "Total Withdrawn",
                        amount = "$1,250.00",
                        change = "All time",
                        isPositive = null,
                        icon = Icons.Default.AccountBalanceWallet,
                        iconColor = Color(0xFF007FFF)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Monetization Active
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF0F1A14))
                        .border(1.dp, Color(0xFF1E3A29), RoundedCornerShape(12.dp))
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF1E3A29)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Verified, contentDescription = null, tint = Color(0xFF00FFD1), modifier = Modifier.size(24.dp))
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Monetization Active", color = Color(0xFF00FFD1), fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.weight(1f))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(Color(0xFF1E3A29))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text("ACTIVE", color = Color(0xFF00FFD1), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Congrats! You are earning from our platform. Keep creating amazing content.", color = Color.LightGray, fontSize = 12.sp, lineHeight = 16.sp)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text("How You Can Start Earning", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))

                // Earning Paths
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    EarningPathCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.Groups,
                        title = "1,000 Followers",
                        desc = "Get 1,000 followers and start earning automatically.",
                        progress = 1000,
                        total = 1000,
                        color = Color(0xFFA55EFC),
                        isCompleted = true
                    )
                    
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF16161C))
                            .border(1.dp, Color(0xFF2A2A35), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("OR", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }

                    EarningPathCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.GroupAdd,
                        title = "20 Referrals",
                        desc = "Refer 20 friends and start earning instantly.",
                        progress = 8,
                        total = 20,
                        color = Color(0xFF007FFF),
                        isCompleted = false
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Earnings Overview", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF16161C))
                            .border(1.dp, Color(0xFF2A2A35), RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("This Week", color = Color.White, fontSize = 12.sp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                // Chart Area
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF16161C))
                        .border(1.dp, Color(0xFF2A2A35), RoundedCornerShape(12.dp))
                        .padding(16.dp)
                ) {
                    androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
                        val pts = listOf(
                            100f, 170f, 130f, 200f, 256.8f, 210f, 130f
                        )
                        val maxVal = 300f
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
                            style = Stroke(width = 3.dp.toPx())
                        )

                        // Points
                        val points = mutableListOf<Offset>()
                        pts.forEachIndexed { i, v ->
                            val x = i * stepX
                            val y = size.height - (v / maxVal * size.height)
                            points.add(Offset(x, y))
                            drawCircle(
                                color = Color.White,
                                radius = 4.dp.toPx(),
                                center = Offset(x, y)
                            )
                        }
                    }
                    
                    // Chart labels
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("$300", color = Color.Gray, fontSize = 10.sp)
                        Text("$200", color = Color.Gray, fontSize = 10.sp)
                        Text("$100", color = Color.Gray, fontSize = 10.sp)
                        Text("$0", color = Color.Gray, fontSize = 10.sp)
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter).padding(start = 24.dp), // offset by Y axis labels
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun").forEach {
                            Text(it, color = Color.Gray, fontSize = 10.sp)
                        }
                    }

                    // Tooltip for peak
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(end = 40.dp) // Manual positioning approximation
                            .offset(y = (-8).dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color(0xFF6B21A8))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text("$256.80", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Bottom Action buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ActionBottomBtn(Icons.Default.AccountBalanceWallet, Color(0xFFA55EFC), "Withdraw")
                    ActionBottomBtn(Icons.Default.ReceiptLong, Color(0xFF007FFF), "Transactions")
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ActionBottomBtn(Icons.Default.CreditCard, Color(0xFFFF4081), "Payment Info")
                    ActionBottomBtn(Icons.Default.Lightbulb, Color(0xFFFFD700), "Earning Tips")
                }

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@Composable
fun StatCard(modifier: Modifier, title: String, amount: String, change: String, isPositive: Boolean?, icon: ImageVector, iconColor: Color) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF16161C))
            .border(1.dp, Color(0xFF2A2A35), RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(title, color = Color.Gray, fontSize = 10.sp)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(amount, color = if (isPositive == null) Color(0xFF007FFF) else Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(4.dp))
        if (isPositive != null) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(if (isPositive) Icons.Default.ArrowUpward else Icons.Default.ArrowDownward, contentDescription = null, tint = if (isPositive) Color(0xFF00FFD1) else Color.Red, modifier = Modifier.size(10.dp))
                Spacer(modifier = Modifier.width(2.dp))
                Text("↑ $change", color = if (isPositive) Color(0xFF00FFD1) else Color.Red, fontSize = 10.sp)
            }
        } else {
            Text(change, color = Color.Gray, fontSize = 10.sp)
        }
    }
}

@Composable
fun EarningPathCard(modifier: Modifier, icon: ImageVector, title: String, desc: String, progress: Int, total: Int, color: Color, isCompleted: Boolean) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF16161C))
            .border(1.dp, Color(0xFF2A2A35), RoundedCornerShape(12.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(color.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(24.dp))
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(title, color = color, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(4.dp))
        Text(desc, color = Color.LightGray, fontSize = 10.sp, textAlign = androidx.compose.ui.text.style.TextAlign.Center, lineHeight = 14.sp)
        Spacer(modifier = Modifier.height(16.dp))
        
        // Progress bar
        Box(modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)).background(Color.DarkGray)) {
            Box(modifier = Modifier.fillMaxHeight().fillMaxWidth(fraction = progress.toFloat()/total).clip(RoundedCornerShape(3.dp)).background(color))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text("$progress / $total", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        
        Spacer(modifier = Modifier.height(12.dp))
        if (isCompleted) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF0F1A14))
                    .border(1.dp, Color(0xFF1E3A29), RoundedCornerShape(16.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Check, contentDescription = null, tint = Color(0xFF00FFD1), modifier = Modifier.size(12.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Completed", color = Color(0xFF00FFD1), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(color.copy(alpha=0.1f))
                    .border(1.dp, color, RoundedCornerShape(16.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CardGiftcard, contentDescription = null, tint = color, modifier = Modifier.size(12.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Invite & Earn", color = color, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun RowScope.ActionBottomBtn(icon: ImageVector, iconColor: Color, title: String) {
    Row(
        modifier = Modifier
            .weight(1f)
            .height(56.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF16161C))
            .border(1.dp, Color(0xFF2A2A35), RoundedCornerShape(12.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(title, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
    }
}
