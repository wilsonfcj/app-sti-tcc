package com.ifsc.lages.sti.tcc.props

enum class ESupport (var codigo: Int, var descricao: String) {

    SUGESTAO(1, "Sugestão"),
    AJUDA(2, "Ajuda"),
    OUTRO(3, "Outro");

    companion object {
        fun getEnun(aCod: Int): ESupport {
            for (lSituacao in ESupport.values()) {
                if (lSituacao.codigo == aCod) {
                    return lSituacao
                }
            }
            return SUGESTAO
        }

        fun getEnun(descricao: String?): ESupport {
            for (lSituacao in ESupport.values()) {
                if (lSituacao.descricao.equals(descricao, ignoreCase = true)) {
                    return lSituacao
                }
            }
            return SUGESTAO
        }
    }

}