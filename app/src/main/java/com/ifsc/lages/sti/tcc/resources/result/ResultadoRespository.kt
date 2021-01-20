package com.ifsc.lages.sti.tcc.resources.result

import com.ifsc.lages.sti.tcc.model.question.QuestionFeedback
import com.ifsc.lages.sti.tcc.model.result.*
import com.ifsc.lages.sti.tcc.resources.generics.BaseResponse
import com.ifsc.lages.sti.tcc.resources.question.QuestaoResponse
import com.ifsc.lages.sti.tcc.resources.result.mapper.*
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.ArrayList

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

    private fun loadLatterResult(idUsuario : Long) : Single<BaseResponse<MutableList<ResultadoResponse.SimuladoCompleto>>> {
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
            .map { MapperResultSimulatedFull().transform(it.data!!)  }
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

    fun loadFeedbackSimulated(request: ResultadoRequest.PorUsuarioESimulado, observer: DisposableObserver<MutableList<QuestionFeedback>>){
        loadFeedbackSimulated(request)
            .toObservable()
            .map { MapperQuestionFeedback().transform(it.data!!)}
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

    fun loadPerformanceMatters(request: ResultadoRequest.PorUsuarioESimulado, observer: DisposableObserver<MutableList<ResultMatters>>){
        loadPerformanceMatters(request)
            .toObservable()
            .map { MapperResultMatters().transform(it.data!!)  }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }

    private fun loadPerformanceMatters(idUsuario : Long) : Single<BaseResponse<MutableList<ResultadoResponse.Disciplina>>> {
        return Single.create {
            try {
                val response = service.loadPerformanceMatters(idUsuario)
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

    fun loadPerformanceMatters(idUsuario : Long, observer: DisposableObserver<MutableList<ResultMatters>>){
        loadPerformanceMatters(idUsuario)
            .toObservable()
            .map { MapperResultMatters().transform(it.data!!)  }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }

    private fun loadResultUserBySimulatedClassrrom(request: ResultadoRequest.PorUsuarioESimulado) : Single<BaseResponse<MutableList<ResultadoResponse.SimuladoUsuario>>> {
        return Single.create {
            try {
                val response = service.loadResultUserBySimulatedClassrrom(request)
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

    fun loadResultUserBySimulatedClassrrom(request: ResultadoRequest.PorUsuarioESimulado, observer: DisposableObserver<MutableList<ResultUser>>) {
        loadResultUserBySimulatedClassrrom(request)
            .toObservable()
            .map { MapperUserClassRoom().transform(it.data!!) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }


    private fun loadFeedbackNotResponseUser(request: ResultadoRequest.PorUsuarioESimulado) : Single<BaseResponse<MutableList<QuestaoResponse.QustaoGabaritoResponse>>> {
        return Single.create {
            try {
                val response = service.loadFeedbackNotResponseUser(request)
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

    fun loadFeedbackNotResponseUser(request: ResultadoRequest.PorUsuarioESimulado, observer: DisposableObserver<MutableList<QuestionFeedback>>){
        loadFeedbackNotResponseUser(request)
            .toObservable()
            .map { MapperQuestionFeedback().transform(it.data!!) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }
}