package com.hbl.assetsmanager.network.models.request.base

import com.google.gson.annotations.SerializedName


data class AssetData(
    @SerializedName("name") var name: String? = null,
    @SerializedName("updatedDate") var updatedDate: String? = null,
    @SerializedName("_id") var Id: String? = "",
    @SerializedName("path") var path: String? = "",
)