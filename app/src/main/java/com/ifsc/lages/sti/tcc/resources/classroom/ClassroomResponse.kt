package com.ifsc.lages.sti.tcc.resources.classroom

import com.google.gson.annotations.SerializedName
import com.ifsc.lages.sti.tcc.resources.result.ResultadoResponse
import java.util.*

sealed class ClassroomResponse {
   class SalaResponse {
        @SerializedName(value = "Id")
        private val id: Long? = null

        @SerializedName(value = "Nome")
        private val nome: String? = null

        @SerializedName(value = "Descricao")
        private val descricao: String? = null

        @SerializedName(value = "Professor")
        private val professor: ProfessorResponse? = null

        @SerializedName(value = "MaxParticipantes")
        private val maxParticipantes: Int? = null

        @SerializedName(value = "QtdParticipantes")
        private val qtdParticipantes = 0

        @SerializedName(value = "DataCriacao")
        private val dataCriacao: Date? = null

        @SerializedName(value = "Participando")
        private val participando = false
    }

    class ProfessorResponse {
        @SerializedName(value = "Id")
        private val id: Long? = null

        @SerializedName(value = "Nome")
        private val nome: String? = null
    }

    class SimuladoBaseResponse {
        @SerializedName(value = "Id")
        private val id: Long? = null

        @SerializedName(value = "idSala")
        private val idSala: Long? = null

        @SerializedName(value = "Nome")
        private val nome: String? = null

        @SerializedName(value = "Descricao")
        private val descricao: String? = null

        @SerializedName(value = "DataInicio")
        private val dataInicio: Date? = null

        @SerializedName(value = "dataCriacao")
        private val dataCriacao: Date? = null

        @SerializedName(value = "DataFimSimulado")
        private val dataFimSimulado: Date? = null

        @SerializedName(value = "TempoMaximo")
        private val tempoMaximo: Long? = null

        @SerializedName(value = "IdUsuario")
        private val idUsuario: Long? = null

        @SerializedName(value = "QuantidadeQuestoes")
        private val quantidadeQuestoes: Int? = null

        @SerializedName(value = "TipoSimulado")
        private val tipoSimulado: Int? = null

        @SerializedName(value = "SimuladoRespondido")
        private val respondido = false

        @SerializedName(value = "ResultadoSimulado")
        private val simuladoResultado: ResultadoResponse.SimuladoCompleto? = null

        @SerializedName(value = "QuantidadeResposta")
        private val quantidadeResposta = 0
    }


}