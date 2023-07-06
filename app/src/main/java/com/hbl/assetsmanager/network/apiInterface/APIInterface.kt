package com.hbl.assetsmanager.network.apiInterface


import com.hbl.assetsmanager.network.models.request.AssetsReqest
import com.hbl.assetsmanager.network.models.request.base.*
import com.hbl.assetsmanager.network.models.response.AssetsResponse
import com.hbl.assetsmanager.network.models.response.base.*
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface APIInterface {
    @POST("m/assets")
    fun getAssets(@Body req: AssetsReqest?): Call<AssetsResponse>
//
//    @Multipart
//    @POST("m/documentPrint")
//    fun printDocs(
//        @Part("printdoc\"; filename=\"hbl.pdf\" ") file: RequestBody?,
//        @Part("trackingid") trackingid: RequestBody?,
//        @Part("cnic") cnic: RequestBody?,
//        @Part("mysisno") mysisno: RequestBody?,
//        @Part("cifno") cifno: RequestBody?,
//        @Part("identifier") identifier: RequestBody?
//    ): Call<PrintDocsResponse>

//    @GET("tracking/{branchCode}")
//    fun getKonnectTrackingID(@Path("branchCode") branchCode: String?): Call<KonnectTrackingIDResponse>


    companion object {
        const val HEADER_TAG = "@"
        const val HEADER_POSTFIX = ": "
        const val HEADER_TAG_PUBLIC = "public"
    }
}