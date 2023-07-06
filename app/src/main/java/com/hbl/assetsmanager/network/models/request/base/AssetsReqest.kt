package com.hbl.assetsmanager.network.models.request

import com.google.gson.annotations.SerializedName
import com.hbl.assetsmanager.network.models.request.base.AssetData


 class AssetsReqest{
    @SerializedName("data") var data =  ArrayList<AssetData>()
}