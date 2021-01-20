package com.ifsc.lages.sti.tcc.model.result

import io.realm.Realm
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.io.Serializable
import java.util.*

open class ResultUser : Serializable {

    var idUsuario: Long? = null
    var idSimulado: Long? = null
    var nome: String? = null
    var dataEnvio: Date? = null
    var tipoSimulado: Int? = null
    var resultadoGeral: ResultQuantitative? = null
    var resultadoMatematica: ResultQuantitative? = null
    var resultadoFundamentoComputacao: ResultQuantitative? = null
    var resultadoTecnologiaComputacao: ResultQuantitative? = null
}