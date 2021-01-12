package com.ifsc.lages.sti.tcc.ui.meussimulados

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import br.edu.ifsc.cancontrol.utilidades.BaseActivty
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.model.simulated.Simulated
import com.ifsc.lages.sti.tcc.ui.desepenhoesp.DesempenhoSimuladoActivity
import com.ifsc.lages.sti.tcc.ui.meussimulados.adapter.SimulatedAdapter
import com.ifsc.lages.sti.tcc.ui.meussimulados.viewmodel.MeusSimuladosViewModel
import com.ifsc.lages.sti.tcc.ui.meussimulados.viewmodel.MeusSimuladosViewModelFactory
import com.ifsc.lages.sti.tcc.utilidades.ConnectionUtil
import com.ifsc.lages.sti.tcc.utilidades.KeyPrefs
import com.ifsc.lages.sti.tcc.utilidades.SharedPreferencesUtil

class MySimulatedActivity : BaseActivty(), SimulatedAdapter.Listener {

    private var recyclerView: RecyclerView? = null
    private var lastSimulatedAdapter : SimulatedAdapter? = null
    private var viewModel :  MeusSimuladosViewModel? = null
    private var simulatedList :  MutableList<Simulated>? = null
    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    var userId : Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simulado)
        setTitleToolbar(getString(R.string.title_my_simuladed))
    }

    override fun mapComponents() {
        super.mapComponents()
        recyclerView = findViewById(R.id.recycler_view)
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout)

    }

    override fun mapActionComponents() {
        super.mapActionComponents()
        userId = SharedPreferencesUtil.get(this@MySimulatedActivity, KeyPrefs.USER_ID, 0L)
        showDisplayList()
        if(ConnectionUtil.isNetworkAvailable(this@MySimulatedActivity)) {
            swipeRefreshLayout!!.isRefreshing = true
            loadSimulateds(userId!!)
        } else {
            showInfosSimulated(userId!!)
        }
        swipeRefreshLayout!!.setOnRefreshListener { loadSimulateds(userId!!) }
    }

    fun showDisplayList() {
        simulatedList = ArrayList()
        lastSimulatedAdapter = SimulatedAdapter(simulatedList!!, this)
        val viewManager = LinearLayoutManager(this)
        recyclerView?.layoutManager = viewManager
        recyclerView?.adapter = lastSimulatedAdapter
    }

    override fun createRestListener() {
        viewModel = ViewModelProvider(this, MeusSimuladosViewModelFactory(this@MySimulatedActivity)).get(MeusSimuladosViewModel::class.java)
        viewModel!!.muesSimulados.observe(this, androidx.lifecycle.Observer {
            swipeRefreshLayout!!.isRefreshing = false
            if(it.error!!.not()) {
                for (simulated in it.success!!) {
                    Simulated.DataBase.save(simulated)
                }
                lastSimulatedAdapter?.updateList(it.success)
            } else {
                Toast.makeText(this@MySimulatedActivity, it.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    fun showInfosSimulated(userId: Long) {
        var infos = Simulated.DataBase.loadByIdUser(userId)
        lastSimulatedAdapter?.updateList(infos!!)
    }

    fun loadSimulateds(userId: Long) {
        viewModel!!.loadSimulatedByUser(userId)
    }

    override fun onItemClick(response: Simulated) {
        if(response.respondido) {
            openResultSimulated(response)
        } else {

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add -> {
                val intent = Intent(this@MySimulatedActivity, RegisterSimulatedActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun openResultSimulated(response: Simulated) {
        var bundle = Bundle()
        bundle.putSerializable("result_overall", response.simuladoResultado)
        val intent = Intent(this@MySimulatedActivity, DesempenhoSimuladoActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }
}