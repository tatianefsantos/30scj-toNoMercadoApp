package br.com.tatisantos.tonomercado.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.com.tatisantos.tonomercado.R
import br.com.tatisantos.tonomercado.api.ProdutoAPI
import br.com.tatisantos.tonomercado.api.RetrofiClient
import br.com.tatisantos.tonomercado.model.Produto
import kotlinx.android.synthetic.main.erro.*
import kotlinx.android.synthetic.main.fragment_lista_produtos.*
import kotlinx.android.synthetic.main.loading.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProdutoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista_produtos, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inputMarca.clearFocus()
        buscarProdutos()

        btnPesquisar.setOnClickListener{
            val api = RetrofiClient.getInstance().create(ProdutoAPI::class.java)
            api.buscarPorMarca(inputMarca.text.toString())
                    .enqueue(object : Callback<List<Produto>>{
                        override fun onFailure(call: Call<List<Produto>>?, t: Throwable?) {
                            Toast.makeText(context, t?.message, Toast.LENGTH_LONG).show()
                        }
                        override fun onResponse(call: Call<List<Produto>>?, response: Response<List<Produto>>?) {
                            if(response?.isSuccessful==true){
                                setupLista(response?.body())
                            } else {
                                Log.d("ERROR", R.string.erroLoading.toString())
                            }
                        }
            })
        }
    }

    fun buscarProdutos(){
        val api = RetrofiClient
                .getInstance()
                .create(ProdutoAPI::class.java)

        loading.visibility = View.VISIBLE

        api.buscarTodos().enqueue(object : Callback<List<Produto>>{
            override fun onFailure(call: Call<List<Produto>>?, t: Throwable?) {
                loading.visibility = View.GONE
                containerErro.visibility = View.VISIBLE
                tvMensagemErro.setText(t?.message)
            }

            override fun onResponse(call: Call<List<Produto>>?, response: Response<List<Produto>>?) {

                if(response?.isSuccessful==true){
                    loading.visibility = View.GONE
                    setupLista(response?.body())
                } else {
                    loading.visibility = View.GONE
                    containerErro.visibility = View.VISIBLE
                    tvMensagemErro.setText(R.string.erroLoading)
                }
            }
        })
    }

    fun setupLista(produtos: List<Produto>?) {
        produtos.let {
            rvBeers.adapter = ListaProdutosAdapter(produtos!!, requireContext())
            val layoutManager = LinearLayoutManager(context)
            rvBeers.layoutManager = layoutManager
        }
    }
}
