package com.ifsc.lages.sti.tcc.resources.result.mapper

import com.ifsc.lages.sti.tcc.model.result.ResultValue
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

        resultadoSimulado.resultadoGeral =
            ResultValue(
                simulado.resultadoGeral!!.erros,
                simulado.resultadoGeral!!.acertos,
                simulado.resultadoGeral!!.naoRespondidas,
                simulado.resultadoGeral!!.total
            )

        resultadoSimulado.resultadoMatematica =
            ResultValue(
                simulado.resultadoMatematica!!.erros,
                simulado.resultadoMatematica!!.acertos,
                simulado.resultadoMatematica!!.naoRespondidas,
                simulado.resultadoMatematica!!.total
            )

        resultadoSimulado.resultadoFundamentoComputacao =
            ResultValue(
                simulado.resultadoFundamentoComputacao!!.erros,
                simulado.resultadoFundamentoComputacao!!.acertos,
                simulado.resultadoFundamentoComputacao!!.naoRespondidas,
                simulado.resultadoFundamentoComputacao!!.total
            )

        resultadoSimulado.resultadoTecnologiaComputacao =
            ResultValue(
                simulado.resultadoTecnologiaComputacao!!.erros,
                simulado.resultadoTecnologiaComputacao!!.acertos,
                simulado.resultadoTecnologiaComputacao!!.naoRespondidas,
                simulado.resultadoTecnologiaComputacao!!.total
            )
        return resultadoSimulado
    }

}