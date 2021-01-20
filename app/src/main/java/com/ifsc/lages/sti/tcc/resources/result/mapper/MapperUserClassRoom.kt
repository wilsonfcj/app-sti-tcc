package com.ifsc.lages.sti.tcc.resources.result.mapper
import com.ifsc.lages.sti.tcc.model.result.ResultQuantitative
import com.ifsc.lages.sti.tcc.model.result.ResultSimulated
import com.ifsc.lages.sti.tcc.model.result.ResultUser
import com.ifsc.lages.sti.tcc.resources.result.ResultadoResponse
import com.ifsc.lages.sti.tcc.utilidades.mapper.MapperUtil
import java.util.ArrayList

class MapperUserClassRoom : MapperUtil<ResultadoResponse.SimuladoUsuario, ResultUser>()   {

    override fun transform(resultado: ResultadoResponse.SimuladoUsuario?) : ResultUser {
        var resultOverall = ResultUser()
        resultOverall.idUsuario = resultado?.idUsuario
        resultOverall.idSimulado = resultado?.idSimulado
        resultOverall.nome = resultado?.nome
        resultOverall.dataEnvio = resultado?.dataEnvio
        resultOverall.tipoSimulado = resultado?.tipoSimulado
        resultOverall.resultadoGeral = transform(resultado!!.resultadoGeral!!)
        resultOverall.resultadoMatematica = transform(resultado!!.resultadoMatematica!!)
        resultOverall.resultadoFundamentoComputacao = transform(resultado!!.resultadoFundamentoComputacao!!)
        resultOverall.resultadoTecnologiaComputacao = transform(resultado!!.resultadoTecnologiaComputacao!!)
        return resultOverall
    }

//    override fun transform(resultado: MutableList<ResultadoResponse.SimuladoUsuario>) : MutableList<ResultUser> {
//        val lList: MutableList<ResultUser> = ArrayList()
//        for (lFrom in resultado) {
//            transform(lFrom)?.let { lList.add(it) }
//        }
//        return lList
//    }

    private fun transform(resultado: ResultadoResponse.Quantitativo): ResultQuantitative? {
        var response = ResultQuantitative()
        response.erros = resultado.erros
        response.acertos = resultado.acertos
        response.naoRespondidas = resultado.naoRespondidas
        response.total = resultado.total
        return response
    }


}