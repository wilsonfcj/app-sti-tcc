package com.ifsc.lages.sti.tcc.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import br.edu.ifsc.cancontrol.utilidades.BaseActivty
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.model.user.User
import com.ifsc.lages.sti.tcc.ui.login.LoginActivity
import com.ifsc.lages.sti.tcc.ui.settings.SettingsActivity
import com.ifsc.lages.sti.tcc.utilidades.ActivityUtil
import com.ifsc.lages.sti.tcc.utilidades.ImageUtil
import com.ifsc.lages.sti.tcc.utilidades.KeyPrefs
import com.ifsc.lages.sti.tcc.utilidades.SharedPreferencesUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivty() {

    var imageProfile : ImageView? = null

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
        setImageProfile()
    }

    fun setImageProfile() {
        var image = SharedPreferencesUtil.get(this@MainActivity, KeyPrefs.USER_PHOTO, "")
        if(image.isNotEmpty()) {
            imageProfile?.setImageBitmap(ImageUtil.convertBase64ToBitmap(image))
        }
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
