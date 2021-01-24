package com.ifsc.lages.sti.tcc.ui.feedback.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.ifsc.lages.sti.tcc.model.question.QuestionFeedback
import com.ifsc.lages.sti.tcc.ui.feedback.fragment.QuestionFeedbackFragment

class FeedbackViewPagerAdapter(
    val context: Context,
    fm: FragmentManager?,
    private val questionFeedbackList: MutableList<QuestionFeedback>
) : FragmentStatePagerAdapter(fm!!, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        var questionFeedback = questionFeedbackList[position]
        return QuestionFeedbackFragment.getInstance(questionFeedback, position + 1)
    }

    override fun getItemPosition(`object`: Any): Int {
        return super.getItemPosition(`object`)
    }

    override fun getCount(): Int {
        return questionFeedbackList.size
    }

}