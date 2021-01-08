package com.ifsc.lages.sti.tcc.resources.result.mapper

import com.ifsc.lages.sti.tcc.model.result.ResultoQualitativo
import com.ifsc.lages.sti.tcc.model.result.ResultadoSimulado
import com.ifsc.lages.sti.tcc.resources.result.ResultadoResponse
import com.ifsc.lages.sti.tcc.utilidades.mapper.MapperUtil

class MapperSimulado : MapperUtil<ResultadoResponse.Simulado, ResultadoSimulado>() {

    public override fun transform(simulado: ResultadoResponse.Simulado?): ResultadoSimulado {
        var resultadoSimulado = ResultadoSimulado()
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

    private fun transform(resultado: ResultadoResponse.Quantitativo): ResultoQualitativo? {
        var response = ResultoQualitativo()
        response.erros = resultado.erros
        response.acertos = resultado.acertos
        response.naoRespondidas = resultado.naoRespondidas
        response.total = resultado.total
        return response
    }
}