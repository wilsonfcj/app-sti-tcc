package com.ifsc.lages.sti.tcc.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.ifsc.lages.sti.tcc.model.ResultOverall
import com.ifsc.lages.sti.tcc.ui.main.fragment.DashboardFundamentosFragment
import com.ifsc.lages.sti.tcc.ui.main.fragment.DashboardGeralFragment
import com.ifsc.lages.sti.tcc.ui.main.fragment.DashboardMatematicaFragment
import com.ifsc.lages.sti.tcc.ui.main.fragment.DashboardTecnologiaFragment

class ViewPagerAdapter(
    val context: Context,
    fm: FragmentManager?,
    private val resultOverall: ResultOverall
) : FragmentStatePagerAdapter(fm!!, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        if(position == 0) {
           return DashboardGeralFragment.getInstance(resultOverall.resultadoGeral!!)
        } else if(position == 1) {
           return DashboardMatematicaFragment.getInstance(resultOverall.resultadoMatematica!!)
        } else if(position == 2) {
           return DashboardFundamentosFragment.getInstance(resultOverall.resultadoFundamentoComputacao!!)
        }
        return return DashboardTecnologiaFragment.getInstance(resultOverall.resultadoTecnologiaComputacao!!)
    }

    override fun getCount(): Int {
        return 4
    }

}