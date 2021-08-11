package com.jeno.gitrepoproject.helper

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.view.Window
import android.widget.TextView
import com.jeno.gitrepoproject.R

class CommonHelper {

    fun progressDialog(pContext: Context, pMessage: String): Dialog {

        var lProgressDialog = Dialog(pContext, R.style.DialogCustomTheme)
        lProgressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        lProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        lProgressDialog.setContentView(R.layout.progress_dialog)
        lProgressDialog.setCancelable(false)
        lProgressDialog.setCanceledOnTouchOutside(false)
        val lMessageBox = lProgressDialog.findViewById(R.id.progressMsg) as TextView
        lMessageBox.setText(pMessage)
        lProgressDialog.show()


        return lProgressDialog
    }

    fun isNetworkAvailable(pContext : Context):Boolean {
        val ConnectionManager = pContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = ConnectionManager.activeNetworkInfo
        return if (networkInfo != null && networkInfo.isConnected == true) {
            true
        } else {
            false
        }
    }
}