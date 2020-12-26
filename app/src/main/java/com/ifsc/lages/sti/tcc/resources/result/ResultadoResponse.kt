package com.ifsc.lages.sti.tcc.resources.result

import com.google.gson.annotations.SerializedName
import java.util.*

sealed class ResultadoResponse {

    class Quantitativo {
        @SerializedName(value = "Erros")
        var erros: Int? = null

        @SerializedName(value = "Acertos")
        var acertos: Int? = null

        @SerializedName(value = "NaoRespondidas")
        var naoRespondidas: Int? = null

        @SerializedName(value = "TotalQuestao")
        var total: Int? = null
    }

    open class Base {
        @SerializedName(value = "ResultadoGeral")
        var resultadoGeral: Quantitativo? = null

        @SerializedName(value = "ResultadoMatematica")
        var resultadoMatematica: Quantitativo? = null

        @SerializedName(value = "ResultadoFundamentos")
        var resultadoFundamentoComputacao: Quantitativo? = null

        @SerializedName(value = "ResultadoTecnologia")
        var resultadoTecnologiaComputacao: Quantitativo? = null
    }

    class GeralUsuario : Base() {
        @SerializedName(value = "IdUsuario")
        var idUsuario: Long? = null

        @SerializedName(value = "Nome")
        var nome: String? = null

        @SerializedName(value = "SimuladosRespondidos")
        var simuladosRespondidos: Int? = null

    }

    open class Simulado : Base() {
        @SerializedName(value = "IdSimulado")
        var idSimulado: Long? = null

        @SerializedName(value = "Nome")
        var nome: String? = null

        @SerializedName(value = "Descricao")
        var descricao: String? = null

        @SerializedName(value = "DataCriacao")
        var dataCriacao: Date? = null

        @SerializedName(value = "DataEnvio")
        var dataEnvio: Date? = null

        @SerializedName(value = "TipoSimulado")
        var tipoSimulado: Int? = null

    }

}