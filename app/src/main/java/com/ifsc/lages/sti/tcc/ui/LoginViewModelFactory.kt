package com.ifsc.lages.sti.tcc.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.model.user.User
import com.ifsc.lages.sti.tcc.resources.BaseView
import com.ifsc.lages.sti.tcc.resources.user.UserRepository
import com.ifsc.lages.sti.tcc.utilidades.ConnectionUtil
import io.reactivex.observers.DisposableObserver

class LoginViewModelFactory (var activity: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(activity = activity, repository = UserRepository()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class") as Throwable
    }
}