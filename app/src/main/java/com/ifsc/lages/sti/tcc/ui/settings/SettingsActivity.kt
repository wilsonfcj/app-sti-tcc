package com.ifsc.lages.sti.tcc.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import br.edu.ifsc.cancontrol.utilidades.BaseActivty
import com.ifsc.lages.sti.tcc.BuildConfig
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.model.user.User
import com.ifsc.lages.sti.tcc.props.EUserType
import com.ifsc.lages.sti.tcc.ui.login.LoginActivity
import com.ifsc.lages.sti.tcc.utilidades.*
import com.ifsc.lages.sti.tcc.utilidades.components.CustomItemUser
import java.util.*

class SettingsActivity : BaseActivty() {

    var imageProfile : ImageView? = null
    var textView :  TextView? = null
    var buttonExit :  Button? = null
    var buttonChangeInfos: Button? = null

    var customEmail : CustomItemUser? = null
    var customPhone : CustomItemUser? = null
    var customBirthday : CustomItemUser? = null
    var customEducationIntitution : CustomItemUser? = null
    var customRegisterNumber : CustomItemUser? = null
    var customYearsJoin : CustomItemUser? = null
    var customVersionCode : CustomItemUser? = null
    var containerStudent : LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }


    override fun mapComponents() {
        super.mapComponents()
        setDisplayHomeAs(false)
        setTitleToolbar(getString(R.string.title_toolbar_my_profile))
        imageProfile = findViewById(R.id.img_view_user)
        textView = findViewById(R.id.txt_view_user_name)
        buttonExit = findViewById(R.id.button_exit)
        buttonChangeInfos = findViewById(R.id.button_change_infos)

        customEmail = findViewById(R.id.custom_email)
        customPhone = findViewById(R.id.custom_phone)
        customBirthday = findViewById(R.id.custom_birth_day)
        customEducationIntitution = findViewById(R.id.custom_education_institituion)
        customRegisterNumber = findViewById(R.id.custom_register_number)
        customYearsJoin = findViewById(R.id.custom_register_years_join)
        customVersionCode = findViewById(R.id.custom_version_code)

        containerStudent = findViewById(R.id.container_student)

        var name = SharedPreferencesUtil.get(this@SettingsActivity, KeyPrefs.USER_NAME, "")
        textView?.text = "Bem vindo(a),\n$name"

        var image = SharedPreferencesUtil.get(this@SettingsActivity, KeyPrefs.USER_PHOTO, "")
        if(image.isNotEmpty()) {
            imageProfile?.setImageBitmap(ImageUtil.convertBase64ToBitmap(image))
        }

        showDisplayInfos()
    }

    fun showDisplayInfos() {

        var birthday = SharedPreferencesUtil.get(this@SettingsActivity, KeyPrefs.USER_BIRTH_DAY, Date())
        var type = SharedPreferencesUtil.get(this@SettingsActivity, KeyPrefs.USER_TYPE, 1)
        var registration = SharedPreferencesUtil.get(this@SettingsActivity, KeyPrefs.USER_REGISTRATION, 0L)
        var email =  SharedPreferencesUtil.get(this@SettingsActivity, KeyPrefs.USER_EMAIL, "")
        var phone = SharedPreferencesUtil.get(this@SettingsActivity, KeyPrefs.USER_PHONE, "")
        var yearJoin = SharedPreferencesUtil.get(this@SettingsActivity, KeyPrefs.USER_YEAR_JOIN, 2017L)
        var educationName = SharedPreferencesUtil.get(this@SettingsActivity, KeyPrefs.EDUCATION_INSTITUITION_NOME, "")

        customEmail?.setTitle(email)
        customPhone?.setTitle(phone)
        customBirthday?.setTitle(StringUtil.data(birthday, "dd/MM/yyyy"))
        customEducationIntitution?.setTitle(educationName)

        if(EUserType.STUDENT.code == type) {
            customRegisterNumber?.setTitle(registration.toString())
            customYearsJoin?.setTitle(yearJoin.toString())
            containerStudent?.visibility = View.VISIBLE
        } else {
            containerStudent?.visibility = View.GONE
        }


        customVersionCode?.setTitle(BuildConfig.VERSION_NAME)
    }

    override fun mapActionComponents() {
        super.mapActionComponents()
        buttonExit?.setOnClickListener {
            User.UserShared.clear(this@SettingsActivity)
            val lIntent = Intent(this@SettingsActivity, LoginActivity::class.java)
            lIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            lIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            lIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(lIntent)
            finish()
        }

        buttonChangeInfos?.setOnClickListener {

        }
    }
}