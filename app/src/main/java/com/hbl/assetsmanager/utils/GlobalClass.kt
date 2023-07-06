package com.hbl.assetsmanager.utils

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.provider.Settings
import android.text.*
import android.text.method.DigitsKeyListener
import android.text.style.ForegroundColorSpan
import android.util.Base64
import android.util.Log
import android.util.Patterns
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.hbl.assetsmanager.R
import com.hbl.assetsmanager.network.store.NetworkStore
import io.realm.Realm
import io.realm.RealmConfiguration
import java.io.File
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

//import android.app.NotificationChannel;
//import android.app.NotificationManager;
class GlobalClass : NetworkStore() {

    val currentDate: String
        get() {
            val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            return sdf.format(Date())
        }

    val currentYear: String
        get() {
            val sdf = SimpleDateFormat("yyyy", Locale.getDefault())
            return sdf.format(Date())
        }


    override fun onCreate() {
        super.onCreate()
        Companion.applicationContext = applicationContext
        init()
    }
    fun init(){
        Realm.init(this)
        val config = RealmConfiguration.Builder().name("HBL_BOT.realm").schemaVersion(1).deleteRealmIfMigrationNeeded().build()
        Realm.setDefaultConfiguration(config)
    }

    fun fileDelete(path: String?) {
        try {
            val fileDir = File(path)
            if (fileDir.exists()) {
                fileDir.delete()
            }
        } catch (ex: Exception) {
            Log.i(
                Constants.TAG, """
     File not file 
     ${ex.message}
     """.trimIndent()
            )
        }
    }

    fun formateChange(time: String): String? {
        return if (time.matches("\\d{4}-\\d{2}-\\d{2}".toRegex())) {
            val inputPattern = "yyyy-MM-dd"
            val outputPattern = "dd-MM-yyyy"
            val inputFormat = SimpleDateFormat(inputPattern)
            val outputFormat = SimpleDateFormat(outputPattern)
            var date: Date? = null
            var str: String? = null
            try {
                date = inputFormat.parse(time)
                str = outputFormat.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            str
        } else {
            time
        }
    }

    val logoutData: Unit
        get() {
//            val intent = Intent(Companion.applicationContext, SplashScreen::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            startActivity(intent)
//            hideLoader()
        }

    fun setEnabled(widget: ConstraintLayout, isEnabled: Boolean) {
        if (isEnabled) {
            widget.isEnabled = true
            widget.alpha = 1f
        } else {
            widget.isEnabled = false
            widget.alpha = 0.5f
        }
    }

    fun setEnabled(widget: RelativeLayout, isEnabled: Boolean) {
        if (isEnabled) {
            widget.isEnabled = true
            widget.alpha = 1f
        } else {
            widget.isEnabled = false
            widget.alpha = 0.5f
        }
    }

    fun setEnabled(widget: LinearLayout, isEnabled: Boolean) {
        if (isEnabled) {
            widget.isEnabled = true
            widget.alpha = 1f
        } else {
            widget.isEnabled = false
            widget.alpha = 0.5f
        }
    }

    fun setEnabled(widget: CoordinatorLayout, isEnabled: Boolean) {
        if (isEnabled) {
            widget.isEnabled = true
            widget.alpha = 1f
        } else {
            widget.isEnabled = false
            widget.alpha = 0.5f
        }
    }

    fun setEnabled(widget: EditText, isEnabled: Boolean) {
        if (isEnabled) {
            widget.isEnabled = true
            widget.alpha = 1f
        } else {
            widget.isEnabled = false
            widget.alpha = 0.5f
        }
    }

    fun setEnabled(widget: Spinner, isEnabled: Boolean) {
        if (isEnabled) {
            widget.isEnabled = true
            widget.alpha = 1f
        } else {
            widget.isEnabled = false
            widget.alpha = 0.5f
        }
    }

    fun setEnabled(widget: TextView, isEnabled: Boolean) {
        if (isEnabled) {
            widget.isEnabled = true
            widget.alpha = 1f
        } else {
            widget.isEnabled = false
            widget.alpha = 0.5f
        }
    }

    fun setEnabled(widget: ImageView, isEnabled: Boolean) {
        if (isEnabled) {
            widget.isEnabled = true
            widget.alpha = 1f
        } else {
            widget.isEnabled = false
            widget.alpha = 0.5f
        }
    }

    fun setEnabled(widget: Button, isEnabled: Boolean) {
        if (isEnabled) {
            widget.isEnabled = true
            widget.alpha = 1f
        } else {
            widget.isEnabled = false
            widget.alpha = 0.5f
        }
    }

    fun setEnabled(widget: ImageButton, isEnabled: Boolean) {
        if (isEnabled) {
            widget.isEnabled = true
            widget.alpha = 1f
        } else {
            widget.isEnabled = false
            widget.alpha = 0.5f
        }
    }

    fun setEnabled(widget: RadioButton, isEnabled: Boolean) {
        if (isEnabled) {
            widget.isEnabled = true
            widget.alpha = 1f
        } else {
            widget.isEnabled = false
            widget.alpha = 0.5f
        }
    }

    fun setEnabled(widget: RecyclerView, isEnabled: Boolean) {
        if (isEnabled) {
            widget.isEnabled = true
            widget.alpha = 1f
        } else {
            widget.isEnabled = false
            widget.alpha = 0.5f
        }
    }

    fun setEnabled(widget: AppCompatImageButton, isEnabled: Boolean) {
        if (isEnabled) {
            widget.isEnabled = true
            widget.alpha = 1f
        } else {
            widget.isEnabled = false
            widget.alpha = 0.5f
        }
    }

    fun setEnabled(widget: AppCompatButton, isEnabled: Boolean) {
        if (isEnabled) {
            widget.isEnabled = true
            widget.alpha = 1f
        } else {
            widget.isEnabled = false
            widget.alpha = 0.5f
        }
    }

    fun edittextTypeCount(editText: EditText, count: Int, type: Int) {
        if (type == Constants.INPUT_TYPE_NUMBER) {
            editText.inputType =
                InputType.TYPE_CLASS_NUMBER or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
            val FilterArray = arrayOfNulls<InputFilter>(1)
            FilterArray[0] = InputFilter.LengthFilter(count)
            editText.filters = FilterArray
            editText.keyListener =
                DigitsKeyListener.getInstance(
                    Companion.applicationContext!!.resources.getString(
                        R.string.numericKeys
                    )
                )
        } else if (type == Constants.INPUT_TYPE_ALPHANUMERIC) {
            editText.inputType = InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS
            editText.filters = arrayOf(
                alphaNumericFilterEdittext,
                InputFilter.LengthFilter(count)
            )
        } else if (type == Constants.INPUT_TYPE_ALPHA) {
            editText.keyListener =
                DigitsKeyListener.getInstance(
                    Companion.applicationContext!!.resources.getString(
                        R.string.alphaKeys
                    )
                )
            editText.inputType = InputType.TYPE_CLASS_TEXT
            val FilterArray = arrayOfNulls<InputFilter>(1)
            FilterArray[0] = InputFilter.LengthFilter(count)
            editText.filters = FilterArray
        } else if (type == Constants.INPUT_TYPE_ALPHA_BLOCK) {
            editText.inputType = InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS
            editText.filters = arrayOf(
                alphaFilterEdittext,
                InputFilter.LengthFilter(count)
            )
        } else if (type == Constants.INPUT_TYPE_ALPHA_BLOCK_LASTNAME) {
            editText.inputType = InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS
            editText.filters = arrayOf(
                alphaFilterEdittextLastName,
                InputFilter.LengthFilter(count)
            )
        } else if (type == 4) {
            editText.inputType = InputType.TYPE_CLASS_TEXT
            editText.filters = arrayOf(
                customEmailAlphaFilters,
                InputFilter.LengthFilter(count)
            )
        } else if (type == Constants.INPUT_TYPE_CUSTOM_EMAIL_ALPHA) {
            editText.keyListener =
                DigitsKeyListener.getInstance(
                    Companion.applicationContext!!.resources.getString(
                        R.string.emailKeys
                    )
                )
            editText.inputType = InputType.TYPE_CLASS_TEXT
            val FilterArray = arrayOfNulls<InputFilter>(1)
            FilterArray[0] = InputFilter.LengthFilter(count)
            editText.filters = FilterArray
        } else if (type == Constants.INPUT_TYPE_CUSTOM_ADDRESS_ALPHANUMERIC) {
            /*  editText.setInputType(InputType.TYPE_CLASS_TEXT);
            InputFilter[] FilterArray = new InputFilter[1];
            FilterArray[0] = new InputFilter.LengthFilter(count);
            editText.setFilters(FilterArray);*/
            editText.inputType = InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS
            editText.filters = arrayOf(
                alphaNumericAddressFilterSpaceEdittext,
                InputFilter.LengthFilter(count)
            )
        } else if (type == Constants.INPUT_TYPE_CUSTOM_ALPHANUMERIC) {
            editText.inputType = InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS
            editText.filters = arrayOf(
                alphaNumericFilterSpaceEdittext,
                InputFilter.LengthFilter(count)
            )
        }
    }

    class AlphaNumericInputFilter : InputFilter {
        override fun filter(
            source: CharSequence, start: Int, end: Int,
            dest: Spanned, dstart: Int, dend: Int,
        ): CharSequence {

            // Only keep characters that are alphanumeric
            val builder = StringBuilder()
            for (i in start until end) {
                val c = source[i]
                if (Character.isLetterOrDigit(c)) {
                    builder.append(c)
                }
            }

            // If all characters are valid, return null, otherwise only return the filtered characters
            val allCharactersValid = builder.length == end - start
            return if (allCharactersValid) null.toString() else builder.toString()
        }
    }

    fun onKeyboardEdittext(editText: EditText) {
        editText.requestFocus()
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED)
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }

    var numericFilterEdittext =
        label@ InputFilter { source: CharSequence, start: Int, end: Int, dest: Spanned?, dstart: Int, dend: Int ->
            var i = start
            while (i < end) {
                if (!Pattern.compile("[1234567890B]*").matcher(source[i].toString()).matches()) {
                    return@InputFilter ""
                }
                ++i
            }
            null
        }
    var numericDashFilterEdittext =
        label1@ InputFilter { source: CharSequence, start: Int, end: Int, dest: Spanned?, dstart: Int, dend: Int ->
            var i = start
            while (i < end) {
                if (!Pattern.compile("[1234567890-]*").matcher(source[i].toString()).matches()) {
                    return@InputFilter ""
                }
                ++i
            }
            null
        }
    var alphaNumericFilterEdittext =
        label@ InputFilter { src: CharSequence, start: Int, end: Int, dest: Spanned?, dstart: Int, dend: Int ->
            var i = start
            while (i < end) {
                if (!Pattern.compile("[A-Z0-9]").matcher(src[i].toString()).matches()) {
                    return@InputFilter ""
                }
                ++i
            }
            null
        }
    var alphaNumericFilterSpaceEdittext =
        label@ InputFilter { src: CharSequence, start: Int, end: Int, dest: Spanned?, dstart: Int, dend: Int ->
            var i = start
            while (i < end) {
                if (!Pattern.compile("[A-Z0-9 ]").matcher(src[i].toString()).matches()) {
                    return@InputFilter ""
                }
                ++i
            }
            null
        }
    var alphaNumericAddressFilterSpaceEdittext =
        label@ InputFilter { src: CharSequence, start: Int, end: Int, dest: Spanned?, dstart: Int, dend: Int ->
            var i = start
            while (i < end) {
                if (!Pattern.compile("[A-Z 0-9!#$%&(){|}~:;<=>?@*+,./^_`\\'\\\" \\t\\r\\n\\f-]+")
                        .matcher(
                            src[i].toString()
                        ).matches()
                ) {
                    return@InputFilter ""
                }
                ++i
            }
            null
        }
    var alphaFilterEdittext =
        label@ InputFilter { src: CharSequence, start: Int, end: Int, dest: Spanned?, dstart: Int, dend: Int ->
            var i = start
            while (i < end) {
                if (!Pattern.compile("[A-Z ]").matcher(src[i].toString()).matches()) {
                    return@InputFilter ""
                }
                ++i
            }
            null
        }
    var alphaFilterEdittextLastName =
        label@ InputFilter { src: CharSequence, start: Int, end: Int, dest: Spanned?, dstart: Int, dend: Int ->
            var i = start
            while (i < end) {
                if (!Pattern.compile("[A-Z_ ]").matcher(src[i].toString()).matches()) {
                    return@InputFilter ""
                }
                ++i
            }
            null
        }
    var customAlphaFilters =
        label@ InputFilter { source: CharSequence, start: Int, end: Int, dest: Spanned?, dstart: Int, dend: Int ->
            var i = start
            while (i < end) {
                if (!Pattern.compile("[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz._ ]*")
                        .matcher(
                            source[i].toString()
                        ).matches()
                ) {
                    return@InputFilter ""
                }
                ++i
            }
            null
        }
    var customAlphaFiltersblock =
        label@ InputFilter { source: CharSequence, start: Int, end: Int, dest: Spanned?, dstart: Int, dend: Int ->
            var i = start
            while (i < end) {
                if (!Pattern.compile("[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz 0123456789~`@#$%^&/()_+='.>,<]")
                        .matcher(
                            source[i].toString()
                        ).matches()
                ) {
                    return@InputFilter ""
                }
                ++i
            }
            null
        }
    var customEmailAlphaFilters =
        label@ InputFilter { source: CharSequence, start: Int, end: Int, dest: Spanned?, dstart: Int, dend: Int ->
            var i = start
            while (i < end) {
                if (Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\\\.+[a-z]+]*")
                        .matcher(source[i].toString()).matches()
                ) {
                    return@InputFilter ""
                }
                ++i
            }
            null
        }
    var customAddressAlphaFilters =
        label@ InputFilter { source: CharSequence, start: Int, end: Int, dest: Spanned?, dstart: Int, dend: Int ->
            var i = start
            while (i < end) {
                if (!Pattern.compile("[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz 0123456789~`@#$%^&/()_+='.>,<]*")
                        .matcher(
                            source[i].toString()
                        ).matches()
                ) {
                    return@InputFilter ""
                }
                ++i
            }
            null
        }


    fun isValidNumber(number: String): Boolean {
        val getNumber = number.substring(0, 1)
        return getNumber === "03"
    }

    fun showLoader(context: Context?) {
        /*  mProgressDialog = new TransparentProgressDialog(context);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();*/
    }

//    fun showDialog(context: Context?) {
//        progressDialog = getProgressDialogInstance(context)
//        progressDialog!!.setCancelable(false)
//        progressDialog!!.show()
//    }
//
//    fun hideLoader() {
//        if (progressDialog != null && progressDialog!!.isShowing) {
//            progressDialog!!.cancel()
//            progressDialog = null
//        }
//
//    }

    fun getAndroidVersion(sdk: Int): String? {
        return when (sdk) {
            1 -> "(Android 1.0)"
            2 -> "Petit Four " + "(Android 1.1)"
            3 -> "Cupcake " + "(Android 1.5)"
            4 -> "Donut " + "(Android 1.6)"
            5 -> "Eclair " + "(Android 2.0)"
            6 -> "Eclair " + "(Android 2.0.1)"
            7 -> "Eclair " + "(Android 2.1)"
            8 -> "Froyo " + "(Android 2.2)"
            9 -> "Gingerbread " + "(Android 2.3)"
            10 -> "Gingerbread " + "(Android 2.3.3)"
            11 -> "Honeycomb " + "(Android 3.0)"
            12 -> "Honeycomb " + "(Android 3.1)"
            13 -> "Honeycomb " + "(Android 3.2)"
            14 -> "Ice Cream Sandwich " + "(Android 4.0)"
            15 -> "Ice Cream Sandwich " + "(Android 4.0.3)"
            16 -> "Jelly Bean " + "(Android 4.1)"
            17 -> "Jelly Bean " + "(Android 4.2)"
            18 -> "Jelly Bean " + "(Android 4.3)"
            19 -> "KitKat " + "(Android 4.4)"
            20 -> "KitKat Watch " + "(Android 4.4)"
            21 -> "Lollipop " + "(Android 5.0)"
            22 -> "Lollipop " + "(Android 5.1)"
            23 -> "Marshmallow " + "(Android 6.0)"
            24 -> "Nougat " + "(Android 7.0)"
            25 -> "Nougat " + "(Android 7.1.1)"
            26 -> "Oreo " + "(Android 8.0)"
            27 -> "Oreo " + "(Android 8.1)"
            28 -> "Pie " + "(Android 9.0)"
            29 -> "Q " + "(Android 10.0)"
            30 -> "R " + "(Android 11.0)"
            31 -> "S " + "(Android 12.0)"
            else -> ""
        }
    }

    fun dismissDialog() {
//        progressDialog!!.dismiss()
    }

    companion object {
//        fun getProgressDialogInstance(context: Context?): TransparentProgressDialog? {
//            if (progressDialog == null) progressDialog = TransparentProgressDialog(
//                context!!
//            )
//            return progressDialog
//        }

        fun deviceID(): String {
            return Settings.Secure.getString(
                applicationContext!!.contentResolver,
                Settings.Secure.ANDROID_ID
            )
        }


        @JvmField
        var applicationContext: Context? = null


        //    TransparentProgressDialog progressDialog;
//        private var progressDialog: TransparentProgressDialog? = null


        @JvmStatic
        fun getCurrentTimeAndDate(format: String?): String {
            val sdf = SimpleDateFormat(format, Locale.getDefault())
            return sdf.format(Date())
        }

        fun getDate(milliSeconds: Long, dateFormat: String?): String {
            // Create a DateFormatter object for displaying date in specified format.
            val formatter = SimpleDateFormat(dateFormat)

            // Create a calendar object that will convert the date and time value in milliseconds to date.
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = milliSeconds
            return formatter.format(calendar.time)
        }

        fun delFile(path: String) {
            val fdelete = File(path)
            if (fdelete.exists()) {
                if (fdelete.delete()) {
                    println("file Deleted :$path")
                } else {
                    println("file not Deleted :$path")
                }
            }
        }

        fun clearText(textView: TextView) {
            textView.text = ""
        }

        fun clearText(editText: EditText) {
            editText.setText("")
        }


        @JvmStatic
        fun isValidEmail(target: String?): Boolean {
            return if (target == null) {
                false
            } else {
                //android Regex to check the email address Validation
                Patterns.EMAIL_ADDRESS.matcher(target).matches()
            }
        }


        fun decodeBase64AndSetImage(img: String?): Bitmap? {
            return if (!TextUtils.isEmpty(img)) {
                val decodedString =
                    Base64.decode(img, Base64.DEFAULT)
                BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            } else {
                null
            }
        }


        @JvmStatic
        fun textColor(text: String?, startIndex: Int, lastIndex: Int, textColor: Int): Spannable {
            val spannable: Spannable = SpannableString(text)
            spannable.setSpan(
                ForegroundColorSpan(textColor), startIndex,
                lastIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            return spannable
        }

        fun textColor(text: String?, lastIndex: Int, textColor: Int): Spannable {
            val spannable: Spannable = SpannableString(text)
            spannable.setSpan(
                ForegroundColorSpan(textColor), 0,
                lastIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            return spannable
        }

        fun setErrorMsg(msg: String, viewId: EditText) {
            val fgcspan = ForegroundColorSpan(Color.RED)
            val ssbuilder = SpannableStringBuilder(msg)
            ssbuilder.setSpan(fgcspan, 0, msg.length, 0)
            viewId.error = ssbuilder
        }

        fun setErrorMsg(msg: String, viewId: EditText, icon: Int) {
            val icon2 = applicationContext!!.resources.getDrawable(icon)
            icon2.setBounds(0, 0, icon2.intrinsicWidth, icon2.intrinsicHeight)
            val fgcspan = ForegroundColorSpan(
                ContextCompat.getColor(
                    applicationContext!!, R.color.green
                )
            )
            val ssbuilder = SpannableStringBuilder(msg)
            ssbuilder.setSpan(fgcspan, 0, msg.length, 0)
            viewId.setError(ssbuilder, icon2)
        }// ignore


        fun getFileAPK(): File {
            val pm = applicationContext!!.packageManager
            val applicationInfo: ApplicationInfo =
                pm.getApplicationInfo(applicationContext!!.packageName, 0)
            val file: File = File(applicationInfo.publicSourceDir)
            return file
        }

        fun lastModifiedDate(): String {
            val pm = applicationContext!!.packageManager
            val packageInfo: PackageInfo =
                pm.getPackageInfo(
                    applicationContext!!.packageName,
                    PackageManager.GET_PERMISSIONS
                )
            val updateTime = Date(packageInfo.lastUpdateTime)
            val sdf = SimpleDateFormat("dd-MMM-yyyy hh:mm a")
            return sdf.format(updateTime)
        }

        fun firstModifiedDate(): String {
            val pm = applicationContext!!.packageManager
            val packageInfo: PackageInfo =
                pm.getPackageInfo(
                    applicationContext!!.packageName,
                    PackageManager.GET_PERMISSIONS
                )
            val installTime = Date(packageInfo.firstInstallTime)
            val sdf = SimpleDateFormat("dd-MMM-yyyy hh:mm a")
            return sdf.format(installTime)
        }

        fun getFolderSizeLabel(file: File): String? {
            val size =
                getFolderSize(file).toDouble() / 1000.0 // Get size and convert bytes into KB.
            return if (size >= 1024) {
                String.format("%.2f MB", (size / 1024))
            } else {
                String.format("%.2f KB", size)
            }
        }

        fun getSizeKBOrMB(size: Int): String? {
            var value = size / 1024
            return if (value >= 1024) {
                var value = value / 1024
                String.format("%d/MB", value)
            } else {
                String.format("%d/KB", size)
            }
        }

        fun getFolderSize(file: File): Long {
            var size: Long = 0
            if (file.isDirectory) {
                for (child in file.listFiles()) {
                    size += getFolderSize(child)
                }
            } else {
                size = file.length()
            }
            return size
        }
    }

}