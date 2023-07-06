package com.hbl.assetsmanager.network.ResponseHandlers.callbacks

import com.hbl.assetsmanager.network.models.response.base.*
import retrofit2.Callback

interface BoolCallBack {
    fun BoolSuccess(response: BaseResponse)
    fun BoolFailure(response: BaseResponse)
}