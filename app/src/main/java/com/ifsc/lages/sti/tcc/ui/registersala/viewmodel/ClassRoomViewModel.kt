package com.ifsc.lages.sti.tcc.ui.registersala.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.model.classrom.Classroom
import com.ifsc.lages.sti.tcc.model.result.ResultUser
import com.ifsc.lages.sti.tcc.model.simulated.Simulated
import com.ifsc.lages.sti.tcc.resources.BaseView
import com.ifsc.lages.sti.tcc.resources.classroom.ClassroomRepository
import com.ifsc.lages.sti.tcc.resources.classroom.ClassroomRequest
import com.ifsc.lages.sti.tcc.resources.question.QuestaoResponse
import com.ifsc.lages.sti.tcc.resources.result.ResultadoRequest
import com.ifsc.lages.sti.tcc.resources.result.ResultadoRespository
import com.ifsc.lages.sti.tcc.resources.simulated.SimuladoRepository
import com.ifsc.lages.sti.tcc.resources.simulated.SimuladoRequest
import com.ifsc.lages.sti.tcc.resources.simulated.SimuladoResponse
import com.ifsc.lages.sti.tcc.utilidades.ConnectionUtil
import io.reactivex.observers.DisposableObserver

class ClassRoomViewModel (var activity: Context, var repository: ClassroomRepository, var repositoryResponse: ResultadoRespository) : ViewModel() {

    private val _minhasSalas = MutableLiveData<BaseView<MutableList<Classroom>>>()
    var minhasSalas : LiveData<BaseView<MutableList<Classroom>>> = _minhasSalas

    private val _enterClassrrom = MutableLiveData<BaseView<Boolean>>()
    var enterClassrrom : LiveData<BaseView<Boolean>> = _enterClassrrom

    private val _registerClassroom = MutableLiveData<BaseView<Classroom>>()
    var registerClassroom : LiveData<BaseView<Classroom>> = _registerClassroom

    private val _deleteCompleto = MutableLiveData<BaseView<Classroom>>()
    var deleteCompleto : LiveData<BaseView<Classroom>> = _deleteCompleto

    private val _loadResponseUsers = MutableLiveData<BaseView<MutableList<ResultUser>>>()
    var loadResponseUsers : LiveData<BaseView<MutableList<ResultUser>>> = _loadResponseUsers

    fun loadResultUserBySimulatedClassrrom (usuarioId: Long, simuladoId : Long) {
        var request =  ResultadoRequest.PorUsuarioESimulado()
        request.idUsuario = usuarioId
        request.idSimulado = simuladoId

        repositoryResponse.loadResultUserBySimulatedClassrrom(request, object : DisposableObserver<MutableList<ResultUser>>() {
            override fun onComplete() {}

            override fun onNext(t: MutableList<ResultUser>) {
                _loadResponseUsers.value = BaseView(success = t, error = false, message = "Resultados consultados com sucesso")
            }

            override fun onError(e: Throwable) {
                var msm = e.message
                if (ConnectionUtil.isNetworkAvailable(activity).not()) {
                    msm = activity.getString(R.string.error_conection)
                }
                _loadResponseUsers.value = BaseView(success = null, error = true, message = msm)
            }
        })
    }

    fun deleteClassroom (usuarioId: Long, salaId : Long) {
        var request = ClassroomRequest.DeleteClassroom()
        request.idUsuario = usuarioId
        request.idSala = salaId

        repository.deleteClassroom(request, object : DisposableObserver<Classroom>() {
            override fun onComplete() {}

            override fun onNext(t: Classroom) {
                _deleteCompleto.value = BaseView(success = t, error = false, message = "Sala de simulado removida com sucesso")
            }

            override fun onError(e: Throwable) {
                var msm = e.message
                if (ConnectionUtil.isNetworkAvailable(activity).not()) {
                    msm = activity.getString(R.string.error_conection)
                }
                _deleteCompleto.value = BaseView(success = null, error = true, message = msm)
            }
        })
    }

    fun enterClassroom2 (usuarioId: Long, salaId : Long, senha : String) {
        var request = ClassroomRequest.EnterClassroom()
        request.idUsuario = usuarioId
        request.idSala = salaId
        request.senha = senha

        repository.enterClassroom(request, object : DisposableObserver<Boolean>() {
            override fun onComplete() {}

            override fun onNext(t: Boolean) {
                _enterClassrrom.value = BaseView(success = t, error = false, message = "Participação aprovada com sucesso")
            }

            override fun onError(e: Throwable) {
                var msm = e.message
                if (ConnectionUtil.isNetworkAvailable(activity).not()) {
                    msm = activity.getString(R.string.error_conection)
                }
                _enterClassrrom.value = BaseView(success = false, error = true, message = msm)
            }
        })
    }

    fun registerClassroom (request :  ClassroomRequest.Register) {
        repository.createClassroom(request, object : DisposableObserver<Classroom>() {
            override fun onComplete() {}

            override fun onNext(t: Classroom) {
                _registerClassroom.value = BaseView(success = t, error = false, message = "Sala de simulado criada com sucesso")
            }

            override fun onError(e: Throwable) {
                var msm = e.message
                if (ConnectionUtil.isNetworkAvailable(activity).not()) {
                    msm = activity.getString(R.string.error_conection)
                }
                _registerClassroom.value = BaseView(success = null, error = true, message = msm)
            }
        })
    }

    fun loadSimulatedByUser(idUser: Long) {
        repository.searchClassroom(idUser, object : DisposableObserver<MutableList<Classroom>>() {
            override fun onComplete() {}

            override fun onNext(t: MutableList<Classroom>) {
                _minhasSalas.value = BaseView(success = t, error = false, message = "Salas carregados com sucesso")
            }

            override fun onError(e: Throwable) {
                var msm = e.message
                if (ConnectionUtil.isNetworkAvailable(activity).not()) {
                    msm = activity.getString(R.string.error_conection)
                }
                _minhasSalas.value = BaseView(success = null, error = true, message = msm)
            }

        })
    }

}