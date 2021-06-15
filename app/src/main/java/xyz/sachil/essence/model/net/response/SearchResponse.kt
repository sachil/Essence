package xyz.sachil.essence.model.net.response

import com.google.gson.annotations.SerializedName
import xyz.sachil.essence.model.net.bean.SearchResult

class SearchResponse(@SerializedName("count") val count: Int) : PageCountResponse<SearchResult>()