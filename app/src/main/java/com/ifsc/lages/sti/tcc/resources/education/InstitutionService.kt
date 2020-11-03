package com.ifsc.lages.sti.tcc.resources.education

import br.edu.ifsc.cancontrol.resources.generics.RetrofitImpl
import com.ifsc.lages.sti.tcc.resources.Api
import com.ifsc.lages.sti.tcc.resources.generics.BaseResponse

class InstitutionService {

    fun queryInstitution() : BaseResponse<MutableList<InstitutionResponse.EducationalInstitution>> {
        val lRetrofit = RetrofitImpl().buildRetrofit()
        val api = lRetrofit.create(Api.EducationInstitution::class.java)
        val objectCall = api.institutions()
        val execute = objectCall.execute()
        val body = execute.body()
        if(body != null) {
            return body
        } else {
            throw Exception("Erro ao realizar a requisição")
        }
    }

}