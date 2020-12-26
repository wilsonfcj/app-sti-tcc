package com.ifsc.lages.sti.tcc.resources

import com.ifsc.lages.sti.tcc.resources.education.InstitutionResponse
import com.ifsc.lages.sti.tcc.resources.generics.BaseResponse
import com.ifsc.lages.sti.tcc.resources.question.QuestaoResponse
import com.ifsc.lages.sti.tcc.resources.result.ResultadoRequest
import com.ifsc.lages.sti.tcc.resources.result.ResultadoResponse
import com.ifsc.lages.sti.tcc.resources.simulated.SimuladoRequest
import com.ifsc.lages.sti.tcc.resources.simulated.SimuladoResponse
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

        @PUT("Alterar")
        fun update(@Body request : UserRequest.Register) : Call<BaseResponse<UserResponse.Login>>
    }

    interface EducationInstitution {
        @GET("BuscarTodasInstituicao")
        fun institutions() : Call<BaseResponse<MutableList<InstitutionResponse.EducationalInstitution>>>
    }

    interface Mock {
        @GET("CadastrarQuestoes")
        fun registerQuestion() : Call<BaseResponse<MutableList<QuestaoResponse.Cadastrada>>>

        @POST("GerarSimulado")
        fun createSimulated(@Body request : SimuladoRequest.Register) : Call<BaseResponse<SimuladoResponse.SimuladoCompleto>>

        @GET("BuscarSimuladoPorId")
        fun loadSimulatedById(@Query("idSimulado") idSimulado : Long) : Call<BaseResponse<SimuladoResponse.SimuladoCompleto>>

        @GET("BuscarQuestoesSimuladoPorId")
        fun loadSimulatedQuestionsById(@Query("idSimulado") idSimulado : Long) : Call<BaseResponse<MutableList<QuestaoResponse.Cadastrada>>>

        @GET("BuscarSimuladosUsuario")
        fun loadSimulatedByUser(@Query("idUsuario") idUsuario : Long) : Call<BaseResponse<MutableList<SimuladoResponse.SimuladoBase>>>

        @POST("SalvarRespostaSimulado")
        fun saveSimulatedResponse(@Body request : SimuladoRequest.RespostaSimuladoRequest) : Call<BaseResponse<ResultadoResponse.Simulado>>
    }

    interface ResultSimulated {
        @POST("BuscarResultadoSimulado")
        fun loadResultSimulated(@Body request: ResultadoRequest.PorUsuarioESimulado) : Call<BaseResponse<ResultadoResponse.Simulado>>

        @POST("BuscarResultadoGeralPorProva")
        fun loadOverallResultBySimulated(@Body request: ResultadoRequest.PorIdUsuarioETipoProva) : Call<BaseResponse<ResultadoResponse.GeralUsuario>>

        @GET("BuscarResultadoGeral")
        fun loadOverallResult(@Query("idUsuario") idUsuario : Long) : Call<BaseResponse<ResultadoResponse.GeralUsuario>>

        @GET("BuscarUltimosResultados")
        fun loadLatterResult(@Query("idUsuario") idUsuario : Long) : Call<BaseResponse<ResultadoResponse.Simulado>>

        @POST("BuscarGabaritoPorSimulado")
        fun loadFeedbackSimulated(@Body request: ResultadoRequest.PorUsuarioESimulado) : Call<BaseResponse<MutableList<QuestaoResponse.QustaoGabaritoResponse>>>
    }
}