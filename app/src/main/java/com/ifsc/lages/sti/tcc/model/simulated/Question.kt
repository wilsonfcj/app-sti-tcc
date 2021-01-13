package com.ifsc.lages.sti.tcc.model.simulated

import io.realm.RealmObject
import java.io.Serializable

open class Question : RealmObject(), Serializable {

    var id: Long? = null
    var ano: Int? = null
    var descricao: String? = null
    var area: Int? = null
    var prova: Int? = null
    var disciplina: Int? = null
    var imagem = false
    var imagemQuestao: String? = null
    var numeroQuestao: Int? = null
    var tipoQuestao: Int? = null
    var alternativasA: String? = null
    var alternativasB: String? = null
    var alternativasC: String? = null
    var alternativasD: String? = null
    var alternativasE: String? = null
    var alternativaImagem = false

}