package com.ifsc.lages.sti.tcc.ui.main.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
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
import com.ifsc.lages.sti.tcc.model.ResultValue
import net.cachapa.expandablelayout.ExpandableLayout
import java.util.ArrayList

class DashboardFundamentosFragment : Fragment(), MapElement {

    var imageView : ImageView? = null
    var expadablePichart : ExpandableLayout? = null
    var resultValue : ResultValue? = null
    var textViewAcertos : TextView? = null
    var textViewErros: TextView? = null
    var layoutError : LinearLayout? = null

    protected val parties = arrayOf(
        "Acertos", "Erros", "Não respondidas"
    )

    var chart: PieChart? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resultValue = arguments!!.getSerializable("result_overall") as ResultValue?
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard_fundamentos, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        mapComponents()
        mapActionComponents()

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {
        @JvmStatic
        fun getInstance(resultOverall : ResultValue): DashboardFundamentosFragment {
            val f =
                DashboardFundamentosFragment()
            val args = Bundle()
            args.putSerializable("result_overall", resultOverall)
            f.arguments = args
            return f
        }
    }

    override fun mapComponents() {
        chart = view?.findViewById(R.id.chart1)
        textViewAcertos = view?.findViewById<TextView>(R.id.tv_geralI)
        textViewErros = view?.findViewById<TextView>(R.id.tv_geralII)
        layoutError = view?.findViewById(R.id.layout_error)
        textViewAcertos?.text = resultValue?.acertos.toString()
        textViewErros?.text = resultValue?.erros.toString()
        createChart()
    }

    override fun mapActionComponents() {

    }

    fun showError() {
        if(resultValue!!.acertos == 0 && resultValue!!.erros == 0 && resultValue!!.naoRespondidas == 0) {
            layoutError?.visibility = View.VISIBLE
            chart?.visibility = View.INVISIBLE
        } else {
            layoutError?.visibility = View.INVISIBLE
            chart!!.visibility = View.VISIBLE
        }
    }

    fun createChart() {
        showError()
        chart!!.setUsePercentValues(true)
        chart!!.getDescription().isEnabled = false
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
        setData()
        chart!!.animateY(1400, Easing.EaseInOutQuad)
        val l = chart!!.getLegend()
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(false)
        l.xEntrySpace = 7f
        l.yEntrySpace = 0f
        l.yOffset = 0f

        chart!!.setEntryLabelColor(Color.WHITE)
        chart!!.setEntryLabelTextSize(12f)
    }

    private fun setData() {
        val values = ArrayList<PieEntry>()

        values.add(PieEntry((resultValue!!.acertos!! * 100) / resultValue!!.total!!.toFloat(), parties[0]))
        values.add(PieEntry((resultValue!!.erros!! * 100) / resultValue!!.total!!.toFloat(), parties[1]))
        if(resultValue!!.naoRespondidas!! > 0)
            values.add(PieEntry((resultValue!!.naoRespondidas!! * 100) / resultValue!!.total!!.toFloat(), parties[2]))

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