package com.ifsc.lages.sti.tcc.model.result

import io.realm.RealmObject
import java.io.Serializable

open class ResultQuantitative : RealmObject(), Serializable{

    var erros: Int? = null
    var acertos: Int? = null
    var naoRespondidas: Int? = null
    var total: Int? = null
}


