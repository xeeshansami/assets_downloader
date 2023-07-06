package com.hbl.assetsmanager.network.enums

import com.hbl.assetsmanager.utils.Config


enum class RetrofitEnums(var url: String) {
    URL_MAPS(Config.BASE_URL_MAP),
    URL_HBL(
        Config.BASE_URL_HBL
    ),
    URL_KONNECT(Config.BASE_URL_MAP);
}