package com.ifsc.lages.sti.tcc.ui.feedback.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ifsc.lages.sti.tcc.resources.result.ResultadoRespository
import com.ifsc.lages.sti.tcc.ui.registersala.viewmodel.ClassRoomViewModel

class FeedbackViewModelFactory (var activity: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FeedbackViewModel::class.java)) {
            return FeedbackViewModel(
                activity = activity,
                repository = ResultadoRespository()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
