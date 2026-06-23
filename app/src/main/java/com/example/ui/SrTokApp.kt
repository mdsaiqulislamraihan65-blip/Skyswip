package com.example.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ui.screens.CameraScreen
import com.example.ui.screens.DiscoverScreen
import com.example.ui.screens.ForYouScreen
import com.example.ui.screens.InboxScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.theme.BackgroundGradientMid
import com.example.ui.theme.DeepObsidian
import com.example.ui.theme.ElectricViolet
import com.example.ui.theme.LiquidGlass
import com.example.ui.theme.LiquidGlassBorder
import com.example.ui.theme.MeshCyan
import com.example.ui.theme.MeshViolet
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.PureBlack

@Composable
fun SrTokApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        containerColor = Color.Transparent,
        bottomBar = {
            if (currentRoute != "camera" && currentRoute != "settings") {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 24.dp)
                ) {
                    BottomBar(navController = navController)
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(PureBlack)
        ) {
            // Simulated Video Background (Dynamic Mesh Gradient)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.linearGradient(
                            colors = listOf(DeepObsidian, BackgroundGradientMid, DeepObsidian),
                            start = Offset(0f, 0f),
                            end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                        )
                    )
            )
            
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .offset(x = (-40).dp, y = 100.dp)
                    .clip(CircleShape)
                    .background(MeshViolet)
                    .blur(60.dp)
            )
            
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.BottomEnd)
                    .offset(x = 40.dp, y = (-100).dp)
                    .clip(CircleShape)
                    .background(MeshCyan)
                    .blur(60.dp)
            )

            NavHost(
                navController = navController,
                startDestination = "home"
            ) {
                composable("home") { ForYouScreen() }
                composable("discover") { DiscoverScreen() }
                composable("camera") { CameraScreen(onClose = { navController.popBackStack() }) }
                composable("inbox") { InboxScreen() }
                composable("profile") { ProfileScreen(onSettingsClick = { navController.navigate("settings") }) }
                composable("settings") { com.example.ui.screens.SettingsScreen(onBack = { navController.popBackStack() }) }
            }
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(32.dp))
            .background(LiquidGlass)
            .border(1.dp, LiquidGlassBorder, RoundedCornerShape(32.dp))
            .padding(horizontal = 8.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AddItem(
            screen = "home",
            icon = Icons.Default.Home,
            currentDestination = currentDestination,
            navController = navController
        )
        AddItem(
            screen = "discover",
            icon = Icons.Default.Search,
            currentDestination = currentDestination,
            navController = navController
        )
        // Camera Action Button
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp, 40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(ElectricViolet, NeonCyan)
                        )
                    )
                    .clickable { navController.navigate("camera") },
                contentAlignment = Alignment.Center
            ) {
                Box(modifier = Modifier.size(52.dp, 36.dp).clip(RoundedCornerShape(10.dp)).background(Color.White))
                Icon(Icons.Default.Add, contentDescription = "Add", tint = PureBlack, modifier = Modifier.size(24.dp))
            }
        }
        AddItem(
            screen = "inbox",
            icon = Icons.Default.MailOutline,
            currentDestination = currentDestination,
            navController = navController
        )
        AddItem(
            screen = "profile",
            icon = Icons.Default.Person,
            currentDestination = currentDestination,
            navController = navController
        )
    }
}

@Composable
fun RowScope.AddItem(
    screen: String,
    icon: ImageVector,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    val selected = currentDestination?.hierarchy?.any { it.route == screen } == true
    androidx.compose.material3.IconButton(
        onClick = {
            navController.navigate(screen) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
        modifier = Modifier.weight(1f)
    ) {
        Icon(
            imageVector = icon, 
            contentDescription = "Navigation Icon",
            tint = if (selected) ElectricViolet else Color.White.copy(alpha = 0.5f),
            modifier = Modifier.size(28.dp)
        )
    }
}
