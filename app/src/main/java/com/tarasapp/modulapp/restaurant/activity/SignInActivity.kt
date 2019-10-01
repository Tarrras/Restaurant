package com.tarasapp.modulapp.restaurant.activity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.tarasapp.modulapp.restaurant.R
import com.tarasapp.modulapp.restaurant.database.Firebase
import kotlinx.android.synthetic.main.activity_sign_in.*
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Context
import android.graphics.Color
import android.view.inputmethod.InputMethodManager


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
            // Start the Signup activity
            val intent = Intent(applicationContext, SignUpActivity::class.java)
            startActivityForResult(intent, REQUEST_SIGNUP)
        }

    }

    public fun login() {

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm!!.hideSoftInputFromWindow(this.currentFocus.windowToken, 0)
        Log.d(TAG, "Login")

        if (!validate()) {
            onLoginFailed()
            return
        }

        btn_login.isEnabled = false

        val progressDialog = ProgressDialog(this,
            com.tarasapp.modulapp.restaurant.R.style.AppTheme_Dark_Dialog)
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
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success")
                            val user = auth.currentUser
                            onLoginSuccess()
                            //updateUI(user)
                        } else {
                            try {
                                throw task.exception!!
                            } catch (invalidEmail: FirebaseAuthInvalidUserException) {
                                Toast.makeText(baseContext,"Не правильно введена почта",Toast.LENGTH_LONG).show()

                                // TODO: take your actions!
                            } catch (wrongPassword: FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(baseContext,"Пароль не верный!",Toast.LENGTH_LONG).show()

                                // TODO: Take your action
                            }
//                            // if user enters wrong email.
//                            // if user enters wrong password.
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithEmail:failure", task.exception)
//                            Toast.makeText(baseContext, "Authentication failed.",
//                                Toast.LENGTH_SHORT).show()
//                            //updateUI(null)
                            onLoginFailed()
                        }

                        // ...
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

    public override fun onBackPressed() {
        moveTaskToBack(true)
        finish()
    }

    public fun onLoginSuccess() {
        btn_login.isEnabled = true
        val intent = Intent(applicationContext, CuisinesActivity::class.java)
        startActivity(intent)
    }

    public fun onLoginFailed() {
       // Toast.makeText(baseContext, "Login failed", Toast.LENGTH_LONG).show()

        btn_login.isEnabled = true
    }

    public fun validate(): Boolean {
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
