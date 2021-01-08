package com.ifsc.lages.sti.tcc.ui.desepenhoesp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.ViewModelProvider
import br.edu.ifsc.cancontrol.utilidades.BaseActivty
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.model.result.ResultOverall
import com.ifsc.lages.sti.tcc.props.EResultOverall
import com.ifsc.lages.sti.tcc.ui.main.dashboard.DashboardEnade
import com.ifsc.lages.sti.tcc.ui.main.dashboard.DashboardPoscomp
import com.ifsc.lages.sti.tcc.ui.main.dashboard.DashboardSpecificPerformance
import com.ifsc.lages.sti.tcc.ui.main.viewmodel.MainViewModel
import com.ifsc.lages.sti.tcc.ui.main.viewmodel.MainViewModelFactory
import com.ifsc.lages.sti.tcc.ui.settings.SettingsActivity
import com.ifsc.lages.sti.tcc.utilidades.*


class PerformanceActivity : BaseActivty() {

    private var dashboardPoscomp : DashboardPoscomp? = null
    private var dashboardEnade : DashboardEnade? = null
    private var dashboardSpecificPerformance : DashboardSpecificPerformance? = null
    private var viewModel :  MainViewModel? = null

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_desempenho_especifico)
    }

    override fun mapComponents() {
        super.mapComponents()
        setDisplayHomeAs(true)
        setTitleToolbar(getString(R.string.title_specific_performance))

        if(dashboardPoscomp == null)
            dashboardPoscomp = DashboardPoscomp(this@PerformanceActivity, findViewById(R.id.card_dashboard_poscomp))

        if(dashboardEnade == null)
            dashboardEnade = DashboardEnade(this@PerformanceActivity, findViewById(R.id.card_dashboard_enade))

        if(dashboardSpecificPerformance == null)
            dashboardSpecificPerformance = DashboardSpecificPerformance(this@PerformanceActivity, findViewById(R.id.card_dashboard_specific_performance))
    }

    override fun onResume() {
        super.onResume()
        callBackOperetion()
    }

    override fun mapActionComponents() {
        super.mapActionComponents()
    }

    fun callBackOperetion() {
        var userId = SharedPreferencesUtil.get(this@PerformanceActivity, KeyPrefs.USER_ID, 0L)
        if (ConnectionUtil.isNetworkAvailable(this@PerformanceActivity)) {
            viewModel!!.loadOverallResultPoscomp(userId)
            viewModel!!.loadOverallResultEnade(userId)
            viewModel!!.loadOverallResultCustom(userId)
        } else {
            var poscomp = showInfos(EResultOverall.POSCOMP.codigo)
            poscomp?.let { dashboardPoscomp!!.showDashboard(it) }

            var endade = showInfos(EResultOverall.ENADE.codigo)
            endade?.let { dashboardEnade!!.showDashboard(it) }

            var personalizada = showInfos(EResultOverall.PERSONALIZADO.codigo)
            personalizada?.let { dashboardSpecificPerformance!!.showDashboard(it) }
        }
    }

    fun showInfos(type : Int) : ResultOverall? {
        var idUser = SharedPreferencesUtil.get(this@PerformanceActivity, KeyPrefs.USER_ID, 0L)
        var result = ResultOverall.DataBase.loadByTypeAndIdUser(type.toLong(), idUser)
        return result
    }

    fun saveResultOverrall(it : ResultOverall, type : Int) : ResultOverall {
        var idUser = SharedPreferencesUtil.get(this@PerformanceActivity, KeyPrefs.USER_ID, 0L)
        it._id = type.toLong()
        it.idUsuario = idUser
        ResultOverall.DataBase.save(it)
        return it
    }

    override fun createRestListener() {
        viewModel = ViewModelProvider(this,
            MainViewModelFactory(this@PerformanceActivity)
        ).get(MainViewModel::class.java)
        viewModel!!.loadOverallResultPoscompView.observe(this, androidx.lifecycle.Observer {
            if(it.error!!.not()) {
                saveResultOverrall(it.success!!, EResultOverall.POSCOMP.codigo)
                dashboardPoscomp!!.showDashboard(it.success)
            } else {
                Toast.makeText(this@PerformanceActivity, it.message, Toast.LENGTH_LONG).show()
            }
        })

        viewModel = ViewModelProvider(this,
            MainViewModelFactory(this@PerformanceActivity)
        ).get(MainViewModel::class.java)
        viewModel!!.loadOverallResultEnadeView.observe(this, androidx.lifecycle.Observer {
            if(it.error!!.not()) {
                saveResultOverrall(it.success!!, EResultOverall.ENADE.codigo)
                dashboardEnade!!.showDashboard(it.success)
            } else {
                Toast.makeText(this@PerformanceActivity, it.message, Toast.LENGTH_LONG).show()
            }
        })


        viewModel = ViewModelProvider(this,
            MainViewModelFactory(this@PerformanceActivity)
        ).get(MainViewModel::class.java)
        viewModel!!.loadOverallResultEnadeView.observe(this, androidx.lifecycle.Observer {
            if(it.error!!.not()) {
                saveResultOverrall(it.success!!, EResultOverall.PERSONALIZADO.codigo)
                dashboardEnade!!.showDashboard(it.success)
            } else {
                Toast.makeText(this@PerformanceActivity, it.message, Toast.LENGTH_LONG).show()
            }
        })

        viewModel = ViewModelProvider(this,
            MainViewModelFactory(this@PerformanceActivity)
        ).get(MainViewModel::class.java)
        viewModel!!.loadOverallResultCustomView.observe(this, androidx.lifecycle.Observer {
            if(it.error!!.not()) {
                dashboardSpecificPerformance!!.showDashboard(it.success!!)
            } else {
                Toast.makeText(this@PerformanceActivity, it.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}
