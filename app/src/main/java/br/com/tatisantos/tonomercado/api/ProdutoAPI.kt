package br.com.tatisantos.tonomercado.api

import br.com.tatisantos.tonomercado.model.Produto
import retrofit2.Call
import retrofit2.http.*

interface ProdutoAPI {

    @GET("/produto")
    fun buscarTodos() : Call<List<Produto>>

    @POST("/produto")
    fun salvar(@Body produto : Produto) : Call<Void>

    @PUT("/produto")
    fun atualizar(@Body produto : Produto) : Call<Void>

    //DELETE("/produto")
    @HTTP(method = "DELETE", path = "/produto", hasBody = true)
    fun excluir(@Body produto : Produto) : Call<Void>

    @GET("/marca/{marca}")
    fun buscarPorMarca(@Path("marca") marca: String) : Call<List<Produto>>

}