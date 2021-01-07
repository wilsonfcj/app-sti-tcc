package com.ifsc.lages.sti.tcc.ui.desepenhoesp.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.ifsc.lages.sti.tcc.model.result.ResultOverall
import com.ifsc.lages.sti.tcc.model.result.ResultadoSimulado
import com.ifsc.lages.sti.tcc.ui.main.fragment.DashboardFundamentosFragment
import com.ifsc.lages.sti.tcc.ui.main.fragment.DashboardGeralFragment
import com.ifsc.lages.sti.tcc.ui.main.fragment.DashboardMatematicaFragment
import com.ifsc.lages.sti.tcc.ui.main.fragment.DashboardTecnologiaFragment

class ViewPagerSimuladoAdapter(
    val context: Context,
    fm: FragmentManager?,
    private val resultOverall: ResultadoSimulado
) : FragmentStatePagerAdapter(fm!!, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        if(position == 0) {
            return DashboardMatematicaFragment.getInstance(resultOverall.resultadoMatematica!!)
        } else if(position == 1) {
            return DashboardFundamentosFragment.getInstance(resultOverall.resultadoFundamentoComputacao!!)
        } else {
            return DashboardTecnologiaFragment.getInstance(resultOverall.resultadoTecnologiaComputacao!!)
        }
    }

    override fun getItemPosition(`object`: Any): Int {
        return super.getItemPosition(`object`)
    }

    override fun getCount(): Int {
        return 3
    }

}