package com.ifsc.lages.sti.tcc.ui.main.dashboard.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.ifsc.lages.sti.tcc.model.result.ResultMatters
import com.ifsc.lages.sti.tcc.model.result.ResultSimulated
import com.ifsc.lages.sti.tcc.ui.main.fragment.*

class ViewPagerLastMatterAdapter(
    val context: Context,
    fm: FragmentManager?,
    private val resultOverall: MutableList<ResultMatters>
) : FragmentStatePagerAdapter(fm!!, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        var obgect = resultOverall[position]
        return DashboardLastMatterFragment.getInstance(obgect)
    }

    override fun getItemPosition(`object`: Any): Int {
        return super.getItemPosition(`object`)
    }

    override fun getCount(): Int {
        return resultOverall.size
    }

}