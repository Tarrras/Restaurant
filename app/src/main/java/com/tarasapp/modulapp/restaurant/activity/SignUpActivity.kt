package com.tarasapp.modulapp.restaurant.activity

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.tarasapp.modulapp.restaurant.R
import kotlinx.android.synthetic.main.activity_sing_up.*

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_up)

        btn_signup.setOnClickListener { signup() }
        link_login.setOnClickListener {
            // Finish the registration screen and return to the Login activity
            finish()
        }
    }

    fun signup() {
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
        // TODO: Implement your own signup logic here.
        android.os.Handler().postDelayed(
            {
                onSignupSuccess()
                progressDialog.dismiss()
            }, 3000)
    }

    fun onSignupSuccess() {
        btn_signup.isEnabled = true
        setResult(RESULT_OK, null)
        finish()
    }

    fun onSignupFailed() {
        Toast.makeText(baseContext, "Login failed", Toast.LENGTH_LONG).show()
        btn_signup.isEnabled = true
    }

    fun validate():Boolean {
        var valid = true
        //val name = _nameText.getText().toString()
        val email = input_email.text.toString()
        val password = input_password.text.toString()
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
        if (password.isEmpty() || password.length < 4 || password.length > 10)
        {
            input_password.error = "between 4 and 10 alphanumeric characters"
            valid = false
        }
        else
        {
            input_password.error = null
        }
        return valid
    }
}
