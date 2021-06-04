package com.lucaspedrosa.santanderdevweek.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lucaspedrosa.santanderdevweek.R
import com.lucaspedrosa.santanderdevweek.data.Conta

const val EXTRA_AGENCIA = "com.lucaspedrosa.santanderdevweek.AGENCIA"
const val EXTRA_CONTA = "com.lucaspedrosa.santanderdevweek.CONTA"

private val CHANNEL_ID = "channel_id_example_01"
private val notificationId = 101

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var conta: Conta

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        buscarCliente()

        clickListener()

        createNotificationChannel()
    }

    private fun buscarCliente() {
        mainViewModel.buscarContaCliente().observe(this, Observer { result ->
            conta = result
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
                // Toast.makeText(this, "Sininho click", Toast.LENGTH_SHORT).show()
                sendNotification()
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
            R.id.iv_menu -> {
                //Toast.makeText(this, "Menu click", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MenuActivity::class.java).apply {
                    putExtra(EXTRA_AGENCIA, getString(R.string.agencia, conta.agencia))
                    putExtra(EXTRA_CONTA, getString(R.string.agencia, conta.numero))
                }
                startActivity(intent)
            }
            R.id.tv_ver_extrato -> Toast.makeText(this, "Ver extrato click", Toast.LENGTH_SHORT).show()
            R.id.mcv_card_pagar -> Toast.makeText(this, "Pagar click", Toast.LENGTH_SHORT).show()
            R.id.mcv_card_transferir -> Toast.makeText(this, "Transferir click", Toast.LENGTH_SHORT).show()
            R.id.mcv_card_recarregar -> Toast.makeText(this, "Recarregar click", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notification Title"
            val descriptionText = "Notification Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent : PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val bitmap = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.logo_santander)
        val bitmapLargeIcon = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.logo_santander_mini)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Example Title")
            .setContentText("Example Description")
            .setLargeIcon(bitmapLargeIcon)
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with (NotificationManagerCompat.from(this)) {
            notify(notificationId, builder.build())
        }
    }
}
