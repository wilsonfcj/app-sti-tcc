package com.ifsc.lages.sti.tcc.props

enum class EOptionsStudent(var codigo: Int, var descricao: String) {

    GERAL(0, "Resultado Geral"),
    CONFERIR_GABARITO(3, "Conferir Gaberito");


    companion object {
        fun getEnun(aCod: Int): EOptionsStudent {
            for (lSituacao in values()) {
                if (lSituacao.codigo == aCod) {
                    return lSituacao
                }
            }
            return GERAL
        }

        fun getEnun(descricao: String?): EOptionsStudent {
            for (lSituacao in values()) {
                if (lSituacao.descricao.equals(descricao, ignoreCase = true)) {
                    return lSituacao
                }
            }
            return GERAL
        }
    }

}