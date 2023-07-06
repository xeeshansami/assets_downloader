package com.hbl.assetsmanager.network.store

import android.app.Application
import com.hbl.assetsmanager.network.ResponseHandlers.callbacks.AssetsCallBack
import com.hbl.assetsmanager.network.ResponseHandlers.handler.AssetsBaseHR
import com.hbl.assetsmanager.network.apiInterface.APIInterface
import com.hbl.assetsmanager.network.enums.RetrofitEnums
import com.hbl.assetsmanager.network.models.request.AssetsReqest
import com.hbl.assetsmanager.network.retrofitBuilder.RetrofitBuilder
import com.hbl.assetsmanager.network.timeoutInterface.IOnConnectionTimeoutListener
import com.hbl.assetsmanager.utils.Config
import com.hbl.assetsmanager.utils.GlobalClass

open class NetworkStore : Application(), IOnConnectionTimeoutListener {

    override fun onConnectionTimeout() {}

    companion object {
        private val consumerStore: NetworkStore? = null

        //    APIInterface globalBaseUrl = RetrofitBuilder.INSTANCE.getRetrofitInstance(GlobalClass.applicationContext, RetrofitEnums.URL_HBL);
        val instance: NetworkStore?
            get() = consumerStore ?: NetworkStore()
    }

    //:TODO post getAssets
    fun getAssets(
        url: RetrofitEnums?,
        req: AssetsReqest?,
        callBack: AssetsCallBack
    ) {
        var privateInstanceRetrofit: APIInterface? =
            GlobalClass.applicationContext?.let {
                RetrofitBuilder.getRetrofitInstance(
                    it,
                    url!!,
                    Config.API_CONNECT_TIMEOUT
                )
            }
        privateInstanceRetrofit?.getAssets(req)!!.enqueue(AssetsBaseHR(callBack))
    }

    /* fun printDocs(
         url: RetrofitEnums?,
         printdoc: RequestBody,
         trackingid: RequestBody,
         cnic: RequestBody,
         mysisno: RequestBody,
         cifno: RequestBody,
         identifier: RequestBody,
         callback: PrintDocCallBack
     ) {
         var privateInstanceRetrofit: APIInterface? =
             GlobalClass.applicationContext?.let { RetrofitBuilder.getRetrofitInstance(it,  url!!, Config.API_CONNECT_TIMEOUT) }
         privateInstanceRetrofit?.printDocs(printdoc,trackingid,cnic,mysisno,cifno,identifier)
             ?.enqueue(PrintDocsHR(callback))
     }


     //:TODO getTrackingID
     fun getKonnectTrackingID(
         url: RetrofitEnums?,
         trackingIDRequest: TrackingIDRequest?,
         callback: KonnectTrackingIDCallBack
     ) {
         var privateInstanceRetrofit: APIInterface? =
             GlobalClass.applicationContext?.let { RetrofitBuilder.getRetrofitInstance(it,  url!!, Config.API_CONNECT_TIMEOUT) }
         privateInstanceRetrofit?.getKonnectTrackingID(trackingIDRequest?.brcode)?.enqueue(KonnectTrackingIDHR(callback))
     }*/


}