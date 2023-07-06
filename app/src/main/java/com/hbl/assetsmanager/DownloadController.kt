package com.hbl.assetsmanager

//import com.badoualy.stepperindicator.BuildConfig
//import com.hbl.bot.BuildConfig
import android.content.Context

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.*
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.hbl.assetsmanager.network.models.response.AssetsData
import com.hbl.assetsmanager.realmDB.RealmController
import java.io.*
import java.net.HttpURLConnection
import java.net.URL


class DownloadController(
    private val context: Context,
    private var list: ArrayList<AssetsData>,
) {

    private var dl_progress: Int = 0
    private var fName = "";

    init {
//        this.fName = fileName
        var destination =
            context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString() + "/"
//        destination += fileName
        // execute this when the downloader must be fired
        val file = File(destination)
        if (file.exists()) {
            file.deleteRecursively()
        }
        if (file.exists()) file.delete()
        var downloadTask = DownloadTask(context);
        downloadTask.execute(list);
    }


    companion object {
        //        private const val FILE_NAME = "BOT_V1.apk"
        private const val FILE_BASE_PATH = "file://"
        private const val MIME_TYPE = "application/vnd.android.package-archive"
        private const val PROVIDER_PATH = ".provider"
        private const val APP_INSTALL_PATH = "\"application/vnd.android.package-archive\""
    }


    private class DownloadTask(private val context: Context) :
        AsyncTask<ArrayList<AssetsData>, Int?, String?>() {
        var alertDialogBuilder: AlertDialog.Builder? = null
        var progressBarFileCounts: ProgressBar? = null
        var progressBarDownload: ProgressBar? = null
        var alertDialog: AlertDialog? = null
        var percentageView: TextView? = null
        var messageBody: TextView? = null
        var messageBody2: TextView? = null
        var timeView: TextView? = null
        var totalFiles: TextView? = null
        var mbsView: TextView? = null

        fun downloaderAlert(context: Context, title: String, subtitle: String, msg: String) {
            alertDialogBuilder = AlertDialog.Builder((context as MainActivity))
            val li = LayoutInflater.from(context)
            val promptsView = li.inflate(R.layout.popup_downloader, null)
            alertDialog = alertDialogBuilder!!.create()
            alertDialog!!.setView(promptsView)
            alertDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alertDialog!!.setCancelable(false)
            alertDialog!!.show()
            val toolbar: Toolbar = promptsView.findViewById(R.id.toolbar)
            toolbar.title = "NEW UPDATE"
            toolbar.subtitle = title
            toolbar.setTitleTextColor(context.resources.getColor(R.color.colorWhite))
            toolbar.setSubtitleTextColor(context.resources.getColor(R.color.colorWhite))
            messageBody = promptsView.findViewById<TextView>(R.id.messageBody)
            timeView = promptsView.findViewById<TextView>(R.id.timeView)
            percentageView = promptsView.findViewById<TextView>(R.id.percentageView)
            mbsView = promptsView.findViewById<TextView>(R.id.mbsView)
            messageBody2 = promptsView.findViewById<TextView>(R.id.messageBody2)
            totalFiles = promptsView.findViewById<TextView>(R.id.totalFiles)
            progressBarFileCounts =
                promptsView.findViewById<ProgressBar>(R.id.progressBarFileCounts)
            progressBarDownload = promptsView.findViewById<ProgressBar>(R.id.progressBarDownload)
            progressBarFileCounts!!.isIndeterminate = true
            progressBarDownload!!.isIndeterminate = false
            var cancel_action = promptsView.findViewById<Button>(R.id.cancel_action)
            messageBody!!.text = subtitle
            messageBody2!!.text = msg
            cancel_action!!.setOnClickListener(View.OnClickListener {
                Toast.makeText(
                    context,
                    "Downloading Forcefully Cancelled: Logging out now...",
                    Toast.LENGTH_LONG
                ).show()
                Handler().postDelayed({
                    (context as MainActivity).finish()
                }, 1000)
                alertDialog!!.dismiss()
            })

            alertDialog!!.setOnDismissListener {

            }
        }

        init {
            // instantiate it within the onCreate method
            // instantiate it within the onCreate method
            (context as MainActivity).runOnUiThread {
                downloaderAlert(
                    context,
                    context.getString(R.string.title_file_download),
                    context.getString(R.string.downloading),
                    ""
                )
            }

        }

        fun fromCountToTimeByInterval(count: Int, interval: Int): String {
//            val minutes = count * interval / 1000 / 60
//            val seconds = count * interval / 1000 % 60
//            val milliSeconds = (count * interval % 1000) / 10

            val hours = count * interval / 3600
            val minutes = count * interval % 3600 / 60
            val seconds = count * interval % 60

            return String.format("%02d:%02d:%02d", hours, minutes, seconds)
//             String.format("%02d:%02d.%02d", minutes, seconds, milliSeconds)
        }

        private var mWakeLock: PowerManager.WakeLock? = null

        @RequiresApi(Build.VERSION_CODES.O)
        override fun doInBackground(vararg sUrl: ArrayList<AssetsData>?): String? {
            var filename = ""
            var fileDownloadedCounter = 0;
            var countFiles = sUrl[0]!!.size;
            var url: URL? = null
            var input: InputStream? = null
            var output: OutputStream? = null
            var connection: HttpURLConnection? = null
            try {
                while (countFiles != 0 && countFiles > 0) {
                    countFiles--;
                    var newUrl = ""
                    Log.i("CheckPositions", "start ${sUrl[0]!!.get(countFiles).path!!}")
                    totalFiles!!.setText("File(s): ${fileDownloadedCounter}/${sUrl[0]!!.size} ")
                    if (!sUrl[0]!!.get(countFiles).path!!.contains("https")) {
                        newUrl = sUrl[0]!!.get(countFiles).path!!.replace("http", "https");
                        url = URL(newUrl)
                    } else {
                        url = URL(sUrl[0]!!.get(countFiles).path!!)
                    }
                    connection = url.openConnection() as HttpURLConnection
                    connection.useCaches = false
                    connection.connect()
                    Log.i("APKURL", newUrl)
                    // expect HTTP 200 OK, so we don't mistakenly save error report
                    // instead of the file
                    if (connection.getResponseCode() !== HttpURLConnection.HTTP_OK) {
                        Log.i(
                            "APKURL", "Server returned HTTP " + connection.getResponseCode()
                                .toString() + " " + connection.getResponseMessage()
                        )
                        return "Server returned HTTP " + connection.getResponseCode()
                            .toString() + " " + connection.getResponseMessage()
                    }

                    // this will be useful to display download percentage
                    // might be -1: server did not report the length
                    val fileLength: Int = connection.getContentLength()

                    // download the file
                    input = connection.getInputStream()
                    var destination = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
                        .toString() + "/"
                    filename = sUrl[0]!!.get(countFiles).path!!.substring(
                        sUrl[0]!!.get(countFiles).path!!.lastIndexOf("/") + 1
                    )
                    destination += filename
                    output = FileOutputStream(destination)
                    val data = ByteArray(4096)
                    var total: Long = 0
                    var count: Int
                    val startTime = System.nanoTime()
                    val elapsedTime = System.nanoTime() - startTime
                    while (input.read(data).also { count = it } != -1) {
                        // allow canceling with back button
                        if (isCancelled()) {
                            input.close()
                            return null
                        }
                        total += count.toLong()
                        // publishing the progress....
                        if (fileLength > 0) // only if total length is known{
                        {
                            Log.i(
                                "Progress",
                                "In Progress $fileLength ${(total * 100 / fileLength).toInt()}"
                            )
                            publishProgress((total * 100 / fileLength).toInt())
                            if ((total * 100 / fileLength).toInt() == 100) {
                                fileDownloadedCounter++
                                totalFiles!!.setText("File(s): ${fileDownloadedCounter}/${sUrl[0]!!.size} ")
                                Log.i(
                                    "Progress",
                                    "Progress Completed $fileLength ${(total * 100 / fileLength).toInt()}"
                                )
//                                RealmController.getInstance().setAssets(sUrl[0]!!.get(countFiles),sUrl[0]!!.get(countFiles).name!!);
                            }
                        }
                       val kilobytes = (total / 1024)
                        val megabytes = (kilobytes / 1024)
                        val allTimeForDownloading: Long = elapsedTime * total / fileLength
                        var remainingTime = allTimeForDownloading - elapsedTime
                        var time = remainingTime * -1000
                        remainingTime = Math.sqrt(Math.pow(remainingTime.toDouble(), 2.0)).toLong()
                        Log.i("TimeSpeed", "$megabytes $kilobytes")
                        (context as MainActivity).runOnUiThread {
                            mbsView!!.text =
                                "File Size: " + megabytes.toString() + "/" + fileLength / (1024 * 1024) + " MB(s)"
                            Log.i(
                                "CheckPositions",
                                "File Size ${megabytes.toString()}  ${fileLength} / ${(1024 * 1024)}  MB(s)"
                            );
//                        timeView!!.text = convertSeconds(time.toInt()/1000)+
//                        timeView!!.setText(fromCountToTimeByInterval(remainingTime.toInt(),1)+" min(s) remaining");
                            timeView!!.setText(
                                "Estimated time left: " + fromCountToTimeByInterval(
                                    remainingTime.toInt(),
                                    1
                                )
                            );
                            Log.i(
                                "Downloading",
                                fromCountToTimeByInterval(remainingTime.toInt(), 1)
                            )
                        }
                        output.write(data, 0, count)
                    }

                }
            } catch (e: Exception) {
                return e.toString()
            } finally {
                try {
                    output?.close()
                    input?.close()
                } catch (ignored: IOException) {
                }
                connection?.disconnect()
                return null
            }

        }

        override fun onPreExecute() {
            super.onPreExecute()
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download

            val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
            mWakeLock = pm.newWakeLock(
                PowerManager.PARTIAL_WAKE_LOCK,
                javaClass.name
            )
            mWakeLock!!.acquire()

        }

        override fun onProgressUpdate(vararg progress: Int?) {
            super.onProgressUpdate(*progress)
            // if we get here, length is known, now set indeterminate to false
            progressBarDownload!!.isIndeterminate = false
            progressBarDownload!!.max = 100
            progress[0]?.let {
                progressBarDownload!!.progress = it
                percentageView!!.text = "$it%"
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        override fun onPostExecute(result: String?) {
            mWakeLock!!.release()
            alertDialog!!.dismiss()
            Log.i("CheckPositions", result.toString());
            if (result != null) {
                if (result!!.contains("Software caused connection abort")) {
                    Toast.makeText(
                        context.applicationContext,
                        "You have a network connection issue, please check your internet and try download again for update",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        context.applicationContext,
                        "Download error: $result",
                        Toast.LENGTH_LONG
                    ).show()
                }

                Log.i("DownloadError", result.toString())
//                Handler().postDelayed({
//                    Toast.makeText(
//                        context.applicationContext,
//                        "Logging out...",
//                        Toast.LENGTH_LONG
//                    ).show()
//                    (context as MainActivity).finish()
//                }, 3000)
            } else {
                Toast.makeText(
                    context,
                    "Apk File downloaded",
                    Toast.LENGTH_LONG
                ).show()
                var destination = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString() + "/"
                destination += result
                val uri = Uri.parse("$FILE_BASE_PATH$destination")
            }
        }

    }
}