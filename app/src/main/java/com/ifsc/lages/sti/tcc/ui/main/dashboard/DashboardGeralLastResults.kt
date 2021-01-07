package com.ifsc.lages.sti.tcc.ui.main.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import br.edu.ifsc.cancontrol.utilidades.MapElement
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.model.result.ResultValue
import com.ifsc.lages.sti.tcc.model.result.ResultadoSimulado
import com.ifsc.lages.sti.tcc.resources.result.ResultadoResponse
import com.ifsc.lages.sti.tcc.ui.desepenhoesp.DesempenhoSimuladoActivity
import com.ifsc.lages.sti.tcc.ui.main.dashboard.adapter.LastSimulatedAdapter
import com.ifsc.lages.sti.tcc.ui.main.dashboard.adapter.ViewPagerLastSimulatedAdapter


class DashboardGeralLastResults(private val mContext: AppCompatActivity, private val mViewRoot: View) : MapElement, LastSimulatedAdapter.Listener {

    private var pagerIndicator: LinearLayout? = null
    private var previous_pos = 0
    private var dotsCount = 0
    private  var dots : Array<ImageView?> = emptyArray()
    private var resultOverall : MutableList<ResultadoSimulado>? = null
    private var recyclerView : ViewPager? = null
    private var adapter : ViewPagerLastSimulatedAdapter? = null
    private var btnPlusInfo : Button? = null

    override fun mapComponents() {
        btnPlusInfo = mViewRoot.findViewById(R.id.btn_plus_info_last) as Button
        recyclerView = mViewRoot.findViewById(R.id.pager_introduction_lasted) as ViewPager
        pagerIndicator = mViewRoot.findViewById(R.id.view_pager_count_dots_last) as LinearLayout
    }

    override fun mapActionComponents() {
        btnPlusInfo?.setOnClickListener {
            var resultadoSimulado = resultOverall?.get(previous_pos - 1)
            var bundle = Bundle()
            bundle.putSerializable("result_overall", resultadoSimulado)
            val intent = Intent(mContext, DesempenhoSimuladoActivity::class.java)
            intent.putExtras(bundle)
            mContext.startActivity(intent)
        }
    }

    init {
        mapComponents()
        mapActionComponents()
    }

    fun showDashboard(resultado : MutableList<ResultadoSimulado>) {
        resultOverall = resultado
        if(adapter == null) {
            adapter =
                ViewPagerLastSimulatedAdapter(
                    mContext,
                    mContext.supportFragmentManager,
                    resultOverall!!
                )

            recyclerView!!.adapter = adapter
            recyclerView!!.currentItem = 0
            recyclerView!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
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
        dots = arrayOfNulls<ImageView?>(size = dotsCount)
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

    override fun onItemClick(response: ResultadoSimulado) {

    }
}