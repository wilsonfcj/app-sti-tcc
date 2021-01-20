package com.ifsc.lages.sti.tcc.resources.result.mapper

import com.ifsc.lages.sti.tcc.model.question.QuestionFeedback
import com.ifsc.lages.sti.tcc.resources.question.QuestaoResponse

class MapperQuestionFeedback {

    fun transform(resultado: QuestaoResponse.QustaoGabaritoResponse?) : QuestionFeedback {
        var resultOverall = QuestionFeedback()
        resultOverall.id = resultado?.id
        resultOverall.ano = resultado?.ano
        resultOverall.descricao = resultado?.descricao
        resultOverall.area = resultado?.area
        resultOverall.prova = resultado?.prova
        resultOverall.disciplina = resultado?.disciplina
        resultOverall.imagem = resultado?.imagem!!
        resultOverall.imagemQuestao = resultado.imagemQuestao
        resultOverall.numeroQuestao = resultado.numeroQuestao
        resultOverall.tipoQuestao = resultado.tipoQuestao
        resultOverall.alternativasA = resultado.alternativasA
        resultOverall.alternativasB = resultado.alternativasB
        resultOverall.alternativasC = resultado.alternativasC
        resultOverall.alternativasD = resultado.alternativasD
        resultOverall.alternativasE = resultado.alternativasE
        resultOverall.alternativaImagem  = resultado.alternativaImagem

        resultOverall.respostaCorreta = resultado.respostaCorreta
        resultOverall.respostaUsuario = resultado.respostaUsuario
        resultOverall.isCorreta = resultado.isCorreta!!
        return resultOverall
    }

    fun transform(resultado: MutableList<QuestaoResponse.QustaoGabaritoResponse>) : MutableList<QuestionFeedback> {
        val lList: MutableList<QuestionFeedback> = ArrayList()
        for (lFrom in resultado) {
            transform(lFrom).let { lList.add(it) }
        }
        return lList
    }

}