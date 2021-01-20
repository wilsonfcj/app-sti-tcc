package com.ifsc.lages.sti.tcc.ui.registersala.simulated

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import br.edu.ifsc.cancontrol.utilidades.BaseActivty
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.model.classrom.Classroom
import com.ifsc.lages.sti.tcc.model.result.ResultUser
import com.ifsc.lages.sti.tcc.model.simulated.Simulated
import com.ifsc.lages.sti.tcc.props.EOptionsResponse
import com.ifsc.lages.sti.tcc.props.EUserType
import com.ifsc.lages.sti.tcc.ui.registersala.adapter.ClassroomAdapter
import com.ifsc.lages.sti.tcc.ui.registersala.adapter.SimulatedResponseUserAdapter
import com.ifsc.lages.sti.tcc.ui.registersala.bottomsheet.result.BottonSheetResponseUserFragment
import com.ifsc.lages.sti.tcc.ui.registersala.bottomsheet.simulated.student.BottonSheetSimulatedUserOptionFragment
import com.ifsc.lages.sti.tcc.ui.registersala.viewmodel.ClassRoomViewModel
import com.ifsc.lages.sti.tcc.ui.registersala.viewmodel.ClassRoomViewModelFactory
import com.ifsc.lages.sti.tcc.utilidades.ConnectionUtil
import com.ifsc.lages.sti.tcc.utilidades.KeyPrefs
import com.ifsc.lages.sti.tcc.utilidades.SharedPreferencesUtil
import com.ifsc.lages.sti.tcc.utilidades.components.CustomLayoutMsm

class ResponseStudentsActivity : BaseActivty(), SimulatedResponseUserAdapter.Listener, BottonSheetResponseUserFragment.CallbackOptions {

    var recyclerView: RecyclerView? = null
    var customLayoutMsm: CustomLayoutMsm? = null
    var swipeRefreshLayout: SwipeRefreshLayout? = null
    var viewModel: ClassRoomViewModel? = null
    var simulatedResponseUserAdapter: SimulatedResponseUserAdapter? = null
    private var resultsUser: MutableList<ResultUser>? = null
    private var idSimulated: Long? = null
    private var response: ResultUser? = null
    private var bottonSheetResponseUserFragment : BottonSheetResponseUserFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_response_students)
        setTitleToolbar(getString(R.string.title_toolbar_response_students))
    }

    override fun mapComponents() {
        super.mapComponents()
        intent.extras?.let {
            idSimulated = intent!!.extras!!.getLong("result_overall", 0L)
        }
        recyclerView = findViewById(R.id.recycler_view)
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout)
        customLayoutMsm = findViewById(R.id.layout_error)
        showDisplayList()
    }

    override fun mapActionComponents() {
        super.mapActionComponents()
    }

    override fun createRestListener() {
        super.createRestListener()
        viewModel =
            ViewModelProvider(this, ClassRoomViewModelFactory(this@ResponseStudentsActivity)).get(
                ClassRoomViewModel::class.java
            )
        viewModel!!.loadResponseUsers.observe(this, androidx.lifecycle.Observer {
            swipeRefreshLayout!!.isRefreshing = false
            if (it.error!!.not()) {
                simulatedResponseUserAdapter?.updateList(it.success!!)
            } else {
                Toast.makeText(this@ResponseStudentsActivity, it.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    fun showDisplayList() {
        resultsUser = ArrayList()
        simulatedResponseUserAdapter = SimulatedResponseUserAdapter(resultsUser!!, this)
        val viewManager = LinearLayoutManager(this)
        recyclerView?.layoutManager = viewManager
        recyclerView?.adapter = simulatedResponseUserAdapter
    }

    override fun onItemClick(response: ResultUser) {
        showDialogOptionUserWhiteResponse(response)
    }

    override fun onResume() {
        super.onResume()
        callResultUsers()
    }

    fun callResultUsers() {
        var userId = SharedPreferencesUtil.get(this@ResponseStudentsActivity, KeyPrefs.USER_ID, 0L)
        if(ConnectionUtil.isNetworkAvailable(this@ResponseStudentsActivity)) {
            swipeRefreshLayout!!.isRefreshing = true
            viewModel!!.loadResultUserBySimulatedClassrrom(userId, idSimulated!!)
        } else {
            Toast.makeText(this@ResponseStudentsActivity, getString(R.string.error_conection), Toast.LENGTH_LONG).show();
        }
    }

    fun showDialogOptionUserWhiteResponse(response: ResultUser) {
        this.response = response
        bottonSheetResponseUserFragment = BottonSheetResponseUserFragment.newInstance()
        bottonSheetResponseUserFragment!!.show(supportFragmentManager, null)
        bottonSheetResponseUserFragment!!.setListener(this)
    }

    override fun onClick(options: EOptionsResponse?) {
        bottonSheetResponseUserFragment?.dismiss()
    }
}