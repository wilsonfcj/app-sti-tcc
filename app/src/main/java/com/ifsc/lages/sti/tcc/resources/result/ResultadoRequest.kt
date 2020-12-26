package com.ifsc.lages.sti.tcc.resources.result

import com.google.gson.annotations.SerializedName

sealed class ResultadoRequest {

    class PorUsuarioESimulado {
        @SerializedName(value = "IdSimulado")
        var idSimulado: Long? = null

        @SerializedName(value = "IdUsuario")
        var idUsuario: Long? = null
    }

    class PorIdUsuarioETipoProva {
        @SerializedName(value = "IdUsuario")
        var idUsuario: Long = 0

        @SerializedName(value = "TipoSimulado")
        var tipoSimulado = 0
    }

}
