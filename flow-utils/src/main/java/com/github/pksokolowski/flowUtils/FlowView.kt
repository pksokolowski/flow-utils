package com.github.pksokolowski.flowUtils

import android.widget.Button
import android.widget.EditText
import androidx.annotation.CheckResult
import androidx.core.widget.doOnTextChanged
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

@ExperimentalCoroutinesApi
@CheckResult
fun EditText.textChanges(): Flow<CharSequence?> {
    return callbackFlow {
        val watcher = doOnTextChanged { text, start, count, after ->
            offer(text)
        }
        awaitClose { removeTextChangedListener(watcher) }
    }
}

@ExperimentalCoroutinesApi
@CheckResult
fun Button.clicks(): Flow<Unit> {
    return callbackFlow {
        setOnClickListener { _ ->
            offer(Unit)
        }
        awaitClose { setOnClickListener(null) }
    }
}