package uz.xsoft.paging3.data.model

import com.google.gson.annotations.SerializedName

sealed class ResponseData {
    data class ResponseSearch(

        @field:SerializedName("query")
        val query: String? = null,

        @field:SerializedName("limit")
        val limit: Int? = null,

        @field:SerializedName("page")
        val page: Int? = null,

        @field:SerializedName("items")
        val itemSearches: List<ItemSearch>
    )

    data class ItemSearch(

        @field:SerializedName("image")
        val image: String? = null,

        @field:SerializedName("share_url")
        val shareUrl: String? = null,

        @field:SerializedName("hide_image")
        val hideImage: Boolean? = null,

        @field:SerializedName("is_main")
        val isMain: Boolean? = null,

        @field:SerializedName("published_on")
        val publishedOn: Int? = null,

        @field:SerializedName("view_url")
        val viewUrl: String? = null,

        @field:SerializedName("id")
        val id: String? = null,

        @field:SerializedName("short_id")
        val shortId: String? = null,

        @field:SerializedName("title")
        val title: String? = null,

        @field:SerializedName("category")
        val category: String? = null,

        @field:SerializedName("views")
        val views: Int? = null
    )
}