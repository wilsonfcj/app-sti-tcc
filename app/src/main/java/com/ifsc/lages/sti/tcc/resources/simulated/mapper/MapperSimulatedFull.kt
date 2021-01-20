package com.ifsc.lages.sti.tcc.resources.simulated.mapper

import com.ifsc.lages.sti.tcc.model.simulated.Simulated
import com.ifsc.lages.sti.tcc.resources.result.mapper.MapperResultMatters
import com.ifsc.lages.sti.tcc.resources.result.mapper.MapperResultSimulated
import com.ifsc.lages.sti.tcc.resources.result.mapper.MapperResultSimulatedFull
import com.ifsc.lages.sti.tcc.resources.simulated.SimuladoResponse
import com.ifsc.lages.sti.tcc.utilidades.mapper.MapperUtil
import io.realm.RealmList

class MapperSimulatedFull : MapperUtil<SimuladoResponse.SimuladoCompleto, Simulated>() {

     override fun transform(simulado: SimuladoResponse.SimuladoCompleto?): Simulated {
        var resultadoSimulado = Simulated()
        resultadoSimulado._id = simulado!!.id
        resultadoSimulado.idSala = simulado.idSala
        resultadoSimulado.nome = simulado.nome
        resultadoSimulado.descricao = simulado.descricao
        resultadoSimulado.dataInicio = simulado.dataInicio
        resultadoSimulado.dataCriacao = simulado.dataCriacao
        resultadoSimulado.dataFimSimulado = simulado.dataFimSimulado
        resultadoSimulado.tempoMaximo = simulado.tempoMaximo
        resultadoSimulado.idUsuario = simulado.idUsuario
        resultadoSimulado.quantidadeQuestoes = simulado.quantidadeQuestoes
        resultadoSimulado.tipoSimulado = simulado.tipoSimulado
        resultadoSimulado.respondido = simulado.respondido

        if(simulado.simuladoResultado != null) {
            resultadoSimulado.simuladoResultado =
                MapperResultSimulatedFull().transform(simulado.simuladoResultado)
            resultadoSimulado.simuladoResultado!!._id = simulado.id
        }

        resultadoSimulado.quantidadeResposta = simulado.quantidadeResposta
        return resultadoSimulado
    }

}