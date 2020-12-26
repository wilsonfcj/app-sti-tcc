package com.ifsc.lages.sti.tcc.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import br.edu.ifsc.cancontrol.utilidades.BaseActivty
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.model.ResultOverall
import com.ifsc.lages.sti.tcc.model.ResultValue
import com.ifsc.lages.sti.tcc.model.user.User
import com.ifsc.lages.sti.tcc.props.EUserType
import com.ifsc.lages.sti.tcc.ui.login.LoginActivity
import com.ifsc.lages.sti.tcc.ui.main.ViewPagerAdapter
import com.ifsc.lages.sti.tcc.ui.main.dashboard.DashboardGeral
import com.ifsc.lages.sti.tcc.ui.settings.SettingsActivity
import com.ifsc.lages.sti.tcc.utilidades.Constants
import com.ifsc.lages.sti.tcc.utilidades.ImageUtil
import com.ifsc.lages.sti.tcc.utilidades.KeyPrefs
import com.ifsc.lages.sti.tcc.utilidades.SharedPreferencesUtil
import com.judemanutd.katexview.KatexView
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem


class MainActivity2 : BaseActivty() {

    private var onboardPager: ViewPager? = null
    private var pagerIndicator: LinearLayout? = null
    var previous_pos = 0
    private var adapter: FragmentStatePagerAdapter? = null

    var imageProfile : ImageView? = null
    var katex_text : KatexView? = null
    lateinit var menuHeader: AccountHeader
    var menuDrawer: Drawer? = null
    var dashboardGeral : DashboardGeral? = null

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
        onboardPager = findViewById(R.id.pager_introduction) as ViewPager
        pagerIndicator = findViewById(R.id.viewPagerCountDots) as LinearLayout

        menu()
        setImageProfile()
        setHeaderInfo()

        dashboardGeral = DashboardGeral(this@MainActivity2, findViewById(R.id.card_dashboard_geral))
//        dashboardMatematica = DashboardGeral(this@MainActivity, findViewById(R.id.card_dashboard_matematica))
//        dashboardFundamentos = DashboardGeral(this@MainActivity, findViewById(R.id.card_dashboard_fundamentos))
//        dashboardTecnologia = DashboardGeral(this@MainActivity, findViewById(R.id.card_dashboard_tecnologia))

        showDashboard()
    }

    private fun showDashboard() {
        var resultOverall = ResultOverall()
        resultOverall.idUsuario = 4
        resultOverall.nome = "Wilson Fernandes Cordova Junior"
        resultOverall.resultadoGeral = ResultValue(10,80 ,0,60)
        resultOverall.resultadoMatematica = ResultValue(10,20 ,0,30)
        resultOverall.resultadoFundamentoComputacao = ResultValue(10,20 ,0,30)
        resultOverall.resultadoTecnologiaComputacao = ResultValue(20,20 ,0,30)

        adapter = ViewPagerAdapter(
            applicationContext,
            supportFragmentManager,
            resultOverall
        )

        onboardPager!!.adapter = adapter
        onboardPager!!.currentItem = 0
        onboardPager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

                // Change the current position intimation
                for (i in 0 until dotsCount) {
                    dots[i].setImageDrawable(
                        ContextCompat.getDrawable(
                            this@MainActivity2,
                            R.drawable.non_selected_item_dot
                        )
                    )
                }

                dots[position].setImageDrawable(
                    ContextCompat.getDrawable(
                        this@MainActivity2,
                        R.drawable.selected_item_dot
                    )
                )
                val pos = position + 1


                previous_pos = pos

            }

            override fun onPageSelected(position: Int) {

            }
        })
    }

    private var dotsCount = 0
    private lateinit var dots: Array<ImageView>

    // setup the
    private fun setUiPageViewController() {
//        dotsCount = mAdapter.getCount()
//        dots = arrayOfNulls(dotsCount)
//        for (i in 0 until dotsCount) {
//            dots.get(i) = ImageView(this)
//            dots.get(i).setImageDrawable(
//                ContextCompat.getDrawable(
//                    this@MainActivity,
//                    R.drawable.non_selected_item_dot
//                )
//            )
//            val params = LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.WRAP_CONTENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT
//            )
//            params.setMargins(6, 0, 6, 0)
//            pagerIndicator?.addView(dots.get(i), params)
//        }
//        dots.get(0).setImageDrawable(
//            ContextCompat.getDrawable(
//                this@MainActivity,
//                R.drawable.selected_item_dot
//            )
//        )
    }


    fun createActionPageView() {
//        mAdapter = OnBoard_Adapter(this, onBoardItems)
//        onboard_pager!!.adapter = mAdapter
//        onboard_pager!!.currentItem = 0
//        onboard_pager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
//            fun onPageScrolled(
//                position: Int,
//                positionOffset: Float,
//                positionOffsetPixels: Int
//            ) {
//            }
//
//            fun onPageSelected(position: Int) {
//
//                // Change the current position intimation
//                for (i in 0 until dotsCount) {
//                    dots.get(i).setImageDrawable(
//                        ContextCompat.getDrawable(
//                            this@MainActivity,
//                            R.drawable.non_selected_item_dot
//                        )
//                    )
//                }
//                dots.get(position).setImageDrawable(
//                    ContextCompat.getDrawable(
//                        this@MainActivity,
//                        R.drawable.selected_item_dot
//                    )
//                )
//                val pos = position + 1
//                if (pos == dotsCount && previous_pos == dotsCount - 1)
////                    show_animation()
//                else if (pos == dotsCount - 1 && previous_pos == dotsCount)
////                    hide_animation()
//                previous_pos = pos
//            }
//
//            fun onPageScrollStateChanged(state: Int) {}
//        })
    }

    fun setImageProfile() {
        var image = SharedPreferencesUtil.get(this@MainActivity2, KeyPrefs.USER_PHOTO, "")
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

    fun setHeaderInfo() {
        var lname = SharedPreferencesUtil.get(this, KeyPrefs.USER_NAME, "")
        var lemail = SharedPreferencesUtil.get(this, KeyPrefs.USER_EMAIL, "")
        if (!lname.isEmpty() && !lemail.isEmpty()) {
            menuHeader.addProfile(
                ProfileDrawerItem()
                    .withEnabled(true)
                    .withName(lname)
                    .withEmail(lemail), 0
            )
        }
    }

    private fun createHeader(): AccountHeader {
        return AccountHeaderBuilder()
            .withActivity(this)
            .withHeaderBackground(R.drawable.shape_header)
            .withProfileImagesVisible(false)
            .withCompactStyle(true)
            .withSelectionListEnabled(false)
            .build()
    }


    fun menu() {
        menuHeader = createHeader()
        menuDrawer = getDrawer()
        menuDrawer?.setToolbar(this@MainActivity2, mToolbar!!)
        setColor()
    }

    fun getDrawer() : Drawer {
        var type = SharedPreferencesUtil.get(this@MainActivity2, KeyPrefs.USER_TYPE, 1)
        if(EUserType.STUDENT.code == type) {
            return drawerAluno()
        }
        return drawerProfessor()
    }

    fun drawerProfessor() : Drawer {
        return DrawerBuilder()
            .withActivity(this)
            .withDrawerGravity(GravityCompat.START)
            .withAccountHeader(menuHeader)
            .addDrawerItems(
                PrimaryDrawerItem()
                    .withIconTintingEnabled(true)
                    .withName(R.string.prompt_dashboard)
                    .withIdentifier(Constants.DashboardEvent.DASHBOARD)
                    .withIcon(R.drawable.ic_dashboard),

                PrimaryDrawerItem()
                    .withIconTintingEnabled(true)
                    .withName(R.string.prompt_alunos)
                    .withIcon(R.drawable.ic_student)
                    .withIdentifier(Constants.DashboardEvent.ALUNOS),

                PrimaryDrawerItem()
                    .withIconTintingEnabled(true)
                    .withName(R.string.prompt_salas)
                    .withIcon(R.drawable.ic_library_book)
                    .withIdentifier(Constants.DashboardEvent.CRIAR_SALA),

                PrimaryDrawerItem()
                    .withIconTintingEnabled(true)
                    .withName(R.string.prompt_questoes)
                    .withIcon(R.drawable.ic_article)
                    .withIdentifier(Constants.DashboardEvent.BUSCAR_QUESTOES),

                PrimaryDrawerItem()
                    .withIconTintingEnabled(true)
                    .withName(R.string.prompt_suporte)
                    .withIcon(R.drawable.ic_support)
                    .withIdentifier(Constants.DashboardEvent.SUPORTE),
                DividerDrawerItem(),

                PrimaryDrawerItem()
                    .withIconTintingEnabled(true)
                    .withName(R.string.prompt_config)
                    .withIcon(R.drawable.ic_settings)
                    .withIdentifier(Constants.DashboardEvent.CONFIGURACOES),

                PrimaryDrawerItem()
                    .withIconTintingEnabled(true)
                    .withName(R.string.prompt_sair)
                    .withIcon(R.drawable.ic_exit)
                    .withIdentifier(Constants.DashboardEvent.SAIR)

            )
            .withOnDrawerItemClickListener { view, position, drawerItem ->
                onDrawerItemClickListener(view, position, drawerItem)
                false
            }
            .build()
    }

    fun  drawerAluno() : Drawer {
        return DrawerBuilder()
            .withActivity(this)
            .withDrawerGravity(GravityCompat.START)
            .withAccountHeader(menuHeader)
            .addDrawerItems(
                PrimaryDrawerItem()
                    .withIconTintingEnabled(true)
                    .withName(R.string.prompt_dashboard)
                    .withIdentifier(Constants.DashboardEvent.DASHBOARD)
                    .withIcon(R.drawable.ic_dashboard),

                PrimaryDrawerItem()
                    .withIconTintingEnabled(true)
                    .withName(R.string.prompt_simulados)
                    .withIcon(R.drawable.ic_descritption)
                    .withIdentifier(Constants.DashboardEvent.CRIAR_SIMULADO),

                PrimaryDrawerItem()
                    .withIconTintingEnabled(true)
                    .withName(R.string.prompt_salas)
                    .withIcon(R.drawable.ic_library_book)
                    .withIdentifier(Constants.DashboardEvent.SALAS_SIMULADOS),

                PrimaryDrawerItem()
                    .withIconTintingEnabled(true)
                    .withName(R.string.prompt_suporte)
                    .withIcon(R.drawable.ic_support)
                    .withIdentifier(Constants.DashboardEvent.SUPORTE),

                DividerDrawerItem(),

                PrimaryDrawerItem()
                    .withIconTintingEnabled(true)
                    .withName(R.string.prompt_config)
                    .withIcon(R.drawable.ic_settings)
                    .withIdentifier(Constants.DashboardEvent.CONFIGURACOES),

                PrimaryDrawerItem()
                    .withIconTintingEnabled(true)
                    .withName(R.string.prompt_sair)
                    .withIcon(R.drawable.ic_exit)
                    .withIdentifier(Constants.DashboardEvent.SAIR)

            )
            .withOnDrawerItemClickListener { view, position, drawerItem ->
                onDrawerItemClickListener(view, position, drawerItem)
                false
            }
            .build()
    }

    open fun onDrawerItemClickListener(
        view: View?,
        position: Int,
        drawerItem: IDrawerItem<*>
    ) {

        when (drawerItem.identifier) {
            Constants.DashboardEvent.DASHBOARD -> {

            }
            Constants.DashboardEvent.CRIAR_SALA -> {

            }
            Constants.DashboardEvent.BUSCAR_QUESTOES -> {

            }
            Constants.DashboardEvent.SAIR -> {
                User.UserShared.clear(this@MainActivity2)
                val lIntent = Intent(this@MainActivity2, LoginActivity::class.java)
                lIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                lIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                lIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(lIntent)
                finish()
            }
            Constants.DashboardEvent.SUPORTE -> {

            }
            Constants.DashboardEvent.SALAS_SIMULADOS -> {

            }
            Constants.DashboardEvent.CRIAR_SIMULADO -> {

            }
            Constants.DashboardEvent.ALUNOS -> {

            }
            Constants.DashboardEvent.CONFIGURACOES -> {
                val lPerfil = Intent(this, SettingsActivity::class.java)
                startActivity(lPerfil)
            }
        }
    }
}
