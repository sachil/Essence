package xyz.sachil.essence.repository.wrap

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import xyz.sachil.essence.model.net.bean.TypeData
import xyz.sachil.essence.model.net.bean.WeeklyPopularData
import xyz.sachil.essence.util.LoadState

data class DataResponse(
    val data: LiveData<PagedList<TypeData>>,
    val loadState: LiveData<LoadState>,
    val refresh: () -> Unit
)
