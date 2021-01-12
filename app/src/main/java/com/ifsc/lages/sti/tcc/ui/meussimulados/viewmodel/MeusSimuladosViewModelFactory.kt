package com.ifsc.lages.sti.tcc.ui.meussimulados.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ifsc.lages.sti.tcc.resources.simulated.SimuladoRepository
import com.ifsc.lages.sti.tcc.ui.register.viewmodel.RegisterViewModel

class MeusSimuladosViewModelFactory (var activity: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MeusSimuladosViewModel::class.java)) {
            return MeusSimuladosViewModel(
                activity = activity,
                repository = SimuladoRepository()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}