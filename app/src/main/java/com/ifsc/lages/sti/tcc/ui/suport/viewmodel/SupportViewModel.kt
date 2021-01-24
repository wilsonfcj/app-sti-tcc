package com.ifsc.lages.sti.tcc.ui.suport.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.resources.BaseView
import com.ifsc.lages.sti.tcc.resources.user.UserRepository
import com.ifsc.lages.sti.tcc.utilidades.ConnectionUtil
import io.reactivex.observers.DisposableObserver

class SupportViewModel (var activity: Context, var repository: UserRepository) : ViewModel() {

    private val _sendMsm = MutableLiveData<BaseView<Boolean>>()
    var sendMsm : LiveData<BaseView<Boolean>> = _sendMsm

    fun send(assunto : String, mensagem : String) {
        repository.sendEmail(assunto, mensagem, object : DisposableObserver<Boolean>() {
            override fun onComplete() {

            }

            override fun onNext(t: Boolean) {
                _sendMsm.value = BaseView(success = t, error = false, message = "Sugestão enviada com sucesso")
            }

            override fun onError(e: Throwable) {
                var msm = e.message
                if (ConnectionUtil.isNetworkAvailable(activity).not()) {
                    msm = activity.getString(R.string.error_conection)
                }
                _sendMsm.value = BaseView(success = null, error = true, message = msm)
            }
        })
    }
}