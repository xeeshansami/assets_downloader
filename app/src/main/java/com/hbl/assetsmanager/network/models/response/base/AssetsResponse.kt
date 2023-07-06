package com.hbl.assetsmanager.network.models.response

import com.google.gson.annotations.SerializedName


 class AssetsResponse{

    @SerializedName("status") var status=""
    @SerializedName("message") var message=""
    @SerializedName("data") var data =ArrayList<AssetsData>()
}