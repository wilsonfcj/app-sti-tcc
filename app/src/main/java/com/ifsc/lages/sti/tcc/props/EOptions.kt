package com.ifsc.lages.sti.tcc.props

enum class EOptions(var codigo: Int, var descricao: String) {
    INICIAR(0, "Iniciar"),
    EDITAR(1, "Editar"),
    REMOVER(2, "Remover"),
    RESPONSE(3, "Resultado");

    companion object {
        fun getEnun(aCod: Int): EOptions {
            for (lSituacao in values()) {
                if (lSituacao.codigo == aCod) {
                    return lSituacao
                }
            }
            return INICIAR
        }

        fun getEnun(descricao: String?): EOptions {
            for (lSituacao in values()) {
                if (lSituacao.descricao.equals(descricao, ignoreCase = true)) {
                    return lSituacao
                }
            }
            return INICIAR
        }
    }

}