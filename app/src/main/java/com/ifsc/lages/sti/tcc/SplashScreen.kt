package com.ifsc.lages.sti.tcc

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.ifsc.lages.sti.tcc.ui.login.LoginActivity
import com.ifsc.lages.sti.tcc.ui.main.MainActivity
import com.ifsc.lages.sti.tcc.utilidades.KeyPrefs
import com.ifsc.lages.sti.tcc.utilidades.SharedPreferencesUtil

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val lSplashAnimTime = 600

        Handler().postDelayed({
            Handler().postDelayed({
                    startActivity()
                }, 2000.toLong()
            )
        }, lSplashAnimTime.toLong())
    }

    fun startActivity() {
        var cpf = SharedPreferencesUtil.get(this@SplashScreen, KeyPrefs.USER_CPF, "")
        if(cpf.isNotEmpty()) {
            startActivity(Intent(this@SplashScreen, MainActivity::class.java))
        } else {
            startActivity(Intent(this@SplashScreen, LoginActivity::class.java))
        }
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }
}