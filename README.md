# Marshall - Cat Medical Records Organizer

A simple Android app to organize all your cat's medical records, images, and documents in one folder.

## Features

✅ **Easy File Management** - Add and remove files easily
✅ **File Organization** - All medical records in one folder
✅ **Image Support** - Store cat photos and medical images
✅ **Document Storage** - Save PDFs, prescriptions, checkup notes
✅ **Simple Interface** - Clean, beginner-friendly design
✅ **Quick Access** - Find files instantly
✅ **File Count** - See how many files you have

## How to Use

1. **Open the app** - You'll see "Cat Medical Records" folder
2. **Click the + button** - Select files to add
3. **Choose your files** - Select medical records, images, documents
4. **Click "Add Files"** - Files are added to your folder
5. **Remove files** - Click the X on any file to remove it

## Installation Instructions

See INSTALLATION_STEPS.md for detailed step-by-step guide

## Project Structure

```
Marshall/
├── src/main/
│   ├── java/com/marshall/app/
│   │   └── MainActivity.kt (Complete app code)
│   └── AndroidManifest.xml
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

## Technologies Used

- Kotlin
- Jetpack Compose (Modern UI)
- Android Material Design 3

## Permissions Required

- READ_EXTERNAL_STORAGE (access files)
- WRITE_EXTERNAL_STORAGE (save files)
- MANAGE_EXTERNAL_STORAGE (manage folders)