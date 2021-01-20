package com.ifsc.lages.sti.tcc.ui.main.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.model.result.ResultMatters
import com.ifsc.lages.sti.tcc.model.result.ResultOverall
import com.ifsc.lages.sti.tcc.model.result.ResultSimulated
import com.ifsc.lages.sti.tcc.props.ETipoSimulado
import com.ifsc.lages.sti.tcc.resources.BaseView
import com.ifsc.lages.sti.tcc.resources.result.ResultadoRequest
import com.ifsc.lages.sti.tcc.resources.result.ResultadoRespository
import com.ifsc.lages.sti.tcc.utilidades.ConnectionUtil
import com.ifsc.lages.sti.tcc.utilidades.KeyPrefs
import com.ifsc.lages.sti.tcc.utilidades.SharedPreferencesUtil
import io.reactivex.observers.DisposableObserver

class MainViewModel (var activity: Context, var repository : ResultadoRespository) : ViewModel()  {

    private val _loadOverallResultView = MutableLiveData<BaseView<ResultOverall>>()
    var loadOverallResultView : LiveData<BaseView<ResultOverall>> = _loadOverallResultView

    private val _loadOverallResultPoscompView = MutableLiveData<BaseView<ResultOverall>>()
    var loadOverallResultPoscompView : LiveData<BaseView<ResultOverall>> = _loadOverallResultPoscompView

    private val _loadOverallResultEnadeView = MutableLiveData<BaseView<ResultOverall>>()
    var loadOverallResultEnadeView : LiveData<BaseView<ResultOverall>> = _loadOverallResultEnadeView

    private val _loadOverallResultCustomView = MutableLiveData<BaseView<ResultOverall>>()
    var loadOverallResultCustomView : LiveData<BaseView<ResultOverall>> = _loadOverallResultCustomView

    private val _loadLastResult = MutableLiveData<BaseView<MutableList<ResultSimulated>>>()
    var loadLastResult : LiveData<BaseView<MutableList<ResultSimulated>>> = _loadLastResult

    private val _resultMatters = MutableLiveData<BaseView<MutableList<ResultMatters>>>()
    var resultMatters : LiveData<BaseView<MutableList<ResultMatters>>> = _resultMatters

    fun loadOverallResultView(idUsuario : Long) {
        repository.loadOverallResult(idUsuario, object : DisposableObserver<ResultOverall>() {

            override fun onComplete() {
            }

            override fun onNext(t: ResultOverall) {
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
        repository.loadOverallResultBySimulated(request, object : DisposableObserver<ResultOverall>() {

            override fun onComplete() {
            }

            override fun onNext(t: ResultOverall) {
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
        request.tipoSimulado = ETipoSimulado.DEFAULT.codigo
        repository.loadOverallResultBySimulated(request, object : DisposableObserver<ResultOverall>() {

            override fun onComplete() {
            }

            override fun onNext(t: ResultOverall) {
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

    fun loadOverallResultCustom(idUsuario : Long) {
        var request = ResultadoRequest.PorIdUsuarioETipoProva()
        request.idUsuario = idUsuario
        request.tipoSimulado = ETipoSimulado.ENADE.codigo
        repository.loadOverallResultBySimulated(request, object : DisposableObserver<ResultOverall>() {

            override fun onComplete() {
            }

            override fun onNext(t: ResultOverall) {
                _loadOverallResultCustomView.value = BaseView(success = t, error = false, message = "Resultado carregado com sucesso")
            }

            override fun onError(e: Throwable) {
                var msm = e.message
                if (ConnectionUtil.isNetworkAvailable(activity).not()) {
                    msm = activity.getString(R.string.error_conection)
                }
                _loadOverallResultCustomView.value = BaseView(success = null, error = true, message = msm)
            }
        })
    }

    fun lastResult(idUsuario : Long) {
        repository.loadLatterResult(idUsuario, object : DisposableObserver<MutableList<ResultSimulated>>() {

            override fun onComplete() {
            }

            override fun onNext(t: MutableList<ResultSimulated>) {
                _loadLastResult.value = BaseView(success = t, error = false, message = "Resultado carregado com sucesso")
            }

            override fun onError(e: Throwable) {
                var msm = e.message
                if (ConnectionUtil.isNetworkAvailable(activity).not()) {
                    msm = activity.getString(R.string.error_conection)
                }
                _loadLastResult.value = BaseView(success = null, error = true, message = msm)
            }
        })
    }

    fun loadResultMatters(userId : Long) {
        repository.loadPerformanceMatters(userId, object : DisposableObserver<MutableList<ResultMatters>>() {

            override fun onComplete() {
            }

            override fun onNext(t: MutableList<ResultMatters>) {
                _resultMatters.value = BaseView(success = t, error = false, message = "Resultado carregado com sucesso")
            }

            override fun onError(e: Throwable) {
                var msm = e.message
                if (ConnectionUtil.isNetworkAvailable(activity).not()) {
                    msm = activity.getString(R.string.error_conection)
                }
                _resultMatters.value = BaseView(success = null, error = true, message = msm)
            }
        })
    }
}