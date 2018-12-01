package br.com.tatisantos.tonomercado.model

data class Produto(var id: String?,
                   var nome: String,
                   var marca: String?,
                   var quantidade: Int,
                   var setor: String,
                   var descricao: String?)