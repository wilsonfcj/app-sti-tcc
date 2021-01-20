package com.ifsc.lages.sti.tcc.ui.registersala.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ifsc.lages.sti.tcc.resources.classroom.ClassroomRepository
import com.ifsc.lages.sti.tcc.resources.result.ResultadoRespository
import com.ifsc.lages.sti.tcc.resources.simulated.SimuladoRepository
import com.ifsc.lages.sti.tcc.ui.register.viewmodel.RegisterViewModel

class ClassRoomViewModelFactory (var activity: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClassRoomViewModel::class.java)) {
            return ClassRoomViewModel(
                activity = activity,
                repository = ClassroomRepository(),
                repositoryResponse = ResultadoRespository()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}