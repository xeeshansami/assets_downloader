package com.hbl.assetsmanager.network.ResponseHandlers.handler

import com.hbl.assetsmanager.network.ResponseHandlers.callbacks.AssetsCallBack
import com.hbl.assetsmanager.network.ResponseHandlers.callbacks.BoolCallBack
import com.hbl.assetsmanager.network.models.response.AssetsResponse
import com.hbl.assetsmanager.network.models.response.base.BaseResponse

class AssetsBaseHR(callBack: AssetsCallBack) : BaseRH<AssetsResponse>() {
    var callback: AssetsCallBack = callBack
    override fun onSuccess(response: AssetsResponse?) {
        response?.let { callback.AssetsSuccess(it) }
    }

    override fun onFailure(response: BaseResponse?) {
        response?.let { callback.AssetsFailure(it) }
    }
}