package android.print

import android.os.CancellationSignal
import android.os.ParcelFileDescriptor
import android.util.Log
import java.io.File

class PrintPdf(private val printAttributes: PrintAttributes) {
    fun print(printAdapter: PrintDocumentAdapter, path: File, fileName: String) {
        printAdapter.onLayout(
            null,
            printAttributes,
            null,
            object : PrintDocumentAdapter.LayoutResultCallback() {
                override fun onLayoutFinished(info: PrintDocumentInfo, changed: Boolean) {
                    printAdapter.onWrite(
                        arrayOf(PageRange.ALL_PAGES),
                        getOutputFile(path, fileName),
                        CancellationSignal(),
                        object : PrintDocumentAdapter.WriteResultCallback() {
                            override fun onWriteFinished(pages: Array<PageRange>) {
                                super.onWriteFinished(pages)
                            }
                        })
                }
            },
            null
        )
    }

    private fun getOutputFile(path: File, fileName: String): ParcelFileDescriptor? {
        if (!path.exists()) {
            path.mkdirs()
        }
        val file = File(path, fileName)
        try {
            file.createNewFile()
            return ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_WRITE)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to open ParcelFileDescriptor", e)
        }
        return null
    }

    companion object {
        private val TAG = PrintPdf::class.java.simpleName
    }
}