package com.tarasapp.modulapp.restaurant.activity

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.squareup.picasso.Picasso
import com.tarasapp.modulapp.restaurant.R
import com.tarasapp.modulapp.restaurant.models.User
import com.tarasapp.modulapp.restaurant.presenters.EditUserPresenter
import com.tarasapp.modulapp.restaurant.views.EditUserView
import kotlinx.android.synthetic.main.activity_edit_user.*
import java.io.File
import java.util.*


const val idUser = "idUser"
private const val RESULT_LOAD_IMAGE = 1

class EditUserActivity : MvpAppCompatActivity(), EditUserView {


    @InjectPresenter
    lateinit var editUserPresenter: EditUserPresenter

    var photoPath: String = " "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_user)

        editUserPresenter.showUser()

        val idUser = intent.getStringExtra(idUser)

        _image_id_edit.setOnClickListener {
            val i = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )

            startActivityForResult(i, RESULT_LOAD_IMAGE)
        }

        fab_ok.setOnClickListener {
            val names = name_text_edit.text.toString().split(" ")
            val user = User(photoPath,city_text_edit.text.toString(), Date(), email_user_edit.text.toString(), names[0], idUser, names[1], phone_user_edit.text.toString())
            editUserPresenter.updateUser(user)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data)
        {
            val selectedImage = data.data
            val filePathColumn = arrayOf<String>(MediaStore.Images.Media.DATA)
            val cursor = contentResolver.query(selectedImage,
                filePathColumn, null, null, null)
            cursor.moveToFirst()
            val columnIndex = cursor.getColumnIndex(filePathColumn[0])
            val picturePath = cursor.getString(columnIndex)
            photoPath = picturePath
            cursor.close()
            _image_id_edit.setImageBitmap(BitmapFactory.decodeFile(picturePath))
        }
    }

    override fun updateUser() {
        Toast.makeText(applicationContext,"Готово!",Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun showUser(user: User) {
        photoPath = user.avatarUrl
        val file = File(photoPath)
        Picasso.with(applicationContext).load(file).error(R.drawable.ic_image_black_24dp).into(_image_id_edit)
        email_user_edit.text = Editable.Factory.getInstance().newEditable(user.email)
        phone_user_edit.text = Editable.Factory.getInstance().newEditable(user.phone)
        val str = user.fname + " " + user.lname
        name_text_edit.text = Editable.Factory.getInstance().newEditable(str)
        city_text_edit.text = Editable.Factory.getInstance().newEditable(user.city)
    }
}
