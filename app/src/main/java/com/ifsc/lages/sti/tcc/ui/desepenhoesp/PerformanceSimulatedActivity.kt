package com.ifsc.lages.sti.tcc.ui.desepenhoesp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import br.edu.ifsc.cancontrol.utilidades.BaseActivty
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.model.result.ResultSimulated
import com.ifsc.lages.sti.tcc.model.simulated.Simulated
import com.ifsc.lages.sti.tcc.model.user.User
import com.ifsc.lages.sti.tcc.props.ETipoSimulado
import com.ifsc.lages.sti.tcc.props.EUserType
import com.ifsc.lages.sti.tcc.ui.main.dashboard.DashboardGeralMattersResults
import com.ifsc.lages.sti.tcc.ui.main.viewmodel.MainViewModel
import com.ifsc.lages.sti.tcc.utilidades.*
import kotlinx.android.synthetic.main.layout_simulado_teacher_values.*


class PerformanceSimulatedActivity : BaseActivty() {

    private var tvName : TextView? = null
    private var tvDate : TextView? = null
    private var tvClock : TextView? = null
    private var tvDateCreate : TextView? = null
    private var tvClockCreate : TextView? = null

    private var tvType : TextView? = null
    private var tvAcerto : TextView? = null
    private var tvError : TextView? = null
    private var tvNaoRespondidas : TextView? = null
    private var tvRespostasEnviadas : TextView? = null

    var simulatedValue : Simulated? = null
    var resultValue : ResultSimulated? = null
    private var viewModel :  MainViewModel? = null
    private var dashboardSimulado : DashboardSimulado? = null
    private var dashboardGeralMattersResults : DashboardGeralMattersResults? = null

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_desempenho_geral_alunos)
    }

    override fun mapComponents() {
        super.mapComponents()
        setDisplayHomeAs(true)

        var user = User.UserShared.load(this@PerformanceSimulatedActivity)

        if(user!!.userType == EUserType.STUDENT.code) {
            label_type_user.text = "Resultado Geral"
            setTitleToolbar(getString(R.string.title_specific_performance))
        } else {
            setTitleToolbar(getString(R.string.title_performance_simulado_geral))
        }


    }

    override fun onResume() {
        super.onResume()
        callBackOperetion()
    }

    override fun mapActionComponents() {
        var lResultValue = intent.extras?.get("result_overall") as Long
        resultValue = ResultSimulated.DataBase.loadById(lResultValue)
        simulatedValue = Simulated.DataBase.loadById(resultValue!!.idSimulado!!)

        tvName = findViewById(R.id.tv_name) as TextView
        tvDate = findViewById(R.id.tv_date_finish) as TextView
        tvClock = findViewById(R.id.tv_clock_finish) as TextView
        tvType = findViewById(R.id.tv_type) as TextView

        tvDateCreate = findViewById(R.id.tv_date_create) as TextView
        tvClockCreate = findViewById(R.id.tv_clock_create) as TextView

        tvAcerto = findViewById(R.id.tv_geralI) as TextView
        tvError = findViewById(R.id.tv_geralII) as TextView
        tvNaoRespondidas = findViewById(R.id.tv_geralIII) as TextView
        tvRespostasEnviadas = findViewById(R.id.tv_geralIV) as TextView

        if(dashboardSimulado == null) {
            dashboardSimulado = DashboardSimulado(
                this@PerformanceSimulatedActivity,
                findViewById(R.id.card_dashboard)
            )
            dashboardSimulado?.showDashboard(resultValue!!)
        }

        if(dashboardGeralMattersResults == null) {
            dashboardGeralMattersResults = DashboardGeralMattersResults(
                this@PerformanceSimulatedActivity,
                findViewById(R.id.card_dashboard_matters)
            )
            dashboardGeralMattersResults?.showDashboard(resultValue!!.resultadoMatters!!)
        }
        setDisplayView()
    }

    fun setDisplayView() {
        tvName?.text = resultValue!!.nome
        tvType?.text = ETipoSimulado.getEnun(resultValue!!.tipoSimulado!!).descricao
        simulatedValue!!.dataFimSimulado?.let {
            tvDate?.text = StringUtil.data(simulatedValue!!.dataFimSimulado!!, "dd/MM/yyyy")
            tvClock?.text = StringUtil.data(simulatedValue!!.dataFimSimulado!!, "HH:mm")
        }
        tvDateCreate?.text = StringUtil.data(resultValue!!.dataCriacao!!, "dd/MM/yyyy")
        tvClockCreate?.text = StringUtil.data(resultValue!!.dataCriacao!!, "HH:mm")



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
        tvRespostasEnviadas?.text =  simulatedValue!!.quantidadeResposta.toString()
    }

    fun callBackOperetion() {
        var userId = SharedPreferencesUtil.get(this@PerformanceSimulatedActivity, KeyPrefs.USER_ID, 0L)
        if (ConnectionUtil.isNetworkAvailable(this@PerformanceSimulatedActivity)) {

        }
    }

    override fun createRestListener() {
    }
}
