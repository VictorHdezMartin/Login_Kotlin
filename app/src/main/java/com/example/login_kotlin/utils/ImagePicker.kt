package com.example.login_kotlin.utils

    import android.net.Uri
    import androidx.activity.result.ActivityResultCaller
    import androidx.activity.result.contract.ActivityResultContracts

    class ImagePicker(private val activityResultCaller: ActivityResultCaller, private val onImageSelected: (Uri) -> Unit) {

        private val getContent = activityResultCaller.registerForActivityResult(
            ActivityResultContracts.GetContent()) { uri: Uri? ->  uri?.let { onImageSelected(it) }
        }

        fun openImagePicker() {
            getContent.launch("image/*")
        }
    }
