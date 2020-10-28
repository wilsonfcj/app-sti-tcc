package com.ifsc.lages.sti.tcc

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.ifsc.lages.sti.tcc.ui.LoginActivity

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val lSplashAnimTime = 600

        Handler().postDelayed({
            Handler().postDelayed({
                    startActivity(Intent(this@SplashScreen, LoginActivity::class.java))
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                    finish()
                }, 2000.toLong()
            )
        }, lSplashAnimTime.toLong())
    }
}