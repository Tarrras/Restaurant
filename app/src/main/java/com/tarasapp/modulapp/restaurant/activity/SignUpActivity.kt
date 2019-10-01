package com.tarasapp.modulapp.restaurant.activity

import android.app.ProgressDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.tarasapp.modulapp.restaurant.R
import com.tarasapp.modulapp.restaurant.database.Firebase
import kotlinx.android.synthetic.main.activity_sing_up.*
import android.content.Intent
import android.view.inputmethod.InputMethodManager
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import androidx.fragment.app.FragmentActivity


class SignUpActivity : AppCompatActivity() {

    private val TAG = "AAA"
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_up)

        auth = Firebase.getAuthInstance()

        btn_signup.setOnClickListener { signup() }
        link_login.setOnClickListener {
            // Finish the registration screen and return to the Login activity
            finish()
        }
    }

    fun signup() {

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm!!.hideSoftInputFromWindow(this.currentFocus.windowToken, 0)
        //Log.d(TAG, "Signup")
        if (!validate())
        {
            onSignupFailed()
            return
        }
        btn_signup.isEnabled = false
        val progressDialog = ProgressDialog(this,
            R.style.AppTheme_Dark_Dialog)
        progressDialog.isIndeterminate = true
        progressDialog.setMessage("Creating Account...")
        progressDialog.show()
        val email = input_email.text.toString()
        val password = input_password.text.toString()

        android.os.Handler().postDelayed(
            {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success")
                            val user = auth.currentUser
                            onSignupSuccess()
                            //updateUI(user)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            try {
                                throw task.exception!!
                            } catch (weakPassword: FirebaseAuthWeakPasswordException) {
                                Toast.makeText(baseContext,"Слабый пароль!",Toast.LENGTH_LONG).show()
                            } catch (malformedEmail: FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(baseContext,"Неправильная почта!",Toast.LENGTH_LONG).show()

                            } catch (existEmail: FirebaseAuthUserCollisionException) {
                                Toast.makeText(baseContext,"Пользователь с таким email уже существует!",Toast.LENGTH_LONG).show()
                            }
//                            // if user enters wrong email.
//                            // if user enters wrong password.
//                            Toast.makeText(baseContext, "Authentication failed.",
//                                Toast.LENGTH_SHORT).show()
                            onSignupFailed()
                            //updateUI(null)
                        }
                    }

                progressDialog.dismiss()
            }, 3000)
    }

    fun onSignupSuccess() {
        btn_signup.isEnabled = true
        val intent = Intent()
        intent.putExtra("name", "wow")
        setResult(RESULT_OK, intent)
        finish()
    }

    fun onSignupFailed() {
        //Toast.makeText(baseContext, "Login failed", Toast.LENGTH_LONG).show()
        btn_signup.isEnabled = true
    }

    fun validate():Boolean {
        var valid = true
        //val name = _nameText.getText().toString()
        val email = input_email.text.toString()
        val password = input_password.text.toString()
        val password_secondary = input_password_secondary.text.toString()
//        if (name.isEmpty() || name.length < 3)
//        {
//            _nameText.setError("at least 3 characters")
//            valid = false
//        }
//        else
//        {
//            _nameText.setError(null)
//        }
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            input_email.error = "enter a valid email address"
            valid = false
        }
        else
        {
            input_email.error = null
        }
        if (password.isEmpty() || password.length < 4 || password.length > 100)
        {
            input_password.error = "between 4 and 100 alphanumeric characters"
            valid = false
        }
        else
        {
            input_password.error = null
        }
        if(password_secondary != password){
            input_password_secondary.error = "Wrong password"
            valid = false
        }
        else
        {
            input_password.error = null
        }
        return valid
    }
}
