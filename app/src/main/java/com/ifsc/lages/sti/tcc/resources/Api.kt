package com.ifsc.lages.sti.tcc.resources

import com.ifsc.lages.sti.tcc.resources.education.InstitutionResponse
import com.ifsc.lages.sti.tcc.resources.generics.BaseResponse
import com.ifsc.lages.sti.tcc.resources.user.UserRequest
import com.ifsc.lages.sti.tcc.resources.user.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface Api {

    interface User {
        @POST("Login")
        fun login(@Body request : UserRequest.Login) : Call<BaseResponse<UserResponse.Login>>

        @POST("Cadastro")
        fun register(@Body request : UserRequest.Register) : Call<BaseResponse<UserResponse.Login>>
    }

    interface EducationInstitution {
        @GET("BuscarTodasInstituicao")
        fun institutions() : Call<BaseResponse<MutableList<InstitutionResponse.EducationalInstitution>>>
    }
}