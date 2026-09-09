package com.marshall.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.InsertDriveFile
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarshallApp()
        }
    }
}

@Composable
fun MarshallApp() {
    val selectedFiles = remember { mutableStateOf(listOf<String>()) }
    val folderName = remember { mutableStateOf("Cat Medical Records") }
    val isAddingFiles = remember { mutableStateOf(false) }

    if (isAddingFiles.value) {
        FilePickerScreen(
            onFileSelected = { fileName ->
                if (!selectedFiles.value.contains(fileName)) {
                    selectedFiles.value = selectedFiles.value + fileName
                }
            },
            onBack = { isAddingFiles.value = false }
        )
    } else {
        MainScreen(
            folderName = folderName.value,
            files = selectedFiles.value,
            onAddFiles = { isAddingFiles.value = true },
            onRemoveFile = { fileName ->
                selectedFiles.value = selectedFiles.value.filter { it != fileName }
            }
        )
    }
}

@Composable
fun MainScreen(
    folderName: String,
    files: List<String>,
    onAddFiles: () -> Unit,
    onRemoveFile: (String) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddFiles,
                containerColor = Color(0xFF6200EE)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add Files", tint = Color.White)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            // Title
            Text(
                text = "🐱 $folderName",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            // File Count
            Text(
                text = "Files: ${files.size}",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Files List
            if (files.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "📁 No files yet",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Click + to add medical records",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(files) { fileName ->
                        FileItemCard(fileName) {
                            onRemoveFile(fileName)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FileItemCard(
    fileName: String,
    onRemove: () -> Unit
) {
    val fileIcon = when {
        fileName.endsWith(".jpg") || fileName.endsWith(".png") -> Icons.Filled.Image
        fileName.endsWith(".pdf") || fileName.endsWith(".doc") -> Icons.Filled.InsertDriveFile
        else -> Icons.Filled.Folder
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE8D5F2), RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = fileIcon,
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .padding(end = 8.dp),
                    tint = Color(0xFF6200EE)
                )
                Text(
                    text = fileName,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
            IconButton(onClick = onRemove, modifier = Modifier.size(32.dp)) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Remove",
                    tint = Color.Red
                )
            }
        }
    }
}

@Composable
fun FilePickerScreen(
    onFileSelected: (String) -> Unit,
    onBack: () -> Unit
) {
    // Sample files to simulate file picker
    val sampleFiles = listOf(
        "vaccination_record_2024.pdf",
        "cat_photo_1.jpg",
        "vet_checkup_jan2024.pdf",
        "surgery_notes.doc",
        "cat_photo_2.jpg",
        "prescription_medication.pdf",
        "blood_test_results.pdf",
        "cat_photo_3.jpg"
    )

    val selectedFiles = remember { mutableStateOf(setOf<String>()) }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF6200EE))
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Text(
                    text = "Select Files",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(sampleFiles) { fileName ->
                    FileSelectItem(
                        fileName = fileName,
                        isSelected = selectedFiles.value.contains(fileName),
                        onToggle = {
                            val newSelected = selectedFiles.value.toMutableSet()
                            if (newSelected.contains(fileName)) {
                                newSelected.remove(fileName)
                            } else {
                                newSelected.add(fileName)
                            }
                            selectedFiles.value = newSelected
                        }
                    )
                }
            }

            // Add Selected Button
            Button(
                onClick = {
                    selectedFiles.value.forEach { onFileSelected(it) }
                    onBack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                enabled = selectedFiles.value.isNotEmpty()
            ) {
                Text("Add ${selectedFiles.value.size} File(s)")
            }
        }
    }
}

@Composable
fun FileSelectItem(
    fileName: String,
    isSelected: Boolean,
    onToggle: () -> Unit
) {
    val fileIcon = when {
        fileName.endsWith(".jpg") || fileName.endsWith(".png") -> Icons.Filled.Image
        fileName.endsWith(".pdf") || fileName.endsWith(".doc") -> Icons.Filled.InsertDriveFile
        else -> Icons.Filled.Folder
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (isSelected) Color(0xFFE8D5F2) else Color(0xFFF5F5F5),
                RoundedCornerShape(12.dp)
            )
            .clickable { onToggle() }
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = fileIcon,
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .padding(end = 8.dp),
                    tint = Color(0xFF6200EE)
                )
                Text(
                    text = fileName,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            if (isSelected) {
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = "Selected",
                    tint = Color(0xFF6200EE),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}