package com.ifsc.lages.sti.tcc.resources.result.mapper

import com.ifsc.lages.sti.tcc.model.result.ResultOverall
import com.ifsc.lages.sti.tcc.model.result.ResultoQualitativo
import com.ifsc.lages.sti.tcc.resources.result.ResultadoResponse
import com.ifsc.lages.sti.tcc.resources.result.ResultadoResponse.GeralUsuario
import com.ifsc.lages.sti.tcc.utilidades.mapper.MapperUtil

class MapperGeralUsuario : MapperUtil<GeralUsuario?, ResultOverall?>() {

    public override fun transform(resultado: GeralUsuario?): ResultOverall? {
        var resultOverall = ResultOverall()

        resultOverall.idUsuario = resultado?.idUsuario
        resultOverall.nome = resultado?.nome
        resultOverall.resultadoGeral = transform(resultado!!.resultadoGeral!!)
        resultOverall.resultadoMatematica = transform(resultado!!.resultadoMatematica!!)
        resultOverall.resultadoFundamentoComputacao = transform(resultado!!.resultadoFundamentoComputacao!!)
        resultOverall.resultadoTecnologiaComputacao = transform(resultado!!.resultadoTecnologiaComputacao!!)
        return resultOverall
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