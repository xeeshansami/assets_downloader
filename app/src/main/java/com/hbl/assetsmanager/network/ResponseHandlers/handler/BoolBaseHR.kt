package com.hbl.assetsmanager.network.ResponseHandlers.handler

import com.hbl.assetsmanager.network.ResponseHandlers.callbacks.BoolCallBack
import com.hbl.assetsmanager.network.models.response.base.BaseResponse

class BoolBaseHR(callBack: BoolCallBack) : BaseRH<BaseResponse>() {
    var callback: BoolCallBack = callBack
    override fun onSuccess(response: BaseResponse?) {
        response?.let { callback.BoolSuccess(it) }
    }

    override fun onFailure(response: BaseResponse?) {
        response?.let { callback.BoolFailure(it) }
    }
}