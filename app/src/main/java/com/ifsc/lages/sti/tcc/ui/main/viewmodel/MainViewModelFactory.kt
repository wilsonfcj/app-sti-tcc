package com.ifsc.lages.sti.tcc.ui.main.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ifsc.lages.sti.tcc.resources.result.ResultadoRespository

class MainViewModelFactory (var activity: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                activity = activity,
                repository = ResultadoRespository()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}