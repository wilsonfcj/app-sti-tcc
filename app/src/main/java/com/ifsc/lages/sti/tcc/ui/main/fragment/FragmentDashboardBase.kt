package com.ifsc.lages.sti.tcc.ui.main.fragment

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.ifsc.lages.sti.tcc.model.result.ResultQuantitative
import com.ifsc.lages.sti.tcc.utilidades.components.CustomLayoutMsm
import java.util.*

open class FragmentDashboardBase : Fragment(), MapElement {

    var resultValue : ResultQuantitative? = null
    var textViewAcertos : TextView? = null
    var textViewErros: TextView? = null
    var chart: PieChart? = null
    var layoutError : CustomLayoutMsm? = null

    protected var tfRegular: Typeface? = null
    protected var tfLight: Typeface? = null

    protected val parties = arrayOf(
        "Acertos", "Erros", "Não respondidas"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tfRegular = Typeface.createFromAsset(activity!!.assets, "OpenSans-Regular.ttf")
        tfLight = Typeface.createFromAsset(activity!!.assets, "OpenSans-Light.ttf")
        resultValue = arguments!!.getSerializable("result_overall") as ResultQuantitative?
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard_tecnologia, container, false)
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
        fun getInstance(resultOverall : ResultQuantitative): FragmentDashboardBase {
            val f =
                FragmentDashboardBase()
            val args = Bundle()
            args.putSerializable("result_overall", resultOverall)
            f.arguments = args
            return f
        }
    }

    override fun mapComponents() {
        chart = view?.findViewById(R.id.chart1)
        textViewAcertos = view?.findViewById(R.id.tv_geralI)
        textViewErros = view?.findViewById(R.id.tv_geralII)
        layoutError = view?.findViewById(R.id.layout_error)
        textViewAcertos?.text = resultValue?.acertos.toString()
        textViewErros?.text = resultValue?.erros.toString()
        createChart2()
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

    fun createChart2() {
        chart = view!!.findViewById(R.id.chart1)
        chart!!.setUsePercentValues(true)
        chart!!.description.isEnabled = false
        chart!!.setExtraOffsets(5f, 10f, 5f, 5f)
        chart!!.dragDecelerationFrictionCoef = 0.95f
        chart!!.setCenterTextTypeface(tfLight)
        chart!!.centerText = generateCenterSpannableText()
        chart!!.isDrawHoleEnabled = true
        chart!!.setHoleColor(Color.WHITE)
        chart!!.setTransparentCircleColor(Color.WHITE)
        chart!!.setTransparentCircleAlpha(110)
        chart!!.holeRadius = 58f
        chart!!.transparentCircleRadius = 61f
        chart!!.setDrawCenterText(true)
        chart!!.rotationAngle = 0f
        chart!!.isRotationEnabled = true
        chart!!.isHighlightPerTapEnabled = true
        chart!!.animateY(1400, Easing.EaseInOutQuad)
        val l = chart!!.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.xEntrySpace = 7f
        l.yEntrySpace = 0f
        l.yOffset = 0f

        // entry label styling
        chart!!.setEntryLabelColor(Color.WHITE)
        chart!!.setEntryLabelTypeface(tfRegular)
        chart!!.setEntryLabelTextSize(12f)
        setData()
    }

    private fun setData() {
        showError()
        val values = ArrayList<PieEntry>()
        values.add(PieEntry((resultValue!!.acertos!! * 100) / resultValue!!.total!!.toFloat(), parties[0]))
        values.add(PieEntry((resultValue!!.erros!! * 100) / resultValue!!.total!!.toFloat(), parties[1]))
        if(resultValue!!.naoRespondidas!! > 0)
            values.add(PieEntry((resultValue!!.naoRespondidas!! * 100) / resultValue!!.total!!.toFloat(), parties[2]))

        val dataSet = PieDataSet(values, "")
        dataSet.sliceSpace = 3f
        dataSet.selectionShift = 5f
        dataSet.setColors(*ColorTemplate.MATERIAL_COLORS)
        dataSet.valueLinePart1OffsetPercentage = 80f
        dataSet.valueLinePart1Length = 0.2f
        dataSet.valueLinePart2Length = 0.4f
        dataSet.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.BLACK)
        data.setValueTypeface(tfLight)
        chart!!.data = data

        chart!!.highlightValues(null)

        chart!!.invalidate()


//        val dataSet = PieDataSet(values, "")
//        dataSet.sliceSpace = 3f
//        dataSet.selectionShift = 5f
//        dataSet.setColors(*ColorTemplate.MATERIAL_COLORS)
//        dataSet.setSelectionShift(0f)
//        val data = PieData(dataSet)
//        data.setValueFormatter(PercentFormatter())
//        data.setValueTextSize(11f)
//        data.setValueTypeface(tfLight)
//        data.setValueTextColor(Color.WHITE)
//
//        chart!!.data = data
//        chart!!.invalidate()
    }

    private fun generateCenterSpannableText(): SpannableString? {
        val s = SpannableString("Total Questões\n ${resultValue!!.total}")
        return s
    }

}