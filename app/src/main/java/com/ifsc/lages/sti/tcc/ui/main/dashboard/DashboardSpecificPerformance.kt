package com.ifsc.lages.sti.tcc.ui.main.dashboard

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import br.edu.ifsc.cancontrol.utilidades.MapElement
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.model.result.ResultOverall
import com.ifsc.lages.sti.tcc.ui.main.dashboard.adapter.ViewPagerAdapter

class DashboardSpecificPerformance(private val mContext: AppCompatActivity, private val mViewRoot: View) : MapElement {

    private var onboardPager: ViewPager? = null
    private var pagerIndicator: LinearLayout? = null
    private var previous_pos = 0
    private var adapter: FragmentStatePagerAdapter? = null
    private var dotsCount = 0
    private  var dots : Array<ImageView?> = emptyArray()
    private var resultOverall : ResultOverall? = null

    override fun mapComponents() {
        onboardPager = mViewRoot.findViewById(R.id.pager_introduction_specific) as ViewPager
        pagerIndicator = mViewRoot.findViewById(R.id.view_pager_count_dots_specific) as LinearLayout
    }

    override fun mapActionComponents() {
    }

    init {
        mapComponents()
        mapActionComponents()
    }

    fun showDashboard(resultado : ResultOverall) {
        resultOverall = resultado
        if(adapter == null) {
            adapter =
                ViewPagerAdapter(
                    mContext,
                    mContext.supportFragmentManager,
                    resultOverall!!
                )

            onboardPager!!.adapter = adapter
            onboardPager!!.currentItem = 0
            onboardPager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {}

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    // Change the current position intimation
                    for (i in 0 until dotsCount) {
                        dots[i]!!.setImageDrawable( ContextCompat.getDrawable(mContext, R.drawable.non_selected_item_dot))
                    }

                    dots[position]!!.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.selected_item_dot))

                    val pos = position + 1
                    previous_pos = pos

                }

                override fun onPageSelected(position: Int) {

                }
            })
            setUiPageViewController()
        } else {
            adapter!!.notifyDataSetChanged()
        }

    }

    private fun setUiPageViewController() {
        dotsCount = adapter!!.count
        dots = arrayOfNulls<ImageView?>(size = 4)
        for (i in 0 until dotsCount) {
            dots[i] = ImageView(mContext)
            dots[i]!!.setImageDrawable(
                ContextCompat.getDrawable(
                    mContext,
                    R.drawable.non_selected_item_dot
                )
            )
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(6, 0, 6, 0)
            pagerIndicator?.addView(dots[i], params)
        }
        dots[0]!!.setImageDrawable(
            ContextCompat.getDrawable(
                mContext,
                R.drawable.selected_item_dot
            )
        )
    }
}