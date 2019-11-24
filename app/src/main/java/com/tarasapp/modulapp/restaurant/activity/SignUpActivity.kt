package com.tarasapp.modulapp.restaurant.activity

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.firebase.auth.FirebaseAuth
import com.tarasapp.modulapp.restaurant.R
import com.tarasapp.modulapp.restaurant.database.Firebase
import com.tarasapp.modulapp.restaurant.presenters.SignUpPresenter
import com.tarasapp.modulapp.restaurant.views.SignUpView
import kotlinx.android.synthetic.main.activity_sing_up.*
import java.util.*


class SignUpActivity : MvpAppCompatActivity(), SignUpView {
    override fun showError(message: String) {
        Toast.makeText(applicationContext,message, Toast.LENGTH_LONG).show()
    }

    private val TAG = "AAA"
    private lateinit var auth: FirebaseAuth
    private lateinit var city: String

    @InjectPresenter
    lateinit var presenter: SignUpPresenter

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
        imm.hideSoftInputFromWindow(this.currentFocus.windowToken, 0)
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

        android.os.Handler().postDelayed({
                presenter.createUser(email,password,this)
                progressDialog.dismiss()
            }, 3000)
    }

    override fun onSignupSuccess() {
        btn_signup.isEnabled = true
        val strs = input_name.text.toString()
        val strs1 = input_lname.text.toString()
        presenter.addUser(strs,strs1, Date(),"Днепр",applicationContext)
        val intent = Intent()
        intent.putExtra("name", "wow")
        setResult(RESULT_OK, intent)
        finish()
    }

    override fun onSignupFailed() {
        btn_signup.isEnabled = true
    }

    fun validate():Boolean {
        var valid = true
        val name = input_name.text.toString()
        val lname = input_lname.text.toString()
        val email = input_email.text.toString()
        val password = input_password.text.toString()
        val password_secondary = input_password_secondary.text.toString()
        if (name.isEmpty()|| name.length < 3  )
        {
            input_layout_fname.error = "Введите как минимум 3 символа"
           // input_name.error = "Введите как минимум 3 символа"
            valid = false
        }
        else
        {
            input_layout_fname.error = null
            input_name.error = null
        }
        if (lname.isEmpty() || lname.length < 3 )
        {
            input_layout_lname.error = "Введите как минимум 3 символа"
            //input_lname.error = "Введите как минимум 3 символа"
            valid = false
        }
        else
        {
            input_layout_lname.error = null
            input_name.error = null
        }
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            input_layout_email.error = "Вы ввели неверный Email"
            //input_email.error = "Вы ввели неверный Email"
            valid = false
        }
        else
        {
            input_layout_email.error = null
            input_email.error = null
        }
        if (password.isEmpty() || password.length < 4 || password.length > 100)
        {
            input_layout_password.error = "Длина пароля должны быть как минимум 4 символа"
            //input_password.error = "Длина пароля должны быть как минимум 4 символа"
            valid = false
        }
        else
        {
            input_layout_password.error = null
            input_password.error = null
        }
        if(password_secondary != password){
            input_layout_password_secondary.error = "Пароли не совпадают"
//
//            input_password_secondary.error = "Пароли не совпадают"
            valid = false
        }
        else
        {
            input_layout_password_secondary.error = null
            input_password.error = null
        }
        return valid
    }
}
