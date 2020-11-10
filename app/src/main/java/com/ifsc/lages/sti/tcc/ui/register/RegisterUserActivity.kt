package com.ifsc.lages.sti.tcc.ui.register

import android.os.Bundle
import androidx.navigation.Navigation
import br.edu.ifsc.cancontrol.utilidades.BaseActivty
import com.ifsc.lages.sti.tcc.R


class RegisterUserActivity : BaseActivty() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)
    }

    override fun mapComponents() {
        super.mapComponents()
        setTitleToolbar("Cadastro")
    }

    override fun mapActionComponents() {
        super.mapActionComponents()
    }
}