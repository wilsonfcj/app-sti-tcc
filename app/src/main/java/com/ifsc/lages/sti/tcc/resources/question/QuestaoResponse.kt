package com.ifsc.lages.sti.tcc.resources.question

import com.google.gson.annotations.SerializedName

open class QuestaoResponse {

    open class Cadastrada {
        @SerializedName(value = "Id")
        var id: Long? = null

        @SerializedName(value = "Ano")
        var ano: Int? = null

        @SerializedName(value = "Descricao")
        var descricao: String? = null

        @SerializedName(value = "Area")
        var area: Int? = null

        @SerializedName(value = "Prova")
        var prova: Int? = null

        @SerializedName(value = "Disciplina")
        var disciplina: Int? = null

        @SerializedName(value = "ComImagem")
        var imagem = false

        @SerializedName(value = "ImagemQuestao")
        var imagemQuestao: String? = null

        @SerializedName(value = "NumeroQuestao")
        var numeroQuestao: Int? = null

        @SerializedName(value = "TipoQuestao")
        var tipoQuestao: Int? = null

        @SerializedName(value = "AlternativasA")
        var alternativasA: String? = null

        @SerializedName(value = "AlternativasB")
        var alternativasB: String? = null

        @SerializedName(value = "AlternativasC")
        var alternativasC: String? = null

        @SerializedName(value = "AlternativasD")
        var alternativasD: String? = null

        @SerializedName(value = "AlternativasE")
        var alternativasE: String? = null

        @SerializedName(value = "AlternativaComImagem")
        var alternativaImagem = false
    }


    class QustaoGabaritoResponse : Cadastrada() {
        @SerializedName(value = "RespostaCorreta")
        var respostaCorreta: String? = null

        @SerializedName(value = "RespostaUsuario")
        var respostaUsuario: String? = null

        @SerializedName(value = "Correta")
        var isCorreta: Boolean? = null
    }


    open class Alternativas : Cadastrada() {
    }
}