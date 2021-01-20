package com.ifsc.lages.sti.tcc.resources.simulated

import com.google.gson.annotations.SerializedName
import com.ifsc.lages.sti.tcc.resources.question.QuestaoResponse
import com.ifsc.lages.sti.tcc.resources.result.ResultadoResponse
import java.util.*
import kotlin.collections.ArrayList

sealed class SimuladoResponse {

    open class SimuladoBase {
        @SerializedName(value = "Id")
        var id: Long? = null

        @SerializedName(value = "idSala")
        var idSala: Long? = null

        @SerializedName(value = "Nome")
        var nome: String? = null

        @SerializedName(value = "Descricao")
        var descricao: String? = null

        @SerializedName(value = "DataInicio")
        var dataInicio: Date? = null

        @SerializedName(value = "dataCriacao")
        var dataCriacao: Date? = null

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

        @SerializedName(value = "SimuladoRespondido")
        var respondido = false

        @SerializedName(value = "ResultadoSimulado")
        var simuladoResultado: ResultadoResponse.SimuladoCompleto? = null

        @SerializedName(value = "QuantidadeResposta")
        var quantidadeResposta : Int? = null
    }

    class SimuladoCompleto: SimuladoBase() {
        @SerializedName(value = "Questoes")
        var questoes: MutableList<QuestaoResponse.Cadastrada>? = null
    }

    class SimuladoQuestoes {
        @SerializedName(value = "Questoes")
        var questoes: List<QuestaoResponse>? = null
    }


}