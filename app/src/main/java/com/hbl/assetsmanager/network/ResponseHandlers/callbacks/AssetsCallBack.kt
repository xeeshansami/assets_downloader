package com.hbl.assetsmanager.network.ResponseHandlers.callbacks

import com.hbl.assetsmanager.network.models.response.AssetsResponse
import com.hbl.assetsmanager.network.models.response.base.*
import retrofit2.Callback

interface AssetsCallBack {
    fun AssetsSuccess(response: AssetsResponse)
    fun AssetsFailure(response: BaseResponse)
}