package com.tarasapp.modulapp.restaurant.activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.tarasapp.modulapp.restaurant.R
import com.tarasapp.modulapp.restaurant.models.Order
import com.tarasapp.modulapp.restaurant.models.User
import com.tarasapp.modulapp.restaurant.presenters.BookingActivityPresenter
import com.tarasapp.modulapp.restaurant.views.BookingActivityView
import kotlinx.android.synthetic.main.activity_booking.*


class BookingActivity : MvpAppCompatActivity(), BookingActivityView {

    lateinit var progressDialog: ProgressDialog
    override fun showCourierDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Заказ принят")
        builder.setMessage("Ваш заказ принят! Ожидайте курьера!")
        builder.setPositiveButton("Ок") { dialog, which ->
            val intent = Intent(this, CuisinesActivity::class.java)
            startActivity(intent)
        }
        val dialog = builder.create()
        dialog.show()
    }

    override fun showPickUpDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Заказ принят")
        builder.setMessage("Ваш заказ принят и будет оформлен к вашему приезду")
        builder.setPositiveButton("Ок") { dialog, which ->
            val intent = Intent(this, CuisinesActivity::class.java)
            startActivity(intent)
        }
        val dialog = builder.create()
        dialog.show()
    }

    override fun startLoading() {
        progressDialog = ProgressDialog(this, R.style.AppTheme_Dark_Dialog)
        progressDialog.isIndeterminate = true
        progressDialog.setMessage("Оформление заказа...")
        progressDialog.show()
    }

    override fun endLoading() {
        progressDialog.dismiss()
    }

    override fun showUser(user: User) {
        name_et.text = Editable.Factory.getInstance().newEditable(user.fname + " " + user.lname)
        email_et.text = Editable.Factory.getInstance().newEditable(user.email)
        phone_et.text = Editable.Factory.getInstance().newEditable(user.phone)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onBackPressed() {
        finish()
    }


    @InjectPresenter
    lateinit var presenter: BookingActivityPresenter

    var totalPrice = 0

    var isChecked: Boolean = false
    var ifCourier: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)

        setSupportActionBar(toolbar_booking)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        totalPrice = intent.getIntExtra("price", 0)

        textView8.text = "Общая цена заказа: $totalPrice UAH"
        presenter.loadUser()


        val arrayAdapter =
            ArrayAdapter.createFromResource(this, R.array.order, R.layout.spinner_item)
        arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        spinner.adapter = arrayAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val choose = resources.getStringArray(R.array.order)
                when {
                    choose[position] == "Способ доставки" -> {
                        constraintLayout_delivery.visibility = View.GONE
                        isChecked = false
                    }
                    choose[position] == "Курьер" -> {
                        ifCourier = true
                        isChecked = true
                        constraintLayout_delivery.animate()
                        constraintLayout_delivery.visibility = View.VISIBLE
                    }
                    else -> {
                        ifCourier = false
                        isChecked = true
                        constraintLayout_delivery.visibility = View.GONE
                    }
                }
            }

        }

        button_makeorder.setOnClickListener {
            if (!isChecked) {
                Toast.makeText(applicationContext, "Выберите способ доставки!", Toast.LENGTH_SHORT)
                    .show()
            } else if (!validate()) {
                Toast.makeText(
                    applicationContext,
                    "Неправильно введенны данные!",
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else {
                val order: Order
                if (ifCourier) {
                    order = Order(
                        totalPrice,
                        name_et.text.toString(),
                        email_et.text.toString(),
                        phone_et.text.toString(),
                        "Курьер",
                        street_et.text.toString(),
                        house_et.text.toString(),
                        kvartira_et.text.toString()
                    )
                } else {
                    order = Order(
                        totalPrice,
                        name_et.text.toString(),
                        email_et.text.toString(),
                        phone_et.text.toString(),
                        "Самовывоз"
                    )
                }
                presenter.makeOrder(order)

            }
        }
    }

    fun validate(): Boolean {
        var valid = true
        val name = name_et.text.toString()
        val email = email_et.text.toString()
        val phone = phone_et.text.toString()

        if (name.isEmpty() || name.length < 3) {
            name_et.error = "Как минимум 3 символа"
            valid = false
        } else {
            name_et.error = null
        }
        if (email.isEmpty() || email.length < 3) {
            email_et.error = "Как минимум 3 символа"
            valid = false
        } else {
            email_et.error = null
        }
        if (phone.isEmpty() || phone.length != 10) {
            phone_et.error = "Неправильно введен номер телефона"
            valid = false
        } else {
            phone_et.error = null
        }

        if (ifCourier) {
            val street = street_et.text.toString()
            val house = house_et.text.toString()
            val kvartira = kvartira_et.text.toString()

            if (street.isEmpty()) {
                street_et.error = "Заполните поле!"
                valid = false
            } else {
                street_et.error = null
            }
            if (house.isEmpty()) {
                house_et.error = "Заполните поле!"
                valid = false
            } else {
                house_et.error = null
            }
            if (kvartira.isEmpty()) {
                kvartira_et.error = "Заполните поле! Если вы живёте в частном доме, впишите `0`"
                valid = false
            } else {
                kvartira_et.error = null
            }
        }
        return valid
    }


}
