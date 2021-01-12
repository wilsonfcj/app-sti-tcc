package com.ifsc.lages.sti.tcc.resources.simulated

import com.ifsc.lages.sti.tcc.model.simulated.Simulated
import com.ifsc.lages.sti.tcc.resources.generics.BaseResponse
import com.ifsc.lages.sti.tcc.resources.question.QuestaoResponse
import com.ifsc.lages.sti.tcc.resources.result.ResultadoRequest
import com.ifsc.lages.sti.tcc.resources.result.ResultadoResponse
import com.ifsc.lages.sti.tcc.resources.simulated.mapper.MapperSimulated
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class SimuladoRepository {

    private val service: SimuladoService
        get() {
            return SimuladoService()
        }


    private fun registerQuestion() : Single<BaseResponse<MutableList<QuestaoResponse.Cadastrada>>> {
        return Single.create {
            try {
                val response = service.registerQuestion()
                when {
                    response.success!! -> {
                        it.onSuccess(response)
                    }
                    else -> it.onError(Exception(response.message))
                }
            } catch (ex : java.lang.Exception) {
                it.onError(Exception("Erro ao cadstrar as questões"))
            }
        }
    }

    fun registerQuestion(observer: DisposableObserver<MutableList<QuestaoResponse.Cadastrada>>){
        registerQuestion()
            .toObservable()
            .map { it.data }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }


    private fun createSimulated(request : SimuladoRequest.Register) : Single<BaseResponse<SimuladoResponse.SimuladoCompleto>> {
        return Single.create {
            try {
                val response = service.createSimulated(request)
                when {
                    response.success!! -> {
                        it.onSuccess(response)
                    }
                    else -> it.onError(Exception(response.message))
                }
            } catch (ex : java.lang.Exception) {
                it.onError(Exception("Erro criar o simulado"))
            }
        }
    }

    fun createSimulated(request : SimuladoRequest.Register, observer: DisposableObserver<SimuladoResponse.SimuladoCompleto>){
        createSimulated(request)
            .toObservable()
            .map { it.data }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }


    private fun loadSimulatedById(idSimulado : Long) : Single<BaseResponse<SimuladoResponse.SimuladoCompleto>> {
        return Single.create {
            try {
                val response = service.loadSimulatedById(idSimulado)
                when {
                    response.success!! -> {
                        it.onSuccess(response)
                    }
                    else -> it.onError(Exception(response.message))
                }
            } catch (ex : java.lang.Exception) {
                it.onError(Exception("Erro buscar simulado por id"))
            }
        }
    }

    fun loadSimulatedById(idSimulado : Long, observer: DisposableObserver<SimuladoResponse.SimuladoCompleto>){
        loadSimulatedById(idSimulado)
            .toObservable()
            .map { it.data }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }

    private fun loadSimulatedQuestionsById(idSimulado : Long) : Single<BaseResponse<MutableList<QuestaoResponse.Cadastrada>>> {
        return Single.create {
            try {
                val response = service.loadSimulatedQuestionsById(idSimulado)
                when {
                    response.success!! -> {
                        it.onSuccess(response)
                    }
                    else -> it.onError(Exception(response.message))
                }
            } catch (ex : java.lang.Exception) {
                it.onError(Exception("Erro buscar as questões do simulado"))
            }
        }
    }

    fun loadSimulatedQuestionsById(idSimulado : Long, observer: DisposableObserver<MutableList<QuestaoResponse.Cadastrada>>){
        loadSimulatedQuestionsById(idSimulado)
            .toObservable()
            .map { it.data }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }

    private fun loadSimulatedByUser(idUsuario : Long) : Single<BaseResponse<MutableList<SimuladoResponse.SimuladoBase>>> {
        return Single.create {
            try {
                val response = service.loadSimulatedByUser(idUsuario)
                when {
                    response.success!! -> {
                        it.onSuccess(response)
                    }
                    else -> it.onError(Exception(response.message))
                }
            } catch (ex : java.lang.Exception) {
                it.onError(Exception("Erro buscar os simulados"))
            }
        }
    }

    fun loadSimulatedByUser(idUsuario : Long, observer: DisposableObserver<MutableList<Simulated>>){
        loadSimulatedByUser(idUsuario)
            .toObservable()
            .map { MapperSimulated().transform(it.data) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }

    private fun saveSimulated(request : SimuladoRequest.RespostaSimuladoRequest) : Single<BaseResponse<ResultadoResponse.Simulado>> {
        return Single.create {
            try {
                val response = service.saveSimulated(request)
                when {
                    response.success!! -> {
                        it.onSuccess(response)
                    }
                    else -> it.onError(Exception(response.message))
                }
            } catch (ex : java.lang.Exception) {
                it.onError(Exception("Erro buscar as questões do simulado"))
            }
        }
    }

    fun saveSimulated(request : SimuladoRequest.RespostaSimuladoRequest, observer: DisposableObserver<ResultadoResponse.Simulado>){
        saveSimulated(request)
            .toObservable()
            .map { it.data }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }

    private fun deleteSimulated(request: ResultadoRequest.PorUsuarioESimulado) : Single<BaseResponse<SimuladoResponse.SimuladoCompleto>> {
        return Single.create {
            try {
                val response = service.deleteSimulated(request)
                when {
                    response.success!! -> {
                        it.onSuccess(response)
                    }
                    else -> it.onError(Exception(response.message))
                }
            } catch (ex : java.lang.Exception) {
                it.onError(Exception("Erro buscar as questões do simulado"))
            }
        }
    }

    fun deleteSimulated(request: ResultadoRequest.PorUsuarioESimulado, observer: DisposableObserver<SimuladoResponse.SimuladoCompleto>){
        deleteSimulated(request)
            .toObservable()
            .map { it.data }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }
}