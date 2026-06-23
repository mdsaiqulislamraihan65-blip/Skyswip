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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ui.theme.ElectricViolet
import com.example.ui.theme.PureBlack

@Composable
fun SettingsScreen(onBack: () -> Unit) {
    var showEditProfile by remember { mutableStateOf(false) }
    var showMonetization by remember { mutableStateOf(false) }
    var showStudio by remember { mutableStateOf(false) }

    if (showEditProfile) {
        EditProfileDialog(onDismiss = { showEditProfile = false })
    }

    if (showMonetization) {
        MonetizationDialog(onDismiss = { showMonetization = false })
    }

    if (showStudio) {
        StudioDialog(onDismiss = { showStudio = false })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PureBlack)
            .statusBarsPadding()
    ) {
        // App Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                "Settings and privacy",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Options List
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SettingsSection("Account") {
                SettingsItem(
                    icon = Icons.Default.Person,
                    title = "Account",
                    subtitle = "Manage your account, edit profile",
                    onClick = { showEditProfile = true }
                )
            }
            SettingsSection("Creator tools") {
                SettingsItem(
                    icon = Icons.Default.Analytics,
                    title = "Studio",
                    subtitle = "Manage your content and grow your audience",
                    onClick = { showStudio = true }
                )
                SettingsItem(
                    icon = Icons.Default.MonetizationOn,
                    title = "Monetization",
                    subtitle = "Gifts, Creator rewards",
                    onClick = { showMonetization = true }
                )
            }
            SettingsSection("Support") {
                SettingsItem(
                    icon = Icons.Default.HelpOutline,
                    title = "Help center",
                    subtitle = "Report a problem, support"
                )
                SettingsItem(
                    icon = Icons.Default.Info,
                    title = "About",
                    subtitle = "Terms of service, privacy policy"
                )
            }
        }
    }
}

@Composable
fun EditProfileDialog(onDismiss: () -> Unit) {
    var name by remember { mutableStateOf("John Creator") }
    var username by remember { mutableStateOf("@john_creator") }
    var email by remember { mutableStateOf("johncreator@gmail.com") }
    var bio by remember { mutableStateOf("Content Creator & Gamer") }
    var phone by remember { mutableStateOf("+880 1234-567890") }
    var gender by remember { mutableStateOf("Male") }
    var country by remember { mutableStateOf("Bangladesh") }

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
                Text("Edit Profile", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }

            rememberScrollState().let { scrollState ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .verticalScroll(scrollState)
                        .padding(horizontal = 16.dp)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    // Hero Section (Cover + Avatar)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    ) {
                        // Cover Photo
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(140.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(
                                    Brush.linearGradient(
                                        colors = listOf(Color(0xFF3F0071), Color(0xFF150050))
                                    )
                                )
                        ) {
                            // Change Cover Button
                            Row(
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(12.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color.Black.copy(alpha = 0.5f))
                                    .padding(horizontal = 12.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.PhotoCamera, contentDescription = null, tint = Color(0xFFA55EFC), modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Change Cover", color = Color.White, fontSize = 12.sp)
                            }
                        }

                        // Profile Photo
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .size(100.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape)
                                    .background(
                                        Brush.linearGradient(
                                            colors = listOf(Color(0xFF00FFD1), Color(0xFFFF007F))
                                        )
                                    )
                                    .padding(4.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(CircleShape)
                                        .background(Color.DarkGray)
                                ) {
                                    // Placeholder for Avatar
                                    Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.fillMaxSize().padding(16.dp), tint = Color.LightGray)
                                }
                            }
                            // Camera Icon Badge
                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .size(30.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFA55EFC))
                                    .padding(4.dp)
                                    .border(2.dp, Color(0xFF0D0D12), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.PhotoCamera, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Fields
                    EditFieldCard("Full Name", name, Icons.Default.Person, true)
                    Spacer(modifier = Modifier.height(12.dp))
                    EditFieldCard("Username", username, Icons.Default.AlternateEmail, true)
                    Spacer(modifier = Modifier.height(12.dp))
                    EditFieldCard("Email Address", email, Icons.Default.MailOutline, true)
                    Spacer(modifier = Modifier.height(12.dp))
                    EditFieldCard("Bio", bio, Icons.Default.Edit, true)
                    Spacer(modifier = Modifier.height(12.dp))
                    EditFieldCard("Phone Number", phone, Icons.Default.Phone, true)
                    Spacer(modifier = Modifier.height(12.dp))
                    EditFieldCard("Gender", gender, Icons.Default.Person, true)
                    Spacer(modifier = Modifier.height(12.dp))
                    EditFieldCard("Country", country, Icons.Default.Public, true)
                    Spacer(modifier = Modifier.height(12.dp))

                    // Action Buttons
                    ActionCard("Change Password", Icons.Default.Lock)
                    Spacer(modifier = Modifier.height(12.dp))
                    ActionCard("Change Profile Photo", Icons.Default.Image)
                    Spacer(modifier = Modifier.height(12.dp))
                    ActionCard("Change Cover Photo", Icons.Default.Image)
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }

            // Bottom Save Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(Color(0xFF6B21A8), Color(0xFF007FFF))
                        )
                    )
                    .clickable { onDismiss() }
                    .padding(vertical = 16.dp)
            ) {
                Text(
                    "Save Changes",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
                Icon(
                    Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.align(Alignment.CenterEnd).padding(end = 16.dp)
                )
            }
        }
    }
}

@Composable
fun EditFieldCard(label: String, value: String, icon: ImageVector, showEdit: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF16161C))
            .border(1.dp, Color(0xFF2A2A35), RoundedCornerShape(12.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = Color(0xFFA55EFC), modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(label, color = Color.Gray, fontSize = 10.sp)
            Text(value, color = Color.White, fontSize = 14.sp)
        }
        if (showEdit) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Edit", color = Color(0xFFA55EFC), fontSize = 14.sp)
                Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color(0xFFA55EFC), modifier = Modifier.size(16.dp))
            }
        }
    }
}

@Composable
fun ActionCard(label: String, icon: ImageVector) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF16161C))
            .border(1.dp, Color(0xFF2A2A35), RoundedCornerShape(12.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = Color(0xFF007FFF), modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(label, color = Color.White, fontSize = 14.sp, modifier = Modifier.weight(1f))
        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
    }
}

@Composable
fun SettingsSection(title: String, content: @Composable () -> Unit) {
    Column {
        Text(
            text = title,
            color = Color.White.copy(alpha = 0.5f),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        content()
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun SettingsItem(icon: ImageVector, title: String, subtitle: String? = null, onClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White.copy(alpha = 0.05f))
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            contentDescription = title,
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            if (subtitle != null) {
                Text(subtitle, color = Color.White.copy(alpha = 0.5f), fontSize = 12.sp)
            }
        }
        Icon(
            Icons.Default.ChevronRight,
            contentDescription = "Navigate",
            tint = Color.White.copy(alpha = 0.5f)
        )
    }
}
