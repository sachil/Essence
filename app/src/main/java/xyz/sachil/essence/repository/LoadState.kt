package xyz.sachil.essence.repository

sealed class LoadState

object Success : LoadState()
object NoMoreData : LoadState()
object Failed : LoadState()
object Loading : LoadState()
object Refreshing : LoadState()