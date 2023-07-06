package com.hbl.assetsmanager.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class NetworkUtils {

    companion object {

        @SuppressLint("NewApi")
        fun isInternetAvailable(): Unit {
//            (SurvayApplication.getCtx().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).run {
//                return this.getNetworkCapabilities(this.activeNetwork)?.hasCapability(
//                    NetworkCapabilities.NET_CAPABILITY_INTERNET
//                ) ?: false
//            }
        }

//        @RequiresApi(Build.VERSION_CODES.M)
//        fun isInternetAvailable(): Boolean {
//            (SurvayApplication.getCtx().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).run {
//                return this.getNetworkCapabilities(this.activeNetwork)?.hasCapability(
//                    NetworkCapabilities.NET_CAPABILITY_INTERNET
//                ) ?: false
//            }
//        }
    }
}