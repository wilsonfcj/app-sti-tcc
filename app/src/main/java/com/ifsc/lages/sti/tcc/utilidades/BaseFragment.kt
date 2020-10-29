package com.ifsc.lages.sti.tcc.utilidades

import android.app.ProgressDialog
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    private var progressDialog : ProgressDialog? = null

    fun showLoading(message: String) {
        progressDialog = ProgressDialog(activity)
        progressDialog?.setMessage(message)
        progressDialog?.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog?.setCancelable(false)
        progressDialog?.setIndeterminate(true)
        progressDialog?.show()
    }

    fun hideLoading() {
        progressDialog?.dismiss()
    }
}
