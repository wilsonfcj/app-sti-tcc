package com.ifsc.lages.sti.tcc.resources.result

import br.edu.ifsc.cancontrol.resources.generics.RetrofitImpl
import com.ifsc.lages.sti.tcc.resources.Api
import com.ifsc.lages.sti.tcc.resources.generics.BaseResponse
import com.ifsc.lages.sti.tcc.resources.question.QuestaoResponse

class ResultadoService {

    fun loadResultSimulated(request: ResultadoRequest.PorUsuarioESimulado) : BaseResponse<ResultadoResponse.Simulado> {
        val lRetrofit = RetrofitImpl().buildRetrofit()
        val api = lRetrofit.create(Api.ResultSimulated::class.java)
        val objectCall = api.loadResultSimulated(request)
        val execute = objectCall.execute()
        val body = execute.body()
        if(body != null) {
            return body
        } else {
            throw Exception("Erro ao realizar a requisição")
        }
    }

    fun loadOverallResultBySimulated(request: ResultadoRequest.PorIdUsuarioETipoProva) : BaseResponse<ResultadoResponse.GeralUsuario> {
        val lRetrofit = RetrofitImpl().buildRetrofit()
        val api = lRetrofit.create(Api.ResultSimulated::class.java)
        val objectCall = api.loadOverallResultBySimulated(request)
        val execute = objectCall.execute()
        val body = execute.body()
        if(body != null) {
            return body
        } else {
            throw Exception("Erro ao realizar a requisição")
        }
    }

    fun loadOverallResult(idUsuario : Long) : BaseResponse<ResultadoResponse.GeralUsuario> {
        val lRetrofit = RetrofitImpl().buildRetrofit()
        val api = lRetrofit.create(Api.ResultSimulated::class.java)
        val objectCall = api.loadOverallResult(idUsuario)
        val execute = objectCall.execute()
        val body = execute.body()
        if(body != null) {
            return body
        } else {
            throw Exception("Erro ao realizar a requisição")
        }
    }

    fun loadLatterResult(idUsuario : Long) : BaseResponse<MutableList<ResultadoResponse.SimuladoCompleto>> {
        val lRetrofit = RetrofitImpl().buildRetrofit()
        val api = lRetrofit.create(Api.ResultSimulated::class.java)
        val objectCall = api.loadLatterResult(idUsuario)
        val execute = objectCall.execute()
        val body = execute.body()
        if(body != null) {
            return body
        } else {
            throw Exception("Erro ao realizar a requisição")
        }
    }

    fun loadFeedbackSimulated(request: ResultadoRequest.PorUsuarioESimulado) : BaseResponse<MutableList<QuestaoResponse.QustaoGabaritoResponse>> {
        val lRetrofit = RetrofitImpl().buildRetrofit()
        val api = lRetrofit.create(Api.ResultSimulated::class.java)
        val objectCall = api.loadFeedbackSimulated(request)
        val execute = objectCall.execute()
        val body = execute.body()
        if(body != null) {
            return body
        } else {
            throw Exception("Erro ao realizar a requisição")
        }
    }

    fun loadPerformanceMatters(request: ResultadoRequest.PorUsuarioESimulado) : BaseResponse<MutableList<ResultadoResponse.Disciplina>> {
        val lRetrofit = RetrofitImpl().buildRetrofit()
        val api = lRetrofit.create(Api.ResultSimulated::class.java)
        val objectCall = api.loadPerformanceMatters(request)
        val execute = objectCall.execute()
        val body = execute.body()
        if(body != null) {
            return body
        } else {
            throw Exception("Erro ao realizar a requisição")
        }
    }

    fun loadPerformanceMatters(idUsuario : Long) : BaseResponse<MutableList<ResultadoResponse.Disciplina>> {
        val lRetrofit = RetrofitImpl().buildRetrofit()
        val api = lRetrofit.create(Api.ResultSimulated::class.java)
        val objectCall = api.loadPerformanceMatters(idUsuario)
        val execute = objectCall.execute()
        val body = execute.body()
        if(body != null) {
            return body
        } else {
            throw Exception("Erro ao realizar a requisição")
        }
    }

    fun loadResultUserBySimulatedClassrrom(request: ResultadoRequest.PorUsuarioESimulado) : BaseResponse<MutableList<ResultadoResponse.SimuladoUsuario>> {
        val lRetrofit = RetrofitImpl().buildRetrofit()
        val api = lRetrofit.create(Api.ResultSimulated::class.java)
        val objectCall = api.loadResultUserBySimulatedClassrrom(request)
        val execute = objectCall.execute()
        val body = execute.body()
        if(body != null) {
            return body
        } else {
            throw Exception("Erro ao realizar a requisição")
        }
    }

    fun loadFeedbackNotResponseUser(request: ResultadoRequest.PorUsuarioESimulado) : BaseResponse<MutableList<QuestaoResponse.QustaoGabaritoResponse>> {
        val lRetrofit = RetrofitImpl().buildRetrofit()
        val api = lRetrofit.create(Api.ResultSimulated::class.java)
        val objectCall = api.loadFeedbackNotResponseUser(request)
        val execute = objectCall.execute()
        val body = execute.body()
        if(body != null) {
            return body
        } else {
            throw Exception("Erro ao realizar a requisição")
        }
    }
}