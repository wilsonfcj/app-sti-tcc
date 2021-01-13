package com.ifsc.lages.sti.tcc.ui.meussimulados.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.model.simulated.Simulated
import com.ifsc.lages.sti.tcc.resources.BaseView
import com.ifsc.lages.sti.tcc.resources.question.QuestaoResponse
import com.ifsc.lages.sti.tcc.resources.simulated.SimuladoRepository
import com.ifsc.lages.sti.tcc.resources.simulated.SimuladoRequest
import com.ifsc.lages.sti.tcc.resources.simulated.SimuladoResponse
import com.ifsc.lages.sti.tcc.utilidades.ConnectionUtil
import io.reactivex.observers.DisposableObserver

class MeusSimuladosViewModel (var activity: Context, var repository: SimuladoRepository) : ViewModel() {

    private val _muesSimulados = MutableLiveData<BaseView<MutableList<Simulated>>>()
    var muesSimulados : LiveData<BaseView<MutableList<Simulated>>> = _muesSimulados

    private val _questoesSimulados = MutableLiveData<BaseView<MutableList<QuestaoResponse.Cadastrada>>>()
    var questoesSimulados : LiveData<BaseView<MutableList<QuestaoResponse.Cadastrada>>> = _questoesSimulados

    private val _simuladoCompleto = MutableLiveData<BaseView<SimuladoResponse.SimuladoBase>>()
    var simuladoCompleto : LiveData<BaseView<SimuladoResponse.SimuladoBase>> = _simuladoCompleto

    private val _simuladoCompletoRegister = MutableLiveData<BaseView<Simulated>>()
    var simuladoCompletoRegister : LiveData<BaseView<Simulated>> = _simuladoCompletoRegister

    fun createSimulated (request : SimuladoRequest.Register) {
        repository.createSimulated(request, object : DisposableObserver<Simulated>() {
            override fun onComplete() {}

            override fun onNext(t: Simulated) {
                _simuladoCompletoRegister.value = BaseView(success = t, error = false, message = "Simulados cadastrado com sucesso")
            }

            override fun onError(e: Throwable) {
                var msm = e.message
                if (ConnectionUtil.isNetworkAvailable(activity).not()) {
                    msm = activity.getString(R.string.error_conection)
                }
                _simuladoCompletoRegister.value = BaseView(success = null, error = true, message = msm)
            }
        })
    }

    fun loadSimulatedByUser(idUser: Long) {
        repository.loadSimulatedByUser(idUser, object : DisposableObserver<MutableList<Simulated>>() {
            override fun onComplete() {

            }

            override fun onNext(t: MutableList<Simulated>) {
                _muesSimulados.value = BaseView(success = t, error = false, message = "Simulados carregados com sucesso")
            }

            override fun onError(e: Throwable) {
                var msm = e.message
                if (ConnectionUtil.isNetworkAvailable(activity).not()) {
                    msm = activity.getString(R.string.error_conection)
                }
                _muesSimulados.value = BaseView(success = null, error = true, message = msm)
            }

        })
    }

    fun loadSimulatedQuestionsById(idUser: Long) {
        repository.loadSimulatedQuestionsById(idUser, object : DisposableObserver<MutableList<QuestaoResponse.Cadastrada>>() {
            override fun onComplete() {

            }

            override fun onNext(t: MutableList<QuestaoResponse.Cadastrada>) {
                _questoesSimulados.value = BaseView(success = t, error = false, message = "Questões carregados com sucesso")
            }

            override fun onError(e: Throwable) {
                var msm = e.message
                if (ConnectionUtil.isNetworkAvailable(activity).not()) {
                    msm = activity.getString(R.string.error_conection)
                }
                _questoesSimulados.value = BaseView(success = null, error = true, message = msm)
            }

        })
    }

    fun loadSimulatedById(idUser: Long) {
        repository.loadSimulatedById(idUser, object : DisposableObserver<SimuladoResponse.SimuladoCompleto>() {
            override fun onComplete() {

            }

            override fun onNext(t: SimuladoResponse.SimuladoCompleto) {
                _simuladoCompleto.value = BaseView(success = t, error = false, message = "Questões carregados com sucesso")
            }

            override fun onError(e: Throwable) {
                var msm = e.message
                if (ConnectionUtil.isNetworkAvailable(activity).not()) {
                    msm = activity.getString(R.string.error_conection)
                }
                _simuladoCompleto.value = BaseView(success = null, error = true, message = msm)
            }

        })
    }

}