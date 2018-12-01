package br.com.tatisantos.tonomercado.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.com.tatisantos.tonomercado.R
import br.com.tatisantos.tonomercado.api.RetrofiClient
import br.com.tatisantos.tonomercado.api.UsuarioAPI
import br.com.tatisantos.tonomercado.model.Usuario
import kotlinx.android.synthetic.main.activity_user.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        btnCadastrar.setOnClickListener {
            var nomeUsuario: String = txtEmail.text.toString()
            var senha: String = txtPass.text.toString()

            if(nomeUsuario.isNullOrEmpty() || senha.isNullOrEmpty()){
                Toast.makeText(this, R.string.emptyFilds, Toast.LENGTH_SHORT).show()

            } else {
                val api = RetrofiClient.getInstance().create(UsuarioAPI::class.java)
                var usuario: Usuario = Usuario(null, nomeUsuario, senha)

                api.salvar(usuario).enqueue(object : Callback<Void> {
                    override fun onFailure(call: Call<Void>?, t: Throwable?) {
                        Toast.makeText(this@UserActivity, t?.message, Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                        Toast.makeText(this@UserActivity, R.string.registrationSuccessfully, Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@UserActivity, LoginActivity::class.java)
                        startActivity(intent)
                    }
                })
            }
        }
    }
}
