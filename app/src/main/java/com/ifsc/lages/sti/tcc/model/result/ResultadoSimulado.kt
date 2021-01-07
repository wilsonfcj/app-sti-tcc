package com.ifsc.lages.sti.tcc.model.result

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.io.Serializable
import java.util.*

open class ResultadoSimulado : Serializable {


    var _id: Long? = null
    var idUsuario: Long? = null
    var idSimulado: Long? = null
    var nome: String? = null
    var descricao: String? = null
    var dataCriacao: Date? = null
    var dataEnvio: Date? = null
    var tipoSimulado: Int? = null

    var resultadoGeral: ResultValue? = null
    var resultadoMatematica: ResultValue? = null
    var resultadoFundamentoComputacao: ResultValue? = null
    var resultadoTecnologiaComputacao: ResultValue? = null
}