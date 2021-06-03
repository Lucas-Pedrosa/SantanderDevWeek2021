package com.lucaspedrosa.santanderdevweek.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.lucaspedrosa.santanderdevweek.R

class MenuActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        bindOnView()

        clickListener()
    }

    private fun bindOnView() {
        findViewById<TextView>(R.id.tv_agencia).text = intent.getStringExtra(EXTRA_AGENCIA)
        findViewById<TextView>(R.id.tv_conta_corrente).text = intent.getStringExtra(EXTRA_CONTA)
    }

    private fun clickListener() {
        findViewById<ImageView>(R.id.iv_close).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_close -> finish()
        }
    }
}
