package com.tarasapp.modulapp.restaurant.presenters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.telephony.TelephonyManager
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.tarasapp.modulapp.restaurant.database.Firebase
import com.tarasapp.modulapp.restaurant.models.User
import com.tarasapp.modulapp.restaurant.views.SignUpView
import java.util.*


@InjectViewState
class SignUpPresenter: MvpPresenter<SignUpView>() {
    fun createUser(email: String, password: String,context: Activity){
        val auth = Firebase.getAuthInstance()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(context) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    viewState.onSignupSuccess()
                } else {
                    try {
                        throw task.exception!!
                    } catch (weakPassword: FirebaseAuthWeakPasswordException) {
                        viewState.showError("Слабый пароль!")
                    } catch (malformedEmail: FirebaseAuthInvalidCredentialsException) {
                        viewState.showError("Неправильная почтп")
                    } catch (existEmail: FirebaseAuthUserCollisionException) {
                        viewState.showError("Пользователь с таким email уже существует!!")
                    }
                    viewState.onSignupFailed()
                }
            }
    }

    @SuppressLint("MissingPermission")
    fun addUser(fName: String, lName: String, date: Date, city: String, context: Context){
        val database = Firebase.getInstance()

        val lib = database.reference
        val id =lib.child("users").push().key
        val telephonyManager: TelephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val mMobileNo = telephonyManager.line1Number
        val user = User(fName,lName,mMobileNo)
        id?.let { lib.child("users").child(it).setValue(user) }
    }
}