package br.com.tatisantos.tonomercado.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofiClient {
    private var retrofit: Retrofit? = null

    fun getInstance(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                    .baseUrl("https://tonomercadoapi.herokuapp.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }
        return retrofit!!
    }
}