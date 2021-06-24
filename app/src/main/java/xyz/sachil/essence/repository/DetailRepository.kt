package xyz.sachil.essence.repository

import android.util.Log
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import xyz.sachil.essence.model.cache.CacheDatabase
import xyz.sachil.essence.model.net.NetClient
import xyz.sachil.essence.model.net.bean.Detail

class DetailRepository : KoinComponent {
    companion object {
        private const val TAG = "DetailRepository"
    }

    private val database: CacheDatabase by inject()
    private val netClient: NetClient by inject()

    suspend fun getDetail(id: String): Detail {
        var detail = database.detailDao().getDetail(id)
        if (detail == null) {
            detail = netClient.getDetail(id)
            database.detailDao().insertDetail(detail)
        }
        return detail
    }

}