package com.ifsc.lages.sti.tcc.ui.registersala.simulated

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
import com.ifsc.lages.sti.tcc.props.*
import com.ifsc.lages.sti.tcc.ui.desepenhoesp.DesempenhoSimuladoActivity
import com.ifsc.lages.sti.tcc.ui.feedback.FeedbackActivity
import com.ifsc.lages.sti.tcc.ui.meussimulados.RegisterSimulatedActivity
import com.ifsc.lages.sti.tcc.ui.meussimulados.adapter.SimulatedAdapter
import com.ifsc.lages.sti.tcc.ui.meussimulados.bottomsheet.BottonSheetSimulatedOptionFragment
import com.ifsc.lages.sti.tcc.ui.meussimulados.viewmodel.MeusSimuladosViewModel
import com.ifsc.lages.sti.tcc.ui.meussimulados.viewmodel.MeusSimuladosViewModelFactory
import com.ifsc.lages.sti.tcc.ui.registersala.adapter.SimulatedTeacherAdapter
import com.ifsc.lages.sti.tcc.ui.registersala.bottomsheet.simulated.student.BottonSheetSimulatedUserOptionFragment
import com.ifsc.lages.sti.tcc.ui.registersala.bottomsheet.simulated.student.BottonSheetSimulatedUserOptionStartFragment
import com.ifsc.lages.sti.tcc.ui.registersala.bottomsheet.simulated.teacher.BottonSheetSimulatedOptionTeacherFragment
import com.ifsc.lages.sti.tcc.utilidades.ConnectionUtil
import com.ifsc.lages.sti.tcc.utilidades.KeyPrefs
import com.ifsc.lages.sti.tcc.utilidades.SharedPreferencesUtil
import com.ifsc.lages.sti.tcc.utilidades.components.CustomLayoutMsm

class MySimulatedClassroomActivity : BaseActivty(), SimulatedAdapter.Listener,
    BottonSheetSimulatedOptionFragment.CallbackOptions,
    BottonSheetSimulatedOptionTeacherFragment.CallbackOptions,
    BottonSheetSimulatedUserOptionFragment.CallbackOptions,
    BottonSheetSimulatedUserOptionStartFragment.CallbackOptions{

    private var recyclerView: RecyclerView? = null
    private var lastSimulatedAdapter : SimulatedAdapter? = null
    private var lastSimulatedAdapter2 : SimulatedTeacherAdapter? = null
    private var viewModel :  MeusSimuladosViewModel? = null
    private var simulatedList :  MutableList<Simulated>? = null
    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    private var userId : Long? = null
    private var userType: Int? = null
    private var checkSimulated: Simulated? = null
    private var customLayoutMsm : CustomLayoutMsm? = null
    private var idClasroom : Long? = null


    private var bottonSheetSimulatedOptionFragment : BottonSheetSimulatedOptionFragment? = null
    private var bottonSheetSimulatedUserOptionFragment : BottonSheetSimulatedUserOptionFragment? = null
    private var bottonSheetSimulatedOptionTeacherFragment : BottonSheetSimulatedOptionTeacherFragment? = null
    private var bottonSheetSimulatedUserOptionStartFragment : BottonSheetSimulatedUserOptionStartFragment? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userType = SharedPreferencesUtil.get(this@MySimulatedClassroomActivity, KeyPrefs.USER_TYPE, 0)
        setContentView(R.layout.activity_simulated)
        when (userType) {
            EUserType.TEACHER.code -> {
                setTitleToolbar(getString(R.string.title_my_simuladed))
            }
            else -> {
                setTitleToolbar(getString(R.string.title_simuladed_available))
            }
        }
    }

    override fun mapComponents() {
        super.mapComponents()
        idClasroom = intent.extras?.getLong("id_classroom")
        recyclerView = findViewById(R.id.recycler_view)
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout)
        customLayoutMsm = findViewById(R.id.layout_error)
        customLayoutMsm!!.title!!.text = getString(R.string.prompt_not_simulated_classrorom)
    }

    fun showDialogOptionStartResponseSimulated(response: Simulated) {
        checkSimulated = response
        bottonSheetSimulatedUserOptionStartFragment = BottonSheetSimulatedUserOptionStartFragment.newInstance()
        bottonSheetSimulatedUserOptionStartFragment!!.show(supportFragmentManager, null)
        bottonSheetSimulatedUserOptionStartFragment!!.setListener(this)
    }

    fun showDialogOptionNotResponse(response: Simulated) {
        checkSimulated = response
        bottonSheetSimulatedOptionFragment = BottonSheetSimulatedOptionFragment.newInstance()
        bottonSheetSimulatedOptionFragment!!.show(supportFragmentManager, null)
        bottonSheetSimulatedOptionFragment!!.setListener(this)
    }

    fun loadSimulated() {
        if(ConnectionUtil.isNetworkAvailable(this@MySimulatedClassroomActivity)) {
            swipeRefreshLayout!!.isRefreshing = true
            loadSimulateds(userId!!, idClasroom!!)
        } else {
            showInfosSimulated(idClasroom!!)
        }
    }

    override fun mapActionComponents() {
        super.mapActionComponents()
        userId = SharedPreferencesUtil.get(this@MySimulatedClassroomActivity, KeyPrefs.USER_ID, 0L)
        showDisplayList()
        swipeRefreshLayout!!.setOnRefreshListener { loadSimulateds(userId!!, idClasroom!!) }
    }

    fun showDisplayList() {
        simulatedList = ArrayList()
        if(userType == EUserType.TEACHER.code) {
            lastSimulatedAdapter2 = SimulatedTeacherAdapter(simulatedList!!, this)
            val viewManager = LinearLayoutManager(this)
            recyclerView?.layoutManager = viewManager
            recyclerView?.adapter = lastSimulatedAdapter2
        } else {
            lastSimulatedAdapter = SimulatedAdapter(simulatedList!!, this)
            val viewManager = LinearLayoutManager(this)
            recyclerView?.layoutManager = viewManager
            recyclerView?.adapter = lastSimulatedAdapter
        }
    }

    override fun createRestListener() {
        viewModel = ViewModelProvider(this, MeusSimuladosViewModelFactory(this@MySimulatedClassroomActivity)).get(MeusSimuladosViewModel::class.java)
        viewModel!!.muesSimuladosClassRooom.observe(this, androidx.lifecycle.Observer {
            swipeRefreshLayout!!.isRefreshing = false
            if(it.error!!.not()) {
                Simulated.DataBase.deleteByIdClassroomStatusAsk(idClasroom!!)
                for (simulated in it.success!!) {
                    Simulated.DataBase.save(simulated)
                }
                if(it.success!!.isEmpty()) {
                    customLayoutMsm?.visibility = View.VISIBLE
                }
                updateList(it.success)
            } else {
                Toast.makeText(this@MySimulatedClassroomActivity, it.message, Toast.LENGTH_LONG).show()
            }
        })

        viewModel!!.deleteCompleto.observe(this, androidx.lifecycle.Observer {
            swipeRefreshLayout!!.isRefreshing = false
            if(it.error!!.not()) {
                Simulated.DataBase.deleteByIdUserStatusAsk(it.success!!.id!!)
                loadSimulated()
                Toast.makeText(this@MySimulatedClassroomActivity, "Simulado removido com sucesso", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this@MySimulatedClassroomActivity, it.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    fun updateList(simulated : MutableList<Simulated>) {
        if(userType == EUserType.TEACHER.code) {
            lastSimulatedAdapter2?.updateList(simulated)
        } else {
            lastSimulatedAdapter?.updateList(simulated)
        }

    }

    fun showInfosSimulated(idClasroom: Long) {
        var infos = Simulated.DataBase.loadByIdClassroom(idClasroom)
        if(infos!!.isEmpty()) {
            customLayoutMsm?.visibility = View.VISIBLE
        }
        updateList(infos)
    }

    fun loadSimulateds(userId: Long, classRoomId : Long) {
        viewModel!!.loadSimulatedByUser(userId, classRoomId)
    }

    override fun onItemClick(response: Simulated) {
        if(response.respondido) {
            if(userType == EUserType.TEACHER.code) {
                if(response.quantidadeResposta!! > 0) {
                    showDialogSelectedWhitResponse(response)
                } else {
                    showDialogOptionNotResponse(response)
                }
            } else {
                showDialogOptionUserWhiteResponse(response)
            }
        } else {
            showDialogOptionStartResponseSimulated(response)
        }
    }

    fun openResultSimulated(response: Simulated) {
        var bundle = Bundle()
        bundle.putSerializable("result_overall", response.simuladoResultado!!._id)
        val intent = Intent(this@MySimulatedClassroomActivity, DesempenhoSimuladoActivity::class.java)
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
            Toast.makeText(this@MySimulatedClassroomActivity, "Em breve", Toast.LENGTH_LONG).show()
        } else {
            if (ConnectionUtil.isNetworkAvailable(this@MySimulatedClassroomActivity).not()) {
                Toast.makeText(
                    this@MySimulatedClassroomActivity,
                    getString(R.string.error_conection),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                swipeRefreshLayout!!.isRefreshing = true
                viewModel!!.deleteSimulated(userId!!, checkSimulated!!._id!!)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if(userType == EUserType.TEACHER.code) {
            val inflater = menuInflater
            inflater.inflate(R.menu.menu_add, menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add -> {
                if (ConnectionUtil.isNetworkAvailable(this@MySimulatedClassroomActivity).not()) {
                    Toast.makeText(this@MySimulatedClassroomActivity, getString(R.string.error_conection), Toast.LENGTH_LONG).show()
                } else {
                    val intent = Intent(this@MySimulatedClassroomActivity, RegisterSimulatedActivity::class.java)
                    var bundle = Bundle()
                    bundle.putLong("id_classroom", idClasroom!!)
                    intent.putExtras(bundle)
                    startActivity(intent, bundle)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun showDialogOptionUserWhiteResponse(response: Simulated) {
        checkSimulated = response
        bottonSheetSimulatedUserOptionFragment = BottonSheetSimulatedUserOptionFragment.newInstance()
        bottonSheetSimulatedUserOptionFragment!!.show(supportFragmentManager, null)
        bottonSheetSimulatedUserOptionFragment!!.setListener(this)
    }

    fun showDialogSelectedWhitResponse(response: Simulated) {
        checkSimulated = response
        bottonSheetSimulatedOptionTeacherFragment = BottonSheetSimulatedOptionTeacherFragment.newInstance()
        bottonSheetSimulatedOptionTeacherFragment!!.show(supportFragmentManager, null)
        bottonSheetSimulatedOptionTeacherFragment!!.setListener(this)
    }

    override fun onClick(options: EOptionsTeacher?) {
        bottonSheetSimulatedOptionTeacherFragment!!.dismiss()
        if (options?.codigo == EOptionsTeacher.GERAL.codigo) {
            openResultSimulated(checkSimulated!!)
        } else if(options?.codigo == EOptionsTeacher.CONFERIR_GABARITO.codigo) {
            if (ConnectionUtil.isNetworkAvailable(this@MySimulatedClassroomActivity).not()) {
                Toast.makeText(this@MySimulatedClassroomActivity, getString(R.string.error_conection), Toast.LENGTH_LONG).show()
            } else {
                openFeedback(checkSimulated!!)
            }
        } else {
            if (ConnectionUtil.isNetworkAvailable(this@MySimulatedClassroomActivity).not()) {
                Toast.makeText(this@MySimulatedClassroomActivity, getString(R.string.error_conection), Toast.LENGTH_LONG).show()
            } else {
                openResultResponse(checkSimulated!!)
            }
        }
    }

    fun openFeedback(response: Simulated) {
        var bundle = Bundle()
        bundle.putSerializable("result_overall", response.simuladoResultado!!._id)
        val intent = Intent(this@MySimulatedClassroomActivity, FeedbackActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    fun openResultResponse(response: Simulated) {
        var bundle = Bundle()
        bundle.putSerializable("result_overall", response.simuladoResultado!!._id)
        val intent = Intent(this@MySimulatedClassroomActivity, ResponseStudentsActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun onClick(options: EOptionsStudent?) {
        bottonSheetSimulatedUserOptionFragment?.dismiss()
        if (options!!.codigo == EOptionsStudent.GERAL.codigo) {
            openResultSimulated(checkSimulated!!)
        } else {
            Toast.makeText(this@MySimulatedClassroomActivity, "Em breve", Toast.LENGTH_LONG).show()
        }
    }

    override fun onClick(options: EOptionsStart?) {
        bottonSheetSimulatedUserOptionStartFragment?.dismiss()
        if (options!!.codigo == EOptionsStart.INICIAR.codigo) {

        }
    }
}