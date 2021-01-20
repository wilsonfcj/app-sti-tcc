package com.ifsc.lages.sti.tcc.ui.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import br.edu.ifsc.cancontrol.utilidades.MapElement
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.model.result.ResultMatters
import com.ifsc.lages.sti.tcc.model.result.ResultSimulated
import com.ifsc.lages.sti.tcc.props.EDisciplina
import com.ifsc.lages.sti.tcc.props.ETipoSimulado
import com.ifsc.lages.sti.tcc.utilidades.StringUtil

class DashboardLastMatterFragment : Fragment(), MapElement {

    private var tvName : TextView? = null
    private var tvType : TextView? = null
    private var tvAcerto : TextView? = null
    private var tvError : TextView? = null
    private var tvNaoRespondidas : TextView? = null

    var resultValue : ResultMatters? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resultValue = arguments!!.getSerializable("result_overall") as ResultMatters?
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_matters_gerarl, container, false)
    }

    override fun mapComponents() {
        tvName = view!!.findViewById(R.id.tv_name) as TextView
        tvType = view!!.findViewById(R.id.tv_type) as TextView
        tvAcerto = view!!.findViewById(R.id.tv_geralI) as TextView
        tvError = view!!.findViewById(R.id.tv_geralII) as TextView
        tvNaoRespondidas = view!!.findViewById(R.id.tv_geralIII) as TextView
        setDisplayView()
    }

    fun setDisplayView() {
        tvName?.text = EDisciplina.getEnum(resultValue!!.disciplinaCod!!).nameSample
        tvType?.text = "Total ${resultValue!!.total}"

        tvAcerto?.text = resultValue!!.acertos!!.toString()
        tvError?.text = resultValue!!.erros!!.toString()
        tvNaoRespondidas?.text = resultValue!!.naoRespondidas!!.toString()
    }

    override fun mapActionComponents() {}

    companion object {
        @JvmStatic
        fun getInstance(resultOverall : ResultMatters): DashboardLastMatterFragment {
            val f = DashboardLastMatterFragment()
            val args = Bundle()
            args.putSerializable("result_overall", resultOverall)
            f.arguments = args
            return f
        }
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        mapComponents()
        mapActionComponents()
    }
}