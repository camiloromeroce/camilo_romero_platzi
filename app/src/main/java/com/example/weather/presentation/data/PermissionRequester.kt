package com.example.weather.presentation.data

import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine

class PermissionRequester(
    fragment: Fragment, private val permission: String
) {

    private var onRequest: (Boolean) -> Unit = {}
    private val launcher =
        fragment.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            onRequest(isGranted)
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun request(): Boolean = suspendCancellableCoroutine { continuation ->
        onRequest = {
            continuation.resume(it, null)
        }
        launcher.launch(permission)
    }
}