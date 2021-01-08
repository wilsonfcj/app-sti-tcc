package com.ifsc.lages.sti.tcc.resources.user

import br.edu.ifsc.cancontrol.resources.generics.RetrofitImpl
import com.ifsc.lages.sti.tcc.resources.Api
import com.ifsc.lages.sti.tcc.resources.generics.BaseResponse

class UserService {

    fun login(request : UserRequest.Login) : BaseResponse<UserResponse.Login> {
        val lRetrofit = RetrofitImpl().buildRetrofit()
        val api = lRetrofit.create(Api.User::class.java)
        val objectCall = api.login(request)
        val execute = objectCall.execute()
        val body = execute.body()
        if(body != null) {
            return body
        } else {
            throw Exception("Erro ao realizar a requisição")
        }
    }

    fun register(request : UserRequest.Register) : BaseResponse<UserResponse.Login> {
        val lRetrofit = RetrofitImpl().buildRetrofit()
        val api = lRetrofit.create(Api.User::class.java)
        val objectCall = api.register(request)
        val execute = objectCall.execute()
        val body = execute.body()
        if(body != null) {
            return body
        } else {
            throw Exception("Erro ao realizar o registro do usuário")
        }
    }

    fun update(request : UserRequest.Register) : BaseResponse<UserResponse.Login> {
        val lRetrofit = RetrofitImpl().buildRetrofit()
        val api = lRetrofit.create(Api.User::class.java)
        val objectCall = api.update(request)
        val execute = objectCall.execute()
        val body = execute.body()
        if(body != null) {
            return body
        } else {
            throw Exception("Erro ao atualizar o registro")
        }
    }

}