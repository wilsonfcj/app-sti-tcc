package com.ifsc.lages.sti.tcc.ui.meussimulados

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import br.edu.ifsc.cancontrol.utilidades.BaseActivty
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.props.ETipoSimulado
import com.ifsc.lages.sti.tcc.ui.meussimulados.bottomsheet.BottonSheetSimulatedTypeFragment
import com.ifsc.lages.sti.tcc.ui.register.bottomsheet.BottonSheetUserTypeFragment
import java.util.*

class RegisterSimulatedActivity : BaseActivty() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_simulated)
        setTitleToolbar(getString(R.string.title_toolbar_register_simulated))
    }

}