package com.ifsc.lages.sti.tcc.resources.classroom

import br.edu.ifsc.cancontrol.resources.generics.RetrofitImpl
import com.ifsc.lages.sti.tcc.resources.Api
import com.ifsc.lages.sti.tcc.resources.generics.BaseResponse
import com.ifsc.lages.sti.tcc.resources.question.QuestaoResponse
import com.ifsc.lages.sti.tcc.resources.simulated.SimuladoRequest
import com.ifsc.lages.sti.tcc.resources.simulated.SimuladoResponse

class ClassroomService {

    fun createClassroom(request : ClassroomRequest.Register) : BaseResponse<ClassroomResponse.SalaResponse> {
        val lRetrofit = RetrofitImpl().buildRetrofit()
        val api = lRetrofit.create(Api.Classroom::class.java)
        val objectCall = api.createClassroom(request)
        val execute = objectCall.execute()
        val body = execute.body()
        if(body != null) {
            return body
        } else {
            throw Exception("Erro ao realizar a requisição")
        }
    }

    fun searchClassroom(idUsuario : Long) : BaseResponse<MutableList<ClassroomResponse.SalaResponse>> {
        val lRetrofit = RetrofitImpl().buildRetrofit()
        val api = lRetrofit.create(Api.Classroom::class.java)
        val objectCall = api.searchClassroom(idUsuario)
        val execute = objectCall.execute()
        val body = execute.body()
        if(body != null) {
            return body
        } else {
            throw Exception("Erro ao realizar a requisição")
        }
    }

    fun enterClassroom(request : ClassroomRequest.EnterClassroom) : BaseResponse<MutableList<ClassroomResponse.SimuladoBaseResponse>> {
        val lRetrofit = RetrofitImpl().buildRetrofit()
        val api = lRetrofit.create(Api.Classroom::class.java)
        val objectCall = api.enterClassroom(request)
        val execute = objectCall.execute()
        val body = execute.body()
        if(body != null) {
            return body
        } else {
            throw Exception("Erro ao realizar a requisição")
        }
    }

    fun deleteClassroom(request : ClassroomRequest.DeleteClassroom) : BaseResponse<ClassroomResponse.SalaResponse> {
        val lRetrofit = RetrofitImpl().buildRetrofit()
        val api = lRetrofit.create(Api.Classroom::class.java)
        val objectCall = api.deleteClassroom(request)
        val execute = objectCall.execute()
        val body = execute.body()
        if(body != null) {
            return body
        } else {
            throw Exception("Erro ao realizar a requisição")
        }
    }

}