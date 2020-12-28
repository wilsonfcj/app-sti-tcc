package com.ifsc.lages.sti.tcc.ui.main.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.model.user.User
import com.ifsc.lages.sti.tcc.props.ETipoSimulado
import com.ifsc.lages.sti.tcc.resources.BaseView
import com.ifsc.lages.sti.tcc.resources.result.ResultadoRequest
import com.ifsc.lages.sti.tcc.resources.result.ResultadoResponse
import com.ifsc.lages.sti.tcc.resources.result.ResultadoRespository
import com.ifsc.lages.sti.tcc.utilidades.ConnectionUtil
import io.reactivex.observers.DisposableObserver

class MainViewModel (var activity: Context, var repository : ResultadoRespository) : ViewModel()  {

    private val _loadOverallResultView = MutableLiveData<BaseView<ResultadoResponse.GeralUsuario>>()
    var loadOverallResultView : LiveData<BaseView<ResultadoResponse.GeralUsuario>> = _loadOverallResultView

    private val _loadOverallResultPoscompView = MutableLiveData<BaseView<ResultadoResponse.GeralUsuario>>()
    var loadOverallResultPoscompView : LiveData<BaseView<ResultadoResponse.GeralUsuario>> = _loadOverallResultPoscompView

    private val _loadOverallResultEnadeView = MutableLiveData<BaseView<ResultadoResponse.GeralUsuario>>()
    var loadOverallResultEnadeView : LiveData<BaseView<ResultadoResponse.GeralUsuario>> = _loadOverallResultEnadeView


    fun loadOverallResultView(idUsuario : Long) {
        repository.loadOverallResult(idUsuario, object : DisposableObserver<ResultadoResponse.GeralUsuario>() {

            override fun onComplete() {
            }

            override fun onNext(t: ResultadoResponse.GeralUsuario) {
                _loadOverallResultView.value = BaseView(success = t, error = false, message = "Resultado carregado com sucesso")
            }

            override fun onError(e: Throwable) {
                var msm = e.message
                if (ConnectionUtil.isNetworkAvailable(activity).not()) {
                    msm = activity.getString(R.string.error_conection)
                }
                _loadOverallResultView.value = BaseView(success = null, error = true, message = msm)
            }
        })
    }

    fun loadOverallResultPoscomp(idUsuario : Long) {
        var request = ResultadoRequest.PorIdUsuarioETipoProva()
        request.idUsuario = idUsuario
        request.tipoSimulado = ETipoSimulado.POSCOMP.codigo
        repository.loadOverallResultBySimulated(request, object : DisposableObserver<ResultadoResponse.GeralUsuario>() {

            override fun onComplete() {
            }

            override fun onNext(t: ResultadoResponse.GeralUsuario) {
                _loadOverallResultPoscompView.value = BaseView(success = t, error = false, message = "Resultado carregado com sucesso")
            }

            override fun onError(e: Throwable) {
                var msm = e.message
                if (ConnectionUtil.isNetworkAvailable(activity).not()) {
                    msm = activity.getString(R.string.error_conection)
                }
                _loadOverallResultPoscompView.value = BaseView(success = null, error = true, message = msm)
            }
        })
    }

    fun loadOverallResultEnade(idUsuario : Long) {
        var request = ResultadoRequest.PorIdUsuarioETipoProva()
        request.idUsuario = idUsuario
        request.tipoSimulado = ETipoSimulado.ENADE.codigo
        repository.loadOverallResultBySimulated(request, object : DisposableObserver<ResultadoResponse.GeralUsuario>() {

            override fun onComplete() {
            }

            override fun onNext(t: ResultadoResponse.GeralUsuario) {
                _loadOverallResultEnadeView.value = BaseView(success = t, error = false, message = "Resultado carregado com sucesso")
            }

            override fun onError(e: Throwable) {
                var msm = e.message
                if (ConnectionUtil.isNetworkAvailable(activity).not()) {
                    msm = activity.getString(R.string.error_conection)
                }
                _loadOverallResultEnadeView.value = BaseView(success = null, error = true, message = msm)
            }
        })
    }

}