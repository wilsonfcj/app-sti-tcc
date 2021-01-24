package com.ifsc.lages.sti.tcc.ui.desepenhoesp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import br.edu.ifsc.cancontrol.utilidades.BaseActivty
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.model.result.ResultSimulated
import com.ifsc.lages.sti.tcc.model.result.ResultUser
import com.ifsc.lages.sti.tcc.model.simulated.Simulated
import com.ifsc.lages.sti.tcc.props.ETipoSimulado
import com.ifsc.lages.sti.tcc.ui.main.dashboard.DashboardGeralMattersResults
import com.ifsc.lages.sti.tcc.ui.main.viewmodel.MainViewModel
import com.ifsc.lages.sti.tcc.utilidades.*


class PerformanceStudantSimulatedActivity : BaseActivty() {

    private var tvNameUser : TextView? = null
    private var tvDateSend : TextView? = null
    private var tvClockSend : TextView? = null

    private var tvName : TextView? = null
    private var tvDate : TextView? = null
    private var tvClock : TextView? = null
    private var tvDateCreate : TextView? = null
    private var tvClockCreate : TextView? = null

    private var tvType : TextView? = null
    private var tvAcerto : TextView? = null
    private var tvError : TextView? = null
    private var tvNaoRespondidas : TextView? = null

    var simulatedValue : Simulated? = null
    var resultValue : ResultUser? = null
    private var viewModel :  MainViewModel? = null
    private var dashboardSimulado : DashboardSimulado? = null
    private var dashboardGeralMattersResults : DashboardGeralMattersResults? = null

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_desempenho_especifico_aluno)
    }

    override fun mapComponents() {
        super.mapComponents()
        setDisplayHomeAs(true)
        setTitleToolbar(getString(R.string.title_performance_simulado_geral))
    }

    override fun onResume() {
        super.onResume()
        callBackOperetion()
    }

    override fun mapActionComponents() {
        resultValue = intent.extras?.get("result_overall") as ResultUser
        var resultSimulated = ResultSimulated()
        resultSimulated.resultadoGeral = resultValue!!.resultadoGeral
        resultSimulated.resultadoMatematica = resultValue!!.resultadoMatematica
        resultSimulated.resultadoFundamentoComputacao = resultValue!!.resultadoFundamentoComputacao
        resultSimulated.resultadoTecnologiaComputacao = resultValue!!.resultadoTecnologiaComputacao
        simulatedValue = Simulated.DataBase.loadById(resultValue!!.idSimulado!!)

        tvNameUser = findViewById(R.id.tv_name_user) as TextView
        tvDateSend = findViewById(R.id.tv_date_send) as TextView
        tvClockSend = findViewById(R.id.tv_clock_send) as TextView

        tvName = findViewById(R.id.tv_name) as TextView
        tvDate = findViewById(R.id.tv_date_finish) as TextView
        tvClock = findViewById(R.id.tv_clock_finish) as TextView
        tvType = findViewById(R.id.tv_type) as TextView

        tvDateCreate = findViewById(R.id.tv_date_create) as TextView
        tvClockCreate = findViewById(R.id.tv_clock_create) as TextView

        tvAcerto = findViewById(R.id.tv_geralI) as TextView
        tvError = findViewById(R.id.tv_geralII) as TextView
        tvNaoRespondidas = findViewById(R.id.tv_geralIII) as TextView

        if(dashboardSimulado == null) {
            dashboardSimulado = DashboardSimulado(
                this@PerformanceStudantSimulatedActivity,
                findViewById(R.id.card_dashboard)
            )
            dashboardSimulado?.showDashboard(resultSimulated)
        }

        if(dashboardGeralMattersResults == null) {
            dashboardGeralMattersResults = DashboardGeralMattersResults(
                this@PerformanceStudantSimulatedActivity,
                findViewById(R.id.card_dashboard_matters)
            )
            dashboardGeralMattersResults?.showDashboard(resultValue!!.resultadoMatters!!)
        }
        setDisplayView()
    }

    fun setDisplayView() {
        tvNameUser?.text = resultValue!!.nome
        tvNameUser?.text = resultValue!!.nome

        tvDateSend?.text = StringUtil.data(resultValue!!.dataEnvio!!, "dd/MM/yyyy")
        tvClockSend?.text = StringUtil.data(resultValue!!.dataEnvio!!, "HH:mm")

        tvName?.text = simulatedValue!!.nome
        tvType?.text = ETipoSimulado.getEnun(simulatedValue!!.tipoSimulado!!).descricao

        simulatedValue!!.dataFimSimulado?.let {
            tvDate?.text = StringUtil.data(simulatedValue!!.dataFimSimulado!!, "dd/MM/yyyy")
            tvClock?.text = StringUtil.data(simulatedValue!!.dataFimSimulado!!, "HH:mm")
        }
        tvDateCreate?.text = StringUtil.data(simulatedValue!!.dataCriacao!!, "dd/MM/yyyy")
        tvClockCreate?.text = StringUtil.data(simulatedValue!!.dataCriacao!!, "HH:mm")

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

    fun callBackOperetion() {
        var userId = SharedPreferencesUtil.get(this@PerformanceStudantSimulatedActivity, KeyPrefs.USER_ID, 0L)
        if (ConnectionUtil.isNetworkAvailable(this@PerformanceStudantSimulatedActivity)) {

        }
    }

    override fun createRestListener() {
    }
}
