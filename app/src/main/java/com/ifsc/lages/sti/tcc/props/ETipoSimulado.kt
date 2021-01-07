package com.ifsc.lages.sti.tcc.props

enum class ETipoSimulado(var codigo: Int, var descricao: String) {
    DEFAULT(0, "Personalizado"), ENADE(1, "ENADE"), POSCOMP(2, "POSCOMP");

    companion object {
        fun getEnun(aCod: Int): ETipoSimulado {
            for (lSituacao in values()) {
                if (lSituacao.codigo == aCod) {
                    return lSituacao
                }
            }
            return DEFAULT
        }

        fun getEnun(descricao: String?): ETipoSimulado {
            for (lSituacao in values()) {
                if (lSituacao.descricao.equals(descricao, ignoreCase = true)) {
                    return lSituacao
                }
            }
            return DEFAULT
        }
    }

}