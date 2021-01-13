package com.ifsc.lages.sti.tcc.resources.result

import com.ifsc.lages.sti.tcc.model.result.ResultOverall
import com.ifsc.lages.sti.tcc.model.result.ResultSimulated
import com.ifsc.lages.sti.tcc.resources.generics.BaseResponse
import com.ifsc.lages.sti.tcc.resources.question.QuestaoResponse
import com.ifsc.lages.sti.tcc.resources.result.mapper.MapperGeralUser
import com.ifsc.lages.sti.tcc.resources.result.mapper.MapperResultSimulated
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

    fun loadOverallResultBySimulated(request: ResultadoRequest.PorIdUsuarioETipoProva, observer: DisposableObserver<ResultOverall>){
        loadOverallResultBySimulated(request)
            .toObservable()
            .map { MapperGeralUser().transform(it.data)  }
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

    fun loadOverallResult(idUsuario : Long, observer: DisposableObserver<ResultOverall>){
        loadOverallResult(idUsuario)
            .toObservable()
            .map { MapperGeralUser().transform(it.data) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }

    private fun loadLatterResult(idUsuario : Long) : Single<BaseResponse<MutableList<ResultadoResponse.Simulado>>> {
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

    fun loadLatterResult(idUsuario : Long, observer: DisposableObserver<MutableList<ResultSimulated>>){
        loadLatterResult(idUsuario)
            .toObservable()
            .map { MapperResultSimulated().transform(it.data!!)  }
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

    private fun loadPerformanceMatters(request: ResultadoRequest.PorUsuarioESimulado) : Single<BaseResponse<MutableList<ResultadoResponse.Disciplina>>> {
        return Single.create {
            try {
                val response = service.loadPerformanceMatters(request)
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

    fun loadPerformanceMatters(request: ResultadoRequest.PorUsuarioESimulado, observer: DisposableObserver<MutableList<ResultadoResponse.Disciplina>>){
        loadPerformanceMatters(request)
            .toObservable()
            .map { it.data }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }
}