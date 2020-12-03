package com.ifsc.lages.sti.tcc.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import br.edu.ifsc.cancontrol.utilidades.BaseActivty
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.ui.settings.SettingsActivity
import com.ifsc.lages.sti.tcc.utilidades.ImageUtil
import com.ifsc.lages.sti.tcc.utilidades.KeyPrefs
import com.ifsc.lages.sti.tcc.utilidades.SharedPreferencesUtil
import com.judemanutd.katexview.KatexView

class MainActivity : BaseActivty() {

    var imageProfile : ImageView? = null
    var katex_text : KatexView? = null

    var tex = "Pode-se afirmar que o gráfico da função \$y = 2 + \\frac{1}{x - 1}\$ é o gráfico da função \$y = \\frac{1}{x}\$"

    var tex2 = "This come from string. You can insert inline formula:" +
            " \\(ax^2 + bx + c = 0\\) " +
            "or displayed formula: $$\\sum_{i=0}^n i^2 = \\frac{(n^2+n)(2n+1)}{6}$$"
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun mapComponents() {
        super.mapComponents()
        setDisplayHomeAs(false)

        setTitleToolbar(getString(R.string.title_toolbar_dashboard))
        imageProfile = findViewById(R.id.profile_image)
        val text = "Pode-se afirmar que o gráfico da função  $$ c = \\pm\\sqrt{a^2 + b^2} $$"
        katex_text = findViewById(R.id.katex_text)
        var teste = tex.replace("$", "$$ ")
        katex_text!!.setText(teste)
        setImageProfile()
    }

    fun setImageProfile() {
        var image = SharedPreferencesUtil.get(this@MainActivity, KeyPrefs.USER_PHOTO, "")
        if(image.isNotEmpty()) {
            imageProfile?.setImageBitmap(ImageUtil.convertBase64ToBitmap(image))
        }


//        mathText!!.isClickable = true
//        mathText!!.setTextSize(14)
//        mathText!!.setTextColor(
//            ContextCompat.getColor(
//                applicationContext,
//                R.color.colorPrimary
//            )
//        )
//        mathText!!.setDisplayText("A derivada da função $$ f(x) = x^x$$ é igual a")

    }

    override fun mapActionComponents() {
        super.mapActionComponents()

        imageProfile?.setOnClickListener(View.OnClickListener { v: View? ->
            val lPerfil = Intent(this, SettingsActivity::class.java)
            val lProfile = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this,
                    imageProfile!!,
                    getString(R.string.transition_avatar)
            )
            startActivity(lPerfil, lProfile.toBundle())
        })
    }
}
