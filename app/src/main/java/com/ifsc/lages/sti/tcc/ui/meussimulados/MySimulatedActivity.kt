package com.ifsc.lages.sti.tcc.ui.meussimulados

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import br.edu.ifsc.cancontrol.utilidades.BaseActivty
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.model.simulated.Simulated
import com.ifsc.lages.sti.tcc.props.EOptions
import com.ifsc.lages.sti.tcc.props.EOptionsStudent
import com.ifsc.lages.sti.tcc.ui.desepenhoesp.DesempenhoSimuladoActivity
import com.ifsc.lages.sti.tcc.ui.feedback.FeedbackActivity
import com.ifsc.lages.sti.tcc.ui.meussimulados.adapter.SimulatedAdapter
import com.ifsc.lages.sti.tcc.ui.meussimulados.bottomsheet.BottonSheetSimulatedOptionFragment
import com.ifsc.lages.sti.tcc.ui.meussimulados.bottomsheet.BottonSheetSimulatedTypeFragment
import com.ifsc.lages.sti.tcc.ui.meussimulados.viewmodel.MeusSimuladosViewModel
import com.ifsc.lages.sti.tcc.ui.meussimulados.viewmodel.MeusSimuladosViewModelFactory
import com.ifsc.lages.sti.tcc.ui.registersala.bottomsheet.simulated.student.BottonSheetSimulatedUserOptionFragment
import com.ifsc.lages.sti.tcc.utilidades.ConnectionUtil
import com.ifsc.lages.sti.tcc.utilidades.KeyPrefs
import com.ifsc.lages.sti.tcc.utilidades.SharedPreferencesUtil
import com.ifsc.lages.sti.tcc.utilidades.components.CustomLayoutMsm

class MySimulatedActivity : BaseActivty(), SimulatedAdapter.Listener, BottonSheetSimulatedUserOptionFragment.CallbackOptions, BottonSheetSimulatedOptionFragment.CallbackOptions {

    private var recyclerView: RecyclerView? = null
    private var lastSimulatedAdapter : SimulatedAdapter? = null
    private var viewModel :  MeusSimuladosViewModel? = null
    private var simulatedList :  MutableList<Simulated>? = null
    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    var userId : Long? = null
    private var bottonSheetSimulatedOptionFragment : BottonSheetSimulatedOptionFragment? = null
    private var bottonSheetSimulatedUserOptionFragment : BottonSheetSimulatedUserOptionFragment? = null
    var checkSimulated: Simulated? = null
    var customLayoutMsm : CustomLayoutMsm? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simulated)
        setTitleToolbar(getString(R.string.title_my_simuladed))
    }

    override fun mapComponents() {
        super.mapComponents()
        recyclerView = findViewById(R.id.recycler_view)
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout)
        customLayoutMsm = findViewById(R.id.layout_error)
    }

    fun showDialogSelected(response: Simulated) {
        checkSimulated = response
        bottonSheetSimulatedOptionFragment = BottonSheetSimulatedOptionFragment.newInstance()
        bottonSheetSimulatedOptionFragment!!.show(supportFragmentManager, null)
        bottonSheetSimulatedOptionFragment!!.setListener(this)
    }

    fun showDialogOptionUserWhiteResponse(response: Simulated) {
        checkSimulated = response
        bottonSheetSimulatedUserOptionFragment = BottonSheetSimulatedUserOptionFragment.newInstance()
        bottonSheetSimulatedUserOptionFragment!!.show(supportFragmentManager, null)
        bottonSheetSimulatedUserOptionFragment!!.setListener(this)
    }

    fun loadSimulated() {
        if(ConnectionUtil.isNetworkAvailable(this@MySimulatedActivity)) {
            swipeRefreshLayout!!.isRefreshing = true
            loadSimulateds(userId!!)
        } else {
            showInfosSimulated(userId!!)
        }
    }

    override fun mapActionComponents() {
        super.mapActionComponents()
        userId = SharedPreferencesUtil.get(this@MySimulatedActivity, KeyPrefs.USER_ID, 0L)
        showDisplayList()
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
                Simulated.DataBase.deleteByIdUserStatusAsk(userId!!)
                for (simulated in it.success!!) {
                    Simulated.DataBase.save(simulated)
                }
                if(it.success.isEmpty()) {
                    customLayoutMsm?.visibility = View.VISIBLE
                }
                lastSimulatedAdapter?.updateList(it.success)
            } else {
                Toast.makeText(this@MySimulatedActivity, it.message, Toast.LENGTH_LONG).show()
            }
        })

        viewModel!!.deleteCompleto.observe(this, androidx.lifecycle.Observer {
            swipeRefreshLayout!!.isRefreshing = false
            if(it.error!!.not()) {
                Simulated.DataBase.deleteByIdUserStatusAsk(it.success!!.id!!)
                loadSimulated()
                Toast.makeText(this@MySimulatedActivity, "Simulado removido com sucesso", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this@MySimulatedActivity, it.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    fun showInfosSimulated(userId: Long) {
        var infos = Simulated.DataBase.loadByIdUser(userId)
        if(infos!!.isEmpty()) {
            customLayoutMsm?.visibility = View.VISIBLE
        }
        lastSimulatedAdapter?.updateList(infos!!)
    }

    fun loadSimulateds(userId: Long) {
        viewModel!!.loadSimulatedByUser(userId)
    }

    override fun onItemClick(response: Simulated) {
        if(response.respondido) {
            showDialogOptionUserWhiteResponse(response)
        } else {
            showDialogSelected(response)
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
                if (ConnectionUtil.isNetworkAvailable(this@MySimulatedActivity).not()) {
                    Toast.makeText(this@MySimulatedActivity, getString(R.string.error_conection), Toast.LENGTH_LONG).show()
                } else {
                    val intent = Intent(this@MySimulatedActivity, RegisterSimulatedActivity::class.java)
                    startActivity(intent)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun openResultSimulated(response: Simulated) {
        var bundle = Bundle()
        bundle.putSerializable("result_overall", response.simuladoResultado!!._id)
        val intent = Intent(this@MySimulatedActivity, DesempenhoSimuladoActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        loadSimulated()
    }

    override fun onClick(options: EOptions?) {
        bottonSheetSimulatedOptionFragment?.dismiss()
        if(options?.codigo == EOptions.INICIAR.codigo) {
//            var bundle = Bundle()
//            bundle.putSerializable("result_overall", checkSimulated)
//            val intent = Intent(this@MySimulatedActivity, SimuladoActivity::class.java)
//            intent.putExtras(bundle)
//            startActivity(intent)
        } else {
            if (ConnectionUtil.isNetworkAvailable(this@MySimulatedActivity).not()) {
                Toast.makeText(
                    this@MySimulatedActivity,
                    getString(R.string.error_conection),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                swipeRefreshLayout!!.isRefreshing = true
                viewModel!!.deleteSimulated(userId!!, checkSimulated!!._id!!)
            }
        }
    }

    override fun onClick(options: EOptionsStudent?) {
        bottonSheetSimulatedUserOptionFragment!!.dismiss()
        if (options == EOptionsStudent.GERAL) {
            openResultSimulated(checkSimulated!!)
        } else {
            openFeedbackUser(checkSimulated!!)
        }
    }

    fun openFeedbackUser(response: Simulated) {
        var bundle = Bundle()
        bundle.putSerializable("result_overall", response.simuladoResultado!!._id)
        bundle.putLong("user_id", userId!!)
        bundle.putBoolean("result_by_user", true)
        val intent = Intent(this@MySimulatedActivity, FeedbackActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }
}