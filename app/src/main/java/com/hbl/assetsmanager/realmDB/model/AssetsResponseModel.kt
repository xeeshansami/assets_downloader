package com.hbl.assetsmanager.network.models.response

import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject


open class AssetsResponseModel:RealmObject() {
    @SerializedName("status")
    var status=""
    @SerializedName("message")
    var message=""
    @SerializedName("data")
    var data= RealmList<AssetsDataModel>()

}