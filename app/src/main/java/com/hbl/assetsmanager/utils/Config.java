package com.hbl.assetsmanager.utils;

import okhttp3.logging.HttpLoggingInterceptor;

public class Config {
    public static final String BASE_URL_MAP = "";
    public static final String LIVE = "https://dob.hbl.com:7000/";
    public static final String RP_TEST = "https://rp.zeuscloudconnect.com:9131/";
    public static final String RP_TEST_BASE = "dapi/api/";
    public static final String RP_TEST_BASE2 = "dapi/api/v2/";
    public static final String LIVE_BASE1 = "api_m/api/";
    public static final String LIVE_BASE2 = "api_m/api/v2/";
    public static final String HOSTPOT = "http://10.9.167.204:9131/";
    public static final String RP_KONNECT_TEST_BASE = "kapi/api/k/";
    public static final String KONNECT_BASE = "api_m/api/v2/k/";

//    public static final String BASE_URL_HBL = LIVE + LIVE_BASE2; // live BOT
//    public static final String BASE_URL_HBL_KONNECT = LIVE + KONNECT_BASE; //LIVE KONNECT

    public static final String BASE_URL_HBL = RP_TEST+RP_TEST_BASE2; //UAT BOT
    public static final String BASE_URL_HBL_KONNECT = RP_TEST + RP_KONNECT_TEST_BASE; //UAT KONNECT


    //public static final String BASE_URL_HBL = "http://13.76.231.121:9130/mapi/api/"; //live
//    public static final String BASE_URL_HBL = "http://13.76.231.121:9131/dapi/api/"; //dev reverse proxy
//    public static final String BASE_URL_HBL = RP_TEST+RP_TEST_BASE; //dev reverse proxy
//    public static final String BASE_URL_HBL = "http://172.18.7.44:9131/dapi/api/"; //remote
//    public static final String BASE_URL_HBL = "http://10.6.226.226:7000/api/"; //saqib
//    public static final String BASE_URL_HBL = HOSTPOT+"api/v2/"; //husnain
//    public static final String BASE_URL_HBL = "https://52.163.117.55:9131/dapi/api/"; // new revers proxy
//    public static final String BASE_URL_HBL =   "http://10.9.167.204:9133/pdf-print/"; //printpdf
//    public static final String URL_PRINT_PDF = "http://13.76.231.121:9132/pdf-print/"; //saqib
//    public static final String BASE_URL_HBL = "http://10.200.164.124:9145/api/"; //saqib
//    public static final String BASE_URL_HBL = LIVE + LIVE_BASE2; //live BOT
//    public static final String BASE_URL_HBL = "https://13.76.231.121:9132/iapi/api/"; //info sec

    public static final long timeoutPdf = 240;
    public static final long timeoutNormal = 60;
    public static final long API_CONNECT_TIMEOUT = timeoutNormal;
    public static final long API_PDF_CONNECT_TIME_OUT = timeoutNormal;
    //    public static final long API_READ_TIMEOUT = timeoutNormal;
//    public static final long API_WRITE_TIMEOUT = timeoutNormal;
    public static final HttpLoggingInterceptor.Level LOG_LEVEL_API = HttpLoggingInterceptor.Level.BODY;
}
