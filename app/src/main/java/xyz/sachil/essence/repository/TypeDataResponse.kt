package xyz.sachil.essence.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import xyz.sachil.essence.model.net.bean.TypeData

data class TypeDataResponse(
    val data: LiveData<PagedList<TypeData>>,
    val loadState: LiveData<LoadState>,
    val refresh: () -> Unit
)
