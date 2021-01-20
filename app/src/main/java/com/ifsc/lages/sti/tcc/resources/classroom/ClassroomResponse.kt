package com.ifsc.lages.sti.tcc.resources.classroom

import com.google.gson.annotations.SerializedName
import com.ifsc.lages.sti.tcc.resources.result.ResultadoResponse
import java.util.*

sealed class ClassroomResponse {

   class SalaResponse {
        @SerializedName(value = "Id")
        val id: Long? = null

        @SerializedName(value = "Nome")
        val nome: String? = null

        @SerializedName(value = "Descricao")
        val descricao: String? = null

        @SerializedName(value = "Professor")
        val professor: ProfessorResponse? = null

        @SerializedName(value = "MaxParticipantes")
        val maxParticipantes: Int? = null

        @SerializedName(value = "QtdParticipantes")
        val qtdParticipantes = 0

        @SerializedName(value = "DataCriacao")
        val dataCriacao: Date? = null

        @SerializedName(value = "Participando")
        val participando = false
    }

    class ProfessorResponse {
        @SerializedName(value = "Id")
        val id: Long? = null

        @SerializedName(value = "Nome")
        val nome: String? = null
    }

    class SimuladoBaseResponse {
        @SerializedName(value = "Id")
        val id: Long? = null

        @SerializedName(value = "idSala")
        val idSala: Long? = null

        @SerializedName(value = "Nome")
        val nome: String? = null

        @SerializedName(value = "Descricao")
        val descricao: String? = null

        @SerializedName(value = "DataInicio")
        val dataInicio: Date? = null

        @SerializedName(value = "dataCriacao")
        val dataCriacao: Date? = null

        @SerializedName(value = "DataFimSimulado")
        val dataFimSimulado: Date? = null

        @SerializedName(value = "TempoMaximo")
        val tempoMaximo: Long? = null

        @SerializedName(value = "IdUsuario")
        val idUsuario: Long? = null

        @SerializedName(value = "QuantidadeQuestoes")
        val quantidadeQuestoes: Int? = null

        @SerializedName(value = "TipoSimulado")
        val tipoSimulado: Int? = null

        @SerializedName(value = "SimuladoRespondido")
        val respondido = false

        @SerializedName(value = "ResultadoSimulado")
        val simuladoResultado: ResultadoResponse.SimuladoCompleto? = null

        @SerializedName(value = "QuantidadeResposta")
        val quantidadeResposta = 0
    }


}