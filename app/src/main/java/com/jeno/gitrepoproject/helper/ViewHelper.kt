package com.jeno.gitrepoproject.helper

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.TextView
import com.jeno.gitrepoproject.R

class ViewHelper {

    fun progressDialog(pContext: Context, pMessage: String): Dialog {

        var lProgressDialog: Dialog = Dialog(pContext, R.style.DialogCustomTheme)
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
}