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
        }
    }

    override fun createRestListener() {
        viewModel = ViewModelProvider(this,
            MainViewModelFactory(this@PerformanceActivity)
        ).get(MainViewModel::class.java)
        viewModel!!.loadOverallResultPoscompView.observe(this, androidx.lifecycle.Observer {

            if(it.error!!.not()) {
                dashboardPoscomp!!.showDashboard(it.success!!)
            } else {
                Toast.makeText(this@PerformanceActivity, it.message, Toast.LENGTH_LONG).show()
            }
        })

        viewModel = ViewModelProvider(this,
            MainViewModelFactory(this@PerformanceActivity)
        ).get(MainViewModel::class.java)
        viewModel!!.loadOverallResultEnadeView.observe(this, androidx.lifecycle.Observer {
            if(it.error!!.not()) {
                dashboardEnade!!.showDashboard(it.success!!)
            } else {
                Toast.makeText(this@PerformanceActivity, it.message, Toast.LENGTH_LONG).show()
            }
        })


        viewModel = ViewModelProvider(this,
            MainViewModelFactory(this@PerformanceActivity)
        ).get(MainViewModel::class.java)
        viewModel!!.loadOverallResultEnadeView.observe(this, androidx.lifecycle.Observer {
            if(it.error!!.not()) {
                dashboardEnade!!.showDashboard(it.success!!)
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
