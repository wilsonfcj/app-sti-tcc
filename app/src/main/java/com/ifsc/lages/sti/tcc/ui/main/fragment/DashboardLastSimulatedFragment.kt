package com.ifsc.lages.sti.tcc.ui.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import br.edu.ifsc.cancontrol.utilidades.MapElement
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.model.result.ResultSimulated
import com.ifsc.lages.sti.tcc.props.ETipoSimulado
import com.ifsc.lages.sti.tcc.utilidades.StringUtil

class DashboardLastSimulatedFragment : Fragment(), MapElement {

    private var tvName : TextView? = null
    private var tvDate : TextView? = null
    private var tvClock : TextView? = null
    private var tvType : TextView? = null
    private var tvAcerto : TextView? = null
    private var tvError : TextView? = null
    private var tvNaoRespondidas : TextView? = null

    var resultValue : ResultSimulated? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resultValue = arguments!!.getSerializable("result_overall") as ResultSimulated?
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_ultimos_simulados, container, false)
    }

    override fun mapComponents() {
        tvName = view!!.findViewById(R.id.tv_name) as TextView
        tvDate = view!!.findViewById(R.id.tv_date) as TextView
        tvClock = view!!.findViewById(R.id.tv_clock) as TextView
        tvType = view!!.findViewById(R.id.tv_type) as TextView

        tvAcerto = view!!.findViewById(R.id.tv_geralI) as TextView
        tvError = view!!.findViewById(R.id.tv_geralII) as TextView
        tvNaoRespondidas = view!!.findViewById(R.id.tv_geralIII) as TextView

        setDisplayView()
    }

    fun setDisplayView() {
        tvName?.text = resultValue!!.nome
        tvDate?.text = StringUtil.data(resultValue!!.dataEnvio!!, "dd/MM/yyyy")
        tvType?.text = ETipoSimulado.getEnun(resultValue!!.tipoSimulado!!).descricao
        tvClock?.text = StringUtil.data(resultValue!!.dataEnvio!!, "HH:mm")

        if(resultValue!!.tipoSimulado!! == ETipoSimulado.DEFAULT.codigo) {
            tvType!!.setBackgroundResource(R.drawable.shape_text_view)
        } else if (resultValue!!.tipoSimulado!! == ETipoSimulado.ENADE.codigo) {
            tvType!!.setBackgroundResource(R.drawable.shape_text_view_one)
        } else {
            tvType!!.setBackgroundResource(R.drawable.shape_text_view_two)
        }


        tvAcerto?.text = resultValue!!.resultadoGeral!!.acertos!!.toString()
        tvError?.text = resultValue!!.resultadoGeral!!.erros!!.toString()
        tvNaoRespondidas?.text = resultValue!!.resultadoGeral!!.naoRespondidas!!.toString()
    }

    override fun mapActionComponents() {}

    companion object {
        @JvmStatic
        fun getInstance(resultOverall : ResultSimulated): DashboardLastSimulatedFragment {
            val f = DashboardLastSimulatedFragment()
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