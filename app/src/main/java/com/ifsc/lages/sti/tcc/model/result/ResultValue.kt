package com.ifsc.lages.sti.tcc.model.result

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.io.Serializable

class ResultValue (
    var erros: Int? = null,
    var acertos: Int? = null,
    var naoRespondidas: Int? = null,
    var total: Int? = null
) : Serializable


