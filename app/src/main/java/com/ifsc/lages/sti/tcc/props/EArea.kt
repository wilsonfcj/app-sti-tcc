package com.ifsc.lages.sti.tcc.props

enum class EArea(var codigo: Int, var situacao: String) {
    DEFAULT(0, "Indefinda"),
    MATEMATICA(1, "Matemática"),
    FUNDAMENTOS_DE_COMPUTACAO(2, "Fundamentos de Computação"),
    TECNOLOGIA_DA_COMPUTACAO(3, "Tecnologia da Computação");

    companion object {
        fun getEnum(aCod: Int): EArea {
            for (lSituacao in values()) {
                if (lSituacao.codigo == aCod) {
                    return lSituacao
                }
            }
            return DEFAULT
        }

        fun getEnum(descricao: String?): EArea {
            for (lSituacao in values()) {
                if (lSituacao.situacao.equals(descricao, ignoreCase = true)) {
                    return lSituacao
                }
            }
            return DEFAULT
        }
    }

}