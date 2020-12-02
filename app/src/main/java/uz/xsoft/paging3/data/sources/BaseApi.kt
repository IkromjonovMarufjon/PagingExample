package uz.xsoft.paging3.data.sources

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import uz.xsoft.paging3.data.model.ResponseData

interface BaseApi {

    /**
     *  gets search results.
     */
    @GET("post/search")
    suspend fun searchResult(
        @Query("query") query: String,
        @Query("l") lang: String,
        @Query("page") page: Int,
    ): Response<ResponseData.ResponseSearch>

}