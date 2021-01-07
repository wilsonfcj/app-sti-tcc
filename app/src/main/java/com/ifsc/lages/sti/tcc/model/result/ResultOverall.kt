package com.ifsc.lages.sti.tcc.model.result

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.io.Serializable

class ResultOverall : Serializable {

    var _id: Long? = null
    var idUsuario: Long? = null
    var nome: String? = null
    var simuladosRespondidos: Int? = null
    var resultadoGeral: ResultValue? = null
    var resultadoMatematica: ResultValue? = null
    var resultadoFundamentoComputacao: ResultValue? = null
    var resultadoTecnologiaComputacao: ResultValue? = null
}