package com.hbl.assetsmanager.network.models.response

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject


open class AssetsDataModel :RealmObject(){
    @SerializedName("_id")
    var Id=""
    @SerializedName("name")
    var name=""
    @SerializedName("path")
    var path=""
    @SerializedName("updatedDate")
    var updatedDate=""
}