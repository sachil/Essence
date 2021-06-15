package xyz.sachil.essence.repository

import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import xyz.sachil.essence.model.cache.CacheDatabase
import xyz.sachil.essence.model.net.NetClient
import xyz.sachil.essence.model.net.bean.Type
import xyz.sachil.essence.util.Utils

@KoinApiExtension
class TypeRepository : KoinComponent {
    companion object {
        private const val TAG = "TypeRepository"
    }

    private val database: CacheDatabase by inject()
    private val netClient: NetClient by inject()

    suspend fun getTypes(category: Utils.Category): List<Type> {
        val typeDao = database.typeDao()
        var result = typeDao.getTypes(category.type)
        if (result.isEmpty()) {
            result = netClient.getTypes(category)
            result.forEach {
                it.category = category.type
            }
            typeDao.insertTypes(result)
        }
        return result
    }

}