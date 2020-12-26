package com.ifsc.lages.sti.tcc.ui.main

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


class MainActivity : BaseActivty() {

    private var onboardPager: ViewPager? = null
    private var pagerIndicator: LinearLayout? = null
    var previous_pos = 0
    private var adapter: FragmentStatePagerAdapter? = null
    private var dotsCount = 0
    private  var dots : Array<ImageView?> = emptyArray()

    var imageProfile : ImageView? = null
    var katex_text : KatexView? = null
    lateinit var menuHeader: AccountHeader
    var menuDrawer: Drawer? = null
    var dashboardGeral : DashboardGeral? = null
//    var dashboardFundamentos : DashboardGeral? = null
//    var dashboardMatematica : DashboardGeral? = null
//    var dashboardTecnologia : DashboardGeral? = null

    //"O determinante da matriz dada abaixo é $\left( \begin{array}{c}2 \\ 2 \\ -1 \\ 2 \\ 3\end{array} \begin{array}{c}7 \\ 8 \\ 0 \\ 0 \\ 0\end{array} \begin{array}{c}9 \\ 3 \\ 4 \\ 0 \\ 0\end{array} \begin{array}{c}−1 \\ 1 \\ 3 \\ -1 \\ 0\end{array} \begin{array}{c}1 \\ 0 \\ 0 \\ 0 \\ 0\end{array} \right)$"
    //var tex = "Seja f : R → R definida por \$\newline f(x) = \\begin{cases}{x^3 - 2x^2 - 2}, & x > -1 \newline x -3, & x \\ se  \\leq -1\\end{cases} \newline \$  \$ L= \\lim_{a \\rightarrow +  \\infty} f(a_n) = -1 + \\frac{1}{n}\$, é correto afirmar que"
    var tex = "\$\\textbf{var} \\, a,b:integer;\$ \$\\newline\$\n" +
            "\$\\textbf{procedure} P (T1 x:integer; T2 y:integer);\$ \$\\newline\$\n" +
            "\$\\, \\, \\, \\,\$ \$\\textbf{var} \\, z:integer;\$ \$\\newline\$\n" +
            "\$\\, \\, \\, \\, \$ \$\\textbf{begin}\$ \$\\newline\$\n" +
            "\$\\, \\, \\, \\, \\, \\, \$ \$z:=x+a\$; \$\\newline\$\n" +
            "\$\\, \\, \\, \\, \\, \\, \$ \$x:=y+1\$; \$\\newline\$\n" +
            "\$\\, \\, \\, \\, \\, \\, \$ \$y:=y+z\$; \$\\newline\$\n" +
            "\$\\, \\, \\, \\,\$ \$\\textbf{end}\$; \$\\newline\$\n" +
            "\$\\, \\, \\, \\,\$ \$\\textbf{begin}\$ \$\\newline\$\n" +
            "\$\\, \\, \\, \\, \\, \\, \$ \$a:= 2\$; \$\\newline\$\n" +
            "\$\\, \\, \\, \\, \\, \\, \$ \$b:= 3\$; \$\\newline\$\n" +
            "\$\\, \\, \\, \\, \\, \\, \$ \$P (a,b)\$; \$\\newline\$\n" +
            "\$\\, \\, \\, \\,\$ \$\\textbf{writeln}(a,b)\$; \$\\newline\$\n" +
            "\$\\, \\, \\, \\,\$ \$\\textbf{end}\$;"
    //var tex = "Considere a matriz abaixo: \$\\newline A = \\left(\\begin{array}{c}1 & 3 & 1 & 1 & 5 \\\\ -2 & -6 & 0 & 4 & -2 \\\\ 1 & 3 & 2 & 3 & 9\\end{array}\\right) \\newline\$ O posto de A, as dimensões dos dois subespaços: imagem de A e núcleo de A, e uma base para a imagem de A são, respectivamente:"

    var tex2 = "This come from string. You can insert inline formula:" +
            " \\(ax^2 + bx + c = 0\\) " +
            "or displayed formula: $$\\sum_{i=0}^n i^2 = \\frac{(n^2+n)(2n+1)}{6}$$"
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun createKatex() {
        val text = "Pode-se afirmar que o gráfico da função  $$ c = \\pm\\sqrt{a^2 + b^2} $$"
        katex_text = findViewById(R.id.katex_text)
        var teste = tex.replace("$", "$$ ")
        katex_text!!.setText(teste)
    }

    override fun mapComponents() {
        super.mapComponents()
        setDisplayHomeAs(false)

        setTitleToolbar(getString(R.string.title_toolbar_dashboard))
        imageProfile = findViewById(R.id.profile_image)
        onboardPager = findViewById(R.id.pager_introduction) as ViewPager
        pagerIndicator = findViewById(R.id.viewPagerCountDots) as LinearLayout

        createKatex()
        setImageProfile()
        menu()
        setHeaderInfo()

        dashboardGeral = DashboardGeral(this@MainActivity, findViewById(R.id.card_dashboard_geral))
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
                    dots[i]!!.setImageDrawable( ContextCompat.getDrawable(this@MainActivity, R.drawable.non_selected_item_dot))
                }

                dots[position]!!.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, R.drawable.selected_item_dot))

                val pos = position + 1
                previous_pos = pos

            }

            override fun onPageSelected(position: Int) {

            }
        })
        setUiPageViewController()
    }

    private fun setUiPageViewController() {
        dotsCount = adapter!!.count
        dots = arrayOfNulls<ImageView?>(size = 4)
        for (i in 0 until dotsCount) {
            dots[i] = ImageView(this)
            dots[i]!!.setImageDrawable(
                ContextCompat.getDrawable(
                    this@MainActivity,
                    R.drawable.non_selected_item_dot
                )
            )
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(6, 0, 6, 0)
            pagerIndicator?.addView(dots[i], params)
        }
        dots[0]!!.setImageDrawable(
            ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.selected_item_dot
            )
        )
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
