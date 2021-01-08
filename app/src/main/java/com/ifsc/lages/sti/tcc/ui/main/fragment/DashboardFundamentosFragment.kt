package com.ifsc.lages.sti.tcc.ui.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.model.result.ResultoQualitativo

class DashboardFundamentosFragment : FragmentDashboardBase() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard_fundamentos, container, false)
    }

    companion object {
        @JvmStatic
        fun getInstance(resultOverall : ResultoQualitativo): DashboardFundamentosFragment {
            val f =
                DashboardFundamentosFragment()
            val args = Bundle()
            args.putSerializable("result_overall", resultOverall)
            f.arguments = args
            return f
        }
    }
}