package com.ifsc.lages.sti.tcc.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import br.edu.ifsc.cancontrol.utilidades.BaseActivty
import com.ifsc.lages.sti.tcc.R
import com.ifsc.lages.sti.tcc.model.ResultOverall
import com.ifsc.lages.sti.tcc.model.ResultValue
import com.ifsc.lages.sti.tcc.model.user.User
import com.ifsc.lages.sti.tcc.props.EUserType
import com.ifsc.lages.sti.tcc.ui.login.LoginActivity
import com.ifsc.lages.sti.tcc.ui.login.viewmodel.LoginViewModel
import com.ifsc.lages.sti.tcc.ui.login.viewmodel.LoginViewModelFactory
import com.ifsc.lages.sti.tcc.ui.main.dashboard.DashboardEnade
import com.ifsc.lages.sti.tcc.ui.main.dashboard.DashboardGeral
import com.ifsc.lages.sti.tcc.ui.main.dashboard.DashboardPoscomp
import com.ifsc.lages.sti.tcc.ui.main.viewmodel.MainViewModel
import com.ifsc.lages.sti.tcc.ui.main.viewmodel.MainViewModelFactory
import com.ifsc.lages.sti.tcc.ui.settings.SettingsActivity
import com.ifsc.lages.sti.tcc.utilidades.*
import com.judemanutd.katexview.KatexView
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem


class MainActivity : BaseActivty() {

    private var imageProfile : ImageView? = null
    private var katex_text : KatexView? = null
    private lateinit var menuHeader: AccountHeader
    private var menuDrawer: Drawer? = null
    private var dashboardGeral : DashboardGeral? = null
    private var dashboardPoscomp : DashboardPoscomp? = null
    private var dashboardEnade : DashboardEnade? = null
    private var viewModel :  MainViewModel? = null


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


        menu()
        setHeaderInfo()
        setImageProfile()

        dashboardGeral = DashboardGeral(this@MainActivity, findViewById(R.id.card_dashboard_geral))
        dashboardPoscomp = DashboardPoscomp(this@MainActivity, findViewById(R.id.card_dashboard_poscomp))
        dashboardEnade = DashboardEnade(this@MainActivity, findViewById(R.id.card_dashboard_enade))
    }

    fun setImageProfile() {
        var image = SharedPreferencesUtil.get(this@MainActivity, KeyPrefs.USER_PHOTO, "")
        if(image.isNotEmpty()) {
            imageProfile?.setImageBitmap(ImageUtil.convertBase64ToBitmap(image))
        }
    }

    override fun onResume() {
        super.onResume()
        callBackOperetion()
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

//        swipeRefreshLayout?.setOnRefreshListener {
//            callBackOperetion()
//        }
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

    fun callBackOperetion() {
        var userId = SharedPreferencesUtil.get(this@MainActivity, KeyPrefs.USER_ID, 0L)
        if (ConnectionUtil.isNetworkAvailable(this@MainActivity)) {
            viewModel!!.loadOverallResultView(userId)
            viewModel!!.loadOverallResultPoscomp(userId)
            viewModel!!.loadOverallResultEnade(userId)
        } else {

        }
    }

    override fun createRestListener() {
        viewModel = ViewModelProvider(this,
            MainViewModelFactory(this@MainActivity)
        ).get(MainViewModel::class.java)
        viewModel!!.loadOverallResultView.observe(this, androidx.lifecycle.Observer {

            if(it.error!!.not()) {
                dashboardGeral!!.showDashboard(it.success!!)
            } else {
                Toast.makeText(this@MainActivity, it.message, Toast.LENGTH_LONG).show()
            }
        })

        viewModel = ViewModelProvider(this,
            MainViewModelFactory(this@MainActivity)
        ).get(MainViewModel::class.java)
        viewModel!!.loadOverallResultPoscompView.observe(this, androidx.lifecycle.Observer {

            if(it.error!!.not()) {
                dashboardPoscomp!!.showDashboard(it.success!!)
            } else {
                Toast.makeText(this@MainActivity, it.message, Toast.LENGTH_LONG).show()
            }
        })

        viewModel = ViewModelProvider(this,
            MainViewModelFactory(this@MainActivity)
        ).get(MainViewModel::class.java)
        viewModel!!.loadOverallResultEnadeView.observe(this, androidx.lifecycle.Observer {

            if(it.error!!.not()) {
                dashboardEnade!!.showDashboard(it.success!!)
            } else {
                Toast.makeText(this@MainActivity, it.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    fun menu() {
        menuHeader = createHeader()
        menuDrawer = getDrawer()
        menuDrawer?.setToolbar(this@MainActivity, mToolbar!!)
        setColor()
    }

    fun getDrawer() : Drawer {
        var type = SharedPreferencesUtil.get(this@MainActivity, KeyPrefs.USER_TYPE, 1)
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
                User.UserShared.clear(this@MainActivity)
                val lIntent = Intent(this@MainActivity, LoginActivity::class.java)
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
