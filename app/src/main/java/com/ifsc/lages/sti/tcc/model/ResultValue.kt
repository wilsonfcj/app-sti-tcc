package com.ifsc.lages.sti.tcc.model

import java.io.Serializable

class ResultValue (
    var erros: Int? = null,
    var acertos: Int? = null,
    var naoRespondidas: Int? = null,
    var total: Int? = null
) : Serializable