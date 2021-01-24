package com.ifsc.lages.sti.tcc.ui.feedback.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.model.question.QuestionFeedback
import com.ifsc.lages.sti.tcc.model.result.ResultUser
import com.ifsc.lages.sti.tcc.resources.BaseView
import com.ifsc.lages.sti.tcc.resources.classroom.ClassroomRepository
import com.ifsc.lages.sti.tcc.resources.result.ResultadoRequest
import com.ifsc.lages.sti.tcc.resources.result.ResultadoRespository
import com.ifsc.lages.sti.tcc.utilidades.ConnectionUtil
import io.reactivex.observers.DisposableObserver

class FeedbackViewModel (var activity: Context, var repository: ResultadoRespository) : ViewModel() {

    private val _loadFeedback = MutableLiveData<BaseView<MutableList<QuestionFeedback>>>()
    var loadFeedback : LiveData<BaseView<MutableList<QuestionFeedback>>> = _loadFeedback

    fun loadFeedback (usuarioId: Long, simuladoId : Long) {
        var request =  ResultadoRequest.PorUsuarioESimulado()
        request.idUsuario = usuarioId
        request.idSimulado = simuladoId

        repository.loadFeedbackNotResponseUser(request, object : DisposableObserver<MutableList<QuestionFeedback>>() {
            override fun onComplete() {}

            override fun onNext(t: MutableList<QuestionFeedback>) {
                _loadFeedback.value = BaseView(success = t, error = false, message = "Resultados consultados com sucesso")
            }

            override fun onError(e: Throwable) {
                var msm = e.message
                if (ConnectionUtil.isNetworkAvailable(activity).not()) {
                    msm = activity.getString(R.string.error_conection)
                }
                _loadFeedback.value = BaseView(success = null, error = true, message = msm)
            }
        })
    }

    fun loadFeedbackByUser (usuarioId: Long, simuladoId : Long) {
        var request =  ResultadoRequest.PorUsuarioESimulado()
        request.idUsuario = usuarioId
        request.idSimulado = simuladoId

        repository.loadFeedbackSimulated(request, object : DisposableObserver<MutableList<QuestionFeedback>>() {
            override fun onComplete() {}

            override fun onNext(t: MutableList<QuestionFeedback>) {
                _loadFeedback.value = BaseView(success = t, error = false, message = "Resultados consultados com sucesso")
            }

            override fun onError(e: Throwable) {
                var msm = e.message
                if (ConnectionUtil.isNetworkAvailable(activity).not()) {
                    msm = activity.getString(R.string.error_conection)
                }
                _loadFeedback.value = BaseView(success = null, error = true, message = msm)
            }
        })
    }
}