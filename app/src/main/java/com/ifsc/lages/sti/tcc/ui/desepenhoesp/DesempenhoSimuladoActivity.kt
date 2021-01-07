package com.ifsc.lages.sti.tcc.ui.desepenhoesp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.ViewModelProvider
import br.edu.ifsc.cancontrol.utilidades.BaseActivty
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.model.result.ResultadoSimulado
import com.ifsc.lages.sti.tcc.props.ETipoSimulado
import com.ifsc.lages.sti.tcc.ui.main.dashboard.DashboardEnade
import com.ifsc.lages.sti.tcc.ui.main.dashboard.DashboardGeral
import com.ifsc.lages.sti.tcc.ui.main.dashboard.DashboardPoscomp
import com.ifsc.lages.sti.tcc.ui.main.dashboard.DashboardSpecificPerformance
import com.ifsc.lages.sti.tcc.ui.main.viewmodel.MainViewModel
import com.ifsc.lages.sti.tcc.ui.main.viewmodel.MainViewModelFactory
import com.ifsc.lages.sti.tcc.ui.settings.SettingsActivity
import com.ifsc.lages.sti.tcc.utilidades.*


class DesempenhoSimuladoActivity : BaseActivty() {

    private var tvName : TextView? = null
    private var tvDate : TextView? = null
    private var tvClock : TextView? = null
    private var tvDateCreate : TextView? = null
    private var tvClockCreate : TextView? = null

    private var tvType : TextView? = null
    private var tvAcerto : TextView? = null
    private var tvError : TextView? = null
    private var tvNaoRespondidas : TextView? = null

    var resultValue : ResultadoSimulado? = null

    private var dashboardPoscomp : DashboardPoscomp? = null
    private var dashboardEnade : DashboardEnade? = null
    private var dashboardSpecificPerformance : DashboardSpecificPerformance? = null
    private var viewModel :  MainViewModel? = null
    private var dashboardSimulado : DashboardSimulado? = null

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_desempenho_simulado)
    }

    override fun mapComponents() {
        super.mapComponents()
        setDisplayHomeAs(true)
        setTitleToolbar(getString(R.string.title_performance_simulado))
    }

    override fun onResume() {
        super.onResume()
        callBackOperetion()
    }

    override fun mapActionComponents() {
        resultValue = intent.extras?.get("result_overall") as ResultadoSimulado
        tvName = findViewById(R.id.tv_name) as TextView
        tvDate = findViewById(R.id.tv_date) as TextView
        tvClock = findViewById(R.id.tv_clock) as TextView
        tvType = findViewById(R.id.tv_type) as TextView

        tvDateCreate = findViewById(R.id.tv_date_create) as TextView
        tvClockCreate = findViewById(R.id.tv_clock_create) as TextView

        tvAcerto = findViewById(R.id.tv_geralI) as TextView
        tvError = findViewById(R.id.tv_geralII) as TextView
        tvNaoRespondidas = findViewById(R.id.tv_geralIII) as TextView

        if(dashboardSimulado == null) {
            dashboardSimulado = DashboardSimulado(
                this@DesempenhoSimuladoActivity,
                findViewById(R.id.card_dashboard_geral)
            )
            dashboardSimulado?.showDashboard(resultValue!!)
        }
        setDisplayView()
    }

    fun setDisplayView() {
        tvName?.text = resultValue!!.nome
        tvType?.text = ETipoSimulado.getEnun(resultValue!!.tipoSimulado!!).descricao
        tvDate?.text = StringUtil.data(resultValue!!.dataEnvio!!, "dd/MM/yyyy")
        tvClock?.text = StringUtil.data(resultValue!!.dataEnvio!!, "HH:mm")
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
    }

    fun callBackOperetion() {
        var userId = SharedPreferencesUtil.get(this@DesempenhoSimuladoActivity, KeyPrefs.USER_ID, 0L)
        if (ConnectionUtil.isNetworkAvailable(this@DesempenhoSimuladoActivity)) {
//            viewModel!!.loadOverallResultPoscomp(userId)
//            viewModel!!.loadOverallResultEnade(userId)
//            viewModel!!.loadOverallResultCustom(userId)
        }
    }

    override fun createRestListener() {
        viewModel = ViewModelProvider(this,
            MainViewModelFactory(this@DesempenhoSimuladoActivity)
        ).get(MainViewModel::class.java)
        viewModel!!.loadOverallResultPoscompView.observe(this, androidx.lifecycle.Observer {

            if(it.error!!.not()) {
                dashboardPoscomp?.showDashboard(it.success!!)
            } else {
                Toast.makeText(this@DesempenhoSimuladoActivity, it.message, Toast.LENGTH_LONG).show()
            }
        })

        viewModel = ViewModelProvider(this,
            MainViewModelFactory(this@DesempenhoSimuladoActivity)
        ).get(MainViewModel::class.java)
        viewModel!!.loadOverallResultEnadeView.observe(this, androidx.lifecycle.Observer {
            if(it.error!!.not()) {
                dashboardEnade?.showDashboard(it.success!!)
            } else {
                Toast.makeText(this@DesempenhoSimuladoActivity, it.message, Toast.LENGTH_LONG).show()
            }
        })

        viewModel = ViewModelProvider(this,
            MainViewModelFactory(this@DesempenhoSimuladoActivity)
        ).get(MainViewModel::class.java)
        viewModel!!.loadOverallResultCustomView.observe(this, androidx.lifecycle.Observer {
            if(it.error!!.not()) {
                dashboardSpecificPerformance?.showDashboard(it.success!!)
            } else {
                Toast.makeText(this@DesempenhoSimuladoActivity, it.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}
