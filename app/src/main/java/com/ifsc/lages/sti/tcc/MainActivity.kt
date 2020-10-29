package com.ifsc.lages.sti.tcc

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.ifsc.lages.sti.tcc.model.user.User
import com.ifsc.lages.sti.tcc.ui.LoginActivity
import com.ifsc.lages.sti.tcc.utilidades.ActivityUtil

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.action_exit -> {
                User.UserShared.clear(this@MainActivity)
                ActivityUtil.Builder(applicationContext, LoginActivity::class.java).build()
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
