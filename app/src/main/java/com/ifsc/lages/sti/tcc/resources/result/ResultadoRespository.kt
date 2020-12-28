package com.ifsc.lages.sti.tcc.resources.result

import com.ifsc.lages.sti.tcc.resources.generics.BaseResponse
import com.ifsc.lages.sti.tcc.resources.question.QuestaoResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

//TODO CRIAR OS MAPPERS
class ResultadoRespository {
    private val service: ResultadoService
        get() {
            return ResultadoService()
        }


    private fun loadResultSimulated(request: ResultadoRequest.PorUsuarioESimulado) : Single<BaseResponse<ResultadoResponse.Simulado>> {
        return Single.create {
            try {
                val response = service.loadResultSimulated(request)
                when {
                    response.success!! -> {
                        it.onSuccess(response)
                    }
                    else -> it.onError(Exception(response.message))
                }
            } catch (ex : java.lang.Exception) {
                it.onError(Exception("Erro ao consultar o resultado"))
            }
        }
    }

    fun loadResultSimulated(request: ResultadoRequest.PorUsuarioESimulado, observer: DisposableObserver<ResultadoResponse.Simulado>){
        loadResultSimulated(request)
            .toObservable()
            .map { it.data }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }

    private fun loadOverallResultBySimulated(request: ResultadoRequest.PorIdUsuarioETipoProva) : Single<BaseResponse<ResultadoResponse.GeralUsuario>> {
        return Single.create {
            try {
                val response = service.loadOverallResultBySimulated(request)
                when {
                    response.success!! -> {
                        it.onSuccess(response)
                    }
                    else -> it.onError(Exception(response.message))
                }
            } catch (ex : java.lang.Exception) {
                it.onError(Exception("Erro ao consultar o resultado"))
            }
        }
    }

    fun loadOverallResultBySimulated(request: ResultadoRequest.PorIdUsuarioETipoProva, observer: DisposableObserver<ResultadoResponse.GeralUsuario>){
        loadOverallResultBySimulated(request)
            .toObservable()
            .map { it.data }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }

    private fun loadOverallResult(idUsuario : Long) : Single<BaseResponse<ResultadoResponse.GeralUsuario>> {
        return Single.create {
            try {
                val response = service.loadOverallResult(idUsuario)
                when {
                    response.success!! -> {
                        it.onSuccess(response)
                    }
                    else -> it.onError(Exception(response.message))
                }
            } catch (ex : java.lang.Exception) {
                it.onError(Exception("Erro ao consultar o resultado"))
            }
        }
    }

    fun loadOverallResult(idUsuario : Long, observer: DisposableObserver<ResultadoResponse.GeralUsuario>){
        loadOverallResult(idUsuario)
            .toObservable()
            .map { it.data }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }

    private fun loadLatterResult(idUsuario : Long) : Single<BaseResponse<ResultadoResponse.Simulado>> {
        return Single.create {
            try {
                val response = service.loadLatterResult(idUsuario)
                when {
                    response.success!! -> {
                        it.onSuccess(response)
                    }
                    else -> it.onError(Exception(response.message))
                }
            } catch (ex : java.lang.Exception) {
                it.onError(Exception("Erro ao consultar o resultado"))
            }
        }
    }

    fun loadLatterResult(idUsuario : Long, observer: DisposableObserver<ResultadoResponse.Simulado>){
        loadLatterResult(idUsuario)
            .toObservable()
            .map { it.data }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }

    private fun loadFeedbackSimulated(request: ResultadoRequest.PorUsuarioESimulado) : Single<BaseResponse<MutableList<QuestaoResponse.QustaoGabaritoResponse>>> {
        return Single.create {
            try {
                val response = service.loadFeedbackSimulated(request)
                when {
                    response.success!! -> {
                        it.onSuccess(response)
                    }
                    else -> it.onError(Exception(response.message))
                }
            } catch (ex : java.lang.Exception) {
                it.onError(Exception("Erro ao consultar o resultado"))
            }
        }
    }

    fun loadFeedbackSimulated(request: ResultadoRequest.PorUsuarioESimulado, observer: DisposableObserver<MutableList<QuestaoResponse.QustaoGabaritoResponse>>){
        loadFeedbackSimulated(request)
            .toObservable()
            .map { it.data }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }
}