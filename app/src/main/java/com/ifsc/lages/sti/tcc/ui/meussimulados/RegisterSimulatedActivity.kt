package com.ifsc.lages.sti.tcc.ui.meussimulados

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import androidx.navigation.NavArgument
import androidx.navigation.fragment.NavHostFragment
import br.edu.ifsc.cancontrol.utilidades.BaseActivty
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.props.ETipoSimulado
import com.ifsc.lages.sti.tcc.ui.meussimulados.bottomsheet.BottonSheetSimulatedTypeFragment
import com.ifsc.lages.sti.tcc.ui.register.bottomsheet.BottonSheetUserTypeFragment
import kotlinx.android.synthetic.main.activity_register_simulated.*
import java.util.*

class RegisterSimulatedActivity : BaseActivty() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_simulated)
        setTitleToolbar(getString(R.string.title_toolbar_register_simulated))
        setupNavigation()
    }

    private fun setupNavigation() {
        intent.extras?.let {
            var idClasroom = intent.extras?.getLong("id_classroom")
            val bundle = Bundle()
            val navHostFragment = fragmentNavHost as NavHostFragment
            val navController = navHostFragment.navController
            bundle.putLong("id_classroom", idClasroom!!)
            navController.setGraph(R.navigation.navigation_register_simulated, bundle)
        }
    }
}