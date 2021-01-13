package com.ifsc.lages.sti.tcc.resources.simulated.mapper

import com.ifsc.lages.sti.tcc.model.simulated.Question
import com.ifsc.lages.sti.tcc.resources.question.QuestaoResponse
import com.ifsc.lages.sti.tcc.utilidades.mapper.MapperUtil

class MapperQuestion : MapperUtil<QuestaoResponse.Cadastrada, Question>() {

    override fun transform(aObject: QuestaoResponse.Cadastrada?): Question {
        var result =  Question()
        result.id = aObject?.id
        result.ano = aObject?.ano
        result.descricao = aObject?.descricao
        result.area = aObject?.area
        result.prova = aObject?.prova
        result.disciplina = aObject?.disciplina
        result.imagem = aObject?.imagem!!
        result.imagemQuestao = aObject.imagemQuestao
        result.numeroQuestao = aObject.numeroQuestao
        result.tipoQuestao = aObject.tipoQuestao
        result.alternativasA = aObject.alternativasA
        result.alternativasB = aObject.alternativasB
        result.alternativasC = aObject.alternativasC
        result.alternativasD = aObject.alternativasD
        result.alternativasE = aObject.alternativasE
        result.alternativaImagem = aObject.alternativaImagem
        return result
    }
}