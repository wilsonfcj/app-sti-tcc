package com.ifsc.lages.sti.tcc.props

enum class EUserType(var code: Int, var description: String) {
    STUDENT(1, "Aluno"),
    TEACHER(2, "Professor");

    companion object {
        open fun getUserType(aCod: Int): EUserType {
            for (lValue in values()) {
                if (lValue.code == aCod) {
                    return lValue
                }
            }
            return STUDENT
        }

        open fun getUserType(description: String?): EUserType {
            for (lValue in values()) {
                if (lValue.description.equals(description, ignoreCase = true)) {
                    return lValue
                }
            }
            return STUDENT
        }
    }
}