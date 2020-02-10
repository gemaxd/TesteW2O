package com.example.testew2o

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.example.testew2o.auth.KeypairAuthenticator
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.Executor


/**
 * Activity responsavel pelo login
 **/
class LoginActivity : AppCompatActivity() {

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private lateinit var keypairAuthenticator: KeypairAuthenticator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(applicationContext,"Authentication error: $errString", Toast.LENGTH_SHORT).show()
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    openItemScreen()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(applicationContext, "Autenticação falhou",Toast.LENGTH_SHORT).show()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Login Biométrico, W2O")
            .setSubtitle("faça login com a sua digital.")
            .setNegativeButtonText("Usar a senha da conta")
            .build()

        //Listener de abertura da biometria
        biometric_login.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }

        logar.setOnClickListener {
            keypairAuthenticator = KeypairAuthenticator()
            if(keypairAuthenticator.validateLogin(usuario.editText!!.text.toString(), senha.editText!!.text.toString())){
                openItemScreen()
            }else{
                val dialog = AlertDialog.Builder(this)
                dialog.setTitle("Atenção!")
                dialog.setMessage("Usuario ou senha incorretos")
                dialog.create().show()
            }
        }
    }

    fun openItemScreen(){
        val intent = Intent(this, ItemsActivity::class.java)
        startActivity(intent)
    }
}
