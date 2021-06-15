package xyz.sachil.essence.repository

import kotlinx.coroutines.CoroutineScope
import xyz.sachil.essence.util.Utils
import kotlin.coroutines.CoroutineContext

data class TypeDataRequest(
    val coroutineScope: CoroutineScope,
    val coroutineContext: CoroutineContext,
    val category: Utils.Category,
    val type: String,
    val popularType: Utils.PopularType = Utils.PopularType.VIEWS
)
