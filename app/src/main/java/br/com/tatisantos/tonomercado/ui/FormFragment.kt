package br.com.tatisantos.tonomercado.ui

import android.app.Activity.RESULT_CANCELED
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import br.com.tatisantos.tonomercado.R
import br.com.tatisantos.tonomercado.api.ProdutoAPI
import br.com.tatisantos.tonomercado.api.RetrofiClient
import br.com.tatisantos.tonomercado.model.Produto
import kotlinx.android.synthetic.main.fragment_form.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FormFragment : Fragment() {

    var produto: Produto = Produto("","","",0, "","")
    lateinit var setores: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setores = resources.getStringArray(R.array.sectors)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_form, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        inputSetor.adapter = ArrayAdapter(activity, R.layout.support_simple_spinner_dropdown_item, setores)

        btnDelete.visibility = View.GONE

        if (!produto.id.isNullOrEmpty()) {
            inputNome.editText?.setText(produto.nome)
            inputDescricao.editText?.setText(produto.descricao)
            inputQuantidade.editText?.setText(produto.quantidade.toString())
            inputMarca.editText?.setText(produto.marca)
            inputSetor.setSelection(makeSelection(produto.setor))

            btnDelete.visibility = View.VISIBLE
        }

        btnDelete.setOnClickListener{
            val api = RetrofiClient.getInstance().create(ProdutoAPI::class.java)
            var mainContext = context as ToNoMercado

            api.excluir(produto).enqueue(object : Callback<Void>{
                override fun onFailure(call: Call<Void>?, t: Throwable?) {
                    Toast.makeText(context, t?.message, Toast.LENGTH_SHORT).show()
                }
                override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                    if(response?.isSuccessful == true){
                        // back to the list
                        mainContext.changeFragment(ProdutoFragment())
                    } else {
                        Toast.makeText(context, response?.message(), Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }

        btnSalvar.setOnClickListener{
            val api = RetrofiClient.getInstance().create(ProdutoAPI::class.java)
            var mainContext = context as ToNoMercado

            // make some check
            if (inputNome.editText?.text.toString().isNullOrEmpty()){
                Toast.makeText(context, R.string.emptyFilds, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            produto.nome = inputNome.editText?.text.toString()
            produto.descricao = inputDescricao.editText?.text.toString()
            var tmpAlcool = inputQuantidade.editText?.text.toString()
            try {
                produto.quantidade = tmpAlcool.toInt()

            } catch (e: NumberFormatException){
                Toast.makeText(context, R.string.qtdOnlyNumbers, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            produto.marca = inputMarca.editText?.text.toString()
            produto.setor = inputSetor.selectedItem.toString()

            if (produto.id.isNullOrEmpty()){
                // create new beer
                produto.id = null
                api.salvar(produto).enqueue(object : Callback<Void> {
                    override fun onFailure(call: Call<Void>?, t: Throwable?) {
                        Log.e("PRODUTO", t?.message)
                    }

                    override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                        if (response?.isSuccessful == true){
                            limparCampos()
                            mainContext.changeFragment(ProdutoFragment())
                        } else {
                            Toast.makeText(context, "Ops...", Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            } else {
                // update information
                api.atualizar(produto).enqueue(object : Callback<Void>{
                    override fun onFailure(call: Call<Void>?, t: Throwable?) {
                        Log.e("PRODUTO", t?.message)
                    }

                    override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                        if(response?.isSuccessful == true){
                            limparCampos()
                            mainContext.changeFragment(ProdutoFragment())
                        } else {
                            Toast.makeText(context, "Ops...", Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }
        }
    }

    private fun limparCampos(){
        inputNome.editText?.setText("")
        inputDescricao.editText?.setText("")
        inputMarca.editText?.setText("")
        inputQuantidade.editText?.setText("")
        inputSetor.setSelection(0)
    }

    fun makeSelection(str: String)
    : Int {
        var i: Int = 0
        for (item: String in setores) {
            if (item.equals(str)) {
                return i
            }
            i++
        }
        return 0
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)

        var mainContext = context as ToNoMercado

        if(resultCode == RESULT_CANCELED){
            return
        }
    }

}
