package com.hbl.assetsmanager

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.webkit.*
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hbl.assetsmanager.network.ResponseHandlers.callbacks.AssetsCallBack
import com.hbl.assetsmanager.network.enums.RetrofitEnums
import com.hbl.assetsmanager.network.models.request.AssetsReqest
import com.hbl.assetsmanager.network.models.request.base.AssetData
import com.hbl.assetsmanager.network.models.response.AssetsDataModel
import com.hbl.assetsmanager.network.models.response.AssetsResponse
import com.hbl.assetsmanager.network.models.response.base.BaseResponse
import com.hbl.assetsmanager.network.store.NetworkStore
import com.hbl.assetsmanager.realmDB.RealmController
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import com.shockwave.pdfium.PdfDocument
import java.io.*
import java.util.*
import kotlin.collections.ArrayList


/**
 * A simple [Fragment] subclass.
 */
class HawPdfViewUploadFragment44 : Fragment(), OnClickListener, OnPageChangeListener,
    OnLoadCompleteListener {
    var count = 5
    var count1 = 0
    var TAG = "Testing"
    var permissions = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    var next: Button? = null
    var scrollBtn: Button? = null
    var pdftrackingID: TextView? = null
    var stopUrl = ""
    var myView: View? = null
    var pdfView: PDFView? = null
    var mydownload: Long = 0
    var ivForm: WebView? = null
    var btNext: Button? = null
    var trackingID = "2010210786-61" //Saving
    var directory = ""
    var progressBar: ProgressBar? = null

    //    var trackingID="0110210024-7003" //CIF
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_cifstep13, container, false)
        return myView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ivForm = view.findViewById(R.id.ivForm)
        btNext = view.findViewById(R.id.btNext)
        progressBar = view.findViewById(R.id.progressBar)
        btNext!!.setOnClickListener(this)
        permission()
    }


    override fun onResume() {
        super.onResume()
        directory = (activity as MainActivity).dataDir.path
        webview(
            "file://$directory/web.html"
        )
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
        }
    }


    @SuppressLint("SetJavaScriptEnabled", "JavascriptInterface", "WifiManagerLeak")
    private fun webview(html: String) {
        ivForm!!.settings.javaScriptEnabled = true;
        ivForm!!.getSettings().builtInZoomControls = true;
        ivForm!!.getSettings().displayZoomControls = true;
        ivForm!!.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY;
        ivForm!!.settings.pluginState = WebSettings.PluginState.ON;
        ivForm!!.settings.allowFileAccess = true;
        ivForm!!.settings.setAllowContentAccess(true);
        ivForm!!.settings.setDomStorageEnabled(true);
        ivForm!!.settings.setUseWideViewPort(true);
        ivForm!!.settings.allowFileAccess = true
        ivForm!!.settings.setLoadWithOverviewMode(true);
        ivForm!!.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, progress: Int) {
                if (progressBar != null) {
                    progressBar!!.progress = progress
                }
            }
        }
        ivForm!!.webViewClient = object : WebViewClient() {
//                override fun onReceivedSslError(
//                    view: WebView?,
//                    handler: SslErrorHandler?,
//                    error: SslError?,
//                ) {
//                    super.onReceivedSslError(view, handler, error)
//                    handler!!.proceed();
//                }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                Handler().postDelayed({
                    progressBar!!.visibility = View.GONE
                }, 5000)

            }


            override fun onLoadResource(view: WebView?, url: String) {
                super.onLoadResource(view, url);
                Log.i("URLS", url.toString())

            }


            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                progressBar!!.visibility = View.VISIBLE;
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?,
            ) {
                super.onReceivedError(view, request, error)
                Log.i("WebviewError", error!!.description.toString())
            }
        }

        ivForm!!.loadUrl(html)
//        delPDF(trackingID, "pdf")

    }


    private fun viewPdfAndNext(file: File) {
        var dialogBuilder = AlertDialog.Builder(activity as MainActivity)
        val layoutView: View = layoutInflater.inflate(R.layout.pdf_viewer_popup, null)
        dialogBuilder.setView(layoutView)
        var alertDialog = dialogBuilder.create()
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.setCancelable(false)
        alertDialog.show()
        var cancel = layoutView.findViewById<Button>(R.id.cancelBtn)
        next = layoutView.findViewById<Button>(R.id.nextBtn)
        pdftrackingID = layoutView.findViewById<TextView>(R.id.pdftrackingID)
        scrollBtn = layoutView.findViewById<Button>(R.id.scrollBtn)
        pdfView =
            layoutView.findViewById<PDFView>(R.id.pdfView)
        var progressBar2 = layoutView.findViewById<ProgressBar>(R.id.progressBar2)
        if (file.exists()) {
            pdfView!!.maxZoom = 100f
            pdfView!!.fromFile(file) //.pages(0, 2, 1, 3, 3, 3) // all pages are displayed by default
                .enableSwipe(true)
                .enableDoubletap(true)
                .defaultPage(0)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .password(null)
                .scrollHandle(DefaultScrollHandle(activity))
                .load()
        }

        next!!.setOnClickListener(View.OnClickListener {
            alertDialog.dismiss()

        })
        scrollBtn!!.setOnClickListener(View.OnClickListener {
            pdfView!!.jumpTo(pdfView!!.pageCount - 1)
            scrollBtn!!.visibility = GONE

        })
        cancel.setOnClickListener(View.OnClickListener {
            alertDialog.dismiss()

        })
        alertDialog.show()

    }


    fun permission() {
        Permissions.check(
            activity as MainActivity,
            permissions,
            null,
            null,
            object : PermissionHandler() {
                override fun onGranted() { // do your task.
                    checkAssets()

//                    downloadHtml()
                }

                @SuppressLint("WrongConstant")
                override fun onDenied(
                    context: Context,
                    deniedPermissions: ArrayList<String>,
                ) {
                    Toast.makeText(
                        activity as MainActivity,
                        "Permission denied, please enabled the permissions from setting",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    fun checkAssets() {
        var assets = RealmController.getInstance().assets
        var req = AssetsReqest()
        if (assets.size != 0) {
            val json = Gson().toJson(assets)
            val listType = object : TypeToken<ArrayList<AssetData>>() {}.type
            val dta = Gson().fromJson<ArrayList<AssetData>>(json, listType)
            req.data=dta
            callAssets(req)
        } else {
            req.data = ArrayList<AssetData>()
            callAssets(req)
        }
    }

    fun callAssets(req: AssetsReqest) {
        NetworkStore.instance?.getAssets(
            RetrofitEnums.URL_HBL,
            req, object : AssetsCallBack {
                override fun AssetsSuccess(response: AssetsResponse) {
                    for (x in response.data) {
                        Log.i("AssetsCheck", "${x.name}, ${x.updatedDate}, ${x.path}")
                    }
                    if(response.data.size!=0) {
                        DownloadController(requireContext(), response.data)
                    }

                }

                override fun AssetsFailure(response: BaseResponse) {

                }
            })
    }

    override fun onPageChanged(page: Int, pageCount: Int) {
        pdftrackingID!!.text = "Page: ${page + 1} - TrackingID: $trackingID"
        if (page == pageCount - 1) {
            scrollBtn!!.visibility = View.GONE
        } else {
            scrollBtn!!.visibility = View.VISIBLE
        }

    }

    override fun loadComplete(nbPages: Int) {
        val meta: PdfDocument.Meta = pdfView!!.getDocumentMeta()
        printBookmarksTree(pdfView!!.getTableOfContents(), "-")
        Log.i("PageCount", nbPages.toString());

    }

    fun printBookmarksTree(tree: List<PdfDocument.Bookmark>, sep: String) {
        for (b in tree) {
            Log.e("PDFVIEWER", String.format("%s %s, p %d", sep, b.title, b.pageIdx))
            if (b.hasChildren()) {
                printBookmarksTree(b.children, "$sep-")
            }
        }
    }


    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager: ConnectivityManager =
            (activity as MainActivity).getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo()!!
            .isConnected()
    }

    fun isInternetAvailable(): Boolean {
        val cm =
            (activity as MainActivity).getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo: NetworkInfo? = cm.activeNetworkInfo
        //should check null because in airplane mode it will be null
        //should check null because in airplane mode it will be null
        val nc: NetworkCapabilities? = cm.getNetworkCapabilities(cm.activeNetwork)
        val downSpeed: Int = nc!!.linkDownstreamBandwidthKbps
        val upSpeed: Int = nc!!.linkUpstreamBandwidthKbps
//        Log.i("NetworkSpeed","DownSpeed $downSpeed, upSpeed $upSpeed")
        return downSpeed <= 0.5
    }

}
