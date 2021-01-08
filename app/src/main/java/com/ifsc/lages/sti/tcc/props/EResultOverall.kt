package com.ifsc.lages.sti.tcc.props

enum class EResultOverall(var codigo: Int, var situacao: String) {
    GERAL(1, "Geral"),
    ENADE(2, "Enade"),
    POSCOMP(3, "Poscomp"),
    PERSONALIZADO(4, "Tecnologia da Computação");

    fun getEnum(aCod: Int): EResultOverall {
        for (lSituacao in values()) {
            if (lSituacao.codigo == aCod) {
                return lSituacao
            }
        }
        return GERAL
    }

    fun getEnum(descricao: String?): EResultOverall {
        for (lSituacao in values()) {
            if (lSituacao.situacao.equals(descricao, ignoreCase = true)) {
                return lSituacao
            }
        }
        return GERAL
    }

}