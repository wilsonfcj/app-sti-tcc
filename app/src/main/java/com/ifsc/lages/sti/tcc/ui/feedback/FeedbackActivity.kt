package com.ifsc.lages.sti.tcc.ui.feedback

import android.os.Bundle
import android.webkit.WebSettings
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import br.edu.ifsc.cancontrol.utilidades.BaseActivty
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.model.user.User
import com.ifsc.lages.sti.tcc.ui.feedback.viewmodel.FeedbackViewModel
import com.ifsc.lages.sti.tcc.ui.feedback.viewmodel.FeedbackViewModelFactory
import com.ifsc.lages.sti.tcc.utilidades.components.CustomLayoutMsm
import com.judemanutd.katexview.KatexView

class FeedbackActivity : BaseActivty() {

    private var idSimulated: Long? = null
    private var viewModel :  FeedbackViewModel? = null
    private var katexText : KatexView? = null
    private var recyclerView: RecyclerView? = null
    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    private var customLayoutMsm : CustomLayoutMsm? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback_simulated)
    }

    override fun mapComponents() {
        intent.extras?.let {
            idSimulated = intent!!.extras!!.getLong("result_overall", 0L)
        }
        recyclerView = findViewById(R.id.recycler_view)
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout)
        customLayoutMsm = findViewById(R.id.layout_error)
    }

    fun createKatex(tex : String) {
        katexText = findViewById(R.id.katex_text)
        var teste = tex.replace("$", "$$ ")

        katexText!!.setText(teste)
        katexText!!.getSettings().setDefaultFontSize(12)
    }

    override fun mapActionComponents() {
        super.mapActionComponents()
    }

    override fun onResume() {
        super.onResume()
        loadFeed()
    }

    fun loadFeed() {
        swipeRefreshLayout!!.isRefreshing = true
        var user = User.UserShared.load(this@FeedbackActivity)
        viewModel?.loadFeedback(user!!._id!!, idSimulated!!)
    }

    override fun createRestListener() {
        super.createRestListener()
        viewModel = ViewModelProvider(this, FeedbackViewModelFactory(this@FeedbackActivity)).get(FeedbackViewModel::class.java)
        viewModel!!.loadFeedback.observe(this, androidx.lifecycle.Observer {
            swipeRefreshLayout!!.isRefreshing = false
            if(it.error!!.not()) {
                createKatex(it.success!![0].descricao!!)
            } else {
                Toast.makeText(this@FeedbackActivity, it.message, Toast.LENGTH_LONG).show()
            }
        })
    }

}