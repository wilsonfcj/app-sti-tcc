package com.ifsc.lages.sti.tcc.ui.feedback

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import br.edu.ifsc.cancontrol.utilidades.BaseActivty
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.model.question.QuestionFeedback
import com.ifsc.lages.sti.tcc.model.user.User
import com.ifsc.lages.sti.tcc.ui.feedback.adapter.FeedbackViewPagerAdapter
import com.ifsc.lages.sti.tcc.ui.feedback.viewmodel.FeedbackViewModel
import com.ifsc.lages.sti.tcc.ui.feedback.viewmodel.FeedbackViewModelFactory
import com.ifsc.lages.sti.tcc.utilidades.components.CustomLayoutMsm

class FeedbackActivity : BaseActivty() {

    private var idUserId: Long? = null
    private var idSimulated: Long? = null
    private var resultByUser: Boolean = false
    private var viewModel :  FeedbackViewModel? = null

    private var customLayoutMsm : CustomLayoutMsm? = null
    private var questionFeedbackList :  MutableList<QuestionFeedback>? = null
    private var adapter : FeedbackViewPagerAdapter? = null
    private var pager : ViewPager? = null
    private var tvNumberQuestion : TextView? = null
    private var questions : MutableList<QuestionFeedback>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback_simulated)
    }

    override fun mapComponents() {
        super.mapComponents()
        setTitleToolbar(getString(R.string.title_feedback))
        intent.extras?.let {
            idSimulated = intent!!.extras!!.getLong("result_overall", 0L)
            resultByUser = intent!!.extras!!.getBoolean("result_by_user", false)
            if(resultByUser) {
                idUserId  = intent!!.extras!!.getLong("user_id", 0L)
            }
        }
        customLayoutMsm = findViewById(R.id.layout_error)
        tvNumberQuestion = findViewById(R.id.tv_number_question)
        pager = findViewById(R.id.pager)
    }

    fun showViewPager(resultado : MutableList<QuestionFeedback>) {
        questions = resultado
        tvNumberQuestion?.text = "1/${questions!!.size}"
        if(adapter == null) {
            adapter =
                FeedbackViewPagerAdapter(
                    this@FeedbackActivity,
                    supportFragmentManager,
                    resultado!!
                )

//            pager!!.offscreenPageLimit = 5
            pager!!.adapter = adapter
            pager!!.currentItem = 0
            pager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {}

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {

                }
                override fun onPageSelected(position: Int) {
                    tvNumberQuestion?.text = "${position + 1}/${questions!!.size}"
                }
            })
        } else {
            adapter!!.notifyDataSetChanged()
        }

    }

    override fun mapActionComponents() {
        super.mapActionComponents()
    }

    override fun onResume() {
        super.onResume()
        loadFeed()
    }

    fun loadFeed() {
        if(resultByUser.not()) {
            showLoading("Buscando questões")
            var user = User.UserShared.load(this@FeedbackActivity)
            viewModel?.loadFeedback(user!!._id!!, idSimulated!!)
        } else {
            showLoading("Buscando resposta usuário")
            viewModel?.loadFeedbackByUser(idUserId!!, idSimulated!!)
        }

    }

    override fun createRestListener() {
        super.createRestListener()
        viewModel = ViewModelProvider(this, FeedbackViewModelFactory(this@FeedbackActivity)).get(FeedbackViewModel::class.java)
        viewModel!!.loadFeedback.observe(this, androidx.lifecycle.Observer {
            hideLoading()
            if(it.error!!.not()) {
                showViewPager(it.success!!)
            } else {
                Toast.makeText(this@FeedbackActivity, it.message, Toast.LENGTH_LONG).show()
            }
        })
    }

}