package com.ifsc.lages.sti.tcc.props

enum class EDisciplina(var code: Int, var description: String, var nameSample: String) {

    PROGRAMACAO(0, "PROGRAMAÇÃO", "Programação"),
    ELETRONICA_DIGITAL(1, "ELETRÔNICA DIGITAL", "Eletrônica digital"),
    MATEMÁTICA_DISCRETA(2, "MATEMÁTICA DISCRETA", "Matemática discreta"),

    //	FASE 2
//    PROGRAMACAO_ORIENTADA_A_OBJETOS(3, "PROGRAMAÇÃO ORIENTADA A OBJETOS" , "P.O.O"),
    ALGEBRA_LINEAR_E_GEOMETRIA_ANALITICA(4, "ÁLGEBRA LINEAR E GEOMETRIA ANALÍTICA", "Álgebra Linear e Geometria analítica"),
    ARQUITETURA_E_ORGANIZACAO_DE_COMPUTADORES(5, "ARQUITETURA E ORGANIZAÇÃO DE COMPUTADORES", "Arquitetura de computadores"),
    //	    INTRODUCAO_AS_REDES_DE_COMPUTADORES(6, "INTRODUÇÃO ÀS REDES DE COMPUTADORES", "Intr"),
    LINGUAGENS_E_PARADIGMAS_DE_PROGRAMACAO(7, "LINGUAGENS E PARADIGMAS DE PROGRAMAÇÃO", "Linguagem e paradigamas de programação"),

    //	FASE 3
    ESTRUTURAS_DE_DADOS(8, "ESTRUTURAS DE DADOS", "Estrutura de dados"),
    SISTEMAS_OPERACIONAIS(9, "SISTEMAS OPERACIONAIS", "Sistemas Operacionais"),
    //	    DESENVOLVIMENTO_DE_APLICAÇÕES_ORIENTADAS_A_OBJETOS(10, "DESENVOLVIMENTO DE APLICAÇÕES ORIENTADAS A OBJETOS"),
    REDES_DE_COMPUTADORES(11, "REDES DE COMPUTADORES", "Redes de computadores"),
    CALCULO(12, "CÁLCULO", "Cálculo"),

    //	FASE 3
//    LABORATORIO_DE_PROGRAMACAO(13, "LABORATÓRIO DE PROGRAMAÇÃO", "Programação"),
    BANCO_DE_DADOS(14, "BANCO DE DADOS", "Banco de dados"),
    COMPILADORES(15, "COMPILADORES", "Compiladores"),
    ENGENHARIA_DE_SOFTWARE(16, "ENGENHARIA DE SOFTWARE", "Engenharia de Software"),
    ESTATÍSTICA_E_PROBABILIDADE(17, "ESTATÍSTICA E PROBABILIDADE", "Estatística e probabilidade"),

    //	Fase 6
    COMPUTACAO_GRAFICA(18, "COMPUTAÇÃO GRÁFICA", "Computação Gráfica"),
    SEGURANÇA_COMPUTACIONAL(19, "SEGURANÇA COMPUTACIONAL", "Segurança computacional"),
    INTELIGENCIA_ARTIFICIAL(20, "INTELIGÊNCIA ARTIFICIAL", "I.A"),
    SISTEMAS_DISTRIBUIDOS(21, "SISTEMAS DISTRIBUÍDOS", "Sistemas Distribuídos"),

    //	Fase 7
    MODELAGEM_E_SIMULACAO(22, "MODELAGEM E SIMULAÇÃO", "Modelagem e simulação"),
    GERENCIA_DE_PROJETOS(23, "GERÊNCIA DE PROJETOS", "Gerência de projetos"),


    TEORIA_DA_COMPUTACAO(24, "TEORIA DA COMPUTAÇÃO", "Teoria da computação"),
    INFORMATICA_E_SOCIEDADE(25, "INFORMÁTICA E SOCIEDADE", "Informática e sociedade"),
    GRAFOS(26, "GRAFOS", "Grafos"),
    CALCULO_NUMERICO(27, "CÁLCULO NUMÉRICO", "Cálculo Numérico");

    companion object {
        fun getEnum(aCod: Int): EDisciplina {
            for (lSituacao in values()) {
                if (lSituacao.code == aCod) {
                    return lSituacao
                }
            }
            return PROGRAMACAO
        }

        fun getEnum(aSituacao: String?): EDisciplina {
            for (lSituacao in values()) {
                if (lSituacao.description.equals(aSituacao, ignoreCase = true)) {
                    return lSituacao
                }
            }
            return PROGRAMACAO
        }
    }

}