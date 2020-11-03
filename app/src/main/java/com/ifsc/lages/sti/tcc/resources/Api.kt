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

//        @POST("farmer")
//        fun registerUser(@Body request : UserRequest.UserBase) : Call<UserResponse.Farmer>
//
//        @PUT("farmer/{id}")
//        fun updateUser(@Path("id") id : Long?, @Body request : UserRequest.UserBase) : Call<UserRequest.UserBase>
//
//        @GET("farmer/cpf/")
//        fun queryUser(@Query("cpf") cpf : String) : Call<UserResponse.Farmer>
    }

    interface EducationInstitution {
        @GET("BuscarTodasInstituicao")
        fun institutions() : Call<BaseResponse<MutableList<InstitutionResponse.EducationalInstitution>>>
    }
}