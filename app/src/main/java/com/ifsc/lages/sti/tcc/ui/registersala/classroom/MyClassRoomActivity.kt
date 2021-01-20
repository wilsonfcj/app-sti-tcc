package com.ifsc.lages.sti.tcc.ui.registersala.classroom

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
import com.ifsc.lages.sti.tcc.model.classrom.Classroom
import com.ifsc.lages.sti.tcc.props.EOptions
import com.ifsc.lages.sti.tcc.props.EUserType
import com.ifsc.lages.sti.tcc.ui.registersala.simulated.MySimulatedClassroomActivity
import com.ifsc.lages.sti.tcc.ui.registersala.adapter.ClassroomAdapter
import com.ifsc.lages.sti.tcc.ui.registersala.bottomsheet.claroom.BottonSheetClassroomOptionFragment
import com.ifsc.lages.sti.tcc.ui.registersala.bottomsheet.claroom.BottonSheetPasswordFragment
import com.ifsc.lages.sti.tcc.ui.registersala.viewmodel.ClassRoomViewModel
import com.ifsc.lages.sti.tcc.ui.registersala.viewmodel.ClassRoomViewModelFactory
import com.ifsc.lages.sti.tcc.utilidades.ConnectionUtil
import com.ifsc.lages.sti.tcc.utilidades.KeyPrefs
import com.ifsc.lages.sti.tcc.utilidades.SharedPreferencesUtil
import com.ifsc.lages.sti.tcc.utilidades.components.CustomLayoutMsm

class MyClassRoomActivity : BaseActivty(), ClassroomAdapter.Listener, BottonSheetPasswordFragment.CallbackPassword, BottonSheetClassroomOptionFragment.CallbackOptions {

    private var viewModel :  ClassRoomViewModel? = null
    var recyclerView: RecyclerView? = null
    var customLayoutMsm : CustomLayoutMsm? = null
    var swipeRefreshLayout: SwipeRefreshLayout? = null
    var userId : Long? = null
    private var classrromList :  MutableList<Classroom>? = null
    private var classRoomAdapter :  ClassroomAdapter? = null
    private var classroom: Classroom? = null
    private var bottonSheetPasswordFragment : BottonSheetPasswordFragment? = null
    private var bottonSheetClassroomOptionFragment : BottonSheetClassroomOptionFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_classroom)
        var userType = SharedPreferencesUtil.get(this@MyClassRoomActivity, KeyPrefs.USER_TYPE, 0)
        when (userType) {
            EUserType.TEACHER.code -> {
                setTitleToolbar(getString(R.string.title_my_classroom_))
            }
            else -> {
                setTitleToolbar(getString(R.string.title_my_classroom))
            }
        }
    }

    override fun mapComponents() {
        super.mapComponents()
        userId = SharedPreferencesUtil.get(this@MyClassRoomActivity, KeyPrefs.USER_ID, 0L)
        recyclerView = findViewById(R.id.recycler_view)
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout)
        customLayoutMsm = findViewById(R.id.layout_error)
        showDisplayList()
        loadClassroom()
    }

    override fun mapActionComponents() {
        super.mapActionComponents()
        swipeRefreshLayout!!.setOnRefreshListener { loadClassroom(userId!!) }
    }

    fun loadClassroom() {
        if(ConnectionUtil.isNetworkAvailable(this@MyClassRoomActivity)) {
            swipeRefreshLayout!!.isRefreshing = true
            loadClassroom(userId!!)
        } else {
            showInfosSimulated(userId!!)
        }
    }

    fun showInfosSimulated(userId: Long) {
        var infos = Classroom.DataBase.loadByIdUser(userId)
        if(infos!!.isEmpty()) {
            customLayoutMsm?.visibility = View.VISIBLE
        }
        classRoomAdapter?.updateList(infos!!)
    }

    fun loadClassroom(userId: Long) {
        viewModel!!.loadSimulatedByUser(userId)
    }

    override fun createRestListener() {
        super.createRestListener()
        viewModel = ViewModelProvider(this, ClassRoomViewModelFactory(this@MyClassRoomActivity)).get(ClassRoomViewModel::class.java)
        viewModel!!.minhasSalas.observe(this, androidx.lifecycle.Observer {
            swipeRefreshLayout!!.isRefreshing = false
            if(it.error!!.not()) {
                Classroom.DataBase.deleteByIdUser(userId!!)
                for (classroom in it.success!!) {
                    if(classroom.participando) {
                        classroom.idUsuario = userId
                        Classroom.DataBase.save(classroom)
                    }
                }
                if(it.success!!.isEmpty()) {
                    customLayoutMsm?.visibility = View.VISIBLE
                }
                classRoomAdapter?.updateList(it.success)
            } else {
                Toast.makeText(this@MyClassRoomActivity, it.message, Toast.LENGTH_LONG).show()
            }
        })

        viewModel!!.enterClassrrom.observe(this, androidx.lifecycle.Observer {
            swipeRefreshLayout!!.isRefreshing = false
            if(it.success!!) {
                loadClassroom(userId!!)
            } else {
                Toast.makeText(this@MyClassRoomActivity, it.message, Toast.LENGTH_LONG).show()
            }
        })

        viewModel!!.deleteCompleto.observe(this, androidx.lifecycle.Observer {
            swipeRefreshLayout!!.isRefreshing = false
            if(it.error!!.not()) {
                Classroom.DataBase.deleteById(it!!.success!!._id!!)
                loadClassroom(userId!!)
            } else {
                Toast.makeText(this@MyClassRoomActivity, it.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    fun showDisplayList() {
        classrromList = ArrayList()
        var userType = SharedPreferencesUtil.get(this@MyClassRoomActivity, KeyPrefs.USER_TYPE, EUserType.STUDENT.code)
        classRoomAdapter = ClassroomAdapter(classrromList!!, this, this@MyClassRoomActivity, userType)
        val viewManager = LinearLayoutManager(this)
        recyclerView?.layoutManager = viewManager
        recyclerView?.adapter = classRoomAdapter
    }

    override fun onItemClick(response: Classroom) {
        classroom = response
        if(response.participando) {
            var userType = SharedPreferencesUtil.get(this@MyClassRoomActivity, KeyPrefs.USER_TYPE, 0)
            if(userType == EUserType.TEACHER.code) {
                if(response.qtdParticipantes == 0) {
                    showDialogSelectedTeacher(response)
                } else {
                    showMySumulated()
                }
            } else {
                showMySumulated()
            }
        } else {
            showDialogSelected(response)
        }
    }

    fun showDialogSelected(response: Classroom) {
        classroom = response
        bottonSheetPasswordFragment = BottonSheetPasswordFragment.newInstance()
        bottonSheetPasswordFragment!!.show(supportFragmentManager, null)
        bottonSheetPasswordFragment!!.setListener(this)
    }

    fun showDialogSelectedTeacher(response: Classroom) {
        classroom = response
        bottonSheetClassroomOptionFragment = BottonSheetClassroomOptionFragment.newInstance()
        bottonSheetClassroomOptionFragment!!.show(supportFragmentManager, null)
        bottonSheetClassroomOptionFragment!!.setListener(this)
    }

    override fun onClick(passwod: String?) {
        if(ConnectionUtil.isNetworkAvailable(this@MyClassRoomActivity)) {
            swipeRefreshLayout!!.isRefreshing = true
            viewModel!!.enterClassroom2(userId!!, classroom!!._id!!, passwod!!)
        } else {
            Toast.makeText(this@MyClassRoomActivity, getString(R.string.error_conection), Toast.LENGTH_LONG).show();
        }
    }

    override fun onResume() {
        super.onResume()
        loadClassroom()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add -> {
                if (ConnectionUtil.isNetworkAvailable(this@MyClassRoomActivity).not()) {
                    Toast.makeText(this@MyClassRoomActivity, getString(R.string.error_conection), Toast.LENGTH_LONG).show()
                } else {
                    val intent = Intent(this@MyClassRoomActivity, RegisterClassroomActivity::class.java)
                    startActivity(intent)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun showMySumulated() {
        val lIntent = Intent(this@MyClassRoomActivity, MySimulatedClassroomActivity::class.java)
        var bundle = Bundle()
        bundle.putLong("id_classroom", classroom!!._id!!)
        lIntent.putExtras(bundle)
        startActivity(lIntent)
    }

    override fun onClick(options: EOptions?) {
        bottonSheetClassroomOptionFragment!!.dismiss()
        if(options?.codigo == EOptions.INICIAR.codigo) {
            showMySumulated()
        } else {
            swipeRefreshLayout!!.isRefreshing = true
            viewModel!!.deleteClassroom(userId!!, classroom!!._id!!)
        }
    }
}