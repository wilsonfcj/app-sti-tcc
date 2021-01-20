package com.ifsc.lages.sti.tcc.props

enum class EOptionsResponse(var codigo: Int, var descricao: String) {

    RESULTADO(0, "Resultado Específico"),
    GABARITO(1, "Confrerir Gabarito");

    companion object {
        fun getEnun(aCod: Int): EOptionsResponse {
            for (lSituacao in values()) {
                if (lSituacao.codigo == aCod) {
                    return lSituacao
                }
            }
            return RESULTADO
        }

        fun getEnun(descricao: String?): EOptionsResponse {
            for (lSituacao in values()) {
                if (lSituacao.descricao.equals(descricao, ignoreCase = true)) {
                    return lSituacao
                }
            }
            return RESULTADO
        }
    }

}