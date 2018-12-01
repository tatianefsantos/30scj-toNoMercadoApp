package br.com.tatisantos.tonomercado.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.tatisantos.tonomercado.R
import br.com.tatisantos.tonomercado.model.Produto
import kotlinx.android.synthetic.main.produto.view.*

class ListaProdutosAdapter(private val list: List<Produto>,
                           private val context: Context)
    : RecyclerView.Adapter<ListaProdutosAdapter.MeuViewHolder>() {

    override fun onBindViewHolder(holder: MeuViewHolder, position: Int) {
        val beer = list[position]
        holder?.let {
            it.bindView(beer, context)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeuViewHolder {
        val view = LayoutInflater.from(context)
                .inflate(R.layout.produto, parent, false)
        return MeuViewHolder(view)
    }


    class MeuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(produto: Produto, context: Context) {
            var act = context as ToNoMercado

            itemView.tvNome.text = produto.nome
            itemView.tvDescricao.text = produto.descricao
            itemView.tvMarca.text = produto.marca
            itemView.tvDepartamento.text = produto.setor
            itemView.tvQuantidade.text = " - " + produto.quantidade.toString()

            itemView.setOnClickListener{
                var form = FormFragment()
                form.produto = produto

                act.changeFragment(form)
            }
        }
    }

}
