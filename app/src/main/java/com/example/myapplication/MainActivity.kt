package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                SmartHomeApp()
            }
        }
    }
}

@Composable
fun SmartHomeApp() {
    var isControlScreen by remember { mutableStateOf(false) } // Untuk berpindah layar

    if (isControlScreen) {
        DeviceControlScreen { isControlScreen = false }
    } else {
        DeviceStatusScreen { isControlScreen = true }
    }
}

@Composable
fun DeviceStatusScreen(onNavigate: () -> Unit) {
    val devices = listOf("Smart AC", "Smart TV", "Smart Lamp", "Smart Door")

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "App Smart Control", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(columns = GridCells.Fixed(2), contentPadding = PaddingValues(8.dp)) {
            items(devices.size) { index ->
                DeviceCard(name = devices[index], isActive = false) {}
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onNavigate) {
            Text(text = "Go to Control")
        }
    }
}

@Composable
fun DeviceControlScreen(onNavigate: () -> Unit) {
    val devices = listOf(
        "Smart AC" to "Temperature 25°",
        "Smart TV" to "Mitsubishi 294",
        "Smart Lamp" to "Philips 203",
        "Smart Door" to "Door 234"
    )

    val deviceStates = remember { mutableStateMapOf(*devices.map { it.first to false }.toTypedArray()) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "App Smart  Control", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(columns = GridCells.Fixed(2), contentPadding = PaddingValues(8.dp)) {
            items(devices.size) { index ->
                val (name, detail) = devices[index]
                DeviceCard(name = name, detail = detail, isActive = deviceStates[name] == true) {
                    deviceStates[name] = !deviceStates[name]!!
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onNavigate) {
            Text(text = "Back to Status")
        }
    }
}

@Composable
fun DeviceCard(name: String, detail: String = "", isActive: Boolean, onToggle: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(120.dp)
            .clickable { onToggle() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = if (isActive) Color(0xFFFFD59E) else Color(0xFFEFEFEF))
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = name, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(text = detail, fontSize = 12.sp, color = Color.Gray)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Switch(checked = isActive, onCheckedChange = { onToggle() })
            }
        }
    }
}
