package com.ifsc.lages.sti.tcc.props

enum class EOptionsStart(var codigo: Int, var descricao: String) {
    INICIAR(0, "Iniciar");

    companion object {
        fun getEnun(aCod: Int): EOptionsStart {
            for (lSituacao in values()) {
                if (lSituacao.codigo == aCod) {
                    return lSituacao
                }
            }
            return INICIAR
        }

        fun getEnun(descricao: String?): EOptionsStart {
            for (lSituacao in values()) {
                if (lSituacao.descricao.equals(descricao, ignoreCase = true)) {
                    return lSituacao
                }
            }
            return INICIAR
        }
    }

}