package com.ifsc.lages.sti.tcc.resources.result.mapper

import com.ifsc.lages.sti.tcc.model.result.ResultQuantitative
import com.ifsc.lages.sti.tcc.model.result.ResultSimulated
import com.ifsc.lages.sti.tcc.resources.result.ResultadoResponse
import com.ifsc.lages.sti.tcc.utilidades.mapper.MapperUtil

class MapperResultSimulated : MapperUtil<ResultadoResponse.Simulado, ResultSimulated>() {

    public override fun transform(simulado: ResultadoResponse.Simulado?): ResultSimulated {
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