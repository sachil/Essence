package xyz.sachil.essence.repository.wrap

import kotlinx.coroutines.CoroutineScope
import xyz.sachil.essence.util.Utils
import kotlin.coroutines.CoroutineContext

data class DataRequest(
    val coroutineScope: CoroutineScope,
    val coroutineContext: CoroutineContext,
    val category: String,
    val type: String
)
