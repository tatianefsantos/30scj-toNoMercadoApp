package br.com.tatisantos.tonomercado.api

import br.com.tatisantos.tonomercado.model.Usuario
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface UsuarioAPI {

    @GET("/usuario/nome/{nome}/senha/{senha}")
    fun login(@Path("nome") nome: String, @Path("senha") senha: String) : Call<Boolean>

    @POST("/usuario")
    fun salvar(@Body usuario : Usuario) : Call<Void>

}