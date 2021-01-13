package com.ifsc.lages.sti.tcc.utilidades.baseview

import android.app.ProgressDialog
import androidx.fragment.app.Fragment
import br.edu.ifsc.cancontrol.utilidades.MapElement

abstract class BaseFragment : Fragment(), MapElement {

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

    open fun createRestListener() {}
}
