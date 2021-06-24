package xyz.sachil.essence.util

sealed class LoadState

object Success : LoadState()
object NoMoreData : LoadState()
object Failed : LoadState()
object Loading : LoadState()
object Refreshing : LoadState()