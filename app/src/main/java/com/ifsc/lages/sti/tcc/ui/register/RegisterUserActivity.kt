package com.ifsc.lages.sti.tcc.ui.register

import android.os.Bundle
import androidx.navigation.Navigation
import br.edu.ifsc.cancontrol.utilidades.BaseActivty
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.model.user.User


class RegisterUserActivity : BaseActivty() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)
    }

    override fun mapComponents() {
        super.mapComponents()
        var user = User.UserShared.load(this@RegisterUserActivity)
        if(user == null) {
            setTitleToolbar(getString(R.string.title_toolbar_register))
        } else {
            setTitleToolbar(getString(R.string.title_toolbar_update))
        }
    }

    override fun mapActionComponents() {
        super.mapActionComponents()
    }
}