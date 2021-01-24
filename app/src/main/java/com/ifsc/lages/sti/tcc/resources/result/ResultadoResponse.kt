package com.ifsc.lages.sti.tcc.resources.result

import com.google.gson.annotations.SerializedName
import java.util.*

sealed class ResultadoResponse {

    open class Quantitativo {
        @SerializedName(value = "Erros")
        var erros: Int? = null

        @SerializedName(value = "Acertos")
        var acertos: Int? = null

        @SerializedName(value = "NaoRespondidas")
        var naoRespondidas: Int? = null

        @SerializedName(value = "TotalQuestao")
        var total: Int? = null
    }

    class Disciplina : Quantitativo() {
        @SerializedName(value = "Disciplina")
        var disciplinaCod: Int? = null

        @SerializedName(value = "DisciplinaNome")
        var disciplinaDes: String? = null
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


    class SimuladoUsuario : Base() {
        @SerializedName(value = "IdUsuario")
        var idUsuario: Long? = null

        @SerializedName(value = "IdSimulado")
        var idSimulado: Long? = null

        @SerializedName(value = "Nome")
        var nome: String? = null

        @SerializedName(value = "DataEnvio")
        var dataEnvio: Date? = null

        @SerializedName(value = "TipoSimulado")
        var tipoSimulado: Int? = null

        @SerializedName(value = "ResultadoDisciplinas")
        val disciplinas: MutableList<Disciplina>? = null
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

    open class SimuladoCompleto : Simulado() {
        @SerializedName(value = "ResultadoDisciplinas")
        val disciplinas: MutableList<Disciplina>? = null
    }

}