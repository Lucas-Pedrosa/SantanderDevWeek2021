package com.lucaspedrosa.santanderdevweek.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lucaspedrosa.santanderdevweek.R
import com.lucaspedrosa.santanderdevweek.data.Conta

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        buscarCliente()

        clickListener()
    }

    private fun buscarCliente() {
        mainViewModel.buscarContaCliente().observe(this, Observer { result ->
            bindOnView(result)
        })
    }

    private fun bindOnView(conta: Conta) {
        findViewById<TextView>(R.id.tv_agencia).text = getString(R.string.agencia, conta.agencia)
        findViewById<TextView>(R.id.tv_conta_corrente).text = getString(R.string.conta_corrente, conta.numero)
        findViewById<TextView>(R.id.tv_usuario).text = getString(R.string.ola_usuario, conta.cliente.nome)
        findViewById<TextView>(R.id.tv_saldo).text = getString(R.string.saldo, conta.saldo)
        findViewById<TextView>(R.id.tv_saldo_limite_valor).text = getString(R.string.limite, conta.limite)
        findViewById<TextView>(R.id.tv_cartao_final_value).text = conta.cartao.numeroCartao
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflate = menuInflater
        inflate.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_notificacao -> {
                Toast.makeText(this, "Sininho click", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun clickListener() {
        findViewById<ImageView>(R.id.iv_menu).setOnClickListener(this)
        findViewById<TextView>(R.id.tv_ver_extrato).setOnClickListener(this)
        findViewById<CardView>(R.id.mcv_card_pagar).setOnClickListener(this)
        findViewById<CardView>(R.id.mcv_card_transferir).setOnClickListener(this)
        findViewById<CardView>(R.id.mcv_card_recarregar).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_menu -> Toast.makeText(this, "Menu click", Toast.LENGTH_SHORT).show()
            R.id.tv_ver_extrato -> Toast.makeText(this, "Ver extrato click", Toast.LENGTH_SHORT).show()
            R.id.mcv_card_pagar -> Toast.makeText(this, "Pagar click", Toast.LENGTH_SHORT).show()
            R.id.mcv_card_transferir -> Toast.makeText(this, "Transferir click", Toast.LENGTH_SHORT).show()
            R.id.mcv_card_recarregar -> Toast.makeText(this, "Recarregar click", Toast.LENGTH_SHORT).show()
        }
    }
}
