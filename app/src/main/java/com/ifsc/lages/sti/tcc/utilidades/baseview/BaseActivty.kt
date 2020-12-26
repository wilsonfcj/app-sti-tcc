package br.edu.ifsc.cancontrol.utilidades

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.ifsc.lages.sti.tcc.R

/*
* BaseActivity é uma classe que faz heraça em uma AppCompatActivity(), que tem como
* objetivo adicionar carateristicas comuns que todas as nossas activities terão. Exemplo:
*
* Chamada de dialog de progresso.
* Toast de mensagens para o usuario.
* Configuração de toolbar.
*
* */
abstract class BaseActivty : AppCompatActivity(), MapElement {
    var mToolbar: Toolbar? = null
    var txtTitleToolbar: TextView? = null
        private set
    var  alertDialog : AlertDialog? = null

    private val progressDialog by lazy { ProgressDialog(this) }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        createRestListener()
        mapComponents()
        mapActionComponents()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun setContentView(view: View) {
        super.setContentView(view)
    }

    override fun mapComponents() {
        txtTitleToolbar = findViewById(R.id.action_bar_title_1)
        mToolbar = findViewById(R.id.main_toolbar)
        mToolbar?.let {
            setSupportActionBar(it)
        }
    }

    fun setTitleToolbar(aTitle: String) {
        mToolbar?.let { setTitle(aTitle!!) }
    }

    override fun mapActionComponents() {}

    fun setDisplayHomeAs(boolean: Boolean) {
        supportActionBar!!.setDisplayHomeAsUpEnabled(boolean)
        if (boolean) {
            setColor()
        }
    }

    fun setColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mToolbar?.navigationIcon
                ?.setColorFilter(getColor(R.color.white), PorterDuff.Mode.SRC_ATOP)
        }
    }

    fun getToolbar(): Toolbar? {
        return this.mToolbar
    }

    open fun createRestListener() {}

    fun dimissDialog() {
        if(alertDialog != null) {
            alertDialog!!.dismiss()
        }
    }

    fun showSimpleToat(msm : String) {
        Toast.makeText(this@BaseActivty, msm!!, Toast.LENGTH_LONG).show()
    }

    fun applyTint(icon: Drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            icon.colorFilter = PorterDuffColorFilter(getColor(R.color.white), PorterDuff.Mode.SRC_IN)
        } else {
            icon.colorFilter = PorterDuffColorFilter(
                resources.getColor(R.color.white),
                PorterDuff.Mode.SRC_IN
            )
        }
    }

    fun showLoading(message: String) {
        progressDialog.setMessage(message)
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog.setCancelable(false)
        progressDialog.isIndeterminate = true
        progressDialog?.show()
    }

    fun hideLoading() {
        progressDialog?.dismiss()
    }

    open fun hideKeyboardFrom(context: Context, view: View) {
        val imm: InputMethodManager =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}