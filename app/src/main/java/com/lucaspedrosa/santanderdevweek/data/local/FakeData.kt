package com.lucaspedrosa.santanderdevweek.data.local

import com.lucaspedrosa.santanderdevweek.data.Cartao
import com.lucaspedrosa.santanderdevweek.data.Cliente
import com.lucaspedrosa.santanderdevweek.data.Conta

class FakeData {

    fun getLocalData(): Conta {
        val cliente = Cliente("Lucas")
        val cartao = Cartao("8555")

        return Conta("44565569-5", "2556", "2.689,52", "4.100,50", cliente, cartao)
    }
}
