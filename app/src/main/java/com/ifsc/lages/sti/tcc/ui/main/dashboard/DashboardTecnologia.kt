package com.ifsc.lages.sti.tcc.ui.main.dashboard

import android.content.Context
import android.graphics.Color
import android.view.View
import br.edu.ifsc.cancontrol.utilidades.MapElement
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.ifsc.lages.sti.tcc.R
import java.util.ArrayList

class DashboardTecnologia(private val mContext: Context, private val mViewRoot: View) : MapElement {

    protected val parties = arrayOf(
        "Acertos", "Erros", "Não respondidas", "Party D", "Party E", "Party F", "Party G", "Party H",
        "Party I", "Party J", "Party K", "Party L", "Party M", "Party N", "Party O", "Party P",
        "Party Q", "Party R", "Party S", "Party T", "Party U", "Party V", "Party W", "Party X",
        "Party Y", "Party Z"
    )

    var chart: PieChart? = null

    override fun mapComponents() {
        createChart()
    }
    override fun mapActionComponents() {}

    init {
        mapComponents()
        mapActionComponents()
    }

    fun createChart() {
        chart = mViewRoot.findViewById(R.id.chart1)
        chart!!.setUsePercentValues(true)
        chart!!.getDescription().isEnabled = false
//        chart!!.setCenterText(generateCenterSpannableText())

        chart!!.setDrawHoleEnabled(true)
        chart!!.setHoleColor(Color.TRANSPARENT)

        chart!!.setTransparentCircleColor(Color.WHITE)
        chart!!.setTransparentCircleAlpha(110)

        chart!!.setHoleRadius(58f)
        chart!!.setTransparentCircleRadius(61f)

        chart!!.setDrawCenterText(true)

        chart!!.setRotationEnabled(true)
        chart!!.setHighlightPerTapEnabled(true)
        chart!!.setCenterTextOffset(0f, -20f)

        setData(3, 100f)

        chart!!.animateY(1400, Easing.EaseInOutQuad)

        val l = chart!!.getLegend()
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(false)
        l.xEntrySpace = 7f
        l.yEntrySpace = 0f
        l.yOffset = 0f

        // entry label styling

        // entry label styling
        chart!!.setEntryLabelColor(Color.WHITE)
        chart!!.setEntryLabelTextSize(12f)
    }

    private fun setData(count: Int, range: Float) {
        val values = ArrayList<PieEntry>()
        for (i in 0 until count) {
            values.add(
                PieEntry(
                    (Math.random() * range + range / 5).toFloat(),
                    parties[i % parties.size]
                )
            )
        }
        val dataSet = PieDataSet(values, "")
        dataSet.sliceSpace = 3f
        dataSet.selectionShift = 5f
        dataSet.setColors(*ColorTemplate.MATERIAL_COLORS)
        dataSet.setSelectionShift(0f)
        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.WHITE)
//        data.setValueTypeface(tfLight)
        chart!!.data = data
        chart!!.invalidate()
    }
}