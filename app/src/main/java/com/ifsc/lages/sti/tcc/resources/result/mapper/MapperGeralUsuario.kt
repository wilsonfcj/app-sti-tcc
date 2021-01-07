package com.ifsc.lages.sti.tcc.resources.result.mapper

import com.ifsc.lages.sti.tcc.model.result.ResultOverall
import com.ifsc.lages.sti.tcc.model.result.ResultValue
import com.ifsc.lages.sti.tcc.resources.result.ResultadoResponse.GeralUsuario
import com.ifsc.lages.sti.tcc.utilidades.mapper.MapperUtil

class MapperGeralUsuario : MapperUtil<GeralUsuario?, ResultOverall?>() {

    public override fun transform(resultado: GeralUsuario?): ResultOverall? {
        var resultOverall = ResultOverall()

        resultOverall.idUsuario = resultado?.idUsuario
        resultOverall.nome = resultado?.nome
        resultOverall.resultadoGeral = ResultValue(resultado!!.resultadoGeral!!.erros, resultado.resultadoGeral!!.acertos, resultado.resultadoGeral!!.naoRespondidas, resultado.resultadoGeral!!.total)
        resultOverall.resultadoMatematica = ResultValue(resultado.resultadoMatematica!!.erros, resultado.resultadoMatematica!!.acertos, resultado.resultadoMatematica!!.naoRespondidas, resultado.resultadoMatematica!!.total)
        resultOverall.resultadoFundamentoComputacao = ResultValue( resultado.resultadoFundamentoComputacao!!.erros,  resultado.resultadoFundamentoComputacao!!.acertos, resultado.resultadoFundamentoComputacao!!.naoRespondidas,resultado.resultadoFundamentoComputacao!!.total)
        resultOverall.resultadoTecnologiaComputacao = ResultValue(resultado.resultadoTecnologiaComputacao!!.erros,resultado.resultadoTecnologiaComputacao!!.acertos, resultado.resultadoTecnologiaComputacao!!.naoRespondidas,resultado.resultadoTecnologiaComputacao!!.total)

        return resultOverall
    }


}