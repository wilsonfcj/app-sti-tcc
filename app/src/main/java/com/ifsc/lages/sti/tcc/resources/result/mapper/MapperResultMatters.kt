package com.ifsc.lages.sti.tcc.resources.result.mapper

import com.ifsc.lages.sti.tcc.model.result.ResultMatters
import com.ifsc.lages.sti.tcc.model.result.ResultQuantitative
import com.ifsc.lages.sti.tcc.model.result.ResultSimulated
import com.ifsc.lages.sti.tcc.resources.result.ResultadoResponse
import com.ifsc.lages.sti.tcc.utilidades.mapper.MapperUtil

class MapperResultMatters : MapperUtil<ResultadoResponse.Disciplina, ResultMatters>() {

    override fun transform(aObject: ResultadoResponse.Disciplina?): ResultMatters {
        var resultMatters = ResultMatters()
        resultMatters.disciplinaCod = aObject?.disciplinaCod
        resultMatters.disciplinaDes = aObject?.disciplinaDes

        resultMatters.erros = aObject?.erros
        resultMatters.acertos = aObject?.acertos
        resultMatters.naoRespondidas = aObject?.naoRespondidas
        resultMatters.total = aObject?.total

        return resultMatters
    }
}