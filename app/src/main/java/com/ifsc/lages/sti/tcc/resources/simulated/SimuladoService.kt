package com.ifsc.lages.sti.tcc.resources.simulated

import br.edu.ifsc.cancontrol.resources.generics.RetrofitImpl
import com.ifsc.lages.sti.tcc.resources.Api
import com.ifsc.lages.sti.tcc.resources.generics.BaseResponse
import com.ifsc.lages.sti.tcc.resources.question.QuestaoResponse
import com.ifsc.lages.sti.tcc.resources.result.ResultadoRequest
import com.ifsc.lages.sti.tcc.resources.result.ResultadoResponse
import retrofit2.http.Body

class SimuladoService {

    fun registerQuestion() : BaseResponse<MutableList<QuestaoResponse.Cadastrada>> {
        val lRetrofit = RetrofitImpl().buildRetrofit()
        val api = lRetrofit.create(Api.Mock::class.java)
        val objectCall = api.registerQuestion()
        val execute = objectCall.execute()
        val body = execute.body()
        if(body != null) {
            return body
        } else {
            throw Exception("Erro ao realizar a requisição")
        }
    }

    fun createSimulated(request : SimuladoRequest.Register) : BaseResponse<SimuladoResponse.SimuladoCompleto> {
        val lRetrofit = RetrofitImpl().buildRetrofit()
        val api = lRetrofit.create(Api.Mock::class.java)
        val objectCall = api.createSimulated(request)
        val execute = objectCall.execute()
        val body = execute.body()
        if(body != null) {
            return body
        } else {
            throw Exception("Erro ao realizar a requisição")
        }
    }

    fun loadSimulatedById(idSimulado : Long) : BaseResponse<SimuladoResponse.SimuladoCompleto> {
        val lRetrofit = RetrofitImpl().buildRetrofit()
        val api = lRetrofit.create(Api.Mock::class.java)
        val objectCall = api.loadSimulatedById(idSimulado)
        val execute = objectCall.execute()
        val body = execute.body()
        if(body != null) {
            return body
        } else {
            throw Exception("Erro ao realizar a requisição")
        }
    }


    fun loadSimulatedQuestionsById(idSimulado : Long) : BaseResponse<MutableList<QuestaoResponse.Cadastrada>> {
        val lRetrofit = RetrofitImpl().buildRetrofit()
        val api = lRetrofit.create(Api.Mock::class.java)
        val objectCall = api.loadSimulatedQuestionsById(idSimulado)
        val execute = objectCall.execute()
        val body = execute.body()
        if(body != null) {
            return body
        } else {
            throw Exception("Erro ao realizar a requisição")
        }
    }

    fun loadSimulatedByUser(idUsuario : Long) : BaseResponse<MutableList<SimuladoResponse.SimuladoBase>> {
        val lRetrofit = RetrofitImpl().buildRetrofit()
        val api = lRetrofit.create(Api.Mock::class.java)
        val objectCall = api.loadSimulatedByUser(idUsuario)
        val execute = objectCall.execute()
        val body = execute.body()
        if(body != null) {
            return body
        } else {
            throw Exception("Erro ao realizar a requisição")
        }
    }

    fun saveSimulated(request : SimuladoRequest.RespostaSimuladoRequest) : BaseResponse<ResultadoResponse.Simulado> {
        val lRetrofit = RetrofitImpl().buildRetrofit()
        val api = lRetrofit.create(Api.Mock::class.java)
        val objectCall = api.saveSimulatedResponse(request)
        val execute = objectCall.execute()
        val body = execute.body()
        if(body != null) {
            return body
        } else {
            throw Exception("Erro ao realizar a requisição")
        }
    }

    fun deleteSimulated(request: ResultadoRequest.PorUsuarioESimulado) : BaseResponse<SimuladoResponse.SimuladoCompleto> {
        val lRetrofit = RetrofitImpl().buildRetrofit()
        val api = lRetrofit.create(Api.Mock::class.java)
        val objectCall = api.deleteSimulated(request)
        val execute = objectCall.execute()
        val body = execute.body()
        if(body != null) {
            return body
        } else {
            throw Exception("Erro ao realizar a requisição")
        }
    }
}