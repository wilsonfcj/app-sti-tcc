package com.ifsc.lages.sti.tcc.ui.feedback.fragment

import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import br.edu.ifsc.cancontrol.utilidades.MapElement
import com.google.android.material.textfield.TextInputLayout
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.model.question.QuestionFeedback
import com.ifsc.lages.sti.tcc.model.result.ResultQuantitative
import com.ifsc.lages.sti.tcc.props.EArea
import com.ifsc.lages.sti.tcc.props.EDisciplina
import com.ifsc.lages.sti.tcc.props.EQuestion
import com.ifsc.lages.sti.tcc.props.ETipoSimulado
import com.ifsc.lages.sti.tcc.utilidades.EditTextMask
import com.ifsc.lages.sti.tcc.utilidades.ImageUtil
import com.judemanutd.katexview.KatexView

class QuestionFeedbackFragment : Fragment(), MapElement {

    var editTextDiscursiveResponse : EditText? = null
    var textViewArea : TextView? = null
    var textViewMatters : TextView? = null
    var textViewType : TextView? = null
    var textViewYear : TextView? = null
    var textViewQuestionNumber : TextView? = null

    var katexTextDescription : KatexView? = null
    var katexTextA : KatexView? = null
    var katexTextB : KatexView? = null
    var katexTextC : KatexView? = null
    var katexTextD : KatexView? = null
    var katexTextE : KatexView? = null

    var imageViewDescription : ImageView? = null
    var imageViewA : ImageView? = null
    var imageViewB : ImageView? = null
    var imageViewC : ImageView? = null
    var imageViewD : ImageView? = null
    var imageViewE : ImageView? = null

    var linearLayoutA : LinearLayout? = null
    var linearLayoutB : LinearLayout? = null
    var linearLayoutC : LinearLayout? = null
    var linearLayoutD : LinearLayout? = null
    var linearLayoutE : LinearLayout? = null
    var resultValue : QuestionFeedback? = null
    var numberQuestion : Int? = null
    var radioGroupDays : LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resultValue = arguments!!.getSerializable("result_question") as QuestionFeedback?
        numberQuestion = arguments!!.getInt("number_question")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feedbak, container, false)
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

    override fun mapComponents() {
        textViewQuestionNumber = view!!.findViewById(R.id.tv_question_number)

        textViewArea = view!!.findViewById(R.id.tv_area)
        textViewMatters = view!!.findViewById(R.id.tv_matters)
        textViewType = view!!.findViewById(R.id.tv_type)
        textViewYear = view!!.findViewById(R.id.tv_years)
        editTextDiscursiveResponse = view!!.findViewById(R.id.edit_text_response_question)

        katexTextDescription = view!!.findViewById(R.id.katex_text)
        katexTextA = view!!.findViewById(R.id.katex_question_a)
        katexTextB = view!!.findViewById(R.id.katex_question_b)
        katexTextC = view!!.findViewById(R.id.katex_question_c)
        katexTextD = view!!.findViewById(R.id.katex_question_d)
        katexTextE = view!!.findViewById(R.id.katex_question_e)
        radioGroupDays = view!!.findViewById(R.id.radio_group_days)

        imageViewDescription = view!!.findViewById(R.id.img_question_desc)
        imageViewA = view!!.findViewById(R.id.img_question_a)
        imageViewB = view!!.findViewById(R.id.img_question_b)
        imageViewC = view!!.findViewById(R.id.img_question_c)
        imageViewD = view!!.findViewById(R.id.img_question_d)
        imageViewE = view!!.findViewById(R.id.img_question_e)

        linearLayoutA = view!!.findViewById(R.id.radio_a)
        linearLayoutB = view!!.findViewById(R.id.radio_b)
        linearLayoutC = view!!.findViewById(R.id.radio_c)
        linearLayoutD = view!!.findViewById(R.id.radio_d)
        linearLayoutE = view!!.findViewById(R.id.radio_e)
    }

    override fun mapActionComponents() {
        showDisplay()
    }

    fun showDisplay() {
        textViewQuestionNumber?.text = "Questão $numberQuestion"
        textViewArea?.text = EArea.getEnum(resultValue!!.area!!).situacao
        textViewMatters?.text = EDisciplina.getEnum(resultValue!!.disciplina!!).nameSample
        textViewType?.text = ETipoSimulado.getEnun(resultValue!!.prova!!).descricao
        textViewYear?.text = resultValue!!.ano.toString()

        resultValue!!.respostaCorreta?.let {
            setColorBackroundResponse(resultValue!!.respostaCorreta!!, R.drawable.shape_square_button_pressed)
            if(resultValue!!.isCorreta!!.not()) {
                if(resultValue!!.tipoQuestao == EQuestion.ALTERNATIVA.codigo) {
                    setColorBackroundResponse( resultValue!!.respostaUsuario!!, R.drawable.shape_square_error)
                }
            }
        }

        if(resultValue!!.tipoQuestao == ETipoSimulado.DEFAULT.codigo) {
            textViewMatters?.setBackgroundResource(R.drawable.shape_text_view)
        } else if (resultValue!!.tipoQuestao == ETipoSimulado.ENADE.codigo) {
            textViewMatters?.setBackgroundResource(R.drawable.shape_text_view_one)
        } else {
            textViewMatters?.setBackgroundResource(R.drawable.shape_text_view_two)
        }

        setKatexText(resultValue!!.descricao!!, katexTextDescription!!)
        if(resultValue!!.imagem) {
            resultValue!!.imagemQuestao?.let {
                imageViewDescription!!.setImageBitmap(ImageUtil.convertBase64ToBitmap(resultValue!!.imagemQuestao!!))
            }
        }

        if(resultValue!!.tipoQuestao == EQuestion.ALTERNATIVA.codigo) {
            editTextDiscursiveResponse!!.visibility = View.GONE
            if(resultValue!!.alternativaImagem.not()) {
                radioGroupDays!!.visibility = View.VISIBLE
                setKatexQuestion("a)", resultValue!!.alternativasA!!, katexTextA!!)
                setKatexQuestion("b)", resultValue!!.alternativasB!!, katexTextB!!)
                setKatexQuestion("c)", resultValue!!.alternativasC!!, katexTextC!!)
                setKatexQuestion("d)", resultValue!!.alternativasD!!, katexTextD!!)
                setKatexQuestion("e)", resultValue!!.alternativasE!!, katexTextE!!)
            } else {
                setImageQuestion("a)", resultValue!!.alternativasA!!, imageViewA!!, katexTextA!!)
                setImageQuestion("b)", resultValue!!.alternativasB!!, imageViewB!!, katexTextB!!)
                setImageQuestion("c)", resultValue!!.alternativasC!!, imageViewC!!, katexTextC!!)
                setImageQuestion("d)", resultValue!!.alternativasD!!, imageViewD!!, katexTextD!!)
                setImageQuestion("e)", resultValue!!.alternativasE!!, imageViewE!!, katexTextE!!)
            }
        } else {
            resultValue!!.respostaUsuario?.let {
                editTextDiscursiveResponse!!.setText(resultValue!!.respostaUsuario!!)
            }
            editTextDiscursiveResponse!!.visibility = View.VISIBLE
            radioGroupDays!!.visibility = View.GONE
        }
    }


    fun setColorBackroundResponse(respostaCorreta: String, drawable : Int) {
        when (respostaCorreta) {
            "A" ->{
                linearLayoutA!!.setBackgroundResource(drawable); //R.drawable.shape_square_button_pressed)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    katexTextA!!.setTextColor(activity!!.getColor(R.color.white))
                } else {
                    katexTextA!!.setTextColor(activity!!.resources.getColor(R.color.white))
                }
            }
            "B" ->{
                linearLayoutB!!.setBackgroundResource(drawable)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    katexTextB!!.setTextColor(activity!!.getColor(R.color.white))
                } else {
                    katexTextB!!.setTextColor(activity!!.resources.getColor(R.color.white))
                }
            }
            "C" ->{
                linearLayoutC!!.setBackgroundResource(drawable)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    katexTextC!!.setTextColor(activity!!.getColor(R.color.white))
                } else {
                    katexTextC!!.setTextColor(activity!!.resources.getColor(R.color.white))
                }
            }
            "D" ->{
                linearLayoutD!!.setBackgroundResource(drawable)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    katexTextD!!.setTextColor(activity!!.getColor(R.color.white))
                } else {
                    katexTextD!!.setTextColor(activity!!.resources.getColor(R.color.white))
                }
            }
            "E" ->{
                linearLayoutE!!.setBackgroundResource(drawable)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    katexTextE!!.setTextColor(activity!!.getColor(R.color.white))
                } else {
                    katexTextE!!.setTextColor(activity!!.resources.getColor(R.color.white))
                }
            }
        }
    }

    fun setKatexText(text: String, katexView : KatexView) {
        try {
            katexView.setText(text.replace("$", "$$ "))
            katexView.getSettings().setDefaultFontSize(12)
        } catch (ex : Exception) {
            ex.printStackTrace()
        }
    }

    fun setImageQuestion(number : String, text: String, imageView : ImageView, katexView : KatexView) {
        try {
            katexView.setText(number)
            katexView.getSettings().setDefaultFontSize(12)
            imageView.setImageBitmap(ImageUtil.convertBase64ToBitmap(text))
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun setKatexQuestion(number : String,text: String, katexView : KatexView) {
        try {
            katexView.setText(number + " " + text.replace("$", "$$ "))
            katexView.getSettings().setDefaultFontSize(12)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    companion object {
        @JvmStatic
        fun getInstance(questionFeedback : QuestionFeedback, position : Int): QuestionFeedbackFragment {
            val f = QuestionFeedbackFragment()
            val args = Bundle()
            args.putSerializable("result_question", questionFeedback)
            args.putInt("number_question", position)
            f.arguments = args
            return f
        }
    }
}