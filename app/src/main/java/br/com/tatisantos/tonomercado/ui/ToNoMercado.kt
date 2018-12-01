package br.com.tatisantos.tonomercado.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import br.com.beering.beerme.ui.SobreFragment
import br.com.tatisantos.tonomercado.R
import kotlinx.android.synthetic.main.activity_to_no_mercado.*

class ToNoMercado : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){
            R.id.navigation_lista ->{
                changeFragment(ProdutoFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_novo -> {
                changeFragment(FormFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_sobre -> {
                changeFragment(SobreFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    fun changeFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.containerFragment, fragment)
        transaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_no_mercado)
        changeFragment(ProdutoFragment())
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}
