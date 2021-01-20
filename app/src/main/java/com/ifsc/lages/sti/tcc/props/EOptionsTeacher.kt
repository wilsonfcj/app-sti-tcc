package com.ifsc.lages.sti.tcc.props

enum class EOptionsTeacher(var codigo: Int, var descricao: String) {
    GERAL(0, "Resultado Geral"),
    REPOSTA_ALUNO(1, "Resposta Alunos"),
    CONFERIR_GABARITO(2, "Conferir gaberito");


    companion object {
        fun getEnun(aCod: Int): EOptionsTeacher {
            for (lSituacao in values()) {
                if (lSituacao.codigo == aCod) {
                    return lSituacao
                }
            }
            return GERAL
        }

        fun getEnun(descricao: String?): EOptionsTeacher {
            for (lSituacao in values()) {
                if (lSituacao.descricao.equals(descricao, ignoreCase = true)) {
                    return lSituacao
                }
            }
            return GERAL
        }
    }

}