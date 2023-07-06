package com.hbl.assetsmanager.network.models.response

import com.google.gson.annotations.SerializedName


data class AssetsData(
    @SerializedName("_id") var Id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("path") var path: String? = null,
    @SerializedName("updatedDate") var updatedDate: String? = null
)