package com.ifsc.lages.sti.tcc.resources.simulated

import com.google.gson.annotations.SerializedName
import java.util.*

sealed class SimuladoRequest {

    class Register {
        @SerializedName(value = "Nome")
        var nome: String? = null

        @SerializedName(value = "Descricao")
        var descricao: String? = null

        @SerializedName(value = "DataInicio")
        var dataInicio: Date? = null

        @SerializedName(value = "DataFimSimulado")
        var dataFimSimulado: Date? = null

        @SerializedName(value = "TempoMaximo")
        var tempoMaximo: Long? = null

        @SerializedName(value = "IdUsuario")
        var idUsuario: Long? = null

        @SerializedName(value = "QuantidadeQuestoes")
        var quantidadeQuestoes: Int? = null

        @SerializedName(value = "TipoSimulado")
        var tipoSimulado: Int? = null

        @SerializedName(value = "AnoProva")
        var anoProva: Int? = null
    }

    class RespostaSimuladoRequest {
        @SerializedName(value = "IdSimulado")
        var idSimulado: Long? = null

        @SerializedName(value = "IdUsuario")
        var idUsuario: Long? = null

        @SerializedName(value = "Respostas")
        var respostas: List<RespostaQuestaoRequest>? = null
    }

    class RespostaQuestaoRequest {
        @SerializedName(value = "IdQuestao")
        var idQuestao: Long? = null

        @SerializedName(value = "RespostaQuestao")
        var respostaQuestao: String? = null

        @SerializedName(value = "TipoQuestao")
        var tipoQuestao: Int? = null
    }

}