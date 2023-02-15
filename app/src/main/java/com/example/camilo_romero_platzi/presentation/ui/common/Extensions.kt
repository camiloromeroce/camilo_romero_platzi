package com.example.camilo_romero_platzi.presentation.ui.common

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.camilo_romero_platzi.R
import com.example.domain.home.common.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.math.BigInteger
import java.security.MessageDigest

fun <T> LifecycleOwner.launchAndCollect(
    flow: Flow<T>,
    state: Lifecycle.State = Lifecycle.State.STARTED,
    body: (T) -> Unit,
) {
    lifecycleScope.launch {
        this@launchAndCollect.repeatOnLifecycle(state) {
            flow.collect(body)
        }
    }
}

fun getCurrentMD5(ts: String): String {
    val input = ts + PRIVATE_KEY + API_KEY
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
}

fun Fragment.getResourceView(resourceId: Int): View =
    requireActivity().findViewById(resourceId)

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = true): View =
    LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

fun ImageView.loadUrl(url: String) {
    Glide.with(context).load(url).into(this)
}

fun ImageView.loadUrlRounded(url: String) {
    Glide.with(context).load(url).circleCrop().error(R.drawable.ic_marvel_logo).into(this)
}

fun buildImageUrl(path: String?, extension: String?): String = "$path.$extension"

fun Toolbar.setEndPadding(custom: Int) = this.setPadding(0, 0, custom, 0)

fun Context.getErrorMessage(errorCode: String): String {
    return when (errorCode.toInt()) {
        connectionErrorCode -> getString(R.string.noConnectionMessage)
        notCharactersErrorCode -> getString(R.string.noCharactersMessage)
        notDetailsErrorCode -> getString(R.string.notDetailsMessage)
        unknownErrorCode -> getString(R.string.unknownMessage)
        else -> getString(R.string.unknownMessage)
    }
}