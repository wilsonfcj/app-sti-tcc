package com.ifsc.lages.sti.tcc.resources.result.mapper

import com.ifsc.lages.sti.tcc.model.result.ResultMatters
import com.ifsc.lages.sti.tcc.model.result.ResultQuantitative
import com.ifsc.lages.sti.tcc.model.result.ResultSimulated
import com.ifsc.lages.sti.tcc.resources.result.ResultadoResponse
import com.ifsc.lages.sti.tcc.resources.simulated.mapper.MapperQuestion
import com.ifsc.lages.sti.tcc.utilidades.mapper.MapperUtil
import io.realm.RealmList

class MapperResultSimulatedFull : MapperUtil<ResultadoResponse.SimuladoCompleto, ResultSimulated>() {

    public override fun transform(simulado: ResultadoResponse.SimuladoCompleto?): ResultSimulated {
        var resultadoSimulado = ResultSimulated()
        resultadoSimulado.idSimulado = simulado!!.idSimulado
        resultadoSimulado.nome = simulado.nome
        resultadoSimulado.descricao = simulado.descricao
        resultadoSimulado.tipoSimulado = simulado.tipoSimulado
        resultadoSimulado.dataEnvio = simulado.dataEnvio
        resultadoSimulado.dataCriacao = simulado.dataCriacao

        resultadoSimulado.resultadoGeral = transform(simulado.resultadoGeral!!)
        resultadoSimulado.resultadoMatematica = transform(simulado.resultadoMatematica!!)
        resultadoSimulado.resultadoFundamentoComputacao = transform(simulado.resultadoFundamentoComputacao!!)
        resultadoSimulado.resultadoTecnologiaComputacao = transform(simulado.resultadoTecnologiaComputacao!!)

        if(simulado.disciplinas.isNullOrEmpty()) {
            resultadoSimulado.resultadoMatters
        }

        if(simulado.disciplinas.isNullOrEmpty().not()) {
            resultadoSimulado.resultadoMatters = RealmList()
            resultadoSimulado.resultadoMatters!!.addAll(MapperResultMatters().transform(simulado.disciplinas!!)!!)
            for (result in resultadoSimulado.resultadoMatters!!) {
                result._id = "${resultadoSimulado.idSimulado}${result.disciplinaCod}"
            }
        }

        return resultadoSimulado
    }

    private fun transform(resultado: ResultadoResponse.Quantitativo): ResultQuantitative? {
        var response = ResultQuantitative()
        response.erros = resultado.erros
        response.acertos = resultado.acertos
        response.naoRespondidas = resultado.naoRespondidas
        response.total = resultado.total
        return response
    }
}