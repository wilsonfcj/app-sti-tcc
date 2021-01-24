package com.ifsc.lages.sti.tcc.props

enum class EQuestion(var codigo: Int, var descricao: String) {
    ALTERNATIVA(1, "Alternativa"),
    DISCURSIVA(2, "Discursiva");

    companion object {
        fun getEnun(aCod: Int): EQuestion {
            for (lSituacao in values()) {
                if (lSituacao.codigo == aCod) {
                    return lSituacao
                }
            }
            return ALTERNATIVA
        }

        fun getEnun(descricao: String?): EQuestion {
            for (lSituacao in values()) {
                if (lSituacao.descricao.equals(descricao, ignoreCase = true)) {
                    return lSituacao
                }
            }
            return ALTERNATIVA
        }
    }

}