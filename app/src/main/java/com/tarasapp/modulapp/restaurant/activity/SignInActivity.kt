package com.tarasapp.modulapp.restaurant.activity

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.tarasapp.modulapp.restaurant.R
import com.tarasapp.modulapp.restaurant.database.Firebase
import kotlinx.android.synthetic.main.activity_sign_in.*


class SignInActivity : AppCompatActivity() {

    private val TAG = "AAA"
    private val REQUEST_SIGNUP = -1
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        auth = Firebase.getAuthInstance()

        input_password.setHintTextColor(Color.WHITE)
        btn_login.setOnClickListener { login() }

        link_signup.setOnClickListener {
            val intent = Intent(applicationContext, SignUpActivity::class.java)
            startActivityForResult(intent, REQUEST_SIGNUP)
        }

    }

    fun login() {

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(this.currentFocus.windowToken, 0)
        if (!validate()) {
            onLoginFailed()
            return
        }

        btn_login.isEnabled = false

        val progressDialog = ProgressDialog(this, R.style.AppTheme_Dark_Dialog)
        progressDialog.isIndeterminate = true
        progressDialog.setMessage("Authenticating...")
        progressDialog.show()
        val email = input_email.text.toString()
        val password = input_password.text.toString()



        android.os.Handler().postDelayed(
            {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            val userId = user?.uid
                            onLoginSuccess()
                        } else {
                            try {
                                throw task.exception!!
                            } catch (invalidEmail: FirebaseAuthInvalidUserException) {
                                Toast.makeText(baseContext,"Не правильно введена почта",Toast.LENGTH_LONG).show()
                            } catch (wrongPassword: FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(baseContext,"Пароль не верный!",Toast.LENGTH_LONG).show()
                            }
                            onLoginFailed()
                        }
                    }
                progressDialog.dismiss()
            }, 3000)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                super.onActivityResult(requestCode, resultCode, data)

                this.finish()
            }
        }
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
        finish()
    }

    fun onLoginSuccess() {
        btn_login.isEnabled = true
        val intent = Intent(applicationContext, CuisinesActivity::class.java)
        startActivity(intent)
    }

    fun onLoginFailed() {
        btn_login.isEnabled = true
    }

    fun validate(): Boolean {
        var valid = true

        val email = input_email.text.toString()
        val password = input_password.text.toString()

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            input_email.error = "enter a valid email address"
            valid = false
        } else {
            input_email.error = null
        }

        if (password.isEmpty() || password.length < 4 ) {
            input_password.error = "between 4 and 100 alphanumeric characters"
            valid = false
        } else {
            input_password.error = null
        }

        return valid
    }
}
