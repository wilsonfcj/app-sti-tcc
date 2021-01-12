package com.ifsc.lages.sti.tcc.ui.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.model.result.ResultQuantitative

class DashboardMatematicaFragment : FragmentDashboardBase() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard_matematica, container, false)
    }

    companion object {
        @JvmStatic
        fun getInstance(resultOverall : ResultQuantitative): DashboardMatematicaFragment {
            val f =
                DashboardMatematicaFragment()
            val args = Bundle()
            args.putSerializable("result_overall", resultOverall)
            f.arguments = args
            return f
        }
    }
}