package com.ifsc.lages.sti.tcc.resources.simulated

import com.google.gson.annotations.SerializedName
import java.io.Serializable

sealed class SimuladoRequest {

    class Register : Serializable {
        @SerializedName(value = "IdSala")
        var idSala: Long? = null

        @SerializedName(value = "Nome")
        var nome: String? = null

        @SerializedName(value = "Descricao")
        var descricao: String? = null

        @SerializedName(value = "DataInicio")
        var dataInicio: String? = null

        @SerializedName(value = "DataFimSimulado")
        var dataFimSimulado: String? = null

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

        @SerializedName(value = "ConfiguracaoPoscomp")
        var sumuladoConfigPoscomp: PoscompInfosRegister? = null

        @SerializedName(value = "ConfiguracaoEnade")
        var sumuladoConfigEnade: EnadeInfosRegister? = null
    }

    class PoscompInfosRegister {
        @SerializedName(value = "QtdFundamentos")
        var qtdFundamentos = 0

        @SerializedName(value = "QtdMatematica")
        var qtdMatematica = 0

        @SerializedName(value = "QtdTecnologia")
        var qtdTecnologia = 0
    }

    class EnadeInfosRegister {
        @SerializedName(value = "QtdFormacaoGeral")
        var qtdFormacaoGeral = 0

        @SerializedName(value = "QtdFormacaoEspecifica")
        var qtdFormacaoEspecifica = 0
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

    class ClassroomRequest {
        @SerializedName(value = "IdSala")
        var idSala: Long? = null

        @SerializedName(value = "IdUsuario")
        var idUsuario: Long? = null
    }

}