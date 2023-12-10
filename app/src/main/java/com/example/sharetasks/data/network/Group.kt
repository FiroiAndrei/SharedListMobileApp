package com.example.sharetasks.data.network

import com.google.gson.annotations.SerializedName


data class Group(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("items")
    var items: List<String>? = null,
    @SerializedName("name")
    var name: String? = null
)