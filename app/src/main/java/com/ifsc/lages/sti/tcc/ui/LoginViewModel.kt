package com.ifsc.lages.sti.tcc.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.model.user.User
import com.ifsc.lages.sti.tcc.resources.BaseView
import com.ifsc.lages.sti.tcc.resources.user.UserRepository
import com.ifsc.lages.sti.tcc.utilidades.ConnectionUtil
import io.reactivex.observers.DisposableObserver

class LoginViewModel (var activity: Context, var repository : UserRepository) : ViewModel()  {


    val _loginViewMonitoring = MutableLiveData<BaseView<User>>()
    var loginViewMonitoring : LiveData<BaseView<User>> = _loginViewMonitoring

    fun registerMonitoring(cpf: String, senha : String) {
        repository.loginUser(cpf, senha, object : DisposableObserver<User>() {

            override fun onComplete() {
            }

            override fun onNext(t: User) {
                _loginViewMonitoring.value = BaseView(success = t, error = false, message = "Usuário autenticado com sucesso")
            }

            override fun onError(e: Throwable) {
                var msm = e.message
                if (ConnectionUtil.isNetworkAvailable(activity).not()) {
                    msm = activity.getString(R.string.error_conection)
                }
                _loginViewMonitoring.value = BaseView(success = null, error = true, message = msm)
            }

//            override fun onComplete() {}
//
//            override fun onNext(t: Monitoring) {
//                _registerMonitoring.value = BaseView(success = t, error = false, message = "Usuário registrado com sucesso")
//            }
//
//            override fun onError(e: Throwable) {
//                var msm = e.message
//                if (ConnectionUtil.isNetworkAvailable(activity).not()) {
//                    msm = activity.getString(R.string.msm_erro_conection)
//                }
//                _registerMonitoring.value = BaseView(success = null, error = true, message = msm)
//            }
        })
    }
}